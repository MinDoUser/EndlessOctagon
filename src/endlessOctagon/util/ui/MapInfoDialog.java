package endlessOctagon.util.ui;

import mindustry.ui.dialogs.BaseDialog;
import mindustry.game.EventType;
//import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.blocks.storage.CoreBlock.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.content.*;
import mindustry.ui.*;
import mindustry.Vars;
import mindustry.world.Block;
import mindustry.type.*;
//import mindustry.ui.dialogs.SettingsMenuDialog.*;

import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.*;
import arc.func.*;
import arc.scene.style.*;
import arc.scene.ui.*;
import arc.util.*;

import endlessOctagon.util.ObjectStack;

/**Displays some intresting and nice features for maps like: <br>
* <b>I.</b> Info when a block can be build.
*more soon
*/
public class MapInfoDialog extends BaseDialog{
  public static final BlockChooserDialog DEFAULT_CHOOSER;
  
  static {
    DEFAULT_CHOOSER = new BlockChooserDialog(b->{
      return b.requirements.length > 0 && !b.isHidden() && b.unlocked() && b.isPlaceable();
    });
  }
  
  public Table left, right;
  /** The blocks to be checked over time*/
  public final Seq<CheckElement> checkList = new Seq<>();
  public MapInfoDialog(){
    super("@map.info");
    
    addCloseButton();
    
    DEFAULT_CHOOSER.onChoose((stack)->{
      if(stack.object != null)
      checkList.add(new CheckElement(stack));
      else Log.warn("Object was null!");
    });
    
    //rebuild();
    
    shown(this::rebuild);
    
    Events.run(EventType.Trigger.update, new Runnable(){
      public static final int WAIT = 8;
      
      private int cycle = 0;
      @Override
      public void run(){
        if(cycle%WAIT == 0){
          checkAll();
        }
        if(cycle%WAIT*5==0){
          rebuild(); // Even rare
        }
      }
    });
    
  }
  
  public void checkAll(){
    checkList.each(elem ->{
      if(elem.removed){
        if(checkList.remove(elem))
        rebuild();
      }
      if(!elem.validBuild() && elem.wasValid)elem.showAgain();
      
      if(elem.validBuild() && elem.showInfo()){
        elem.checked = true;
        elem.wasValid = true;
        Vars.ui.showInfoFade("Block [stat]"+elem.target.localizedName+"[]"+Core.bundle.get("buildable"));
      }
    });
  }
  
  public void rebuild(){
    cont.table(t->{
      buildLeftSide(t);
    }).left();
  }
  
  public void buildLeftSide(Table l){
    this.left = l;
    if(Vars.state.isMenu()){
      l.add("Open a map to show"); //This shouldn't happen, but...
      return;
    }
    l.table(topT ->{
        topT.button(Core.bundle.get("newentry"), Icon.add, ()->{
          DEFAULT_CHOOSER.show();
        }).disabled((b)->DEFAULT_CHOOSER.isShown()).size(300, 75);
      }).top();
    left.table(botT ->{
      Table t = new Table();
      if(checkList.isEmpty()){
        t.add("[accent]"+Core.bundle.get("empty"));
      }else{
      checkList.each(elem -> {
        t.add(elem.build());
        t.row();
        t.row();
      });
      }
      botT.pane(t).scrollX(false);
      }).bottom();
  }
  
  public static class CheckElement {
    public CoreBuild core;
    public Block target;
    public int amount;
    /** Whether this CheckElement was removed. Do not change it unless you know what you're doing.*/
    public boolean removed = false;
    public boolean checked = false;
    public boolean wasValid = false;
    public CheckElement(ObjectStack<Block> stack){
      this(stack.object, stack.amount);
    }
    
    public CheckElement(Block target, int amount){
      this(Vars.player.core(),target, amount);
    }
    
    public CheckElement(CoreBuild core, Block target){
      this(core, target, 1);
    }
    
    public CheckElement(CoreBuild core, Block target, int amount){
      this.core = core;
      this.target = target;
      if(amount <= 0) this.amount = 1;
      else this.amount = amount;
    }
    
    public boolean showInfo(){
      return !checked;
    }
    
    public void showAgain(){
      if(!wasValid) return;
      if(validBuild)return; //I still valid, not show again.
      checked = false; 
    }
    
    public boolean validBuild(){
      if(core == null) return false; //True?
      ItemStack[] req = target.requirements;
      return core.items.has(req);
    }
    
    public Table build(){
      if(removed)return new Table(); // Do nothing if removed.
      
      final String BUILD_STRING = Core.bundle.get("stat.canbuild")+":";
      
      Table rTable = new Table();
      rTable.setBackground(Tex.whiteui);
      rTable.setColor(Pal.darkestGray);
      rTable.table(tl->{
        tl.image(target.uiIcon);
        tl.row();
        tl.add(target.localizedName);
      }).left();
      rTable.table(tr->{
        Label buildLabel = new Label("");
        String cStr = validBuild() ?  Core.bundle.get("no","No") : Core.bundle.get("yes","Yes");
        buildLabel.setText(BUILD_STRING+" "+cStr);
        tr.add(buildLabel).padRight(150f);
        tr.button(Icon.cancel, ()->{this.removed = true;});
        if(amount > 1){
          tr.row();
          tr.add("x"+amount).padRight(75f);
        }
      }).right();
      
      return rTable;
    }
  }
  /** A basic BlockChooser. Contains a field for the blocks to be choosed and a slider to choose amount*/
  public static class BlockChooserDialog extends BaseDialog {
    public static final int MAX_PER_ROW = 7;
    
    private final Seq<Cons<ObjectStack<Block>>> chooseListeners = new Seq<>(1);
    /** The current block and the previous block, can both be null...*/
    private ObjectStack<Block> currentBlock, prevBlock = null;
    /** The amount.*/
    private int currentAmount = 1, lastAmount = 1;
    /** The default return value if {@link #currentBlock} is {@code null}*/
    public Block defaultBlock = Blocks.duo;
    
    private Boolf<Block> use;
    public BlockChooserDialog(Boolf<Block> use){
      super("");
      this.use = use;
      
      this.buttons.button("Ok!", Icon.left, ()->{
        this.hide();
        afterChoose();
      });
      this.buttons.button("@cancel", Icon.cancel, ()->{
        this.currentBlock.object = null; //Reset.
        this.currentBlock.amount = 1;
        this.hide();
      });
    }
    public BlockChooserDialog(Boolf<Block> use, Block defaultBlock){
      this(use);
      if(defaultBlock != null)this.defaultBlock = defaultBlock;
    }
    
     public void onChoose(Cons<ObjectStack<Block>> cons){
       if(cons == null)return;
       chooseListeners.add(cons);
     }    
         
     protected void afterChoose(){
       chooseListeners.each(cons -> cons.get(get()));
     }
    /** Returns the current block stack or {@link #defaultBlock} as Stack if it is null.*/
    public ObjectStack<Block> get(){
      if(currentBlock.object == null) return new ObjectStack<Block>(defaultBlock, 1);
      return currentBlock;
    }
    
    public void rebuild(){
      cont.table(top -> {
      buildBlockChooser(top);
      }).top();
      cont.table(bottom -> {
        Slider slider = new Slider(1f,10f,1f,false);
        Label valueLabel = new Label("",Styles.outlineLabel);
        
        slider.changed(()->{
          this.lastAmount = currentAmount;
          this.currentAmount = (int)slider.getValue();
          valueLabel.setText(""+currentAmount);
        });
        
        bottom.add(Core.bundle.get("stat.amount", "Amount")+":");
        bottom.add(valueLabel);
        bottom.row();
        bottom.add(slider);
      }).bottom();
    }
    
    public void buildBlockChooser(Table t){
      // The group so only one is active
      ButtonGroup<ImageButton> group = new ButtonGroup<>();
      Seq<Block> cBlocks = Vars.content.blocks().select(use);
      Table blocks = new Table();
      int i = 1;
      for(var block : cBlocks){
        var icon = new TextureRegionDrawable(block.uiIcon);
        blocks.button(icon, Styles.clearTogglei, ()->{
          prevBlock = currentBlock;
          currentBlock = new ObjectStack<Block>(block, currentAmount);
        }).group(group).size(40);
        if(i%MAX_PER_ROW==0)blocks.row();
        i++;
      }
    }
  }
  
}

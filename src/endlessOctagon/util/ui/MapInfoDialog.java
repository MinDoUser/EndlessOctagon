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
      boolean r = b.requirements.length > 0 && !b.isHidden() && b.isPlaceable() && b.environmentBuildable(); ///Checks whether the block can be build
      return Vars.state.isCampaign() ? r&&b.unlocked():r;
    }, 25);
  }
  
  public Table left, right;
  /** The blocks to be checked over time*/
  public final Seq<CheckElement> checkList = new Seq<>();
  public MapInfoDialog(){
    super("@map.info");
    
    addCloseButton();
    
    DEFAULT_CHOOSER.onChoose((stack)->{
      if(stack != null && stack.object != null){
      checkList.add(new CheckElement(stack));
        //Log.info("Stack:"+stack);
        //checkList.each(e->Log.info(e));
        //Rebuild, otherwhise no changes.
        rebuild();
      }
      else Log.warn("Object was null!");
    });
    
    //rebuild();
    
    shown(this::rebuild);
    
    
    
    Events.run(EventType.Trigger.update, new Runnable(){ // Wait, this is stupid... Everytime there will be a new instance, there will be also a new Event Listener...
      public static final int WAIT = 60; // Think this is enough();
      
      private int cycle = 0;
      @Override
      public void run(){
        cycle++;
        if(cycle%WAIT == 0 && !Vars.state.isMenu()){
          checkAll();
        }
        if(
          (cycle%WAIT == 0) && (Vars.state.isMenu()) && (Core.settings.getBool("clearonmapclose")) && (!checkList.isEmpty())
        ) {
          checkList.clear();
          rebuild(); //Else no changes will be performed.
          //Log.info("Seqence cleared.");
        }
      }
    });
    
    update(this::checkRemove);
    
  }
  
  public void checkRemove(){
    checkList.each(elem -> {
    if(elem.removed){
        if(checkList.remove(elem))
        rebuild();
      }
    });
  }
  
  public void checkAll(){
    checkList.each(elem ->{
      if(!elem.validBuild() && elem.wasValid)elem.showAgain();
      
      if(elem.validBuild() && elem.showInfo()){
        elem.checked = true;
        elem.wasValid = true;
        Vars.ui.showInfoFade("Block [stat]"+elem.target.localizedName+"[] "+Core.bundle.get("buildable", "is buildable"));
      }
    });
  }
  
  public void rebuild(){
    cont.clear();
    cont.table(t->{
      buildLeftSide(t);
    });
  }
  
  public void buildLeftSide(Table l){
    this.left = l;
    if(Vars.state.isMenu()){
      l.add("Open a map to show"); //This shouldn't happen, but...
      return;
    }
    l.table(topT ->{
        topT.button(Core.bundle.get("newentry", "New Entry"), Icon.add, ()->{
          //Log.info("Show");
          DEFAULT_CHOOSER.show();
        }).disabled((b)->DEFAULT_CHOOSER.isShown()).size(300, 75);
      topT.button("@clear", Icon.cancel, ()->{
        checkList.clear();
        rebuild();
      }).width(150);
      });
      l.row();
      l.image().growX().minHeight(10f);
    l.row();
    l.table(botT ->{
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
      });
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
      if(validBuild())return; //If still valid, not show again.
      checked = false; 
    }
    
    public boolean validBuild(){
      if(core == null) return false; //True?
      ItemStack[] req = target.requirements;
      ItemStack[] nR = ItemStack.mult(req, (float)amount);
      return core.items.has(nR);
    }
    
    public Table build(){
      if(removed)return new Table(); // Do nothing if removed.
      
      final String BUILD_STRING = Core.bundle.get("canbuild", "Can Build")+":";
      
      Table rTable = new Table();
      rTable.setBackground(Tex.whiteui);
      rTable.setColor(Pal.darkestGray);
      rTable.table(tl->{
        tl.image(target.uiIcon).size(Vars.iconLarge).left();
        tl.row();
        tl.add(target.localizedName);
      }).width(470).left();
      rTable.table(tr->{
        Label buildLabel = new Label("");
        String cStr = !validBuild() ?  Core.bundle.get("no","No") : Core.bundle.get("yes","Yes");
        buildLabel.setText(BUILD_STRING+" "+cStr);
        tr.add(buildLabel).padRight(150f);
        tr.button(Icon.cancel, ()->{this.removed = true;});
          tr.row();
          tr.add("x"+amount).padRight(75f);
      }).right();
      
      return rTable;
    }
  }
  /** A basic BlockChooser. Contains a field for the blocks to be choosed and a slider to choose amount*/
  public static class BlockChooserDialog extends BaseDialog {
    public static final int MAX_PER_ROW = 7;
    
    private final Seq<Cons<ObjectStack<Block>>> chooseListeners = new Seq<>(1);
    
    public final int MAX_CHOOSE;
    /** The current block and the previous block, can both be null...*/
    private ObjectStack<Block> currentBlock, prevBlock = null;
    /** The amount.*/
    private int currentAmount = 1, lastAmount = 1;
    /** The default return value if {@link #currentBlock} is {@code null}*/
    public Block defaultBlock = Blocks.duo;
    
    private Boolf<Block> use;
    public BlockChooserDialog(Boolf<Block> use, int maxChoose){
      super("@blockchooser");
      this.MAX_CHOOSE = maxChoose;
      this.use = use;
      
      currentBlock = new ObjectStack<>(defaultBlock, 1);
      
      shown(this::rebuild);
      
      this.buttons.button("Ok!", Icon.ok, ()->{
        this.hide();
        afterChoose();
      }).size(180, 75);
      this.buttons.button("@cancel", Icon.cancel, ()->{
        if(this.currentBlock == null) currentBlock = new ObjectStack<>(defaultBlock, 1);
        this.currentBlock.object = defaultBlock; //Reset.
        this.currentBlock.amount = 1;
        this.hide();
      }).size(180, 75);
    }
    public BlockChooserDialog(Boolf<Block> use, Block defaultBlock, int maxChoose){
      this(use,maxChoose);
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
      if(currentBlock == null || currentBlock.object == null) return new ObjectStack<Block>(defaultBlock, 1);
      return currentBlock;
    }
    
    public void rebuild(){
      cont.clear();
      cont.table(top -> {
      buildBlockChooser(top);
      });
      cont.row();
      cont.image().growX().minHeight(10f);
      cont.row();
      cont.table(bottom -> {
        Slider slider = new Slider(1f,(float)MAX_CHOOSE,1f,false);
        Label valueLabel = new Label("1",Styles.outlineLabel);
        
        slider.changed(()->{
          this.lastAmount = currentAmount;
          this.currentAmount = (int)slider.getValue();
          if(currentBlock  != null)currentBlock.amount = currentAmount;
          valueLabel.setText(""+currentAmount);
        });
        
        bottom.add(Core.bundle.get("amount", "Amount")+":");
        bottom.row();
        bottom.add(valueLabel);
        bottom.add(slider);
      });
    }
    
    public void buildBlockChooser(Table t){
      // The group so only one is active
      ButtonGroup<ImageButton> group = new ButtonGroup<>();
      Seq<Block> cBlocks = Vars.content.blocks().select(use);
      Label blockLabel;
      if(!cBlocks.isEmpty())
      blockLabel = new Label(cBlocks.first().localizedName);
      else blockLabel = new Label("No Blocks");
      Table blocks = new Table();
      int i = 1;
      for(var block : cBlocks){
        var icon = new TextureRegionDrawable(block.uiIcon);
        ImageButton button = blocks.button(icon, Styles.clearTogglei, ()->{
          prevBlock = currentBlock;
          currentBlock = new ObjectStack<Block>(block, currentAmount);
          blockLabel.setText(block.localizedName);
        }).group(group).size(50).get(); //Resize them to a good size
        button.resizeImage(Vars.iconLarge);
        if(i%MAX_PER_ROW==0)blocks.row();
        i++;
      }
      blocks.row();
      Table bt = new Table();
      bt.add(blockLabel).left();
      t.add(bt);
      t.row();
      t.pane(blocks).minWidth(450f).scrollX(false);
    }
  }
  
}

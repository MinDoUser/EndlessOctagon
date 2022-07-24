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

import endlessOctagon.util.ObjectStack;

/**Displays some intresting and nice features for maps like: <br>
* <b>I.</b> Info when a block can be build.
*more soon
*/
public class MapInfoDialog extends BaseDialog{
  public Table left, right;
  /** The blocks to be checked over time*/
  public final Seq<CheckElement> checkList = new Seq<>();
  public MapInfoDialog(){
    super("@map.info");
    
    addCloseButton();
    
    rebuild();
    
    shown(this::rebuild);
    
    Events.run(EventType.Trigger.update, new Runnable(){
      public static final int WAIT = 4;
      
      private int cycle = 0;
      @Override
      public void run(){
        if(cycle%WAIT == 0){
          checkAll();
        }
      }
    });
    
  }
  
  public void checkAll(){
    
  }
  
  public void rebuild(){
    cont.table(t->{
      buildLeftSide(t);
    }).left();
  }
  
  public void buildLeftSide(Table l){
    this.left = l;
    l.table(topT ->{
        
      }).top();
    left.table(botT ->{
        
      }).bottom();
  }
  
  public static class CheckElement {
    public CoreBuild core;
    public Block target;
    public int amount;
    public CheckElement(Block target, int amount){
      this(Vars.player.core(),target, amount);
    }
    
    public CheckElement(CoreBuild core, Block target){
      this(core, target, 1);
    }
    
    public CheckElement(CoreBuild core, Block target, int amount){
      this.core = core;
      this.target = target;
      this.amount = amount;
    }
    
    public boolean validBuild(){
      ItemStack[] req = target.requirements;
      return core.items.has(req);
    }
    
    public Table build(){
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
        tr.add(buildLabel).padRight(50f);
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

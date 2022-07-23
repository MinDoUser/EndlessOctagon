package endlessOctagon.util.ui;

import mindustry.ui.dialogs.BaseDialog;
import mindustry.game.EventType.*;
//import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.blocks.storage.CoreBlock.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.content.*;

import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.*;
import arc.struct.*;

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
    public BlockCheckElement(Block target, int amount){
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
      return core.block.items.has(req);
    }
    
    public Table build(){
      Table rTable = new Table();
      rtable.setBackground(Tex.whiteui);
      rTable.setColor(Pal.darkestGray);
      rTable.table(tl->{
        tl.image(target.uiIcon);
        tl.row();
        tl.add(target.localizedName);
      }).left();
      rTable.table(tr->{
        tr.add(Core.bundle.get("stat.canbuild")+":"+validBuild()?Core.bundle.get("yes"):Core.bundle.get("no")).padRight(50f);
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
    private ObjectStack<Block> currentBlock, pevBlock = null;
    /** The amount.*/
    private int currentAmount = 1, lastAmount = 1;
    /** The default return value if {@link #currentBlock} is {@code null}*/
    public Block defaultBlock = Blocks.duo;
    
    private Boolf<Block> use;
    public BlockChooserDialog(Boolf<Block> use){
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
      if(defaultBlock = defaultBlock;
    }
    
     public void onChoose(Cons<ObjectStack<Block>> cons){
       if(cons == null)return;
       chooseListeners.add(cons);
     }    
         
     protected void afterChoose(){
       chooseListeners.each(cons -> cons.get(get());
     }
    /** Returns the current block stack or {@link #defaultBlock} as Stack if it is null.*/
    public ObjectStatck<Block> get(){
      if(currentBlock.object == null) return new ObjectStack<Block>(defaultBlock, 1);
      return currentBlock;
    }
    
    public void rebuild(){
      cont.table(top -> {
      buildBlockChooser(top);
      }).top();
    }
    
    public void buildBlockChooser(Table t){
      Seq<Block> cBlocks = Vars.content.blocks().select(use);
      Table blocks = new Table();
      int current = 1;
      for(var block : cBlocks){
        if(i%MAX_PER_ROW==0)blocks.row();
        i++;
      }
    }
  }
  
}

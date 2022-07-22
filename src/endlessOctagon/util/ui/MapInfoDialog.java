package endlessOctagon.util.ui;

import mindustry.ui.dialogs.BaseDialog;
import mindustry.game.EventType.*;
//import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.blocks.storage.CoreBlock.*;
import mindustry.gen.*;
import mindustry.graphics.*;

import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.*;
import arc.struct.*;

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
      this.(Vars.player.core(),target, amount);
    }
    
    public BlockCheckElement(CoreBuild core, Block target){
      this(core, target, 1);
    }
    
    public BlockCheckElement(CoreBuild core, Block target, int amount){
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
  /** A basic BlockChooser. Conatins a field for the blocks to be choosed and a slider to choose amount*/
  public static class BlockChooserDialog extends BaseDialog {
    public static final int MAX_PER_ROW = 7;
    
    private Boolf<Block> use;
    public BlockChooserDialog(Boolf<Block> use){
      this.use = use;
      
      //this.buttons.button();
    }
    
    public void rebuild(){
      cont.table(top -> {
      rebuildBlockChooser(top);
      }).top();
    }
    
    public void rebuildBlockChooser(Table t){
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

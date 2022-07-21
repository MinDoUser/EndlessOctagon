package endlessOctagon.world.blocks.defense;

import mindustry.world.blocks.defense.*;
import mindustry.world.*;
import mindustry.world.meta.*;

import arc.struct.*;
import arc.func.Boolf;

import static mindustry.Vars.*;

public class ConnectOverdriveProjector extends OverdriveProjector {
  
  public static final Stat connections = new Stat("connections");
  
  public int maxConnections = 5;
  
  public final Seq<Block> linked = new Seq<>();
  
  public static final Boolf<Block> CHECKER = block -> block.canOverdrive;
  
  public ConnectOverdriveProject(String name) {
    super(name);
    this.cofigureable = true;
  }
  
  @Override
  public void setStats(){
    stats.add(connections, maxConnections);
  }
  
    @Override
    public void setBars(){
        super.setBars();
        addBar("boost", (ConnectOverdriveBuild entity) -> new Bar(() -> Core.bundle.format("bar.connected", linked.size(), maxConnections), () -> Pal.accent, () -> entity.linked()));
    }
  
  public void addLink(Block link){
    if(CHECKER.get(link)){
      linked.add(link);
    }
  }
  
  public void removeLink(Block target){
    if(linked.contains(target))linked.remove(target);
  }
  
  public class ConnectOverdriveBuild extends OverdriveBuild {
    public int linked(){
      return linked.size();
    }
  }
}

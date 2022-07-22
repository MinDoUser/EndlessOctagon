package endlessOctagon.world.blocks.defense;

import mindustry.world.blocks.defense.*;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import mindustry.gen.*; //?
import mindustry.Vars;
import mindustry.ui.*;

import arc.struct.*;
import arc.func.Boolf;
import arc.math.geom.*;
import arc.math.*;
import arc.util.*;
import arc.graphics.g2d.*;
import arc.*;

import static mindustry.Vars.*;
//TODO: Move all this linked stuff into a own class/ interface?
public class ConnectOverdriveProjector extends OverdriveProjector {
  
  public static final Stat connections = new Stat("connections");
  
  public int maxConnections = 5;
  
  public static final Boolf<Building> CHECKER = build -> build.block.canOverdrive;
  
  public ConnectOverdriveProjector(String name) {
    super(name);
    this.configurable = true;
    
    this.config(Building.class, (ConnectOverdriveBuild entity, Building build)->entity.checkLink(build));
  }
  
  public static boolean isInRange(float srcx, float srcy, Tile other, float range){
      return Intersector.overlaps(Tmp.cr1.set(srcx, srcy, range), other.getHitbox(Tmp.r1));
  }
  public static boolean isInRange(float srcx, float srcy, Building otherBuild, float range){
    return isInRange(scrx, scry, otherBuild.tile(), range);
  }
  
  @Override
  public void setStats(){
    stats.add(connections, maxConnections);
  }
  
    @Override
    public void setBars(){
        super.setBars();
        addBar("boost", (ConnectOverdriveBuild entity) -> new Bar(() -> Core.bundle.format("bar.connected", entity.linked(), maxConnections), () -> Pal.accent, () -> entity.linked()));
    }
  

  
  public class ConnectOverdriveBuild extends OverdriveBuild {
    public final Seq<Building> linked = new Seq<>();
    
      public void addLink(Building link){
    if(CHECKER.get(link)){
      linked.add(link);
    }
  }
  
  public void removeLink(Building target){
    if(linked.contains(target))linked.remove(target);
  }
  
  public void checkLink(Building build){
    if(!isInRange(this.x, this.y, build, range()));
        if(linked.contains(build))removeLink(build); // Call this first so eventually already linked blocks will be removed before testing the size.
    if(linked() >= ConnectOverdriveProjector.this.maxConnections || ConnectOverdriveProjector.CHECKER.get(build))return;
    else addLink(build);
  }
    
    public int linked(){
      java.util.ArrayList<Building> list = linked.list().trimToSize();
      return list.size();
    }
    
     @Override
        public void drawSelect(){
            Lines.stroke(1f);
          
          for(var other : linked){
            Drawf.square(other.x, other.y, other.block.size * Vars.tilesize / 2 + 1, Pal.accent);
          }
        }
       
       @Override
        public void updateTile(){
            super.smoothEfficiency = Mathf.lerpDelta(super.smoothEfficiency, super.efficiency, 0.08f);
            super.heat = Mathf.lerpDelta(super.heat, super.efficiency > 0 ? 1f : 0f, 0.08f);
            super.charge += super.heat * Time.delta;

            if(hasBoost){
                super.phaseHeat = Mathf.lerpDelta(super.phaseHeat, super.optionalEfficiency, 0.1f);
            }

            if(super.charge >= reload){
                float realRange = range + super.phaseHeat * phaseRangeBoost;

                super.charge = 0f;
                indexer.eachBlock(this, realRange, 
                                  other -> ConnectOverdriveProjector.CHECKER.get(other) && linked.contains(other),
                                  other -> other.applyBoost(super.realBoost(), reload + 1f));
            }

            if(timer(timerUse, useTime) && super.efficiency > 0){
                super.consume();
            }
        }
    
    @Override
    public void onConfigureBuildTapped(Building build){
      if(build == this){
        deselect();
        return; //No if this.
      }
      configure(build);
    }
  }
}

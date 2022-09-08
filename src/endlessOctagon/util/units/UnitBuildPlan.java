package endlessOctagon.util.units;

import mindustry.world.blocks.units.UnitFactory.UnitPlan;
import mindustry.type.*;

public class UnitBuildPlan extends UnitPlan implements Comparable<UnitBuildPlan> {
  /** The priority of this plan. The higher, the more priorized it is. -1 for no priorization */
    public int priority = -1;
  
  public UnitBuildPlan(UnitType unit, float time, ItemStack[] requirements){
    super(unit,time,requirements);
  }
  
  public UnitBuildPlan(UnitPlan other){
    this(other.unit, other.time, other.requirements);
  }
  
  public UnitBuildPlan(){
    super();
  }
  
  @Override
  public int compareTo(UnitBuildPlan other){
    if(other == null || this.hasPriorization() || other.hasPriorization())return 0;
    return Integer.compare(this.priority, other.priority);
  }
  
  public UnitBuildPlan copy(){
    UnitBuildPlan plan = new UnitBuildPlan(unit,time,requirements);
    plan.priority = priority;
    return plan;
  }
  
  public boolean hasPriorization(){
    return priority >= 0;
  }
  
  @Override
  public String toString(){
    return "UnitBuildPlan { unit: "+unit.localizedName+", time: "+time+", requirements: "+requirements+", priority: "+priority+'}';
  }
}

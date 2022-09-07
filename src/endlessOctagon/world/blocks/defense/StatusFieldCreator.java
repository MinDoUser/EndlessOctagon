package endlessOctagon.blocks.defense;

import mindustry.world.*;
import mindustry.logic.*;
import mindustry.gen.*; //?
import mindustry.type.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.effect.*;
import mindustry.world.meta.*;
//import mindustry.annotations.Annotations.*;

import arc.util.*;
import arc.math.*;
import arc.graphics.g2d.*;

import endlessOctagon.content.*;

public class StatusFieldCreator extends Block{
  public float range = 100f;
  /**Douration of a effect in ticks*/
  public static final float duration = 1f*Time.toMinutes;
  /**  Do not set to {@code null}. Use {@link StatusEffects#none} instead*/
  public StatusEffect status = StatusEffects.none;
  /** The effect being played when apply the status*/
  public Effect effect = new WaveEffect(){{
      sizeFrom = 12;
      sizeTo = range;
      interp = Interp.pow3In;
  }};
  
  public StatusFieldCreator(String name){
    super(name);
    solid = true;
    update = true;
    group = BlockGroup.projectors;
    hasPower = true;
    hasItems = true;
    emitLight = true;
    lightRadius = 60f;
    suppressable = false;
    configurable = false;
    envEnabled |= Env.space;
  }
  
  @Override
    public boolean outputsItems(){
        return false;
    }
  
   @Override
    public void setStats(){
        super.setStats();
        stats.add(Stat.range, range / tilesize, StatUnit.blocks);
    }
  
  public class StatusFieldCreatorBuild extends Building implements Ranged {
    @Override
    public float range(){
      return range+2f; //A bit more...
    }
  }

}

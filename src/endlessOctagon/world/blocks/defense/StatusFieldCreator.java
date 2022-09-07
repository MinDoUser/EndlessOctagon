package endlessOctagon.blocks.defense;

import mindustry.world.*;
import mindustry.logic.*;
import mindustry.gen.*; //?
import mindustry.type.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.effect.*;
import mindustry.world.meta.*;
import mindustry.graphics.*;
//import mindustry.annotations.Annotations.*;

import arc.util.*;
import arc.math.*;
import arc.graphics.g2d.*;

import endlessOctagon.content.*;


import static mindustry.Vars.*;

public class StatusFieldCreator extends Block{
  public float range = 100f;
  /** Whether to ignore the efficiency*/
  public boolean ignoreEfficiency = false;
  /**Douration of a effect in ticks*/
  public static final float duration = 1f*Time.toMinutes;
  /**  Do not set to {@code null}. Use {@link StatusEffects#none} instead*/
  public StatusEffect status = StatusEffects.none;
  /** Whether the effect is applied on enemies only. If false, it's onyl applied on units in the same team*/
  public boolean onEnemy = true;
  /** If true, this effect is applied to all nearby units*/
  public boolean all = false;
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
        if(!(status == StatusEffects.none || status.isHidden()))stats.add(EOStats.statusEffect, EOStats.createContentValue(status));
        stats.add(Stat.range, range / tilesize, StatUnit.blocks);
    }
  
  @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);
        
        Drawf.dashCircle(x * tilesize + offset, y * tilesize + offset, range, Pal.accent);
    }
  
  public class StatusFieldCreatorBuild extends Building implements Ranged {
    
    private float timer = 0f;
    
    @Override
    public float range(){
      return range+2f; //A bit more...
    }
    
    @Override
    public void updateTile(){
      boolean b = consumeBuilder.size <= 0 || ignoreEfficiency;
      if(efficiency > 0.0 || b){
      timer += b?Time.delta:Time.delta*efficiency;
      if(timer >= duration){
        timer = 0;
        effect.at(this.x, this.y, status.color);
        
        Units.nearby(null, this.x, this.y, range(), (unit)->{
          if((unit.team == this.team && (!onEnemy || all)) || (unit.team != this.team && (onEnemy || all)) ){
            unit.apply(status, duration);
          }
        });
      }
    }
    }
    
  }

}

package endlessOctagon.bullets;

import mindustry.entities.bullet.*;
import mindustry.entities.*;
import mindustry.content.*;
import arc.*;
import arc.audio.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.*;
//import mindustry.annotations.Annotations.*;
import mindustry.ctype.*;
import mindustry.game.EventType.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;

import static mindustry.Vars.*;

/** A bullet wich boosts nearby ally blocks when destroyed*/
public class OverdriveBulletType extends BasicBulletType implements Ranged{
  public float boost = 1.5f;
  public float range = 75.0f;
  
  public OverdriveBulletType(float speed, float damage, String bulletSprite, float boost){
        super(speed, damage, bulletSprite);
        this.boost = boost;
  }
  public OverdriveBulletType(float speed, float damage, float boost){
        this(speed, damage, "bullet", boost);
  }
  
  @Override
  public float range(){
    return range;
  }
  
  @Override
  public void despawned(Bullet b){
        if(super.despawnHit){
            super.hit(b);
        }
        indexer.eachBlock(this, range()+1, other -> other.block.canOverdrive, other -> other.applyBoost(boost, 100f));
}

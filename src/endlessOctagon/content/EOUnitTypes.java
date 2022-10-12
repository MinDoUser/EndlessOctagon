package endlessOctagon.content;


import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.ai.*;
import mindustry.ai.types.*;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.ammo.*;
import mindustry.type.unit.*;
import mindustry.type.weapons.*;
import mindustry.world.meta.*;
import mindustry.content.*;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;
import static mindustry.Vars.*;

import endlessOctagon.type.*;
import endlessOctagon.entities.*;

public final class EOUnitTypes implements endlessOctagon.util.Loadable {
  
  public static UnitType 
    //Core units
    volatileUnit, //can't name it 'volatile' cuz it's a word of java ...
    //Copters
    array
    ;
  
  @Override
  public void loadObject(){
    loadCoreUnits();
    loadCopters();
    // More methods and calls.
  }
  /** Load core units */
  private void loadCoreUnits(){
    volatileUnit = new UnitType("volatile"){{
            aiController = BuilderAI::new;
      
            constructor = UnitEntity::create;
            isEnemy = false;

            lowAltitude = true;
            flying = true;
            mineSpeed = 6.5f;
            mineTier = 1;
            buildSpeed = 1.1f;
            drag = 0.05f;
            speed = 2.8f;
            rotateSpeed = 15f;
            accel = 0.1f;
            itemCapacity = 45;
            health = 240f;
            engineOffset = 6f;
            hitSize = 8f;
            alwaysUnlocked = true;
            useUnitCap = false;

            weapons.add(new Weapon(){{
                reload = 37f;
                x = 0f;
                y = 1f;
                top = false;
                ejectEffect = Fx.none;

                 bullet = new BasicBulletType(4f, 40){{
                    sprite = "missile-large";
                    smokeEffect = Fx.shootBigSmoke;
                    shootEffect = Fx.pointHit;
                    width = 5f;
                    height = 7f;
                    lifetime = 42f;
                    hitSize = 4f;
                    hitColor = backColor = trailColor = Color.valueOf("ffd37f");
                    frontColor = Color.white;
                    trailWidth = 1.7f;
                    trailLength = 5;
                    despawnEffect = hitEffect = Fx.hitBulletColor;
                    buildingDamageMultiplier = 0.25f;
                }};
            }});
        }};
  }
  
  private void loadCopters(){
    array = new EOUnitType("array"){{
      flying = true;
      lowAltitude = true;
      engineSize = -1f;
      mineSpeed = 2.57f;
      mineTier = 2;
      speed = 2.2f;
      rotateSpeed = 2.8f;
      hitSize = 9.5f;
      itemCapacity = 55;
      health = 725f;
      
      targetFlags = new BlockFlag[]{BlockFlag.factory, BlockFlag.generator, null};
      ammoType = new ItemAmmoType(EOItems.iron);
      
      hasRotors = true;
      addRotors(
        new UnitRotor(name){{
            speed = 27f;
            x=0f;
            y = -1.2f;
        }}
      );
      
      weapons.add(new Weapon("eo-missile-launcher"){{
        reload = 40f;
        x = 7f;
        rotate = true;
        shake = 1f;
        shoot.shots = 4;
        shoot.shotDelay = 3.5f;
        inaccuracy = 5f;
        velocityRnd = 0.2f;
        shootSound = Sounds.missile;

        bullet = new MissileBulletType(3f, 22){{
              width = 8f;
              height = 8f;
              shrinkY = 0f;
              drag = -0.003f;
              homingRange = 65f;
              keepVelocity = false;
              splashDamageRadius = 20f;
              splashDamage = 22f;
              lifetime = 50f;
              trailColor = Pal.unitBack;
              backColor = Pal.unitBack;
              frontColor = Pal.unitFront;
              hitEffect = Fx.blastExplosion;
              despawnEffect = Fx.blastExplosion;
              weaveScale = 6f;
              weaveMag = 1f;
       }};
      }});
    }};
  }
  
}

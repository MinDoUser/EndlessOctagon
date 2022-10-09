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

public final class EOUnitTypes implements endlessOctagon.util.Loadable {
  
  public static UnitType 
    //Core units
    volatileUnit //can't name it 'volatile' cuz it's a word of java ...
    ;
  
  @Override
  public void loadObject(){
    loadCoreUnits();
    // More methods and calls.
  }
  /** Load core units */
  private void loadCoreUnits(){
    volatileUnit = new UnitType("volatile"){{
            aiController = BuilderAI::new;
            isEnemy = false;

            lowAltitude = true;
            flying = true;
            mineSpeed = 6.5f;
            mineTier = 1;
            buildSpeed = 1.1f;
            drag = 0.05f;
            speed = 3.1f;
            rotateSpeed = 15f;
            accel = 0.1f;
            itemCapacity = 45;
            health = 240f;
            engineOffset = 6f;
            hitSize = 8f;
            alwaysUnlocked = true;

            weapons.add(new Weapon(){{
                reload = 17f;
                x = 0f;
                y = 1f;
                top = false;
                ejectEffect = Fx.none;

                 bullet = new BasicBulletType(4f, 40){{
                    sprite = "missile-large";
                    smokeEffect = Fx.shootBigSmoke;
                    shootEffect = Fx.shootBigColor;
                    width = 5f;
                    height = 7f;
                    lifetime = 42f;
                    hitSize = 4f;
                    hitColor = backColor = trailColor = Color.valueOf("ffd37f");
                    frontColor = Color.white;
                    trailWidth = 1.7f;
                    trailLength = 5;
                    despawnEffect = hitEffect = Fx.hitBulletColor;
                }};
            }});
        }};
  }
  
}

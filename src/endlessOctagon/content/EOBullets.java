package endlessOctagon.content;

import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.content.*;
import mindustry.graphics.*;
import mindustry.entities.*;

import arc.graphics.Color;
  
import endlessOctagon.bullets.*;
  
public class EOBullets implements endlessOctagon.util.Loadable {
  public static  BulletType 
    defOverdrive, shockBullet, thunderBullet;
  @Override
  public void loadObject(){
    defOverdrive = new OverdriveBulletType(2.7f, 16, 2.0f){{
            width = 8f;
            height = 10f;
            shrinkY = 0f;
            lifetime = 62f;
            backColor = Pal.accent;
            frontColor = Pal.accent;
            despawnEffect = Fx.absorb;
            collidesAir = true;
    }};
    Effect sfe = new MultiEffect(EOFx.blueWaveSmall, Fx.steam);
    shockBullet = new BasicBulletType(4.5f, 65){{
                width = 12f;
                hitSize = 7f;
                height = 20f;
                shootEffect = sfe;
                smokeEffect = Fx.shootSmallSmoke;
                ammoMultiplier = 2;
                pierceCap = 2;
                pierce = false;
                pierceBuilding = false;
                hitColor = backColor = trailColor = EOPal.darkBlue;
                frontColor = Color.white;
                trailWidth = 2.1f;
                trailLength = 10;
                hitEffect = despawnEffect = Fx.hitBulletColor;
                buildingDamageMultiplier = 0.85f;
            }};
    thunderBullet = new BasicBulletType(3.75f, 125){{
      sprite = "large-orb";
      width = 17f;
      height = 21f;
      hitSize = 8f;
      shootEffect = new MultiEffect(Fx.shootTitan, Fx.explosion, new WaveEffect(){{
                    colorFrom = colorTo = EOPal.lightBlue;
                    lifetime = 14f;
                    sizeTo = 20f;
                    strokeFrom = 3f;
                    strokeTo = 0.3f;
                }});
      smokeEffect = Fx.shootBigSmoke;
      ammoMultiplier = 1;
       hitColor = backColor = trailColor = EOPal.darkBlue;
       frontColor = Color.white;
       trailWidth = 1.75f;
       trailLength = 7;
       hitEffect = Fx.hitBulletColor;
       buildingDamageMultiplier = 1.75f; //Lol, why not?
      despawnEffect = new MultiEffect(Fx.hitBulletColor, new WaveEffect(){{
                    sizeTo = 30f;
                    colorFrom = EOPal.darkBlue;
                    colorTo = EOPal.lightBlue;
                    lifetime = 16f;
      }});
      trailEffect = EOFx.blueWaveSmall;
      trailInterval = 6f;
      intervalBullet = new LightningBulletType(){{
                    damage = 33;
                    collidesAir = false;
                    ammoMultiplier = 1f;
                    lightningColor = EOPal.lightBlue;
                    lightningLength = 14;
                    lightningLengthRand = 10;

                    buildingDamageMultiplier = 0.75f;

                    lightningType = new BulletType(0.0001f, 0f){{
                        lifetime = Fx.lightning.lifetime;
                        hitEffect = Fx.hitLancer;
                        despawnEffect = Fx.none;
                        status = StatusEffects.shocked;
                        statusDuration = 10f;
                        hittable = false;
                        lightColor = Color.white;
                        buildingDamageMultiplier = 0.75f;
                    }};
                }};
      bulletInterval = 8f;
    }};
  }
}

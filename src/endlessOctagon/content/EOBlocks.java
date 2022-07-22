package endlessOctagon.content;

import arc.graphics.*;
import arc.math.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.ctype.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.*;
import mindustry.world.blocks.campaign.*;
import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.legacy.*;
import mindustry.world.blocks.liquid.*;
import mindustry.world.blocks.logic.*;
import mindustry.world.blocks.payloads.*;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.sandbox.*;
import mindustry.world.blocks.storage.*;
import mindustry.world.blocks.units.*;
import mindustry.world.consumers.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import mindustry.content.*;
import static mindustry.content.Fx.*;

import static mindustry.type.ItemStack.*;

import endlessOctagon.world.blocks.crafter.*;
import endlessOctagon.world.blocks.defense.*;
import endlessOctagon.bullets.*;

public final class EOBlocks implements endlessOctagon.util.Loadable{
  public static final Seq<Block> eravirBlocks = new Seq<>();
  
  public static Block 
    //Turrets
    crowl, shock,
    //Walls
    oxaWall, oxaWallLarge,
    //Crafter
    oxaForge,
  //Effect
    connectProjector,
    //Environment
    sump,
    wallOreGenium;
  
  private float calculateTurretRange(mindustry.entities.bullet.BulletType b){
    return (b.lifetime * b.speed)+2f;
  }
  
  public static boolean addAndCheck(Block b){
    boolean is = isEravirBlock(b);
    if(is){
      eravirBlocks.add(b);
      Log.info("Block "+b.name+" was added");
    }else{
      Log.info("Block "+b.name+" was sadly not added.");
    }
    return is;
  } 
  
  public static boolean isEravirBlock(Block b){
    boolean[] bools = new boolean[b.requirements.length];
    int i = 0;
    if(b.requirements.length >= 0)return false; //Not add this crap without requirements.
    for(var stack : b.requirements){
      Item item = stack.item;
      if(EOItems.eravirItems.contains(item))bools[i] = true;
         else bools[i] = false;
      i++;
    }
    for(boolean bool : bools){
      if(!bool)return false;
    }
    return true;
  }
   
  @Override
  public final void loadObject(){
    //Start of turrets
    final OverdriveBulletType CROWL_BULLET = new OverdriveBulletType(2.7f, 16, 2.0f){{
            width = 8f;
            height = 10f;
            shrinkY = 0f;
            lifetime = 62f;
            backColor = Pal.accent;
            frontColor = Pal.accent;
            despawnEffect = Fx.absorb;
            collidesAir = true;
    }};
    crowl = new ItemTurret("crowl"){{
            requirements(Category.turret, with(Items.copper, 135, Items.lead, 50, EOItems.oxa, 125, Items.titanium, 15));
            ammo(
                EOItems.oxa, CROWL_BULLET
            );

            //spread = 2f;
            //shots = 1;
            //alternate = true;
            reload = 45f;
            //restitution = 0.03f;
            range = calculateTurretRange(CROWL_BULLET);//(CROWL_BULLET.lifetime * CROWL_BULLET.speed) + 2f;
            shootCone = 15f;
            ammoUseEffect = Fx.smeltsmoke;
            health = 350;
            inaccuracy = 3f;
            rotateSpeed = 10f;
            size = 2;
      
            coolant = consumeCoolant(0.25f);

            limitRange();
        }};
    Effect sfe = new MultiEffect(EOFx.blueWaveSmall, Fx.steam);
    //TODO move them all into a own class...
    final BulletType SHOCK_BULLET = new BasicBulletType(4.5f, 65){{
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
    
            shock = new PowerTurret("shock"){{
            requirements(Category.turret, with(Items.titanium, 60, Items.silicon, 170, Items.metaglass, 25));
            range = calculateTurretRange(SHOCK_BULLET);

            shoot.firstShotDelay = 40f;

            recoil = 2f;
            reload = 80f;
            shake = 2f;
            shootEffect = EOFx.blueWaveSmall;
            smokeEffect = Fx.none;
            //heatColor = Color.red;
            size = 2;
            scaledHealth = 280;
            targetAir = true;
            moveWhileCharging = false;
            accurateDelay = false;
            shootSound = Sounds.laser;
            coolant = consumeCoolant(0.22f);

            consumePower(2.5f);

            shootType = SHOCK_BULLET;
              
             drawer = new DrawTurret("reinforced-"){{
                parts.add(new RegionPart("-barrel"){{
                    progress = PartProgress.recoil;
                    mirror = false;
                    under = true;
                    moveX = 0f;
                    moveY = -3f;
                    moveRot = 0f;
                }});
             }};
        }};
    //End of turrets
    //Start of defense
    connectProjector =
      new ConnectOverdriveProjector("connectProjector){{
          requirements(Category.effect, with(Items.titanium, 170, Items.metaglass, 275, Items.silicon, 50, EOItems.oxa, 130));
            consumePower(4.75f);
            size = 3;
            range = 180;
            consumeItem(EOItems.oxa).boost();                                           
    }};
    //End of defense
    // Start of walls
    
    final int wallHealthMultiplier = 4;
    
    oxaWall = new Wall("oxa-wall"){{
            requirements(Category.defense, with(EOItems.oxa, 8));
            health = 100 * wallHealthMultiplier;
        }};

        oxaWallLarge = new Wall("oxa-wall-large"){{
            requirements(Category.defense, ItemStack.mult(oxaWall.requirements, 4));
            health = 100 * wallHealthMultiplier * 4;
            size = 2;
        }};
    //end of walls
    //Start of environment
    sump = new Floor("sump"){{
            speedMultiplier = 0.47f;
            variants = 3;
            status = StatusEffects.muddy;
            statusDuration = 30f;
            attributes.set(Attribute.oil, 0.56f);
            attributes.set(Attribute.water, 1.95f);
        }};
    wallOreGenium = new OreBlock("ore-wall-genium", EOItems.genium){{
            wallOre = true;
        }};
    //End of environment
    //Start of Crafters
    oxaForge = new SolarCrafter("oxa-forge"){{
            requirements(Category.crafting, with(Items.metaglass, 25, Items.silicon, 45, Items.lead, 120, Items.graphite, 20));
            
            craftEffect = smeltsmoke;
            outputItem = new ItemStack(EOItems.oxa, 2);
            craftTime = 34f;
            itemCapacity = 10;
            size = 2;
            hasItems = false;
            hasLiquids = true;
            hasPower = false;
            
            drawer = new DrawMulti(new DrawDefault(), new DrawFlame(Color.valueOf("ffef99")));

            consumeItems(with(Items.coal, 1, Items.sand, 2));
        }};
  }
}

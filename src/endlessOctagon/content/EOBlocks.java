package endlessOctagon.content;

import arc.graphics.*;
import arc.math.*;
import arc.struct.*;
import arc.graphics.g2d.*;
import mindustry.*;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.*;
import mindustry.entities.part.DrawPart.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.unit.*;
import mindustry.world.*;
import mindustry.world.blocks.*;
import mindustry.world.blocks.campaign.*;
import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.heat.*;
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
import endlessOctagon.world.blocks.storage.*;
import endlessOctagon.world.blocks.units.*;
import endlessOctagon.bullets.*;
import endlessOctagon.util.units.*;

public final class EOBlocks implements endlessOctagon.util.Loadable{
  public static final Seq<Block> eravirBlocks = new Seq<>();
  
  public static Block 
    //Turrets
    crowl, shock, thunder,
  
    shadow,
  
    mars,
    //Walls
    oxaWall, oxaWallLarge,
    ironWall, ironWallLarge,
    //Crafter
    oxaForge, siliconFuser,
  
  //Drills
  alphaDrill, //Soon: betaDrill, gammaDrill, omegaDrill(?),
  
  steamCondenser,
  //Storage
  chest,
  coreElement,
  //Liquid
  plateConduit, plateRouter,
  //units
  T1UnitGate,
  //Effect
    connectProjector,
    //Environment
    sump,
    lightStone,
    ironStone,
    greenStone,
    lightStoneWall,
    ironStoneWall,
    greenStoneWall,
    wallOreGenium;
  
  private float calculateTurretRange(mindustry.entities.bullet.BulletType b){
    return (b.lifetime * b.speed)+2f;
  }
   
  @Override
  public final void loadObject(){
    //Start of turrets
    
    crowl = new ItemTurret("crowl"){{
            requirements(Category.turret, with(Items.copper, 135, Items.lead, 50, EOItems.oxa, 125, Items.titanium, 15));
            ammo(
                EOItems.oxa, EOBullets.defOverdrive
            );

            //spread = 2f;
            //shots = 1;
            //alternate = true;
            reload = 45f;
            //restitution = 0.03f;
            range = calculateTurretRange(EOBullets.defOverdrive);
            shootCone = 15f;
            ammoUseEffect = Fx.smeltsmoke;
            health = 350;
            inaccuracy = 3f;
            rotateSpeed = 10f;
            size = 2;
      
            coolant = consumeCoolant(0.25f);
      
            drawer = new DrawTurret("reinforced-");

            limitRange();
        }};
    
            shock = new PowerTurret("shock"){{
            requirements(Category.turret, with(Items.titanium, 60, Items.silicon, 170, Items.metaglass, 25));
            range = calculateTurretRange(EOBullets.shockBullet);

            shoot.firstShotDelay = 40f;

            recoil = 2f;
            reload = 80f;
            shake = 0f;
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

            shootType = EOBullets.shockBullet;
              
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
    
    thunder = new ItemTurret("thunder"){{
            requirements(Category.turret, with(Items.surgeAlloy, 200, Items.metaglass, 420, Items.silicon, 700, Items.titanium, 1150, Items.thorium, 400));

            size = 3;
      
            ammo(
            Items.surgeAlloy, EOBullets.thunderBullet 
            );
      
            heatColor = EOPal.lightBlue;
      
            range = calculateTurretRange(EOBullets.thunderBullet); 
      
            reload = 180f;
            recoil = 1.3f;

            shoot.shots = 5;
            shoot.shotDelay = 7f;

            minWarmup = 0.75f;
            coolantMultiplier = 3f;

            shake = 1.1f;
            ammoPerShot = 2;
      
            coolant = consumeCoolant(0.26f);
      
            drawer = new DrawTurret("reinforced-");

            consumePower(4.5f);
    }};
    
    //TODO: Remove or change stats.
    shadow = new ItemTurret("shadow"){{
            requirements(Category.turret, with(Items.silicon, 135, EOItems.iron, 50, Items.graphite, 75));
            ammo(
                EOItems.iron, new BasicBulletType(6f, 85){{
                width = 12f;
                hitSize = 7f;
                height = 17f;
                shootEffect = Fx.colorSparkBig;
                smokeEffect = Fx.shootBigSmoke;
                ammoMultiplier = 2;
                pierceCap = 2;
                pierce = false;
                pierceBuilding = false;
                hitColor = backColor = trailColor = EOItems.iron.color;
                frontColor = Color.white;
                trailWidth = 1.8f;
                trailLength = 6;
                hitEffect = despawnEffect = Fx.hitBulletColor;
                buildingDamageMultiplier = 0.80f;
            }},
              
              EOItems.multiSteel, new BasicBulletType(5.2f, 122){{
                width = 12f;
                hitSize = 7f;
                height = 17f;
                shootEffect = Fx.colorSparkBig;
                smokeEffect = Fx.shootBigSmoke;
                ammoMultiplier = 2;
                hitColor = backColor = trailColor = EOItems.multiSteel.color;
                frontColor = Color.white;
                trailWidth = 2.4f;
                trailLength = 6;
                hitEffect = despawnEffect = Fx.hitBulletColor;
                buildingDamageMultiplier = 0.80f;
                reloadMultiplier = 1.2f;
                rangeChange = 2.5f*8f;
            }}
        );

            coolantMultiplier = 6f;
            //shootSound = Sounds.shootAlt; Sounds do not exist?

            shake = 1.1f;
            ammoPerShot = 2;
            drawer = new DrawTurret("reinforced-");
            shootY = 1;
            outlineColor = Pal.darkOutline;
            size = 2;
            envEnabled |= Env.space;
            reload = 40f;
            recoil = 2f;
            range = 170;
            shootCone = 3f;
            scaledHealth = 180;
            rotateSpeed = 1.5f;
            alwaysUnlocked = true;

            coolant = consume(new ConsumeLiquid(Liquids.water, 15f / 60f));
            limitRange();
        }};
    
    mars = new ItemTurret("mars"){{
            requirements(Category.turret, with(Items.silicon, 175, EOItems.iron, 125, Items.graphite, 175));
            ammo(
                EOItems.iron, new BasicBulletType(6f, 85){{
                width = 12f;
                hitSize = 7f;
                height = 17f;
                shootEffect = Fx.colorSparkBig;
                smokeEffect = Fx.shootBigSmoke;
                ammoMultiplier = 2;
                pierce = false;
                pierceBuilding = false;
                hitColor = backColor = trailColor = EOItems.iron.color;
                frontColor = Color.white;
                trailWidth = 1.8f;
                trailLength = 5;
                hitEffect = despawnEffect = Fx.hitBulletColor;
                buildingDamageMultiplier = 0.80f;
            }},
              
              EOItems.multiSteel, new BasicBulletType(5.2f, 122){{
                width = 12f;
                hitSize = 7f;
                height = 17f;
                shootEffect = Fx.colorSparkBig;
                smokeEffect = Fx.shootBigSmoke;
                ammoMultiplier = 2;
                hitColor = backColor = trailColor = EOItems.multiSteel.color;
                frontColor = Color.white;
                trailWidth = 2.4f;
                trailLength = 6;
                hitEffect = despawnEffect = Fx.hitBulletColor;
                buildingDamageMultiplier = 0.80f;
                reloadMultiplier = 1.2f;
                rangeChange = 1.8f*8f;
            }}
        );

            coolantMultiplier = 6f;
            //shootSound = Sounds.shootAlt; Sounds do not exist?

            shake = 1.1f;
            ammoPerShot = 3;
            drawer = new DrawTurret("reinforced-");
            shootY = 1;
            outlineColor = Pal.darkOutline;
            size = 3;
            envEnabled |= Env.space;
            reload = 42f;
            recoil = 2f;
            range = 170;
            shootCone = 3f;
            scaledHealth = 185;
            rotateSpeed = 1.5f;
            alwaysUnlocked = true;

            coolant = consume(new ConsumeLiquid(Liquids.water, 7.5f / 60f));
            limitRange();
        }};
    //End of turrets
    //Start of defense
    //=== === === === === === === === === ===
    //TODO Fix this type and check it later.
    if(false)connectProjector =
      new ConnectOverdriveProjector("connectProjector"){{
          requirements(Category.effect, with(Items.titanium, 170, Items.metaglass, 275, Items.silicon, 50, EOItems.oxa, 130));
            consumePower(4.75f);
            size = 3;
            range = 180;
            consumeItem(EOItems.oxa).boost();    // 0_0?                                        
    }};
    //=== === === === === === === === === ===
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
    
    
    ironWall = new Wall("iron-wall"){{
            requirements(Category.defense, with(EOItems.iron, 8));
            health = 100 * wallHealthMultiplier;
        }};

    ironWallLarge = new Wall("iron-wall-large"){{
            requirements(Category.defense, ItemStack.mult(ironWall.requirements, 4));
            health = 100 * wallHealthMultiplier * 4;
            size = 2;
        }};
    //end of walls
    //Liquids
      plateConduit = new Conduit("plate-conduit"){{
            requirements(Category.liquid, with(EOItems.multiSteel, 2));
            health = 85;
        }};
    
      plateRouter = new LiquidRouter("plate-router"){{
            requirements(Category.liquid, with(EOItems.iron, 4, EOItems.multiSteel, 4));
            liquidCapacity = 30f;
        }};
    //End of liquids
    //Start of environment
    sump = new Floor("sump"){{
            speedMultiplier = 0.47f;
            variants = 3;
            status = StatusEffects.muddy;
            statusDuration = 30f;
            attributes.set(Attribute.oil, 0.56f);
            attributes.set(Attribute.water, 1.95f);
        }};
    
    greenStone = new Floor("green-stone"){{
            speedMultiplier = 0.95f;
            attributes.set(Attribute.water, 0.22f); // A bit wet?
            attributes.set(Attribute.oil, 0.37f);
        }};
    
    lightStone = new Floor("light-stone"); //Nothing.
    
    ironStone = new Floor("iron-stone"){{
            attributes.set(Attribute.water, -0.05f);
        }};
    
    greenStoneWall = new StaticWall("green-wall"){{
            greenStone.asFloor().wall = this;
            attributes.set(Attribute.sand, 1.3f);
        }};
    lightStoneWall = new StaticWall("light-stone-wall"){{
            lightStone.asFloor().wall = this;
            attributes.set(Attribute.sand, 0.45f);
        }};
    ironStoneWall = new StaticWall("iron-stone-wall"){{
            ironStone.asFloor().wall = this;
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
    
    siliconFuser = new GenericCrafter("silicon-fuser"){{
            requirements(Category.crafting, with(EOItems.multiSteel, 70, Items.graphite, 44));
            craftEffect = Fx.none;
            outputItem = new ItemStack(Items.silicon, 4);
            craftTime = 50f;
            size = 3;
            hasPower = true;
            hasLiquids = false;
            envEnabled |= Env.space | Env.underwater;
            envDisabled = Env.none;
            itemCapacity = 32;
            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawParticles(){{
              color = Color.valueOf("acaeb7");
              particleSize = 1.5f;
            }}, new DrawDefault());
            fogRadius = 4;
            researchCost = with(EOItems.iron, 170, Items.graphite, 60);
            //ambientSound = Sounds.smelter;
            //ambientSoundVolume = 0.12f;

            consumeItems(with(Items.graphite, 2, Items.sand, 5));
            consumePower(1.8f);
        }};
    
      //End of crafter
    //Start of drills
        alphaDrill = new Drill("alpha-drill"){{
            requirements(Category.production, with(Items.silicon, 100, EOItems.multiSteel, 75, EOItems.iron, 175));
            drillTime = 270;
            size = 3;
            hasPower = true;
            tier = 3;
            liquidBoostIntensity = 1f;
            updateEffect = new MultiEffect(Fx.pulverizeMedium, Fx.ventSteam);
            drillEffect = Fx.mineBig;
          squareSprite = false;

            consumePower(0.75f);
            consumeLiquid(Liquids.water, 0.09f);
        }};
    
        steamCondenser = new AttributeCrafter("steam-condenser"){{
            requirements(Category.production, with(Items.graphite, 20, EOItems.iron, 75));
            attribute = Attribute.steam;
            minEfficiency = 9f - 0.0001f;
            baseEfficiency = 0f;
            displayEfficiency = false;
            craftEffect = Fx.turbinegenerate;
            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidRegion(Liquids.water), new DrawDefault());
            craftTime = 170f;
            size = 3;
            ambientSoundVolume = 0.06f;
            hasLiquids = true;
            boostScale = 1f / 9f;
            outputLiquid = new LiquidStack(Liquids.water, 34f / 60f);
            liquidCapacity = 60f;
          squareSprite = false;
        }};
    //End of drills
    //Start of storage
    chest = new Chest("chest"){{
            requirements(Category.effect, with(Items.silicon, 150, EOItems.iron, 200));
            size = 2;
            itemCapacity = 250;
            scaledHealth = 55;
        }};
    
    coreElement = new CoreBlock("core-element"){{
            //TODO update cost
            requirements(Category.effect, with(Items.graphite, 1100, Items.silicon, 800, EOItems.iron, 1400, EOItems.multiSteel, 900));
            
            squareSprite = false;
            isFirstTier = true;
            unitType = EOUnitTypes.volatileUnit;
            health = 4750;
            itemCapacity = 3500;
            size = 4;
            armor = 5f;
            alwaysUnlocked = true;
            incinerateNonBuildable = true;

            buildCostMultiplier = 0.7f;

            unitCapModifier = 22;
            researchCostMultiplier = 0.07f;
        }
                                                
        @Override
        public TextureRegion[] icons(){
          return new TextureRegion[]{arc.Core.atlas.find("eo-block-core-element-ui")};
        }
                                               
     };

    //End of storage
    //Start of units
    T1UnitGate = new UnitGate("t1-unit-gate"){{
      requirements(Category.effect, with(Items.silicon, 150, EOItems.iron, 200, EOItems.multiSteel, 100, Items.graphite, 225));
      //TODO: Replace with mod units and mod items later
      plans = Seq.with(
                new UnitBuildPlan(UnitTypes.dagger, 60f * 40f, with(Items.silicon, 20, Items.graphite, 35)),
                new UnitBuildPlan(UnitTypes.crawler, 60f * 20f, with(Items.silicon, 10, Items.metaglass, 30))
            );
      size = 3;
      
      consumePower(0.75f);
      
      scaledHealth = 75;
    }};
    //End of units
  }
  //Utils.
    
  public static boolean addAndCheck(Block b){
    boolean is = isEravirBlock(b);
    if(is){
      eravirBlocks.add(b);
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
}

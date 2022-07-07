package endlessOctagon.content;

import arc.graphics.*;
import arc.math.*;
import arc.struct.*;
import mindustry.*;
import mindustry.ctype.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
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
import endlessOctagon.bullets.*;

public final class EOBlocks implements ContentList{
  public static Block 
    //Turrets
    crowl,
    //Walls
    oxaWall, oxaWallLarge,
    //Crafter
    oxaForge,
    //Environment
    sump;
   
  @Override
  public final void load(){
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

            spread = 2f;
            shots = 1;
            alternate = true;
            reloadTime = 45f;
            restitution = 0.03f;
            range = CROWL_BULLET.lifetime * CROWL_BULLET.speed;
            shootCone = 15f;
            ammoUseEffect = Fx.smeltsmoke;
            health = 350;
            inaccuracy = 3f;
            rotateSpeed = 10f;
            size = 2;

            limitRange();
        }};
    //End of turrets
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
            
            drawer = new DrawSmelter();

            consumes.items(with(Items.silicon, 1, Items.copper, 2));
        }};
  }
}

package endlessOctagon.content;


import arc.func.*;
import arc.graphics.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.game.*;
import mindustry.graphics.*;
import mindustry.graphics.g3d.*;
import mindustry.graphics.g3d.PlanetGrid.*;
import mindustry.maps.planet.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import mindustry.Vars;

import mindustry.content.*;

import endlessOctagon.type.*;
import endlessOctagon.content.*;
import endlessOctagon.maps.planet.*;

public final class EOPlanets implements endlessOctagon.util.Loadable{
  public static Planet 
  esag, eravir; //Eravir, finally!
  @Override
  public void loadObject(){
  esag  = new ExtendedPlanet("esag", Planets.serpulo, 1.25f, 4){{
            accessible = false;
    
            hiddenItems.addAll(Vars.content.items());
    
            //orbitRadius = 4;

            meshLoader = () -> new SunMesh(
                this, 4,
                5, 0.3, 1.7, 1.2, 1,
                1.1f,
                Color.valueOf("dadada"),
                Color.valueOf("afa29e"),
                Color.valueOf("8d8280"),
                Color.valueOf("747474"),
                Color.valueOf("8c8c8c")
            );
        }};
    
    eravir = new ExtendedPlanet("eravir", Planets.sun, 1f, 2){{
            generator = new EravirPlanetGenerator();
            meshLoader = () -> new HexMesh(this, 6);
            cloudMeshLoader = () -> new MultiMesh(
                new HexSkyMesh(this, 2, 0.15f, 0.14f, 5, Color.valueOf("8da7b1").a(0.75f), 2, 0.42f, 1f, 0.43f),
                new HexSkyMesh(this, 3, 0.6f, 0.15f, 5, Color.valueOf("decaba").a(0.75f), 2, 0.42f, 1.2f, 0.45f)
            );
            accessible = false;
            alwaysUnlocked = true;
            landCloudColor = Color.valueOf("8da7b1");
            atmosphereColor = Color.valueOf("859e97");
            //defaultEnv = Env.scorching | Env.terrestrial; //TODO: Check the envs
            startSector = 10;
            atmosphereRadIn = 0.02f;
            atmosphereRadOut = 0.3f;
            orbitSpacing = 12f;
            totalRadius += 2.6f;
            lightSrcTo = 0.74f;
            lightDstFrom = 0.24f;
            clearSectorOnLose = true;
            defaultCore = EOBlocks.coreElement;
            iconColor = Color.valueOf("b2cfd2");
            hiddenItems.addAll(Vars.content.items()).removeAll(EOItems.eravirItems);
      
            updateLighting = true;

            ruleSetter = r -> {
                r.waveTeam = Team.crux;
                //r.placeRangeCheck = false; //TODO true or false?
                r.attributes.set(Attribute.heat, 0.8f);
                r.showSpawns = true;
                r.fog = true;
                r.staticFog = true;
                r.lighting = true;
                r.coreDestroyClear = true;
                r.onlyDepositCore = false; //TODO not sure
            };

            unlockedOnLand.add(EOBlocks.coreElement);
        }};
  }
}

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

public final class EOPlanets implements endlessOctagon.util.Loadable{
  public static Planet 
    //eravir, ;
  esag;
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
  }
}

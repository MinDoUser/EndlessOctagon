package endlessOctagon;

import mindustry.content.*;
import mindustry.type.*;
import mindustry.Vars;

import arc.struct.*;
import arc.Core;

import endlessOctagon.type.ExtendedPlanet;

import static endlessOctagon.type.ExtendedPlanet.createPlanetStats;

public final class Overrides {
  public static final void overridePlanets(){
    Seq<Planet> planets = Vars.content.planets();//{Planets.serpulo, Planets.eriker, Planets.tantros};
    planets.each(planet->{
      if(!(planet.uiIcon == Core.atlas.find("error"))) planet.uiIcon = Core.atlas.find("eo-esag");
      if(planet instanceof ExtendedPlanet)return; //Not create them doubled
      createPlanetStats(planet);
    });
  }
}

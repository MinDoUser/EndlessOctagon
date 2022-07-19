package endlessOctagon;

import mindustry.content.*;
import mindustry.type.*;
import mindustry.Vars;

import arc.struct.*;

import static endlessOctagon.type.ExtendedPlanet.createPlanetStats;

public final class Overrides {
  public static final void overridePlanets(){
    Seq<Planet> planets = Vars.content.planets();//{Planets.serpulo, Planets.eriker, Planets.tantros};
    planets.each(planet->{
      createPlanetStats(planet);
    });
  }
}
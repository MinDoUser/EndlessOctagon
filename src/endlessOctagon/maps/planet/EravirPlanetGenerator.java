package endlessOctagon.maps.planet;

import arc.graphics.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import arc.util.noise.*;
import mindustry.ai.*;
import mindustry.content.*;
import mindustry.game.*;
import mindustry.maps.generators.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;


import endlessOctagon.content.*;

public class EravirPlanetGenerator extends PlanetGenerator {
  
  protected Block[] blocks = {EOBlocks.lightStone, EOBlocks.ironStone, EOBlocks.lightStone}; //TODO More environment
  
  @Override
  public Color getColor(Vec3 location){
    return Color.white; //TODO fix when having blocks.
  }
  
}

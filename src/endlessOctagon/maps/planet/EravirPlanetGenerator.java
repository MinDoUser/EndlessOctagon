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

public class EravirPlanetGenerator extends BlankPlanetGenerator {
  protected static final byte def = 0, cold 1; //Wich mind of blocks
  
  protected Block[][] blocks = {
                                {EOBlocks.lightStone, EOBlocks.ironStone, Blocks.dirt, Blocks.mud},
                                {EOBlocks.lightStone, Blocks.ice, Blocks.redIce}
                                }; //TODO More environment
  
  //TODO: Better color choose, but I think it's Ok for now.
  
  @Override
  public Color getColor(Vec3 location){
    Block block = getBlock(location);
    
    return Tmp.c1.set(block.mapColor).a(1f - block.albedo); 
  }
  
  @Override
    public float getHeight(Vec3 position){
        return Mathf.pow(rawHeight(position), 2.3f);
    }
  
  //From erekir generator. //TODO: Understand and upgrade
  float rawHeight(Vec3 position){
        return Simplex.noise3d(seed, octaves, persistence, 1f/heightScl, 10f + position.x, 10f + position.y, 10f + position.z);
    }

    float rawTemp(Vec3 position){
        return position.dst(0, 0, 1)*2.2f - Simplex.noise3d(seed, 8, 0.54f, 1.4f, 10f + position.x, 10f + position.y, 10f + position.z) * 2.9f;
    }

    Block getBlock(Vec3 position){
        float ice = rawTemp(position);
        Tmp.v32.set(position);

        float height = rawHeight(position);
        Tmp.v31.set(position);
        height *= 1.2f;
        height = Mathf.clamp(height);
        byte ind = (ice < 0.25 ? cold:def);
        Block result = blocks[ind][Mathf.clamp((int)(height * blocks[ind].length), 0, blocks[ind].length - 1)];

        /*if(ice < 0.3 + Math.abs(Ridged.noise3d(seed + crystalSeed, position.x + 4f, position.y + 8f, position.z + 1f, crystalOct, crystalScl)) * crystalMag){
            return Blocks.crystallineStone;
        }*/

        if(ice < 0.6){
            if(result == Blocks.dirt || result == Blocks.mud){
                return Blocks.dirt;
            }
          if(result == EOBlocks.sump || result == EOBlocks.greenStone){
            retunr EOBlocks.sump;
          }
        }

        position = Tmp.v32;


        /*if(ice > redThresh){
            result = Blocks.redStone;
        }else if(ice > redThresh - 0.4f){
            //TODO this may increase the amount of regolith, but it's too obvious a transition.
            result = Blocks.regolith;
        }*/

        return result;
    }

  
}

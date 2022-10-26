package endlessOctagon.world.draw;

import mindustry.world.draw.*;
import mindustry.world.*;
import mindustry.gen.*;
import mindustry.*;

import arc.math.*;
import arc.Core;
import arc.graphics.g2d.*;
//TODO: Still not done!
public class DrawTextureBar extends DrawBlock {
  public TextureRegion bar;
  /** Whether to draw a vertical/horizontal bar or not*/
  public boolean vertical = true, horizontal = false;
  /** Used for not full-square sprites to avoid bar is drawen outside the block.*/
  public float startX = 0, startY = 0;
  
  @Override
  public void draw(Building build){
    if(!vertical && !horizontal)return; //If nothing should be drawn, we don't need to do something.
    Draw.alpha(build.warmup());
    float rotation;
    float dx,dy;
    if(vertical){
      rotation = 90;
      dx = build.x;
      dy = build.y;
    }else if(horizontal){
      rotation = 0;
      dx = build.x + Mathf.sin(build.totalProgress(), 6f, Vars.tilesize / 3f * build.block.size)+startX;
      dy = build.y;
    }else{
      dx = build.x;
      dy = build.y;
      rotation = 0;
    }
    
    
    
    Draw.rect(bar,dx,dy, rotation);
    
    Draw.reset();
  }
  
  @Override
  public void load(Block block){
    this.bar = Core.atlas.find(block.name+"-bar");
  }
  
}

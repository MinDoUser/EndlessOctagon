package endlessOctagon.world.blocks.storage;

import mindustry.world.blocks.storage.*;
import mindustry.gen.*;
import mindustry.type.*;

import mindustry.Vars;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
/** A storage block wich can only keep one kind of item*/
public class Chest extends StorageBlock {
  public Chest(String name){
    super(name);
    
    coreMerge = false; //We don't want this connets to a core...
  }
  
  public class ChestBuild extends StorageBuild {
        @Override
        public boolean acceptItem(Building source, Item item){
            boolean hasCap = items.get(item) < itemCapacity;
            boolean same = items.first() == null || items.first() == item;
            return same && hasCap;
        }
    
        @Override
        public void drawSelect(){
          Item item;
            if((item = items.first()) != null){
                float dx = x - size * Vars.tilesize/2f, dy = y + size * Vars.tilesize/2f, s = Vars.iconSmall / 4f;
                Draw.mixcol(Color.darkGray, 1f);
                Draw.rect(item.fullIcon, dx, dy - 1, s, s);
                Draw.reset();
                Draw.rect(item.fullIcon, dx, dy, s, s);
            }
        }
  }
}

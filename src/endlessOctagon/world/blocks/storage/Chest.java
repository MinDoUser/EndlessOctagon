package endlessOctagon.world.blocks.storage;

import mindustry.world.blocks.storage.*;
import mindustry.gen.*;
import mindustry.type.*;
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
            if(items.first() != null){
                //TODO: Draw current item.
            }
        }
  }
}

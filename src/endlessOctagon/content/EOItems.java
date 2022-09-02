package endlessOctagon.content;

import arc.graphics.*;
import arc.struct.*;

import mindustry.ctype.*;
import mindustry.type.*;
import mindustry.content.*;

public class EOItems implements endlessOctagon.util.Loadable{
  public static Seq<Item> eravirItems = new Seq<>();
  /** Items color*/
  public static final Color 
    OXA_COLOR = Color.valueOf("eab678"), 
    GENIUM_COLOR = Color.valueOf("eab678"),
  
    C_IRON = Color.valueOf("b364cd"),
    C_MULTI_STEEL = Color.valueOf("daddea");
  
    public static Item 
      oxa, genium,
  
    iron, multiSteel;
  public final void loadObject(){
    oxa = new Item("oxa", OXA_COLOR){{
            hardness = 1;
            cost = 0.75f;
            alwaysUnlocked = false;
        }};
     genium = new Item("genium", GENIUM_COLOR){{
            hardness = 1;
            cost = 1.2f;
            alwaysUnlocked = false;
        }};
    
    
    iron = new Item("iron", C_IRON){{
      hardness = 1;
      cost = 0.88f;
      alwaysUnlocked = true;
    }};
    
    multiSteel = new Item("multi-steel", C_MULTI_STEEL){{
      hardness = 1;
      cost = 1.25f;
      alwaysUnlocked = false;
    }};
    
    eravirItems.addAll(iron, multiSteel, Items.graphite, Items.silicon);
  }
}

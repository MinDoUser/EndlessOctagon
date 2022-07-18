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
    GENIUM_COLOR = Color.valueOf("eab678");
  
    public static Item 
      oxa, genium;
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
    
    
    eravirItems.addAll(oxa, genium, Items.metaglass, Items.graphite, Items.thorium, Items.silicon, Items.titanium);
  }
}

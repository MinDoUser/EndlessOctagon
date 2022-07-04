package endlessOctagon.content;

import arc.graphics.*;
import mindustry.ctype.*;
import mindustry.type.*;

public class EOItems implements ContentList{
  /** Items color*/
  public static final Color 
    OXA_COLOR = Color.valueOf("eab678");
    public static Item oxa;
  public final void load(){
    oxa = new Item("oxa", OXA_COLOR){{
            hardness = 1;
            cost = 0.75f;
            alwaysUnlocked = false;
        }};
  }
}

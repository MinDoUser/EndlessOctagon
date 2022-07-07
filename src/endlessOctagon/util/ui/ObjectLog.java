package endlessOctagon.util.ui;

import arc.scene.ui.layout.*;
import arc.graphics.g2d.TextureRegion;
import arc.util.*;

import mindustry.ctype.*;
import mindustry.ui.*;

/** A Log containing a Object, it's name and it's image*/
public class ObjectLog{
  private UnlockableContent object;
  private TextureRegion icon;
  private String name, description, type;
  
  public static final String
    NOTHING = "",
    CHANGED = "Changed: ",
    FIXED = "Fixed: ",
    REMOVED = "Removed: ";
  /**
  @param uo The object
  @param description The description
  @param type The type of change. You can use: NOTHING, CHANGED, FIXED, REMOVED or anything else (not recommended)
  */
  public ObjectLog(UnlockableContent uo, String description, String type){
    object = uo;
    icon = uo.fullIcon;
    name = uo.localizedName;
    description = description;
    type = type;
  }
  
  public Table buildTable(){
    Table t = new Table();
    Table topT = new Table();
    topT.add("[lightgrey]"+type);
    topT.image(icon);
    topT.add("[stat]"+name);
    topT.row();
    topT.image().growX();
    t.add(topT).align(Align.center);
    t.add("[white]"+description);
    return t;
  }
}

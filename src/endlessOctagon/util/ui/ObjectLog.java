package endlessOctagon.util.ui;

import arc.scene.ui.layout.*;
import arc.graphics.g2d.TextureRegion;
import arc.util.*;
import arc.scene.event.*;
import arc.scene.ui.*;

import mindustry.ctype.*;
import mindustry.gen.*;
import mindustry.ui.*;
import mindustry.Vars;

/** A Log containing a Object, it's name and it's image*/
public class ObjectLog{
  public UnlockableContent object;
  public TextureRegion icon;
  public String name, description, type;
  
  public static final String
    NOTHING = "",
    CHANGED = "Changed: ",
    FIXED = "Fixed: ",
    REMOVED = "Removed: ",
    NEW = "New: ";
  /**
  @param uo The object
  */
  public ObjectLog(UnlockableContent uc){
    object = uc;
    icon = uc.fullIcon;
    name = uc.localizedName;
  }
  
  public Table buildTable(){
    Table t = new Table();
    Table topT = new Table();
    topT.add("[lightgrey]"+type);
    Image image = new Image(object.uiIcon).setScaling(Scaling.fit);
    image.addListener(new HandCursorListener());
    topT.add(image);
    image.clicked(()->{
      Vars.ui.content.show(object);
    });
    topT.add("[stat]"+name);
    topT.row();
    t.add(topT).align(Align.center);
    t.row();
    t.add("[white]"+description);
    return t;
  }
}

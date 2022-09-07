package endlessOctagon.content;

//import endlessOctagon.util.*;

import mindustry.world.meta.*;
import mindustry.ctype.*;

import arc.scene.ui.*;
import arc.util.*;
import arc.scene.event.*;

public final class EOStats {
  public static final Stat 
    special = new Stat("special");
  
  public static StatValue createContentValue(UnlockableContent content){
    if(content == null || content.isHidden()){
      return table -> {table.add("No valid UnlockableContent: \""+content+"\"");};
    }
    return table -> {
      Label name = new Label(content.localizedName);
      Image image = new Image(content.uiIcon).setScaling(Scaling.fit);
      image.addListener(new HandCursorListener());
      image.clicked(()->{
      Vars.ui.content.show(content);
    });
      table.add(image);
      table.add(name);
    };
  }
}

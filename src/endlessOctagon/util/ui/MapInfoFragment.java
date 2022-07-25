package endlessOctagon.util.ui;

import endlessOctagon.EOVars;
import endlessOctagon.util.*;

import mindustry.Vars;
import mindustry.gen.*;

import arc.scene.*;

public class MapInfoFragment implements Fragment {
  public void build(Group parent){
    parent.fill(e -> {
      e.bottom().left(); 
      e.button("Map Info", Icon.info, ()->{
        EOVars.mapInfo.show();
      }).size(100, 64);
    });
  }
}

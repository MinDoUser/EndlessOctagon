package endlessOctagon;

import arc.util.*;

import mindustry.mod.*;

import endlessOctagon.content.*;

public class EndlessOctagonMod extends Mod{

    public EndlessOctagonMod(){
      Log.info("Mod constructor loaded...");
    }
  
  @Override
  public void loadContent(){
        new EOItems().load();
      new EOBlocks().load();
  }
}

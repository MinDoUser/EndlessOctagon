package endlessOctagon;

import arc.util.*;
import arc.scene.ui.layout.Table;

import mindustry.mod.*;
import mindustry.ui.dialogs.*;

import endlessOctagon.content.*;
import endlessOctagon.util.ui.*;
import endlessOctagon.content.*;

public class EndlessOctagonMod extends Mod{
    
    public static final ObjectLog[] CURRENT_LOGS = {
        new ObjectLog(EOItems.oxa, "A new item", "New:")
    };

    public EndlessOctagonMod(){
      Log.info("Mod constructor loaded...");
        Events.on(ClientLoadEvent.class, e -> {
            //show dialog upon startup
            Time.runTask(10f, () -> {
                startScreen();
            });
        });
    }
  
    public void startScreen(){
        BaseDialog dialog = new BaseDialog("");
        Table cont = dialog.cont;
        for(ObjectLog log : CURRENT_LOGS){
            cont.add(log.buildTable());
            cont.row();
            cont.row();
        }
        cont.button("I know", dialog::hide).size(100f, 50f);
    }
    
  @Override
  public void loadContent(){
        new EOItems().load();
      new EOBlocks().load();
  }
}

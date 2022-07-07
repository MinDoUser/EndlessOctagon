package endlessOctagon;

import arc.util.*;
import arc.*;
import arc.scene.ui.layout.Table;

import mindustry.mod.*;
import mindustry.ui.dialogs.*;
import mindustry.game.EventType.*;

import endlessOctagon.content.*;
import endlessOctagon.util.ui.*;
import endlessOctagon.content.*;

public class EndlessOctagonMod extends Mod{
    
    public ObjectLog[] CURRENT_LOGS;

    public EndlessOctagonMod(){
      Log.info("Mod constructor loaded...");
        Events.on(ClientLoadEvent.class, e -> {
            //show dialog upon startup
            Time.runTask(10f, () -> {
                CURRENT_LOGS = new ObjectLog[]{
                         new ObjectLog(EOItems.oxa, "A new item", "New:")
                };
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
        dialog.show();
    }
    
  @Override
  public void loadContent(){
        new EOItems().load();
      new EOBlocks().load();
  }
}

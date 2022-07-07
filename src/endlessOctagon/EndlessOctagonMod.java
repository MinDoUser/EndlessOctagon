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
                startScreen();
            });
        });
    }
  
    public void startScreen(){
        CURRENT_LOGS = new ObjectLog[]{
                         new ObjectLog(EOItems.oxa, "A new item", "New:")
                };// Update here?
        BaseDialog dialog = new BaseDialog("");
        Table cont = dialog.cont;
        for(ObjectLog log : CURRENT_LOGS){
            cont.add(log.buildTable());
            cont.row();
            cont.row();
        }
        cont.row();
        dialog.buttons.button("Ok!", dialog::hide).size(100f, 50f);
        dialog.show();
    }
    
  @Override
  public void loadContent(){
        new EOItems().load();
      new EOBlocks().load();
  }
}

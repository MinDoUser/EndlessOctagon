package endlessOctagon;

import arc.util.*;
import arc.*;
import arc.scene.ui.layout.Table;

import mindustry.mod.*;
import mindustry.ui.dialogs.*;
import mindustry.ui.*;
import mindustry.game.EventType.*;
import mindustry.Vars;
import mindustry.ui.dialogs.SettingsMenuDialog;
import mindustry.gen.*; // Whethe is this package? Can't find it...

import endlessOctagon.content.*;
import endlessOctagon.util.ui.*;
import endlessOctagon.content.*;

public class EndlessOctagonMod extends Mod{
    
    public BaseDialog changeDialog;
    
    public ObjectLog[] CURRENT_LOGS;

    public EndlessOctagonMod(){
      Log.info("Mod constructor loaded...");
        Events.on(ClientLoadEvent.class, e -> {
            //show dialog upon startup
            Time.runTask(10f, () -> {
                changeScreen();
                loadSettings();
            });
        });
    }
  
    public void changeScreen(){
        CURRENT_LOGS = new ObjectLog[]{
                         new ObjectLog(EOItems.oxa){{
                              description = "A new Item";
                              type = ObjectLog.NEW;
                         }}
                };// Update here?
        changeDialog = new BaseDialog("");
        Table cont = changeDialog.cont;
        Table changes = new Table();
        changes.add("1.0.1", Styles.techLabel);
	changes.row();
        for(ObjectLog log : CURRENT_LOGS){
            changes.add(log.buildTable());
            changes.row();
            changes.row();
        }
	cont.add(changes); // Scroll bar later
        cont.row();
        changeDialog.buttons.button("Ok!", changeDialog::hide).size(100f, 50f);
        changeDialog.show();
    }
    
    public void loadSettings(){
        SettingsMenuDialog settingTable = Vars.ui.settings;
		
		settingTable.game.row();
        settingTable.game.button("Change Log", Icon.info, ()->{
            changeDialog.show();
        });
    }
    
  @Override
  public void loadContent(){
        new EOItems().load();
      new EOBlocks().load();
  }
}

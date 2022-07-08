package endlessOctagon;

import arc.util.*;
import arc.*;
import arc.graphics.g2d.*;
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
	changes.image().growX();
	changes.row();
        for(ObjectLog log : CURRENT_LOGS){
            changes.add(log.buildTable());
            changes.row();
	    changes.image().growX();
            changes.row();
        }
	addLog(changes, "Added Change Log", Icon.add);
	addLog(changes, "Added new button to open change log. \n You can find it here: Settings -> Game -> Change Log", Icon.wrench);
	cont.pane(changes);
        cont.row();
        changeDialog.buttons.button("Ok!", changeDialog::hide).size(100f, 50f);
        changeDialog.show();
    }
	/**
	* adds a new log to the existing table.
	*/
    public void addLog(Table table, String log, @Nullable TextureRegion icon){
	    table.row();
	    table.image(icon);
	    table.add(log);
	    table.image().growX();
    }
    
    public void loadSettings(){
        SettingsMenuDialog settingTable = Vars.ui.settings;
		
		settingTable.game.row();
        settingTable.game.button("Change Log", Icon.info, ()->{
            changeDialog.show();
        }).size(300f, 100f);
    }
    
  @Override
  public void loadContent(){
        new EOItems().load();
      new EOBlocks().load();
  }
}

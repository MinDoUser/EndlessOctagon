package endlessOctagon;

import arc.util.*;
import arc.*;
import arc.graphics.g2d.*;
import arc.scene.ui.layout.Table;
import arc.scene.style.*;

import mindustry.mod.*;
import mindustry.ui.dialogs.*;
import mindustry.ui.*;
import mindustry.game.EventType.*;
import mindustry.Vars;
import mindustry.ui.dialogs.SettingsMenuDialog;
import mindustry.gen.*; // Whethe is this package? Can't find it...

import endlessOctagon.content.*;
import endlessOctagon.util.ui.*;
import endlessOctagon.util.*;
import endlessOctagon.content.*;

public final class EndlessOctagonMod extends Mod{
    
    public BaseDialog changeDialog;
    
    public ObjectLog[] CURRENT_LOGS_V_1_0_1;

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
	
	
    public final void loadChangeDialog(){
	    CURRENT_LOGS_V_1_0_1 = new ObjectLog[]{
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
        for(ObjectLog log : CURRENT_LOGS_V_1_0_1){
            changes.add(log.buildTable());
            changes.row();
	    //changes.image().growX();
            //changes.row();
        }
	addLog(changes, "Added Change Log", Icon.add);
	addLog(changes, "Added new button to open change log. \n You can find it here: Settings -> Game -> Change Log", Icon.wrench);
	addLog(changes, "Added a option to hide startup dialog. \n You can find it here: Settings -> Game -> Mod Settings", Icon.wrench);
	cont.pane(changes);
        cont.row();
        changeDialog.buttons.button("Ok!", changeDialog::hide).size(100f, 50f);
    }

  	/** ONLY ON STARTUP! ?*/
    public final void changeScreen(){
	    if(Core.settings.getBool("hidestartlog", false))return; //no.
        
        changeDialog.show();
    }
	/**
	* adds a new log to the existing table.
	*/
    public void addLog(Table t, String log, @Nullable TextureRegionDrawable icon){
	    Table table = new Table();
	    table.row();
	    table.image(icon);
	    table.add("  "+log);
	    table.image().growX();
	    t.add(table).row();
    }
    
    public void loadSettings(){
	    new EOSettings().loadObject();
	    //=== === === === === === === === ===
        SettingsMenuDialog settingTable = Vars.ui.settings;
		
		settingTable.game.row();
        settingTable.game.button("Change Log", Icon.info, ()->{
            changeDialog.show();
        }).size(250f, 100f);
    }
    
  @Override
  public void loadContent(){
        new EOItems().load();
      new EOBlocks().load();
  }
}

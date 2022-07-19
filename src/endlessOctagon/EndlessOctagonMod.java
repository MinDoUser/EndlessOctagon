package endlessOctagon;

import arc.util.*;
import arc.*;
import arc.graphics.g2d.*;
import arc.graphics.*;
import arc.scene.ui.layout.Table;
import arc.scene.style.*;

import mindustry.mod.*;
import mindustry.ui.dialogs.*;
import mindustry.ui.*;
import mindustry.game.EventType.*;
import mindustry.Vars;
import mindustry.ui.dialogs.SettingsMenuDialog;
import mindustry.gen.*; // Where is this package? Can't find it...

import endlessOctagon.content.*;
import endlessOctagon.util.ui.*;
import endlessOctagon.util.*;
import endlessOctagon.content.*;

public final class EndlessOctagonMod extends Mod{
    
    public BaseDialog changeDialog;
    
    public ObjectLog[] CURRENT_LOGS_V_1_0_1;

    public EndlessOctagonMod(){
      Log.info("Mod constructor loaded...");
        Events.on(ClientLoadEvent.class, event -> {
		Vars.content.blocks().each(e->{
			EOBlocks.addAndCheck(e); // For testing purposes yet.
		});
            //show dialog upon startup
            Time.runTask(10f, () -> {
		    loadChangeDialog();
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
                         }},
		    	new ObjectLog(EOBlocks.crowl){{
                              description = EOBlocks.crowl.description;
                              type = ObjectLog.NEW;
                         }},
		    	new ObjectLog(EOBlocks.sump){{
                              description = "A new ground tile";
                              type = ObjectLog.NEW;
                         }}
                };// Update here?
        changeDialog = new BaseDialog("");
        Table cont = changeDialog.cont;
        Table changes = new Table();
        changes.add("1.0.1", Styles.techLabel).row();
	    changes.row();
	changes.image().growX();
	changes.row();
        for(ObjectLog log : CURRENT_LOGS_V_1_0_1){
            changes.add(log.buildTable());
            changes.row();
	    changes.image().growX();
            changes.row();
        }
	addLog(changes, "Added Change Log", Icon.add, false);
	addLog(changes, "Added new button to open change log. \n You can find it here: Settings -> Game -> Change Log", Icon.wrench, false);
	addLog(changes, "Added a option to hide startup dialog. \n You can find it here: Settings -> Game -> Mod Settings", Icon.wrench, false);
	changes.add(new WarningBar()).growX().height(30f).color(Color.white);
	    changes.row();
	addLog(changes, "[white]MINDUSTRY BUILD 136 || V7", null, true);//.row();
	changes.add(new WarningBar()).growX().height(30f).color(Color.white);
	addLog(changes, "Updated all stuff to make it work on V7", Icon.wrench, false);
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
    public static void addLog(Table t, String log, @Nullable TextureRegionDrawable icon, boolean tech){
	    Table table = new Table();
	    table.row();
	    if(icon != null)table.image(icon);
	    if(tech)table.add("  "+log, Styles.techLabel);
	    else table.add("  "+log);
	    table.row();
	    t.image().growX();
	    t.row();
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
	public static final endlessOctagon.util.Loadable[] loadables = {
		new EOItems(), new EOBlocks(), new EOPlanets()
	};
    
  @Override
  public void loadContent(){
	  for(var load : loadables){
        	load.loadObject();
	  }
  }
}

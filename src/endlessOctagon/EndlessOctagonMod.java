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

//TESTING ONLY!
import mindustry.content.*;
import mindustry.type.*;

import endlessOctagon.content.*;
import endlessOctagon.util.unit.*; //Testing only, remove later
import endlessOctagon.util.ui.*;
import endlessOctagon.util.ui.MapInfoDialog;
import endlessOctagon.util.ui.MapInfoDialog.*;
import endlessOctagon.util.*;
import endlessOctagon.content.*;

public final class EndlessOctagonMod extends Mod{
    
    public BaseDialog changeDialog;
    
    public ObjectLog[] LOGS_V_1_0_1, LOGS_V_1_1_1;
	
    public static CustomDatabase cDatabase;

    public EndlessOctagonMod(){
      Log.info("Mod constructor loaded...");
        Events.on(ClientLoadEvent.class, event -> {
		Overrides.overridePlanets();
		Vars.ui.database = cDatabase = new CustomDatabase();
		/*Vars.content.blocks().each(e->{
			EOBlocks.addAndCheck(e); // For testing purposes yet.
			Log.info("End");
			Log.info(EOBlocks.eravirBlocks);
		});*/
		//Log.info(Vars.content.planets()); //Testing
		new MapInfoFragment().build(Vars.ui.hudGroup);
            //show dialog upon startup
            Time.runTask(10f, () -> {
		    loadChangeDialog();
                changeScreen();
                loadSettings();
            });
        });
    }
	
	
    public final void loadChangeDialog(){
	    //TODO redo the following...
	    LOGS_V_1_0_1 = new ObjectLog[]{
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
	    
	    LOGS_V_1_1_1 = new ObjectLog[]{
		    new ObjectLog(EOItems.iron){{
                              description = "A new basic item.";
                              type = ObjectLog.NEW;
                    }},
		    new ObjectLog(EOItems.multiSteel){{
                              description = "A new item.";
                              type = ObjectLog.NEW;
                    }},
		    new ObjectLog(EOBlocks.shadow){{
                              description = "The basic turret";
                              type = ObjectLog.NEW;
                    }},
		    new ObjectLog(EOBlocks.plateConduit){{
                              description = "Transports liquid";
                              type = ObjectLog.NEW;
                    }},
		    new ObjectLog(EOBlocks.plateRouter){{
                              description = "Distributes liquids";
                              type = ObjectLog.NEW;
                    }}
	    };
	    
        changeDialog = new BaseDialog("");
        Table cont = changeDialog.cont;
        Table changes = new Table();
        createTitle(changes, "V 1.0.1");
        addObjectLogs(changes, LOGS_V_1_0_1);
	addLog(changes, "Added Change Log", Icon.add, false);
	addLog(changes, "Added new button to open change log. \n You can find it here: Settings -> Game -> Change Log", Icon.wrench, false);
	addLog(changes, "Added a option to hide startup dialog. \n You can find it here: Settings -> Game -> Mod Settings", Icon.wrench, false);
	changes.add(new WarningBar()).growX().height(30f).color(Color.white);
	    changes.row();
	    changes.row();
	    
	addLog(changes, "[white]MINDUSTRY BUILD 136 || V7", null, true);//.row();
	    changes.row();
	changes.add(new WarningBar()).growX().height(30f).color(Color.white);
	addLog(changes, "Updated all stuff to make it work on V7", Icon.wrench, false);
	    createTitle(changes, "V 1.1.1");
	 addLog(changes, "Changed the \"Goal\" of the mod. See README.md for more information", null, false); //TODO: Replace with own update icon
	  addObjectLogs(changes, LOGS_V_1_1_1);
	cont.pane(changes);
        cont.row();
        changeDialog.buttons.button("Ok!", changeDialog::hide).size(100f, 50f);
    }

	public static final void createTitle(Table table, String title){
		table.row();
		table.add(title, Styles.techLabel).row();
	    	table.row();
		table.image().growX();
		table.row();
	}
	
	public static final void addObjectLogs(Table table, ObjectLog[] logs){
	for(ObjectLog log : logs){
            	table.add(log.buildTable());
            	table.row();
	    	table.image().growX();
            	table.row();
        }
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
	settingTable.game.row(); 
	    ///TESTING ONLY !
	 settingTable.game.button("Table Test", Icon.cancel, ()->{
            	BaseDialog dialog = new BaseDialog("Table Test");
		dialog.cont.table(t -> {
			UnitBuildPlan plan1 = new UnitBuildPlan(UnitType.beta, 30f, new ItemStack[]{new ItemStack(Item.silicon, 12), new ItemStack(Item.titanium, 75)});
			t.add(UnitBuildPlan.createTableOfPlan(plan1));
			t.row();
			UnitBuildPlan plan2 = new UnitBuildPlan(UnitType.risso, 120f, new ItemStack[]{new ItemStack(Item.thorium, 120), new ItemStack(Item.scrap, 175)});
			t.add(UnitBuildPlan.createTableOfPlan(plan2));
		});
		 dialog.addCloseButton();
		 dialog.show();
        }).size(250f, 100f);
    }
	public static final endlessOctagon.util.Loadable[] loadables = {
		new EOFx(), new EOBullets(), new EOItems(), new EOBlocks(), new EOPlanets()
	};
    
  @Override
  public void loadContent(){
	  for(var load : loadables){
        	load.loadObject();
	  }
  }
}

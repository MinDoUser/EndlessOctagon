package endlessOctagon.util;

import arc.struct.*;
import arc.func.*;
import arc.util.*;
import arc.*;

import mindustry.ui.dialogs.*;
import mindustry.ui.*;
import mindustry.Vars;
import mindustry.gen.*; //Where is this ... package?

import static mindustry.ui.dialogs.SettingsMenuDialog.SettingsTable;

import endlessOctagon.EndlessOctagonMod;
import endlessOctagon.util.ui.*;

public final class EOSettings implements Loadable{
  
  //public final Seq<Setting> settings = new Seq<>();
	
	public EOSettings(){
		// Does nothing, just code :P
	}
  
  
  @Override
  public void loadObject(){
    BaseDialog modSettingDialog  = new BaseDialog("@settings.mod");
	modSettingDialog.buttons.button("@back", Icon.left, ()->{
		modSettingDialog.hide();
	}).size(450f, 80f); //I mean, why not gigantic?
    SettingsTable modSettings = new SettingsTable();
	modSettingDialog.cont.add("Settings", Styles.techLabel).row();
    modSettingDialog.cont.pane(modSettings);
     SettingsMenuDialog settings = Vars.ui.settings;
		
    settings.game.row();
    settings.game.button("@settings.mod", Icon.info, ()->{
	    modSettingDialog.show();
	    }).size(250f, 100f);
		modSettings.checkPref("hidestartlog", false);//.description = Core.bundle.get("hidestartlog.description");
	  modSettings.checkPref("showlockedblocks", true, bool -> {
		  CustomDatabse cdb = EndlessOctagonMod.cDatabase;
		  if(cdb == null){
			  if(Vars.ui.database instanceof CustomDatabase data){
				  cdb = data;
			  }else return;
		  }
		  cdb.rebuildDialog();
	  });
  }
  
  /*public void addCheckBox(SettingsTable table, String name, boolean def,@Nullable Boolc changed, @Nullable String d){
    setting.add(table.checkPref(name, def, changed).description = d==null?"":d);
  }*/

}

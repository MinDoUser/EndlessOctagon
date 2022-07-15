package endlessOctagon.util;

import arc.struct.*;
import arc.func.*;
import arc util.*;
import arc.*;

import mindustry.ui.dialogs.*;
import mindustry.ui.*;
import mindustry.Vars;

import static mindustry.ui.dialogs.SettingsMenuDialog.SettingsTable;

public final class EOSettings implements Loadable{
  
  public final Seq<Setting> settings = new Seq<>();
  
  
  @Override
  public void loadObject(){
    BaseDialog modSettingDialog  = new BaseDialog("@settings.mod");
    SettingsTable modSettings = new SettingsTable();
    modSettingDialog.cont.pane(modSettings);
     SettingsMenuDialog settings = Vars.ui.settings;
		
		settings.menu.row();
    menu.button("@settings.mod", Styles.cleart, ()->modSettingDialog.show());
    modSettings.add("$eo-settings", Styles.techLabel);
		modSettings.checkPref("hidestartlog", false).description = Core.bundle.get("hidestartlog.description");
  }
  
  /*public void addCheckBox(SettingsTable table, String name, boolean def,@Nullable Boolc changed, @Nullable String d){
    setting.add(table.checkPref(name, def, changed).description = d==null?"":d);
  }*/

}

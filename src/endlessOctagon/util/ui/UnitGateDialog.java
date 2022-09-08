package endlessOctagon.util.ui;

import mindustry.ui.dialogs.*;

import arc.util.*;

import endlessOctagon.world.blocks.units.*;
import endlessOctagon.world.blocks.units.UnitGate.*;

public class UnitGateDialog extends BaseDialog {
  
  protected UnitGateBuild unitGate = null;
  
  public UnitGateDialog(){
    super("");
    
    addCloseButton();
    
    shown(this::rebuild);
  }
  
  public void rebuild(){
    cont.clear();
    if(unitGate == null){
      cont.add("No gate selected!");
      Log.warn("No gate selected!");
      return;
    }
    if(build.block.plans.size <= 0){
      cont.add("The selected gate has no plans!");
      return;
    }
    
  }
  /**Use this instead of {@link show()}*/
  public void show(UnitGateBuild build){
    if(build == null){
      Log.error(new NullPointerException("Build was null!"));
      return
    }
    unitGate = build;
    this.title.setText("Unit Gate ("+(unitGate.x/8)+','+(unitGate.y/8)+")");
    
    this.show();
  }
}

package endlessOctagon.util.ui;

import mindustry.ui.dialogs.*;

import arc.util.*;

import endlessOctagon.world.blocks.units.*;
import endlessOctagon.world.blocks.units.UnitGate.*;

public class UnitGateDialog extends BaseDialog {
  
  protected final UnitGateBuild unitGate;
  
  public UnitGateDialog(UnitGateBuild build){
    super("");
    if(build == null)throw new NullPointerException("build was null");
    unitGate = build;
    
    addCloseButton();
    
    shown(this::rebuild);
  }
  
  public void rebuild(){
    cont.clear();
    UnitGate gate = (UnitGate)unitGate.block;
    if(gate.plans.size <= 0){
      cont.add("Thegate has no plans!");
      return;
    }
    
  }
  /*
  public void show(UnitGateBuild build){
    if(build == null){
      Log.err(new NullPointerException("Build was null!"));
      return;
    }
    unitGate = build;
    this.title.setText("Unit Gate ("+(unitGate.x/8)+','+(unitGate.y/8)+")");
    
    this.show();
  }*/
}

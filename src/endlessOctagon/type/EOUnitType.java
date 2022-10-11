package endlessOctagon.type;

import mindustry.type.*;
import mindustry.gen.*;

import arc.struct.*;

import endlessOctagon.entities.*;

public class EOUnitType extends UnitType {
  
  //Rotors.
  /** Whether this unit has rotors or not */
  public boolean hasRotors = false;
  public Seq<UnitRotor> rotors = new Seq<>(2);
  
  
  public EOUnitType(String name){
    super(name);
    
    constructor = UnitEntity::create; //Prevent nulls. o_o
  }
  
  public void addRotors(UnitRotor... rotors){
    this.rotors.addAll(rotors);
  }
  
  @Override
  public void load(){
    super.load();
    if(rotors != null && hasRotors){
      for(var rotor : rotors){
        if(rotor != null)rotor.loadRegions();
      }
    }
  }
  
  @Override
  public void update(Unit unit){
    super.update(unit);
    
    if(rotors != null && hasRotors){
      for(var rotor : rotors){
        if(rotor != null)rotor.toDrawData().updateRot(unit);
      }
    }
  }
  
  @Override
  public void draw(Unit unit){
    super.draw(unit);
    if(rotors != null && hasRotors){
      for(var rotor : rotors){
        if(rotor != null)rotor.toDrawData().drawRotor(unit);
      }
    }
  }
  
}

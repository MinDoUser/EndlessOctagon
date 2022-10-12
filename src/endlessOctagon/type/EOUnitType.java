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
  protected Seq<UnitRotor.RotorDrawData> drawDatas = new Seq<>(2);
  
  
  public EOUnitType(String name){
    super(name);
    
    constructor = UnitEntity::create; //Prevent nulls. o_o
  }
  /** Use this method for add rotors!*/
  public void addRotors(UnitRotor... rotors){
    this.rotors.addAll(rotors);
    for(var rotor : rotors){
      this.drawDatas.add(rotor.toDrawData());
    }
  }
  
  @Override
  public void load(){
    super.load();
    if(rotors != null){
      for(var rotor : rotors){
        if(rotor != null)rotor.loadRegions();
      }
    }
  }
  
  @Override
  public void update(Unit unit){
    super.update(unit);
    
    if(drawDatas != null && hasRotors){
      for(var data : drawDatas){
        if(data != null)data.updateRot(unit);
      }
    }
  }
  
  @Override
  public void draw(Unit unit){
    super.draw(unit);
    if(drawDatas != null && hasRotors){
      for(var data : drawDatas){
        if(data != null)data.drawRotor(unit);
      }
    }
  }
  
}

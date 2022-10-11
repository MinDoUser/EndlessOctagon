package endlessOctagon.entities;

import mindustry.type.*;
import mindustry.gen.*;

import arc.Core;
import arc.graphics.g2d.*;
import arc.math.*;

/** A rotor for units*/
public class UnitRotor {
  public final String name;
  public TextureRegion bladeRegion, bladeOutlineRegion, topRegion;
  public float speed = 27f;
  public boolean mirror = false;
  /** Offset */
  public float x = 0, y = 0;
  /** The amount of blades. */
  public int blades = 4;
  
  public UnitRotor(String name){
    this.name = name;
  }
  /** Creates a new rotor*/
  public UnitRotor(UnitType type){
    this(type.name);
  }
  
  public void loadRegions(){
    bladeRegion = find("blade");
    bladeOutlineRegion = find("blade-outline");
    topRegion = find("top");
  }
  //TODO: Put a static method somewhere ?
  protected TextureRegion find(String regionName){
    return Core.atlas.find(name+"-"+regionName);
  }
  /** Creates a new rotor, similar to this one*/
  public UnitRotor copy(){
    UnitRotor rotor = new UnitRotor(name);
    rotor.speed = speed;
    rotor.blades = blades;
    rotor.mirror = mirror;
    return rotor;
  }
  
  public RotorDrawData toDrawData(){
    return new RotorDrawData(this);
  }
  
  public static class RotorDrawData {
    public UnitRotor rotor;
    public float rotorRot = 0f;
    public float rotorOffset = 0f;
    
    public RotorDrawData(UnitRotor rotor){
      this.rotor = rotor;
    }
    /** Update rotation. Call this in {@code update(Unit unit)} of the UnitType*/
    public void updateRot(Unit unit){
      rotor.rotorRot += rotor.rotor.speed * Time.delta;
      rotor.rotorRot %= 360f; //Reset when > 360° 
    }
    /** Draw rotor. Call this in {@code draw(Unit unit)} of the UnitType */
    public void drawRotor(Unit unit){
            float x = unit.x + Angles.trnsx(unit.rotation - 90f, rotor.x, rotor.y);
            float y = unit.y + Angles.trnsy(unit.rotation - 90f, rotor.x, rotor.y);

            Draw.alpha(0.85f);
            for(int j = 0; j < rotor.blades; j++){
                Draw.rect(rotor.bladeRegion, x, y,
                    unit.rotation - 90f
                    + 360f / rotor.blades * j
                    + mount.rotorRot
                );
            }

            Draw.color();
            Draw.rect(rotor.topRegion, x, y, unit.rotation - 90f);
            Draw.reset();
    }
    
  }
  
  
}

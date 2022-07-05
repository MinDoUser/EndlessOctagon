package endlessOctagon.world.blocks.crafter;

import mindustry.world.blocks.production.*;

import arc.*;
import arc.math.*;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;


/** A Crafter that works with energy of the sun.
* It won't work when the sun isn't shining.
*/
public class SolarCrafter extends GenericCrafter{
  
  public SolarCrafter(String name){
    super(name);
    if(this.details == null){
      details = "A \"Solar Crafter\". Used the sun to operate. \n\n Keep sure you have enough sun!";
      this.hideDetails = false; // Never hide if not null.
    }
  }
  
      @Override
    public void setBars(){
        super.setBars();

        bars.add("efficiency", (SolarCrafterBuild entity) ->
            new Bar(() ->
            Core.bundle.format("bar.efficiency", (int)(entity.efficiencyScale() * 100)),
            () -> Pal.lightOrange,
            entity::efficiencyScale));
    }
  
    public class SolarCrafterBuild extends GenericCrafterBuild{
      @Override
        public float getProgressIncrease(float base){
            return super.getProgressIncrease(base) * efficiencyScale();
        }

        public float efficiencyScale(){
            return getEfficiency();
        }
      
      public final float getEfficiency(){
        return Mathf.maxZero(Attribute.light.env() +
                    (state.rules.lighting ?
                        1f - state.rules.ambientLight.a :
                        1f
                    ));
      }
    }

  
}

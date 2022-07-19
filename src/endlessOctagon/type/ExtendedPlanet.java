package endlessOctagon.type;

import mindustry.type.*;
import mindustry.world.meta.*;

import arc.scene.ui.layout.*;
import arc.struct.*;
/** A planet with stats, yeah!*/
public class ExtendedPlanet extends Planet{
  public static final Stat
    isAccessible = new Stat("accessible"), dayNightCycle = new Stat("daynightcycle"),atmosphere = new Stat("atmosphere"),
    spawnsWavesInBackground = new Stat("spawnswaves"), hasInvations = new Stat("hasinvations"), clearSectors = new Stat("clearsectors"),
    planetParent = new Stat("planetparent"), hasOwnTechTree = new Stat("hastechtree"), planetHiddenItems = new Stat("hiddenitems");
                                                                                                                         
    public ExtendedPlanet(String name, Planet parent, float radius){
      super(name, parent, radius);
    }
  //:O Stats?!, ye, keep cool.
  @Override
  public void setStats(){
    final int MAX_PER_ROW = 5;
    stats.add(isAccessible, super.accesible);
    stats.add(dayNightCycle, super.updateLighting);
    stats.add(atmosphere, super.hasAtmosphere);
    stats.add(spawnsWavesInBackground, super.allowWaveSimulation);
    stats.add(hasInvations, super.allowSectorInvasion);
    stats.add(clearSectors, super.clearSectorOnLose);
    stats.add(planetParent, (super.parent == null)? "[lightgrey]<none>[]":super.parent.localizedName);
    stats.add(hasOwnTechTree, super.techTree != null);
    stats.add(planetHiddenItems, (table)->{
      Seq<Item> hidden = super.hiddenItems;
      int i = 0;
      hidden.each(item->{
        i++;
        table.image(item.uiIcon);
        table.add(item.localizedName);
        table.add("  ");
        if(i%MAX_PER_ROW==0){
          table.row();
        }
      });
    });
  }
}

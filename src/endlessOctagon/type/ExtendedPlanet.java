package endlessOctagon.type;

import mindustry.type.*;
import mindustry.world.meta.*;
import mindustry.Vars;

import arc.scene.ui.layout.*;
import arc.scene.ui.*;
import arc.struct.*;
import arc.func.*;
import arc.scene.event.*;
/** A planet with stats, yeah!*/
public class ExtendedPlanet extends Planet{
  public static final int MAX_PER_ROW = 5;
  
  public static final Stat
    isAccessible = new Stat("accessible"), dayNightCycle = new Stat("daynightcycle"),atmosphere = new Stat("atmosphere"),
    spawnsWavesInBackground = new Stat("spawnswaves"), hasInvations = new Stat("hasinvations"), clearSectors = new Stat("clearsectors"),
    planetParent = new Stat("planetparent"), hasOwnTechTree = new Stat("hastechtree"), planetHiddenItems = new Stat("hiddenitems");
                                                                                                                         
    public ExtendedPlanet(String name, Planet parent, float radius){
      super(name, parent, radius);
    }
  
  public ExtendedPlanet(String name, Planet parent, float radius, int sectorSize){
      super(name, parent, radius, sectorSize);
    }
  
  //TODO: Constructor with @param Planet to copy theese normal planets into a planet with stats.
  
  
  //:O Stats?!, ye, keep cool.
  @Override
  public void setStats(){
    createPlanetStats(this);
  }
  @Override
  public boolean isHidden(){return false;}
  
  public static void createPlanetStats(Planet planet){
    Stats s = planet.stats;
    s.add(isAccessible, planet.accessible);
    s.add(dayNightCycle, planet.updateLighting);
    s.add(atmosphere, planet.hasAtmosphere);
    s.add(spawnsWavesInBackground, planet.allowWaveSimulation);
    s.add(hasInvations, planet.allowSectorInvasion);
    s.add(clearSectors, planet.clearSectorOnLose);
    s.add(planetParent, (planet.parent == null)? "[lightgrey]<none>[]":planet.parent.localizedName);
    s.add(hasOwnTechTree, planet.techTree != null);
    s.add(planetHiddenItems, (table)->{
      Seq<Item> hidden = planet.hiddenItems;
      hidden.each(new Cons<>(){ //Pain
        private int i = 0; // ...
        @Override
        public void get(Item item){
        i++;
          Image img = new Image(item.uiIcon);
          img.addListener(new HandCursorListener());
          img.clicked(()->{
            Vars.ui.content.show(item);
          });
        table.add(img);
        table.add(item.localizedName);
        table.add("  ");
        if(i%MAX_PER_ROW==0){
          table.row();
        }
      }
    });
  });
  }
}

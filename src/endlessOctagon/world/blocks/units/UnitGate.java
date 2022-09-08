package endlessOctagon.world.blocks.units;

import mindustry.world.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.entities.*;
import mindustry.graphics.*;

import arc.struct.*;
import arc.*;
import arc.scene.ui.layout.*;

import endlessOctagon.util.units.*;
import endlessOctagon.EOVars;

public class UnitGate extends Block {
  
  public Seq<UnitBuildPlan> plans = new Seq<>(4);
  
  public UnitGate(String name){
    super(name);
    
    configurable = true;
    
    config(Integer.class, this::onConfigure);
    config(UnitType.class, (UnitGateBuild tile, UnitType val) -> {
      int next = plans.indexOf(p -> p.unit == val);
      onConfigure(tile, next);
    });
  }
  /** This will reset all current plans! Only the *buildPlans* are in the plans list*/
  public void plans(UnitBuildPlan... buildPlans){
    plans = new Seq<>(buildPlans);
  }
  
  @Override
  public void init(){
    int capacity = 0;
    for(UnitBuildPlan plan : plans){
      if(plan != null && plan.requirements != null){
        for(ItemStack stack : plan.requirements){
          capacity = Math.max(capacity, stack.amount*2);
        }
      }
    }
    if(capacity < 10) capacity = 10;
    itemCapacity = Math.max(itemCapacity,capacity);
    super.init();
  }
  
  @Override
    public void setBars(){
        super.setBars();
        addBar("progress", (UnitGateBuild e) -> new Bar("bar.progress", Pal.ammo, e::unitProgress));
      
        addBar("units", (UnitGateBuild e) ->
          new Bar(
            () -> e.getPlan().unit == null ? "[lightgray]" + Iconc.cancel :
                Core.bundle.format("bar.unitcap",
                    Fonts.getUnicodeStr(e.getPlan().unit.name),
                    e.team.data().countType(e.getPlan().unit),
                    Units.getStringCap(e.team)
                ),
            () -> Pal.power,
            () -> e.getPlan().unit == null ? 0f : (float)e.team.data().countType(e.getPlan().unit) / Units.getCap(e.team)
        ));
    }
  
  protected void onConfigure(UnitGateBuild tile, Integer i){
    if(!configurable || i == null)return;
      
      if(tile.selectedPlan == i)return;
      tile.selectedPlan = (i<0 || i >= plans.size) ? -1:i;
      tile.progress = 0f;
  }
  
  public class UnitGateBuild extends Building {
    public final UnitGateDialog unitGateDialog = new UnitGateDialog(this);
    public int selectedPlan = -1;
    public float progress = 0f;
    /** @return null if {@code selectedPlan} smaller than 0 or greater than the size of plans*/
    public UnitBuildPlan getPlan(){
      if(selectedPlan < 0 || selectedPlan >= plans.size)return null;
      return plans.get(selectedPlan);
    }
    
    public float unitProgress(){
      return (selectedPlan == -1) ? 0 : progress/getPlan().time;
    }
    
    @Override
     public void buildConfiguration(Table table){
       table.button(Icon.book, Styles.cleari, ()-> {
         unitGateDialog.show();
       }).tooltip("Configure");
     }
    
  }
}

package endlessOctagon.world.blocks.units;

import mindustry.world.*;
import mindustry.world.meta.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.entities.*;
import mindustry.graphics.*;
import mindustry.game.EventType.*;
import mindustry.world.consumers.*;
import mindustry.Vars;
//import mindustry.annotations.Annotations; //"Package does not exist" ... ?!

import arc.struct.*;
import arc.*;
import arc.scene.ui.layout.*;
import arc.scene.style.*;
import arc.util.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;

import endlessOctagon.util.units.*;
import endlessOctagon.util.ui.*;

public class UnitGate extends Block {
  // @Load is not found ?! 
  public TextureRegion topRegion;
  
  public Seq<UnitBuildPlan> plans = new Seq<>(4);
  
  public UnitGate(String name){
    super(name);
    
    hasItems = hasPower = true;
    acceptsItems = true;
    update = true;
    destructible = true;
    solid = true;
    breakable = true;
    
    group = BlockGroup.units;
    
    flags = EnumSet.of(BlockFlag.unitAssembler);
    
    category = Category.units;
    
    configurable = true;
    
    config(Integer.class, this::onConfigure);
    config(UnitType.class, (UnitGateBuild tile, UnitType val) -> {
      int next = plans.indexOf(p -> p.unit == val);
      onConfigure(tile, next);
    });
    
    consume(new ConsumeItemDynamic((UnitGateBuild e) -> e.selectedPlan != -1 ? plans.get(Math.min(e.selectedPlan, plans.size - 1)).requirements : ItemStack.empty));
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
    public void load(){
        super.load();

        topRegion = Core.atlas.find(name + "-top");
    }
  
  @Override
    public void setBars(){
        super.setBars();
        addBar("progress", (UnitGateBuild e) -> new Bar("bar.progress", Pal.ammo, e::unitProgress));
      
        addBar("units", (UnitGateBuild e) ->
          new Bar(
            () -> e.getPlan() == null ? "[lightgray]" + Iconc.cancel :
                Core.bundle.format("bar.unitcap",
                    Fonts.getUnicodeStr(e.getPlan().unit.name),
                    e.team.data().countType(e.getPlan().unit),
                    Units.getStringCap(e.team)
                ),
            () -> Pal.power,
            () -> e.getPlan() == null ? 0f : (float)e.team.data().countType(e.getPlan().unit) / Units.getCap(e.team)
        ));
    }
  
  protected void onConfigure(UnitGateBuild tile, Integer i){
    if(!configurable || i == null)return;
      
      if(tile.selectedPlan == i)return;
      tile.selectedPlan = (i<0 || i >= plans.size) ? -1:i;
      tile.progress = 0f;
  }
  //TODO: Replace this with a Queue
  public class UnitGateBuild extends Building {
    public final UnitGateDialog unitGateDialog = new UnitGateDialog(this);
    public int selectedPlan = -1;
    public float progress = 0f, speedScl = 0f, time = 0f;
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
    
    @Override
    public void display(Table table){
      super.display(table);
      
      TextureRegionDrawable reg = new TextureRegionDrawable();

       table.row();
       table.table(t -> {
            t.left();
            t.image().update(i -> {
                i.setDrawable(selectedPlan == -1 ? Icon.cancel : reg.set(getPlan().unit.uiIcon));
                i.setScaling(Scaling.fit);
                i.setColor(selectedPlan == -1 ? Color.lightGray : Color.white);
            }).size(32).padBottom(-4).padRight(2);
            t.label(() -> selectedPlan == -1 ? "@none" : getPlan().unit.localizedName).wrap().width(230f).color(Color.lightGray);
       }).left();
    }
    
    @Override
        public void draw(){
            Draw.rect(region, x, y);

            if(selectedPlan != -1){
                UnitBuildPlan plan = plans.get(selectedPlan);
                Draw.draw(Layer.blockOver, () -> Drawf.construct(this, plan.unit, rotdeg() - 90f, progress / plan.time, speedScl, time));
            }

            Draw.z(Layer.blockOver+0.25f);

            Draw.rect(topRegion, x, y);
        }
    
    @Override
        public void updateTile(){
            if(!configurable){
                selectedPlan = 0;
            }

            if(selectedPlan < 0 || selectedPlan >= plans.size){
                selectedPlan = -1;
            }
          
            if(selectedPlan >= 0 && efficiency > 0){
              time += edelta() * speedScl * Vars.state.rules.unitBuildSpeed(team);
              progress += edelta() * Vars.state.rules.unitBuildSpeed(team);
              speedScl = Mathf.lerpDelta(speedScl, 1f, 0.05f);
            }else{
                speedScl = Mathf.lerpDelta(speedScl, 0f, 0.05f);
            }

            if(progress >= getPlan().time){
                getPlan().unit.spawn(this.team,this.x, this.y);
                consume();
                
                progress = 0;
                
                //Events.fire(new UnitCreateEvent(getPlan().unit, this));
              }
          if(selectedPlan < 0)progress = 0;
        }
    
    @Override
        public Object config(){
            return selectedPlan;
        }
    
  }
}

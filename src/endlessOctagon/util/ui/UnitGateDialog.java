package endlessOctagon.util.ui;

import mindustry.ui.dialogs.*;
import mindustry.ui.*;
import mindustry.graphics.*;
import mindustry.gen.*;
import mindustry.*;

import arc.util.*;
import arc.scene.ui.layout.*;
import arc.scene.ui.*;
import arc.graphics.*;
import arc.*;
import arc.struct.*;

import endlessOctagon.util.units.*;
import endlessOctagon.world.blocks.units.*;
import endlessOctagon.world.blocks.units.UnitGate.*;

public class UnitGateDialog extends BaseDialog {
  
  protected final UnitGateBuild unitGate;
  
  protected Seq<UnitBuildPlan> plansSeq;
  
  public UnitGateDialog(UnitGateBuild build){
    super("");
    if(build == null)throw new NullPointerException("build was null");
    unitGate = build;
    plansSeq = unitGate.plans();
    
    addCloseButton();
    
    shown(this::rebuild);
  }
  
  public void rebuild(){
    title.setText("Unit Gate ("+(unitGate.x/8f)+","+(unitGate.y/8f)+")");
    
    cont.clear();
    //UnitGate gate = (UnitGate)unitGate.block;
    if(plansSeq.size <= 0){
      cont.image(Icon.warning);
      cont.add("The gate has no plans!");
      return;
    }
    Table table = new Table();
    for(var plan : plansSeq){
      table.add(createTableOfPlan(plan, unitGate));
      table.row();
      table.row();
    }
    
    cont.pane(table).scrollX(false);
    
    
  }
  public static Table createTableOfPlan(UnitBuildPlan plan, UnitGateBuild building){
    return createTableOfPlan(plan, building, true);
  }
  
  /**@param useBuilding if !useBuilding, building is not needed and can be null, else not*/
  public static Table createTableOfPlan(UnitBuildPlan plan, UnitGateBuild building, boolean useBuilding){
    Table rTable = new Table();
    rTable.setBackground(Tex.whiteui);
    rTable.setColor(Pal.darkestGray);
    rTable.table(t -> {
      t.image(plan.unit.uiIcon).size(Vars.iconLarge).left();
      t.row();
      t.add(plan.unit.localizedName).left();
      t.row();
      t.add(Strings.fixed(plan.time/60f, 1)+Core.bundle.get("unit.seconds")).color(Color.lightGray).left();
    }).width(150).left();
      rTable.table(t -> {
        t.button(Icon.infoCircle, ()->{
          var unit = plan.unit;
          BaseDialog dialog = new BaseDialog(unit.localizedName);
          dialog.addCloseButton();
          dialog.cont.table(table -> {
            table.left();
            table.image(unit.uiIcon).size(Vars.iconLarge);
            table.row();
            table.add(unit.localizedName, Styles.techLabel);
            table.row();
            table.image().growX().color(Color.white);
          }).width(300);
          dialog.cont.row();
          if(unit.description != null)dialog.cont.add(unit.description);
          dialog.cont.row();
          dialog.cont.table(table -> {
            table.add(Core.bundle.get("stat.buildcost"));
            table.table(req -> {
              req.right();
              for(int i = 0; i < plan.requirements.length; i++){
              if(i % 6 == 0){
                 req.row();
              }

              var stack = plan.requirements[i];
              req.add(new ItemDisplay(stack.item, stack.amount, false)).pad(5).left();
              }
            }).right().grow().pad(10f);
            dialog.show();
          });
        }).tooltip("Info");
          
          t.button(Icon.units, ()-> {
            building.configure(plan.unit);
          }).tooltip("Spawn").disabled((e)->!useBuilding);
          t.row();
          final String BUILD_STRING = Core.bundle.get("canbuild", "Can Build")+":";
          if(useBuilding){
          
            boolean build =  building.items.has(plan.requirements);
            t.add(BUILD_STRING + (!build ? Core.bundle.get("no","No") : Core.bundle.get("yes","Yes"))).right();
          }else{
            t.add(BUILD_STRING+" ? ").right();
          }
        }).width(750f);
     // });
    return rTable;
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

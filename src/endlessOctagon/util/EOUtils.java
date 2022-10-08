package endlessOctagon.util;

import mindustry.game.*;
import mindustry.Vars;

public final class EOUtils {
  
  public static final float maxAlpha = 0.75f, minAlpha = 0.0f; //float
  
  /** This is not the best options, see the other "updateLight" methods */
  public static void updateLight(boolean darker){
    updateLight(darker, Vars.state.rules);
  }
  
  public static void updateLight(boolean darker, Rules rules){
    updateLight(darker, 0.005f, rules);
  }
  
  public static void updateLight(boolean darker, float change, Rules rules){
    updateLight(darker, change, 25, rules);
  }
  
  public static void updateLight(boolean darker, float change, int steps, Rules rules){
    updateLight(darker, 2.5f, change, steps, rules);
  }
  
  public static void updateLight(boolean darker, float delayBetween, float change, int steps, Rules rules){
    updateLight(darker, 30f, delayBetween, change, steps, rules);
  }
  
  public static void updateLight(boolean darker, float startDelay, float delayBetween, float change, int steps, Rules rules){
    if(!rules.lighting)return;
    for(int i = 0; i < steps; i++){
      arc.util.Time.run(startDelay+(delayBetween*i), ()->{
        rules.ambientLight.a += darker ? change : -change;
        if(rules.ambientLight.a <= minAlpha)rules.ambientLight.a = minAlpha;
        if(rules.ambientLight.a >= maxAlpha)rules.ambientLight.a = maxAlpha;
      });
    }
  }
}

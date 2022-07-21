package endlessOctagon.content;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.units.UnitAssembler.*;
import mindustry.entities.effect.*;

import static arc.graphics.g2d.Draw.rect;
import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;
import static mindustry.Vars.*;

public final class EOFx implements endlessOctagon.util.Loadable {
   public static final Effect blueWave;
  @Override
  public void loadObject(){
    blueWave = new WaveEffect(){{
      colorFrom = EOPal.lightBlue;
      colorTo = EOPal.darkBlue;
      lightColor = colorTo;
      sides = 10;
      interp = Interp.pow2Out;
      lightInterp = Interp.pow2In;
      sizeTo = 120;
    }};
  }
}

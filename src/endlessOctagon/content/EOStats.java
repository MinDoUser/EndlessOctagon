package endlessOctagon.content;

import endlessOctagon.util.*;

public final class EOStats implements Loadable{
  public static final Stat 
    special;
  @Override
  public void loadObject(){
    special = new Stat("special");
  }
}

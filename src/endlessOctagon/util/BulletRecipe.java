package endlessOctagon.util;

import mindustry.entities.bullet.*;
import mindustry.type.*;
import mindustry.graphics.*;

import arc.graphics.g2d.*;
import arc.ui.scene.*;
import arc.util.scaling.*;
import arc.*;

//TODO: Need to create a own content type somehow.
public class BulletRecipe {
  public final String name, localizedName, description;
  /** The bullet to be shoot*/
  public BulletType type;
  /** Whether this is locked or not. Do not set.*/
  public boolean unlocked = false;
  
  public boolean alwaysUnlocked = false;
  
  public TextureRegion icon;
  
  //TODO: Create own tech nodes for bullet recipes
  
  public ItemStack[] requirements = ItemStack.empty;
  public ItemStack[] researchCost = ItemStack.empty;
  
  public BulletRecipe(String name, BulletType type, ItemStack[] req){
    this.name = Vars.content.transforName(name);
    requirements = req;
    this.type = type;
    
    unlocked = Core.settings == null ? false : Core.settings.getBool(name"-unlocked", false);
    
    loadIcon();
    loadInfo();
  }
  
  public BulletType(String name, BulletType type, Item item, int amount){
    this(name, type, new ItemStack[]{new ItemStack(item, (amount < 0 ? 0:amount))});
  }
         
  public BulletType(String name, BulletType type, Item item){
    this(name,type, item, 1);
  }    
  
  public boolean unlocked(){
    return unlocked || alwaysUnlocked;
  }
  public void unlock(){
  	  unlocked = true;
  	  onUnlock();
  	  saveState();
  }
  
  /** lock this recipe again*/
  public void lock(){
  	  unlocked = false;
  	  onLocked();
  	  saveState();
  }
  /** Called when this recipe got unlocked*/
  public void onUnlock(){
  	  Vars.ui.showInfoToast(icon, Core.bundle.fomat(
  }
  /** Called when this content got locked*/
  public void onLock(){
  }
  
  public void saveState(){
  	  if(Core.settings != null){
  	  	  Core.settings.put(name+"-unlocked", unlocked);
  	  }
  }
  
  protected void loadState(){
  	  if(Core.settings != null){
  	  	  unlocked = Core.settings.getBool(name+"-unlocked", false);
  	  }
  }
  
  public void loadIcon(){
    icon = Core.atlas.find(name, Core.atlas.find(name+"-icon"));
  }
  
  public void loadInfo(){
    localizedName = Core.bundle.get(name+".name",name);
    description = Core.bundle.getOrNull(name+".description");
  }
  
  public Image getIcon(){
  	  return new Image(unlock.uiIcon).setScaling(Scaling.fit) : new Image(Icon.lock, Pal.gray);
  }
  
  /** Returns the localized name of this recipe or <code>???</code> if it's still locked.*/
  public String getName(){
  	  if(!unlocked()){
  	  	  return "<???>";
  	  }
  	  
  	  return localizedName;
  }
  /** Always return the full name.*/
  @Override
  public String toString(){
  	  if(localizedName == null)loadInfo();
  	  return localizedName;
  }
  
                                                 
}
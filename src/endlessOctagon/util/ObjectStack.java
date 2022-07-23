package endlessOctagon.util;
// Easy.
/**A stack of any object.*/
public class ObjectStack<T> {
  public final T object;
  /** The amount*/
  public int amount = 0;
  
  public ObjectStack(){}
  
  public ObjectStack(T object, int amount){
    this.object = object;
    this.amount = amount;
  }
  
  public ObjectStack(ObjectStack<? extends T> other){
    this.object = other.object;
    this.amount = other.amount;
  }
  /** Multiplies the amount of this stack by param {@code mult}*/
  public ObjectStack mulBy(float mult){
    float aMult = (float)Math.abs(mult);
    this.amount = (int)Math.round(this.amount * aMult);
    return this; //Chain, ye
  }
  
  @Override
  public boolean equals(Object other){
    if(other instanceof ObjectStack o){
      if((o.amount == this.amount) && (o.object.equals(this.object)))return true;
    }else return false;
  }
  
  @Override
  public void toString(){
    return "ObjectStack:"+ object +' '+amount;
  }
}

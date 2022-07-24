package endlessOctagon.util;
// Easy. no?
/**A stack of any object.*/
public class ObjectStack<T> {
  public T object;
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
  
  public ObjectStack<T> set(T object, int amount){
    this.object = object;
    this.amount = amount;
    return this;
  }
  
  public static <E> ObjectStack<E> of(E object, int amount){
    ObjectStack<E> rStack = new ObjectStack().set(object, amount);
    return rStack;
  }
  
  /** Multiplies the amount of this stack by param {@code mult}*/
  public ObjectStack<T> mulBy(float mult){
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
  public String toString(){
    return "ObjectStack:"+ object +' '+amount;
  }
}

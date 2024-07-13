public class Topping implements java.io.Serializable{
  boolean hasBoba;
  Boba bobaType;
  boolean hasPudding;
  String name;

  public Topping(){
    hasBoba = false;
    hasPudding = false;
  }

  public Topping(boolean hasBoba, Boba bobaType, boolean hasPudding){
    this.hasBoba = hasBoba;
    this.bobaType = bobaType;
    this.hasPudding = hasPudding;
  } //end Topping

  public Topping(boolean hasBoba, Boba bobaType, boolean hasPudding, Flavor flavor){
    this.hasBoba = hasBoba;
    this.bobaType = bobaType;
    this.hasPudding = hasPudding;
    this.name = flavor.toString() + bobaType.toString();
  } //end second constructor

  public Boba getBobaType(){
    return this.bobaType;
  } //end getBobaType

  public boolean hasBoba(){
    return this.hasBoba;
  } //end hasBoba

  public boolean hasPudding(){
    return this.hasPudding;
  } //end hasPudding
  
  public String getBobaName(){
    if(this.hasPudding){
      return("Pudding");
    } else {
      if(this.hasBoba){
        return(this.bobaType.toString());
      } //end if
    } //end else
    return "";
  } //end getBobaName

  public String getName(){
    return this.name;
  } //end getName
} //end Topping

import java.io.*;

public class Drink implements Serializable{
  String name;
  Tea teaType;
  Topping[] toppings;
  String description;
  String size;
  
  public static void main(String[] args){
    Tea t = Tea.SLUSH;
    Topping[] toppings = new Topping[2];
    toppings[0] = new Topping(false, null, true);
    toppings[1] = new Topping(true, Boba.JELLY, false);
    Drink d = new Drink("Refresher", t);
    d.setToppings(toppings); 
    System.out.println("Name: " + d.getName() + " of tea type: " + t + " and toppings " + toppings[0].getName() + " and " + toppings[1].getName()); 
  } //end main

  public Drink(String name, Tea teaType){
    this.name = name;
    this.teaType = teaType;
    this.toppings = new Topping[4];
    this.size = "Medium";
  } //end parameter constructor

  public Drink(String name, Tea teaType, String description){
    this.name = name;
    this.teaType = teaType;
    this.description = description;
    this.toppings = new Topping[4];
    this.size = "Medium";
  } //end null constructor

  public String getName(){
    return this.name;
  } //end getName

  public Tea getTeaType(){
    return this.teaType;
  } //end getTeaType

  public Topping[] getToppings(){
    return this.toppings;
  } //end getToppings

  public String getDescription(){
    return this.description;
  } //end getDescription

  public void setToppings(Topping[] toppings){
    this.toppings = toppings;
  } //end setToppings

  public void setDescription(String description){
    this.description = description;
  } //end setDescription

  public String getSize(){
    return this.size;
  } //end getSize

  public void setSize(String size){
    this.size = size;
  } //end setSize
} //end Drink

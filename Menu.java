import java.util.*;
import java.text.DecimalFormat;
import java.io.*;

public class Menu implements Cost, Serializable{
  private ArrayList<Drink> drinks;
  private ArrayList<Topping> toppings;
  private Flavor[] flavors = {Flavor.MANGO, Flavor.STRAWBERRY, Flavor.PEACH, Flavor.BROWN_SUGAR, Flavor.TARO, Flavor.MATCHA, Flavor.HONEY};

  public Menu(){
    this.drinks = new ArrayList<Drink>();
    this.toppings = new ArrayList<Topping>();
    this.initializeToppings();
    this.initializeDrinks();
  } 

  public ArrayList<Drink> getDrinks(){
    return this.drinks;
  } //end getDrinks

  public ArrayList<Topping> getToppings(){
    return this.toppings;
  } //end getToppings

  public void setDrinks(ArrayList<Drink> drinks){
    this.drinks = drinks;
  }

  public void setToppings(ArrayList<Topping> toppings){
    this.toppings = toppings;
  }

  public Flavor[] getFlavors(){
    return this.flavors;
  } //end getFlavors

  public void initializeToppings(){
    toppings.add(new Topping(true, Boba.BUBBLE, false)); //reg bubble
    toppings.add(new Topping(false, null, true)); //pudding
    for(int i = 0; i < flavors.length-1; i++){
      if(i < 2){ //mango, strawberry or pom
        toppings.add(new Topping(true, Boba.JELLY, false, flavors[i]));
        toppings.add(new Topping(true, Boba.POPPING, false, flavors[i]));
      } else if (i == 3){ //brown sugar
        toppings.add(new Topping(true, Boba.BUBBLE, false, flavors[i]));
        toppings.add(new Topping(true, Boba.CRYSTAL, false, flavors[i]));
      } else { // taro or matcha
        toppings.add(new Topping(true, Boba.POPPING, false, flavors[i]));
        toppings.add(new Topping(true, Boba.CRYSTAL, false, flavors[i]));
      } //end if else
    } //end for    
  } //end initializeToppings, 16 total

  public void initializeDrinks(){
    for(int i = 0; i < flavors.length; i++){
      String name = flavors[i].toString();  
      if(i <= 2){ //mango, strawberry, pom
        drinks.add(new Drink((name+"Slush"), Tea.SLUSH, ("Refreshing " + name + "blended with ice and sugar")));
        drinks.add(new Drink((name+"Green Tea"), Tea.GREEN, ("Relaxing " + name + "infused into our signature green tea")));
      } else if(i == 3){ //brown sugar
        drinks.add(new Drink((name+"Milk Tea"), Tea.MILK, ("Black Milk Tea infused with "+ name + "for an added sweet taste")));
        drinks.add(new Drink((name+"Thai Tea"), Tea.THAI, ("Thai Tea infused with " + name + "for an added sweet taste"))); 
      } else if(i == 4){ //taro
        drinks.add(new Drink((name+"Milk Tea"), Tea.MILK, ("Black Milk Tea infused with " + name + "for an added flavor")));
        drinks.add(new Drink((name+"Slush"), Tea.SLUSH, (name + "flavoring blended with ice in our specialty slush format")));
        drinks.add(new Drink((name+"Thai Tea"), Tea.THAI, ("Thai tea infused with " + name + "for an added flavor")));
      } else { //matcha and honey
        drinks.add(new Drink((name+"Milk Tea"), Tea.MILK, (name + "flavored Milk Tea")));
        drinks.add(new Drink((name+"Green"), Tea.GREEN, ("Classic " + name + "Green Tea")));
        if(i == 6){
          drinks.add(new Drink((name+"Oolong"), Tea.OOLONG, ("Oolong Tea infused with " + name + "for a sweet aftertaste")));
          drinks.add(new Drink((name+"Thai"), Tea.THAI, ("Thai Tea inufsed with " + name + "for a sweet aftertaste")));
        } //end inner if
      } //end if else
    } //end for
  } //end initializeDrinks 

  public void printToppings(){
    System.out.println("1) " + toppings.get(0).getBobaName() + getDots(toppings.get(0), true) + " (+$" + calculateToppingPrice(toppings.get(0)) + ")");
    System.out.println("2) " + toppings.get(1).getBobaName() + getDots(toppings.get(1), true ) + " (+$" + calculateToppingPrice(toppings.get(1)) + ")");
    for(int i = 2; i < toppings.size(); i++){
      String dots = getDots(toppings.get(i), false);
      if(i >= 9 ){ //formatting
        dots = dots.substring(0, dots.length()-1); //needs to be one less because the number has one more digit
      } 
      System.out.println(i+1 + ") " + toppings.get(i).getName() + dots + " (+$" + calculateToppingPrice(toppings.get(i)) + ")");
    } //end for
  } //end printToppings

  public void printDrinks(){
    for(int i = 0; i < drinks.size(); i++){
      String dots = getDots(drinks.get(i));
      double price = calculatePrice(drinks.get(i));
      DecimalFormat df = new DecimalFormat("0.00");
      if(i >= 9){
        dots = dots.substring(0, dots.length()-1);
      } //end if
      System.out.println((i+1) + ") " + drinks.get(i).getName() + dots + " $" + df.format(price));
      System.out.println(drinks.get(i).getDescription() + "\n");  
    } //end for
  } //end printDrinks

  public String getDots(Drink d){
    int numDots = 70;
    String strDots = "";
    numDots -= d.getName().length();
    numDots -= 7;
    for(int i = 0; i < numDots; i++){
      strDots += ".";
    } //end for
    return strDots;
  } //end getDots

  public String getDots(Topping t, boolean needsBobaName){
    int numDots = 30;
    String strDots = "";
    if(needsBobaName){
      numDots -= t.getBobaName().length();
    } else {
      numDots -= t.getName().length();
    } //end if else
    for(int i = 0; i < numDots; i++){
      strDots += ".";
    } //end for
    return strDots;
  } //end getDots

  @Override public double calculatePrice(Drink d){
    double price = 0;
    Topping[] toppings = d.getToppings();
    if(d.getTeaType().equals(Tea.MILK) || d.getTeaType().equals(Tea.GREEN)){
      price += teaCost[0]; //add tea cost to total price
    } else {
      price += teaCost[3];
    } //end if-else
    DecimalFormat df = new DecimalFormat("0.00");
    return Double.valueOf(df.format(price));
  } //end calculatePrice

  public double calculateNewPrice(Drink d){
  //calculates price for drink in order after user chooses size and toppings
    double price = calculatePrice(d);
    if(d.getSize().equals("Large")){
      price += 0.6;
    } //end if
    if(d.getToppings().length != 0){
      price += calculateToppingPrice(d);
    } //end if
    DecimalFormat df = new DecimalFormat("0.00");
    return Double.valueOf(df.format(price));
  } //end calculateNewPrice

  public double calculateToppingPrice(Drink d){
  //prevents nullpoint error by only calling if drink has toppings
    double price = 0;
    Topping[] toppings = d.getToppings();
    for(int i = 0; i < toppings.length; i++){
      if(toppings[i].hasPudding()){
        price += toppingCost[1]; //crystal and jelly cost the same
      } else if(toppings[i].getBobaType().equals(Boba.CRYSTAL) || toppings[i].getBobaType().equals(Boba.JELLY)){
        price += toppingCost[1]; //pudding is also same as crystal and jelly
      } else if(toppings[i].getBobaType().equals(Boba.BUBBLE)){
        price += toppingCost[0]; //bubble is cheapest
      } else {
        price += toppingCost[3]; //popping is most expensive bc it seems bougie
      } //end if-else
    } //end for
    return price;
  } //end calculateToppingPrice

  public String calculateToppingPrice(Topping t){
    double price = 0;
    if(t.hasPudding()){
      price = toppingCost[1];
    } else if(t.getBobaType().equals(Boba.CRYSTAL) || t.getBobaType().equals(Boba.JELLY)){
      price = toppingCost[1];
    } else if(t.getBobaType().equals(Boba.BUBBLE)){
      price = toppingCost[0];
    } else {
      price = toppingCost[3];
    }
    DecimalFormat df = new DecimalFormat("0.00");
    return df.format(price);
  } //end calculateToppingPrice overriden
} //end Menu








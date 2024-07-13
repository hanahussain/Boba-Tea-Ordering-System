import java.util.*;
import java.io.*;

public class Admin extends GeneralUser{
  
  public Admin(){
    super();
    this.setUsername("admin");
    this.setPassword("98765");
  } //end constructor

  public static void main(String[] args){
    Admin a = new Admin();
    //ArrayList<Drink> drinks = new ArrayList<Drink>();
    //drinks.add(new Drink("Refresher", Tea.SLUSH, "Cool and nice"));
    //drinks.add(new Drink("Milk Tea", Tea.MILK, "Classic"));
    //ArrayList<Topping> toppings = new ArrayList<Topping>();
    //toppings.add(new Topping(false, null, true));
    //toppings.add(new Topping(true, Boba.BUBBLE, false));
    //a.menu.setToppings(toppings);
    //a.menu.setDrinks(drinks);
    a.menu.printDrinks();
    System.out.println("\n TOPPINGS ");
    a.menu.printToppings();
  } //end main

  public Drink startDrink(){
    Scanner input = new Scanner(System.in);
    boolean keepGoing = true;
    
    Tea[] teas = {Tea.MILK, Tea.GREEN, Tea.OOLONG, Tea.THAI, Tea.SLUSH};
    int choice=1;
    System.out.println("\nHere are your options for tea!");
    for(int i = 0; i < teas.length; i++){
      System.out.println(i+1 + ") " + teas[i]);
    } //end for
    System.out.println("");
    boolean confirmed = false;
    while(!confirmed){ 
      while(keepGoing){
        System.out.print("Please enter an integer representing the type of tea of your new drink: ");  
        if(input.hasNextInt()){
          choice = input.nextInt();
          if(choice > 0 && choice < 6){
            keepGoing = false;
          } else {
            System.out.println("Please enter an integer between 1 and 5.");
            input.nextLine();
          } //end inner if else
        } else {
          System.out.println("Invalid input. Please enter an integer choice (1, 2, 3, 4 or 5).");
          input.nextLine(); 
        } //end if-else
      } //end keepGoing while
      input.nextLine(); 
      System.out.print("You have chosen " + choice + ": " + teas[choice-1] + ". Are you sure? (Y/N): ");
      String confirm = input.nextLine();
      if(confirm.toUpperCase().equals("Y")){
        confirmed = true;
      } else {
        System.out.println(choice + " was not selected.");
        keepGoing = true;
      } //end inner if-else
    } //end !confirmed
    keepGoing = true; 
    String flavor = pickFlavor();
    String name = "";
    while(keepGoing){
      System.out.print("Name your new drink!: ");
      name = input.nextLine();
      System.out.print("You named it " + name + ". Are you sure about this? (Y/N): ");
      String response = input.nextLine();
      if(response.toUpperCase().equals("Y")){
        keepGoing = false;
      } else {
        System.out.println("Please try naming your drink again, then answer Y to confirm.");
        input.nextLine();
      } //end if else-if 
    } //end while

    return new Drink((flavor + name), teas[choice-1]);
  } //end startDrink

  public String pickFlavor(){
    Scanner input = new Scanner(System.in);
    boolean validFlavor = false;
    boolean confirmed = false;
    int choice = 0;
    Flavor[] flavors = menu.getFlavors();
    System.out.println("Here are your options for flavorings!");
    for(int i = 0; i < flavors.length; i++){
      System.out.println((i+1) + ") " + flavors[i].toString());
    } //end for loop 
    while(!confirmed){ 
      while(!validFlavor){
        System.out.print("Please enter an integer representing the flavor of your new drink: ");  
        if(input.hasNextInt()){
          choice = input.nextInt();
          if(choice > 0 && choice < 8){
            validFlavor = true;
          } else {
            System.out.println("Your integer must be in between 1 and 7.");
            input.nextLine();
          } //end inner if else
        } else {
          System.out.println("Invalid input. Please enter an integer choice (1, 2, 3, 4 or 5).");
          input.nextLine(); 
        } //end if-else
      } //end keepGoing while
      input.nextLine(); 
      System.out.print("You have chosen " + choice + ": " + flavors[choice-1] + ". Are you sure? (Y/N): ");
      String confirm = input.nextLine();
      if(confirm.toUpperCase().equals("Y")){
        confirmed = true;
      } else {
        System.out.println(choice + " was not selected.");
        validFlavor = false;
      } //end inner if-else
    } //end !confirmed
    return flavors[choice-1].toString();
  } //end pickFlavor
  /*
  public void addDrink(){
    loadMenu();
    Drink d = startDrink();
    //boolean keepGoing = true;
    //Scanner input = new Scanner(System.in);
    
    int toppingNum=0;
    while(keepGoing){
      System.out.println("\nHow many toppings will your drink have? (up to 4): ");
      if(input.hasNextInt()){
        toppingNum = input.nextInt();
        if(toppingNum >= 0 && toppingNum <= 4){
          System.out.println("You want " + toppingNum + " toppings, right? (Y/N): ");
          String response = input.nextLine();
          if(response.toUpperCase().equals("Y")){
            keepGoing = false;
          } //end innner inner if
        } else {
          System.out.println("Your integer must be in between 0 and 4");
          input.nextLine();
        } //end inner if
      } else {
        System.out.println("Invalid input. Please enter an integer representing number of desired toppings.");
        input.nextLine();
      } //end if
    } //end while
    Topping[] toppings = chooseToppings(toppingNum);
    
    String description = makeDescription();
    //d.setToppings(toppings);
    d.setDescription(description);
    System.out.println("All done! You've made a drink named " + d.getName() + ": " + d.getTeaType());
    
    for(int i = 0; i < toppings.length; i++){
      if(i < toppings.length-1){
        System.out.println(toppings[i] + ", ");
      } else {
        System.out.println("and " + toppings[i]);
      } //end if else
    } //end for
    
    saveMenu();
  } //end addDrink

  public Topping[] chooseToppings(int toppingNum){
    Scanner input = new Scanner(System.in);
    boolean keepGoing = true;
    Topping[] toppings = new Topping[toppingNum];
    ArrayList<Topping> allToppings = menu.getToppings();
    int choice=1; 
    this.menu.printToppings();
    for(int i = 0; i < toppingNum; i++){
      keepGoing = true;
      while(keepGoing){
        System.out.println("\nEnter integer representing topping #" + (i+1) + ": ");
        if(input.hasNextInt()){ 
          choice = input.nextInt();
          if(choice > 0 && choice <= allToppings.size()){
            System.out.println("You have chosen " + allToppings.get(choice-1) + ". Are you sure? (Y/N): ");
            String response = input.nextLine();
            if(response.equals("Y")){
              keepGoing = false;
            } //end inner inner if 
          } else {
            System.out.println("Your integer must be in between 1 and " + allToppings.size());
            input.nextLine();
          } //end inner if else 
        } else {
          System.out.println("Invalid input. Please enter an integer representing topping choice (1- " + allToppings.size() + ")");
          input.nextLine();
        } //end outermost if esle
      } //end while
      toppings[i] = allToppings.get(choice-1);  
    } //end for
    return toppings;
  } //end chooseToppings
*/
  public void addDrink(){
    Drink d = startDrink();
    Scanner input = new Scanner(System.in);
    boolean keepGoing = true;
    String description = "";
    System.out.println("\nLast step! Choosing a description for your new drink (100 chars max)!");
    while(keepGoing){
      System.out.print("Enter description: ");
      description = input.nextLine();
      if(description.length() <= 100){
        System.out.print("Are you sure this is the description you want? (Y/N): ");
        String response = input.nextLine();
        if(response.toUpperCase().equals("Y")){
          keepGoing = false;
        } //end inner if 
      } else {
        System.out.println("Your description must be no longer than 100 characters. Please shorten it.");
        input.nextLine();
      } //end outer if else
    } //end while 
    d.setDescription(description);
    System.out.println("All done! You've made a drink named " + d.getName() + ": " + d.getDescription());
    //ArrayList<Drink> drinks = this.menu.getDrinks();
    this.menu.getDrinks().add(d);
    //this.menu.setDrinks(drinks);   
    this.saveMenu();   
  } //end makeDescription

  public void removeDrink(){
    loadMenu();
    Scanner input = new Scanner(System.in);
    boolean validDrink = false;
    Drink d;
    ArrayList<Drink> drinks = this.menu.getDrinks();
    this.menu.printDrinks();
    while(!validDrink){
      System.out.print("Enter the number of the drink you want to delete, or 0 to quit: "); 
      String choice = input.nextLine();
      if(choice.equals("0")){
        validDrink = true;
      } else {
        int choiceInt = Integer.parseInt(choice);
        if(choiceInt > 0 && choiceInt <= drinks.size()){
          validDrink = true;
          d = drinks.get(choiceInt-1);
          System.out.print("Are you sure you want to delete " + d.getName() + "? (Y/N): ");
          String confirm = input.nextLine();
          if(confirm.toUpperCase().equals("Y")){
            drinks.remove(d);
          } else if(confirm.toUpperCase().equals("N")){
            System.out.println("Drink was not deleted");
            validDrink = false;
          } else {
            System.out.println("Invalid input. Try again.");
          } //end innermost if else
        } else {
          System.out.println("Invalid input. Please enter a 0 or an integer from the list presented to you.");
        } //end inner if else
      } //end if else
    } //end while
    this.menu.setDrinks(drinks);
    saveMenu();
  } //end removeDrink

  public void userMenu(){
    boolean keepGoing = true;
    Scanner input = new Scanner(System.in);
    while(keepGoing){
      System.out.print("0) Quit \n1) Add Drink to Menu \n2) Delete Drink from Menu \nEnter choice: ");
      String choice = input.nextLine();
      if(choice.equals("0")){
        keepGoing = false;
        System.out.println("Successfully logged out.");
      } else if (choice.equals("1")){
        addDrink();
      } else if(choice.equals("2")){
        removeDrink();
      } else {
        System.out.println("Invalid input. Enter 0, 1 or 2 representing choice.");
      } //end if else strcuture
    } //end while
  } //end userMenu
} //end Admin

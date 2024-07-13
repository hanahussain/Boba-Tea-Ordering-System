import java.util.*;
import java.text.DecimalFormat;

public class Customer extends GeneralUser {
  private double credit;
  private int drinksBought;
 
  public static void main(String[] args){
   // Customer c = new Customer();
    //c.userMenu();
  } //end main

  public Customer(String username, String password){
    super();
    this.username = username;
    this.password = password;
  } //end constructor

  public void setCredit(double credit){
    this.credit = credit;
  } //end setCredit

  public double getCredit(){
    return this.credit;
  } //end getCredit

  public int getDrinksBought(){
    return this.drinksBought;
  } //end getDrinksBought

  public void setDrinksBought(int drinkNum){
    drinksBought = drinkNum;
  } //end setDrinksBought

  public boolean earnedFreeDrink(){
    return (this.drinksBought >= 15);
  } //end earnedFreeDrink

  public ArrayList<Drink> makeOrder(){
    boolean keepGoing = true;
    ArrayList<Drink> order = new ArrayList<Drink>();
    boolean changedMind = false;
    while(keepGoing){
      order.add(pickSpecifics());
      if(!order.get(order.size()-1).getName().equals("")){ //check for if they want multiple of same drink
        int numDrinks = chooseQuantity(order);
        for(int i = 1; i < numDrinks; i++){ //dont account for the drink already added
          order.add(order.get(order.size()-1));
        } //end for
      } //end if
      if(order.get(order.size()-1).getName().equals("")){ //if user wanted to leave select menu, drink selected will be nothing
        changedMind = true;
        order.remove(order.size()-1);
      } else if(!wantsAnotherDrink()){
        keepGoing = false;
      } //end if
      if(keepGoing != false && !changedMind){
        innerDecision(order, false);
      } //end if
      changedMind = false;
    } //end while
    innerDecision(order, true);
    return order;
  } //end makeOrder 

  public int chooseQuantity(ArrayList<Drink> order){
    Scanner input = new Scanner(System.in);
    boolean validAmount = false;
    int quantity = 1;
    while(!validAmount){
      System.out.print("\nEnter quantity of this drink to order: ");
      if(input.hasNextInt()){
        quantity = input.nextInt();
        if(quantity > 0){
          if(quantity <= 30){
            validAmount = true;
          } else {
            System.out.println("You're capped at 30 drinks. Please enter a lower quantity.");
            input.nextLine();
          } //end inner if else
        } else {
          input.nextLine();
          System.out.println("Please enter a non-zero number. If needed, you can delete this drink later.");
        } //end inner if else
      } else {
        System.out.println("Invalid input. Please enter an integer representing the amount of this drink you want.");
        input.nextLine();
      } //end if else
    } //end while
    return quantity;
  } //end chooseQuantity

  public Drink chooseDrink(){
    loadMenu();
    Scanner input = new Scanner(System.in);
    boolean validInput = false;
    String menuType = "";
    int amtDrinks = 0;
    ArrayList<Drink> drinks = this.menu.getDrinks();
    Drink selection = new Drink("", null);
    Tea teaType = Tea.MILK;
    Flavor flavorType = Flavor.MANGO;
    String filter = "Search";
    System.out.println("\n1) Order from full menu \n2) Filter Menu by Tea \n3) Filter Menu by Flavor \n4) Filter Menu by Search");
    while(!validInput){
      System.out.print("Enter menu option: ");
      menuType = input.nextLine();
      if(menuType.equals("1")){
        this.menu.printDrinks();
        validInput = true;
      } else if (menuType.equals("2")){
        validInput = true;
        teaType = pickTea();
        amtDrinks = printSelect(teaType);
      } else if(menuType.equals("3")){
        validInput = true;
        flavorType = pickFlavor();
        amtDrinks = printSelect(flavorType);
      } else if(menuType.equals("4")){ 
        validInput = true;
        filter = pickFilter();
        amtDrinks = printSelect(filter);
      } else {
        System.out.println("Invalid input. Please enter a 1, 2, 3 or 4.");
      } //end if-else    
    } //end while
    int choice = makeDrinkChoice(menuType, amtDrinks, teaType, flavorType, filter);
    if(choice != 0){
      if(confirmDrink(choice, menuType, teaType, flavorType)){
        if(menuType.equals("1")){ //only selections from unfiltered list must be altered
          selection = drinks.get(choice-1);
        } else {
          selection = drinks.get(choice); //convertChoice made this correct, so no alteration needed
        } //end innermost if else
      } //end confirmDrink if
    } //end choice if
    return selection;   
  } //end chooseDrink

  public Tea pickTea(){ 
    Scanner input = new Scanner(System.in);
    System.out.println("\nPick a tea type to view to filter menu.");
    System.out.println("\n1) Milk \n2) Green \n3) Oolong \n4) Thai \n5) Slush");
    int teaInt = 0;
    boolean validInput = false;
    while(!validInput){
      System.out.print("Enter integer tea choice: ");
      if(input.hasNextInt()){
        teaInt = input.nextInt();
        if(teaInt > 0 && teaInt < 6){
          validInput = true;
        } else {
          System.out.println("Invalid input. Your integer must be in between 1 and 5.");
          input.nextLine();
        } //end inner if else
      } else {
        System.out.println("Invalid input. Enter a tea choice (any integer 1-5)");
        input.nextLine();
      } //end if-else
    } //end while
    Tea teaType;
    if(teaInt == 1){
      teaType = Tea.MILK;
    } else if(teaInt == 2){
      teaType = Tea.GREEN;
    } else if(teaInt == 3){
      teaType = Tea.OOLONG;
    } else if(teaInt == 4){
      teaType = Tea.THAI;
    } else {
      teaType = Tea.SLUSH;
    } //end if-else structure
    System.out.println("\n----------" + teaType.toString() + " Menu----------\n");
    return teaType;
  } //end pickTea

  public int printSelect(Tea teaType){ 
    loadMenu();
    ArrayList<Drink> drinks = this.menu.getDrinks();
    int counter = 0;
    DecimalFormat df = new DecimalFormat("0.00");
    System.out.println("0) Back to Menu selection\n");
    for(int i = 0; i < drinks.size(); i++){
      if(drinks.get(i).getTeaType().equals(teaType)){
        counter++;
        System.out.println(counter + ") " + drinks.get(i).getName() +  this.menu.getDots(drinks.get(i)) + "$" + df.format(this.menu.calculatePrice(drinks.get(i))));
        System.out.println(drinks.get(i).getDescription() + "\n"); 
      } //end if
    } //end for
    return counter; //tells us how many teas the user had to choose from
  }

  public Flavor pickFlavor(){
    Scanner input = new Scanner(System.in);
    System.out.println("Pick a flavor type to filter menu.");
    System.out.println("\n1) Mango \n2) Strawberry \n3) Peach \n4) Brown Sugar \n5) Taro \n6) Matcha \n7) Honey");
    int flavorInt = 0;
    boolean validInput = false;
    while(!validInput){
      System.out.print("Enter integer flavor choice: ");
      if(input.hasNextInt()){
        flavorInt = input.nextInt();
        if(flavorInt > 0 && flavorInt < 8){
          validInput = true;
        } else {
          System.out.println("Invalid input. Your integer must be in between 1 and 7.");
          input.nextLine();
        } //end inner if-else
      } else {
        System.out.println("Invalid input. Please enter a flavor choice (integer from 1-7)"); 
        input.nextLine(); 
      } //end if-else
    } //end while
    Flavor flavorType;
    if(flavorInt == 1){
      flavorType = Flavor.MANGO;
    } else if(flavorInt == 2){
      flavorType = Flavor.STRAWBERRY;
    } else if(flavorInt == 3){
      flavorType = Flavor.PEACH;
    } else if(flavorInt == 4){
      flavorType = Flavor.BROWN_SUGAR;
    } else if(flavorInt == 5){
      flavorType = Flavor.TARO;
    } else if(flavorInt == 6){
      flavorType = Flavor.MATCHA;
    } else {
      flavorType = Flavor.HONEY;
    } //end if else structure
    System.out.println("\n----------" + flavorType.toString() + "Menu----------\n");
    return flavorType;
  } //end pickFlavor

  public int printSelect(Flavor flavorType){
    loadMenu();
    ArrayList<Drink> drinks = this.menu.getDrinks();
    int counter = 0;
    DecimalFormat df = new DecimalFormat("0.00");
    System.out.println("0) Back to Menu Selection\n");
    for(int i = 0; i < drinks.size(); i++){
      if(drinks.get(i).getName().contains(flavorType.toString())){ 
        counter++;
        Drink currDrink = drinks.get(i);
        System.out.println(counter + ") " + currDrink.getName() + this.menu.getDots(currDrink) + "$" + df.format(this.menu.calculatePrice(currDrink)));
        System.out.println(currDrink.getDescription() + "\n");
      } //end if
    } //end for
    return counter;
  } //end printSelect

  public String pickFilter(){
    Scanner input = new Scanner(System.in);
    System.out.print("Enter word or phrase to filter menu: ");
    String filter = input.nextLine();
    return filter;  
  } //end chooseFilterSearch

  public int printSelect(String filter){
    System.out.println("Search results for \"" + filter + "\": \n");
    System.out.println("0) Return to Menu Selection\n");
    DecimalFormat df = new DecimalFormat("0.00");
    int counter = 0;
    for(int i = 0; i < menu.getDrinks().size(); i++){
      Drink currDrink = menu.getDrinks().get(i);
      if(containsIgnoringCase(filter, currDrink)){ 
        counter++;
        System.out.println(counter + ") " + currDrink.getName() + menu.getDots(currDrink) + "$" + df.format(menu.calculatePrice(currDrink)));
        System.out.println(currDrink.getDescription() + "\n");
      } //end if
    } //end for
    return counter;
  } //end filterMenu

  public boolean containsIgnoringCase(String filter, Drink d){
    boolean contains = false;
    filter.toLowerCase();
    String name = d.getName().toLowerCase();
    if(name.contains(filter)){
      contains = true;
    } else if(filter.contains(name)){
      contains = true;
    } else if(offByOne(filter, d)){
      contains = true;
    } //end if else 
    return contains;
  } //end containsIgnoreCase

  public boolean offByOne(String filter, Drink d){
//accounts for typos when user is entering word to filter search
    String name = d.getName();
    String smaller = filter;
    String bigger = name;
    int beginning = 0;
    boolean foundMatch = false;
    if(name.length() < filter.length()){
      smaller = name;
      bigger = filter;
    } //end if
    for(int i = 0; i < bigger.length()-1; i++){
      for(int j = 0; j < smaller.length()-1; j++){
        if(smaller.charAt(j) == bigger.charAt(i) && smaller.charAt(j+1)==bigger.charAt(i+1)){
          beginning = i;
          j = smaller.length(); //break out of inner loop
          foundMatch = true;
        } //end if  
      } //end inner for
      if(foundMatch){ //prevents containsOffByTwo being called unnecessarily
        if(containsOffByOne(smaller, bigger.substring(beginning))){
          return true;
        } //end if
      } //end outer if
    } //end for
    return false;
  } //end offByTwo
  
  public boolean containsOffByOne(String smaller, String bigger){
    int sameChars = 0;
    String nowSmaller = smaller;
    String nowBigger = bigger;
    if(nowSmaller.length() > nowBigger.length()){
      nowSmaller = bigger;
      nowBigger = smaller;
    } //end if
    for(int i = 0; i < nowSmaller.length(); i++){
      if(nowSmaller.charAt(i) == nowBigger.charAt(i)){
        sameChars++;
      } //end for
    } //end for 
    return (sameChars-nowSmaller.length() <= 1 && sameChars-nowSmaller.length() >= -1);
  } //end containsOffByTwo

  public int makeDrinkChoice(String menuType, int amtDrinks, Tea teaType, Flavor flavorType, String filter){
  //follows printing of menu THIS FUNCTION STILL WORKS REGARDLESS OF FILTER
    boolean validInput = false;
    String choice = "-1";
    Scanner input = new Scanner(System.in);
    boolean canConvert = false;
    while(!validInput){
      System.out.print("\nPlease enter an integer representing your drink choice: ");
      choice = input.nextLine();
      int choiceInt = 0;
      try{
        choiceInt = Integer.parseInt(choice);
        canConvert = true;
      } catch (NumberFormatException e){
        System.out.println("Invalid input. Please enter a number from the list presented to you.");
      } //end try catch
      if(menuType.equals("1")){
        if(canConvert){
          if(choiceInt > 0 && choiceInt <= this.menu.getDrinks().size()){
            validInput = true;
          } else {
            System.out.println("Invalid input. Enter an integer from the menu list presented to you.");
          } //end inner if else
        } //end canConvert
      } else { //select menu was printed
        if(canConvert){
          if(choiceInt >= 0 && choiceInt <= amtDrinks){
            validInput = true;
            choice = convertChoice(choiceInt, menuType, teaType, flavorType, filter);
          } else {
          System.out.println("Invalid input. Enter an integer from the menu list presented to you.");
          } //end innermost if else
        } //end inner if esle
      } //end outer if else
    } //end while
    return Integer.parseInt(choice);
  } //end makeDrinkChoice

  public String convertChoice(int choice, String menuType, Tea teaType, Flavor flavorType, String filter){
    ArrayList<Drink> drinks = this.menu.getDrinks();
    int counter = 0;
    int finalChoice = 0;
    if(menuType.equals("2")){
      for(int i = 0; i < drinks.size(); i++){
        if(drinks.get(i).getTeaType().equals(teaType)){
          counter++;
          if(counter == choice){
            finalChoice = i;
          } //end inner if
        } //end outer if 
      } //end for
    } else if(menuType.equals("3")){
      for(int i = 0; i < drinks.size(); i++){
        if(drinks.get(i).getName().contains(flavorType.toString())){
          counter++;
          if(counter == choice){
            finalChoice = i;
          } //end innermost if
        } //end if
      } //end for
    } else if(menuType.equals("4")){
      for(int i = 0; i < drinks.size(); i++){
        if(containsIgnoringCase(filter, drinks.get(i))){
          counter++;
          if(counter == choice){
            finalChoice = i;
          } //end innermost if
        } //end if 
      } //end for
    } //end outermost if else
    return String.valueOf(finalChoice);
  } //end convertChoice

  public boolean confirmDrink(int choice, String menuType, Tea teaType, Flavor flavorType){
  //if menuType is 2, loop through drinks and increment counter based on teaType match, set currDrink accordingly
  //Do while loop until they're sure they wanna add or not add drink to cart
  //return their final decision 
    ArrayList<Drink> drinks = this.menu.getDrinks();
    Drink currDrink = drinks.get(0);
    if(menuType.equals("1")){
      currDrink = drinks.get(choice -1);
    } else {
      currDrink = drinks.get(choice);
    }           
    boolean decided = false;;
    Scanner input = new Scanner(System.in);
    boolean confirmed = false;
    while(!decided){
      System.out.print("Are you sure you want to add " + currDrink.getName() + " to your cart? (Y/N): ");
      String confirmStr = input.nextLine();
      if(confirmStr.toUpperCase().equals("Y")){
        decided = true;
        confirmed = true;
      } else if (confirmStr.toUpperCase().equals("N")){
        decided = true;
        System.out.println("Drink was not added to cart.");
      } else {
        System.out.println("Invalid input. Please enter Y to confirm drink or N to not add drink to cart.");
      } //end outer if else
    } //end while
    return confirmed;
  } //end confirmDrink 

  public boolean wantsAnotherDrink(){
    boolean wantsAnother = false;
    boolean validInput = false;
    Scanner input = new Scanner(System.in);
    while(!validInput){
      System.out.print("Do you want to order another drink? (Y/N): ");
      String choice = input.nextLine();
      if(choice.toUpperCase().equals("Y")){
        validInput = true;
        wantsAnother = true;
      } else if (choice.toUpperCase().equals("N")){
        validInput = true;
      } else {
        System.out.println("Invalid input. Please enter Y to order another drink or N to stop ordering.");
      } //end if else
    } //end while
    return wantsAnother;
  } //end wantsAnotherDrink

  public void innerDecision(ArrayList<Drink> order, boolean last){
  //act as a rest stop for user to make decisions mid ordering
    Scanner input = new Scanner(System.in);
    boolean keepGoing = true;
    System.out.print("\nWhat would you like to do?");
    while(keepGoing){
      if(last){
        System.out.print("\n0) Continue to Checkout \n1) View Order \n2) Delete Item(s) from Cart \nEnter integer choice: ");
      } else {
        System.out.print("\n0) Continue Ordering \n1) View Order \n2) Delete Item(s) from Cart \nEnter integer choice: ");
      } //end if
      String choice = input.nextLine();
      if(choice.equals("0")){
        keepGoing = false;
      } else if(choice.equals("1")){
        printOrder(order);
      } else if(choice.equals("2")){
        deletionMenu(order);
      } else {
        System.out.println("Invalid input. Please enter 0, 1 or 2 representing your decision.");
      } //end if else
    } //end while
  } //end innerDecision

  public void printOrder(ArrayList<Drink> order){
    double total = 0;
    System.out.println("\n---------Order Summary---------\n");
    DecimalFormat df = new DecimalFormat("0.00");
    for(int i = 0; i < order.size(); i++){
      Drink currDrink = order.get(i);
      String dots = menu.getDots(currDrink);
      if(i >= 9){
        dots = dots.substring(0, dots.length()-1);
      }
      double cost = this.menu.calculateNewPrice(currDrink);
      total += cost;
      System.out.println((i+1) + ") " + currDrink.getName() + dots + "$" + df.format(cost));
      System.out.println(currDrink.getSize());
      System.out.println(printChosenToppings(currDrink) + "\n");
    } //end for
    System.out.println("-----------TOTAL-----------\n$" + df.format(total) + "\n");
  } //end printOrder

  public String printChosenToppings(Drink d){
    String toppings = "Toppings: ";
    Topping[] toppingList = d.getToppings();
    if(toppingList.length == 0){
      toppings += "None";
    } else if (toppingList.length == 1){
      if(toppingList[0].hasPudding() || toppingList[0].getBobaType().equals(Boba.BUBBLE)){
        toppings += toppingList[0].getBobaName();
      } else {
        toppings += toppingList[0].getName();
      } //end inner if else
    } else {
      if(toppingList[0].hasPudding() || toppingList[0].getBobaType().equals(Boba.BUBBLE)){   
        toppings += toppingList[0].getBobaName() + " and ";
      } else {
        toppings += toppingList[0].getName() + " and ";
      }
      if(toppingList[0].hasPudding() || toppingList[0].getBobaType().equals(Boba.BUBBLE)){
        toppings += toppingList[1].getBobaName();
      } else {
        toppings += toppingList[1].getName();
      }
    } //end if else
    return toppings;
  }

  public Drink pickSpecifics(){
    Drink d = chooseDrink();
    if(!d.getName().equals("")){
      Scanner input = new Scanner(System.in);
      boolean keepGoing = true;
      while(keepGoing){
        System.out.print("\n-Medium (+$0.00) \n-Large (+$0.60) \nEnter drink size: ");
        String size = input.nextLine();
        if(size.toUpperCase().equals("LARGE")){
          d.setSize("Large");
          keepGoing = false;
        } else if(size.toUpperCase().equals("MEDIUM")){
          keepGoing = false;
        } else {
          System.out.println("Invalid input. Please enter medium or large.");
        } //end if else
        if(keepGoing == false){
          System.out.print("You have chosen " + d.getSize() + ". Confirm drink size? (Y/N): ");
          String confirm = input.nextLine();
          if(confirm.toUpperCase().equals("N")){
            keepGoing = true;
          } else if(!confirm.toUpperCase().equals("Y")){
            keepGoing = true;
            System.out.println("Invalid input.");
          } //end if else
        } //end if
      } //end while
      d.setToppings(pickToppings());
    } //end if
    return d;
  } //end pickSpecifics

  public int pickToppingNum(){
    Scanner input = new Scanner(System.in);
    boolean keepGoing = true;
    int toppingNum=0;
    while(keepGoing){
      System.out.print("\nHow many toppings will your drink have? (up to 2): ");
      if(input.hasNextInt()){
        toppingNum = input.nextInt();
        if(toppingNum >= 0 && toppingNum <= 2){
          input.nextLine(); //clears buffer
          System.out.print("You want " + toppingNum + " toppings, right? (Y/N): ");
          String response = input.nextLine();
          if(response.toUpperCase().equals("Y")){
            keepGoing = false;
          } //end innner inner if
        } else {
          System.out.println("Your integer must be in between 0 and 2");
          input.nextLine();
        } //end inner if
      } else {
        System.out.println("Invalid input. Please enter an integer representing number of desired toppings.");
        input.nextLine();
      } //end if
    } //end while
    return toppingNum;
  }//end pickToppingNum

  public Topping[] pickToppings(){
    int toppingNum = pickToppingNum();
    Scanner input = new Scanner(System.in);
    boolean keepGoing = true;
    Topping[] toppings = new Topping[toppingNum];
    ArrayList<Topping> allToppings = this.menu.getToppings();
    int choice = 1;
    if(toppingNum > 0){ 
      this.menu.printToppings();
    }
    for(int i = 0; i < toppingNum; i++){
      keepGoing = true;
      while(keepGoing){
        System.out.print("\nEnter integer representing topping #" + (i+1) + ": ");
        if(input.hasNextInt()){ 
          choice = input.nextInt();
          if(choice > 0 && choice <= allToppings.size()){
            input.nextLine(); //clear Scanner buffer
            if(choice == 1 || choice == 2){
              System.out.print("You have chosen " + allToppings.get(choice-1).getBobaName() + ". Are you sure? (Y/N): ");
            } else {
              System.out.print("You have chosen " + allToppings.get(choice-1).getName() + ". Are you sure? (Y/N): ");
            } //end if else for name formatting
            String response = input.nextLine();
            if(response.toUpperCase().equals("Y")){
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
  } //end pickToppings

  public void deletionMenu(ArrayList<Drink> order){
    Scanner input = new Scanner(System.in);
    boolean keepGoing = true;
    boolean deletionOccurred = false;
    double total = 0;
    int target = 1;
    DecimalFormat df = new DecimalFormat("0.00");
    while(keepGoing){
      System.out.println("\n---------Order Summary--------");
      for(int i = 0; i < order.size(); i++){
        Drink currDrink = order.get(i);
        double cost = this.menu.calculateNewPrice(currDrink);
        total += cost;
        if(i == target-1){
          System.out.println("==>" + (i+1) + ") " + currDrink.getName() + this.menu.getDots(currDrink) + "$" + df.format(cost) + "<==");
        } else {
          if(!deletionOccurred){
            System.out.println((i+1) + ") " + currDrink.getName() + this.menu.getDots(currDrink) + "$" + df.format(cost));
          } //end inner if
        } //end if else
      } //end for
      System.out.println("-----------TOTAL----------\n$" + df.format(total)); 
      total = 0;
      System.out.print("\nEnter D to delete the selected drink, press enter to select next drink, or enter 0 to quit: ");
      String choice = input.nextLine();
      if(choice.toUpperCase().equals("D")){
        System.out.print("Are you sure you want to delete " + order.get(target-1).getName() + "? (Y/N): ");
        String confirm = input.nextLine();
        if(confirm.toUpperCase().equals("Y")){
          order.remove(target-1);
          System.out.println("\nDrink was deleted from your order.");
        } else if(confirm.toUpperCase().equals("N")){
          System.out.println("\nDrink was not deleted");
          target = incrementTarget(target, order.size());
        } else {
          System.out.println("Invalid input. Nothing happened.");
        } //end inner if else
      } else if(choice.equals("0")){
        System.out.println("Exiting deletion menu...");
        keepGoing = false;
      } else if(choice.equals("")){
        target = incrementTarget(target, order.size());
      } else {
        System.out.println("\nInvalid input. Nothing happened.");
      } //end outer if else
      deletionOccurred = false;
    } //end while
  } //end deletionMenu

  public int incrementTarget(int target, int size){
    target++;
    if(target > size){
      target = 1;
    } //end if
    return target;
  } //end incrementTarget

  public double printFinalOrder(ArrayList<Drink> order){
//designed formatting if they earned a free drink
    double total = 0;
    Drink cheapestDrink = order.get(0);
    double cheapest = menu.calculateNewPrice(order.get(0));
    for(int i = 1; i < order.size(); i++){
      if(menu.calculateNewPrice(order.get(i)) < cheapest){
        cheapestDrink = order.get(i);
        cheapest = menu.calculateNewPrice(order.get(i));
      } //end if
    } //end for
    System.out.println("\n---------Order Summary---------\n");
    DecimalFormat df = new DecimalFormat("0.00");
    for(int i = 0; i < order.size(); i++){
      Drink currDrink = order.get(i);
      if(!currDrink.equals(cheapestDrink)){ 
        double cost = this.menu.calculateNewPrice(currDrink);
        total += cost;
        System.out.println((i+1) + ") " + currDrink.getName() + this.menu.getDots(currDrink) + "$" + df.format(cost));
      } else {
        String dots = menu.getDots(currDrink);
        dots += "...";
        System.out.println((i+1) + ") " + currDrink.getName() + dots + "FREE");
      } //end if else
      System.out.println(currDrink.getSize());
      System.out.println(printChosenToppings(currDrink) + "\n");
    } //end for
    System.out.println("-----------TOTAL-----------\n$" + df.format(total) + "\n");
    double tax = total * 0.07;
    System.out.println("Tax: " + df.format(tax));
    total += tax;
    System.out.println("--------FINAL TOTAL--------\n$" + df.format(total) + "\n");
    return total;
  } //end printFinalOrder

  public void checkout(ArrayList<Drink> order){
    double total = 0;
    Scanner input = new Scanner(System.in);
    if(earnedFreeDrink()){
      System.out.println("\nYou've met your 15 drink punch card requirement! One drink is free!");
      total = printFinalOrder(order);
    } else {
      printOrder(order);
      total = calculateTaxedTotal(order);
    } //end if else
    boolean keepGoing = true;
    DecimalFormat df = new DecimalFormat("0.00");
    while(keepGoing){
      System.out.print("\nWhat do you want to do? \n1) Check Credit \n2) Checkout \n3) Cancel Order \nEnter choice: ");
      String choice = input.nextLine();
      if(choice.equals("1")){
        System.out.println("Here's your credit: $" + df.format(this.getCredit()));
      } else if(choice.equals("2")){
        if(total <= this.getCredit()){
          if(total > 0){
            double credit = this.getCredit()-total;
            this.setCredit(Double.valueOf(df.format(credit)));
            System.out.println("\nOrder complete! Your new store credit is $" + df.format(credit));
            keepGoing = false;
            if(earnedFreeDrink()){
              drinksBought -= 15;
            } //end if
            for(int i = 0; i < order.size(); i++){
              this.drinksBought++;
            } //end for
            cancelOrder(order);
            System.out.println("\nSecurely logging you out of our system...");
          } else {
            System.out.println("You have nothing in your cart. Logging you out of our system...");
            keepGoing = false;
          }
        } else {
          System.out.println("You can't afford this order....");
        } //end inner if else
      } else if(choice.equals("3")){
          System.out.print("Warning--You are about to delete your order. Confirm cancellation? (Y/N): ");
          String response = input.nextLine();
          if(response.toUpperCase().equals("Y")){
            keepGoing = false;
            cancelOrder(order);
            System.out.println("Your order has been cancelled and you have been logged out");
          } else if(response.toUpperCase().equals("N")){
            System.out.println("Order not cancelled. Back to menu...");
          } else {
            System.out.println("Invalid input. Back to menu...");
          } //end if else
      } else {
        System.out.println("Invalid input. Enter an integer between 1 and 3");
      } //end outer if else
    } //end while 
  } //end checkout /


  public double calculateTaxedTotal(ArrayList<Drink> order){
    double tax = 0;
    double total = 0;
    for(int i = 0; i < order.size(); i++){
      total += this.menu.calculateNewPrice(order.get(i));
    }
    DecimalFormat df = new DecimalFormat("0.00");
    tax = total * 0.07;
    total += tax;
    String strTotal = df.format(total);
    String strTax = df.format(tax);
    System.out.println("Tax: $" + strTax);
    System.out.println("\n--------FINAL TOTAL-------\n$" + strTotal);
    return Double.valueOf(df.format(total));
  } //end calculateTaxedTotal

  public void cancelOrder(ArrayList<Drink> order){
    for(int i = 0; i < order.size(); i++){
      order.remove(i);
      i--; //since remove offsets the indices by 1
    } //end for
  } //end csncelOrder
   
  @Override public void userMenu(){
    boolean keepGoing = true;
    Scanner input = new Scanner(System.in);
    System.out.print("\nWelcome back to BestTea's Ordering System! \n\nWhat will you do?");
    while(keepGoing){
      System.out.print("\n0) Quit \n1) Check Credit \n2) Change Credit \n3) Check Punch Card \n4) Place Order \nEnter choice: ");
      String choice = input.nextLine();
      if(choice.equals("0")){
        keepGoing = false;
      } else if(choice.equals("1")){
        DecimalFormat df = new DecimalFormat("0.00");
        System.out.println("Here's your store credit: $" + df.format(this.getCredit()));
      } else if(choice.equals("2")){
        System.out.print("1) Withdraw \n2) Deposit \nEnter choice: ");
        String choiceStr = input.nextLine();
        if(choiceStr.equals("1")){
          changeBalance(false);
        } else if(choiceStr.equals("2")){
          changeBalance(true);
        } else {
          System.out.println("Invalid input. Returning to previous menu...");
        } //end if
      } else if(choice.equals("3")){
        System.out.println("You've bought " + drinksBought + " out of 15 drinks needed for a free drink.");
        if(earnedFreeDrink()){
          System.out.println("One item from your next order will be free as you have bought enough drinks!");
        } //end if
      }else if(choice.equals("4")){
        checkout(makeOrder());
        keepGoing = false;
      } else {
        System.out.println("Invalid input. Please enter 0, 1, 2, 3, or 4.");
      } //end if esle
    } //end while
  } //end userMenu

  public void changeBalance(boolean isDeposit){
    Scanner input = new Scanner(System.in);
    double amount = 0;
    boolean validInput = false;
    while(!validInput){
      try{
        validInput = true;
        if(isDeposit){
          System.out.print("Enter an amount of money to deposit as credit: ");
        } else {
          System.out.print("Enter amount of credit to withdraw as money: ");
        } //end if-else
        String strAmount = input.nextLine();
        amount = Double.parseDouble(strAmount);
        if(!isDeposit && amount > this.getCredit()){
          validInput = false;
          System.out.println("You can't withdraw more than you have....");
        } //end if
      } catch(NumberFormatException e){
        System.out.println("Enter a decimal amount of money (don't use a dollar sign)");
        validInput = false;
      } //end try catch
    } //end while
    DecimalFormat df = new DecimalFormat("0.00");
    amount = Double.valueOf(df.format(amount));
    if(isDeposit){
      double totalAmount = amount + this.getCredit();
      totalAmount = Double.valueOf(df.format(totalAmount));
      this.setCredit(totalAmount);
      System.out.println("Amount deposited: $" + df.format(amount));
    } else {
      double totalAmount = this.getCredit() - amount;
      totalAmount = Double.valueOf(df.format(totalAmount)); 
      this.setCredit(totalAmount);
      System.out.println("Amount withdrawn: $" + df.format(amount));
    } //end if else
  } //end changeBalance
 
//filter menu based off flavors or keywords entered
//specialty drinks
//view previous orders
//discounts on drinks not doing as well

} //end Customer






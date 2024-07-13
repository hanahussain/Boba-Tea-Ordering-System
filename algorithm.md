Admin login:

--username: admin

--password: 98765

Previously made customer login:

--username: yee

--password: yee

## Final Project - Boba Tea Ordering Simulator Algorithm

### (abstract) GeneralUser class

#### Goal 
Create blueprint for user types who will use shop system

#### Input
String username and password attributes; Menu attribute

#### Output
None

*setters, getters, Constructor work as usual, no algorithm needed*


#### saveMenu method

#### Goal
Save the current status of Menu attribute

#### Input
None

#### Output
None

#### Steps
In a try clause:
  Make a new file output stream named fileOS and connect it to “menu.dat”
  Make a new object output stream named objectOS and connect it to fileOS
  Use objectOS to write the menu object
In a catch class with Exception e:
  Print the error message

#### loadMenu method

#### Goal
Load the current status of Menu into memory

#### Input
None

#### Output
None

#### Steps
In a try clause:
  Make a new file input stream named fileIS and connect it to “menu.dat”
  Make a new object output stream named objectIS and connect it to fileIS
  Use objectIS to read the menu as an object before casting it as type Menu
In a catch clause with exception e:
  Print the error message


### Customer class

#### Input
Integer attributes credit and drinksBought

#### Output
None

*setCredit, getCredit, setDrinksBought, getDrinksBought work as usual, no algorithm needed*

#### changeBalance method

#### Input
boolean isDeposit

#### Output
None (void return type)

#### Steps
Initialize local boolean validInput to false

Initialize double amount to 0

Make a new Scanner object named input

Set up a while loop with condition not validInput:
  In a try clause:

    Set validInput to true

    If isDeposit is true:

      Ask the user how much money they want to deposit as store credit

    Otherwise:

      Ask user how much credit they want to withdraw

    Set the next line of input into local String strAmount

    Set amount equal to the double value of strAmount (this is why try clause needed)

    If it’s not a deposit and amount is more than the customer’s current credit:

      Set validInput to false

      Print error message to user

    In a catch clause with NumberFormatException:

      Tell user to enter a decimal amount of money
      
      Set validInput to false

    Make a new DecimalFormat object df with the format “0.00”

    Set amount equal to the double value of df formatting amount

    If it’s a deposit:

      Set local double totalAmount to amount added to the customer’s current credit

      Set the current credit to totalAmount

      Let user know how much they deposit

    Otherwise:

      Set totalAmount to the current credit minus amount

      Set the current credit to totalAmount

      Tell the user how much they withdrew


#### earnedFreeDrink method

#### Goal
Determine whether or not the customer has bought enough drinks to earn a free drink

#### Input
None

#### Output
boolean earnedDrink 

#### Steps
Set boolean earnedDrink to false

If drinksBought is greater than or equal to 15:

  Set earnedDrink to true

Return earnedDrink


#### makeOrder method

#### Goal
Allow customer to add drinks to their “cart”

#### Input
None

#### Output
ArrayList of Drink objects representing the customer’s order

#### Steps
Initialize boolean keepGoing to true

Make a new ArrayList of Drink objects named order

Initialize boolean changedMind to false

Set up a while loop with condition keepGoing:

  Add the method call of chooseDrink to the order

  If the last drink in the order has a non-empty name:

    Call chooseQuantity and set it equal to numDrinks

    Loop through numDrinks with i starting at 1:

      Add the last drink as the newest last drink of the order

  If the last drink in the order has an empty name (they wanted to go back to menu selection):

    Set changedMind to true

    Remove the last item in the order

  Otherwise:

    If the method call of wantsAnotherDrink returns false:

      Set keepGoing to false

  If keepGoing is not false and the user hasn’t changed their mind:

    Call innerDecision with arguments order and false

  Set changedMind to false

Call innerDecision with arguments order and true

Return order


#### chooseQuantity method

#### input
None

#### Output
Integer quantity

#### Steps
Make a new Scanner object named input

Set boolean validAmount to false

Set up a while loop with condition not validAmount:

  Print asking user for quantity

  If their response can be converted to an integer:

    Set quantity equal to the next integer of the Scanner object

    If quantity is more than 0:
 
      If quantity is less than or equal to 30:

        Set validAmount to true

      Otherwise:

        Tell user theyre capped at 30 drinks

    Otheriwse:

      Tell user to enter a non-zero number

  Otherwise:
 
    Print invalid input message

Return quantity

   

Initialize integer quantity to 1

#### deleteFromCart method

#### Input
ArrayList of Drinks representing the customer’s order

#### Output
None

#### Steps
Set boolean keepGoing to true

Make a new Scanner object named input

Initialize integer removal to 0

Set up a while loop with condition keepGoing:

  Ask the user if they want to remove anything from their card

  Store their response in String response

  If response is equal to Y:

    Ask user to enter the int of the drink they want to remove

  If their response can be converted to an integer:

    Store their response in removal

    If removal is in between 1 and the size of the order:

      Ask confirmation message to user, store in confirm

      If confirm is equal to Y:

        Remove the removal’th element of order

        Print removal message

      Otherwise:

        Print non-removal message

    Print message asking user if they want to remove another drink

    Store the user’s response in String anotherOne

    If anotherOne is equal to Y:

      Set keepGoing to false

  Otherwise:

    Print invalid input message to user

  Otherwise:

    Set keepGoing to false

    Tell user their order stayed the same



#### chooseDrink method

#### Goal
Allows user to choose a drink to add to their cart

#### Input
None

#### Output
Drink selection

#### Steps
Create an admin object named a

Call the getDrinks getter method on a and set it equal to local ArrayList drinks

Make a new Scanner object named input

Initialize a boolean selectionMade to false

Initialize a boolean validInput to false

Initialize Drink selection calling null constructor

Initialize integer teaInt to 0

Initialize integer choice to 0

Set up a while loop with condition not selectionMade:

  Set up a while loop with condition not validInput:

    Ask the user which tea type they want: 1) Black, 2) Green, 3) Oolong, 4) Thai, or 5) Slush

    If the user’s response can be converted to an int:

      Store the response in local int intInput

      If the intInput is in between 1 and 5:

        Set teaInt to intInput        

        Set validInput to true

      Otherwise:

         Print message saying to enter an integer between 1 and 5

  Otherwise:

    Print invalid input message 

  Call printMenu method with teaInt parameter

  Set choice equal to the method call of makeDrinkChoice

  If choice isn’t 0:

    If the confirmDrink method call with argument choice-1 is true:

      Set selectionMade to true

      Set selection equal to the choice-1’th element of drinks ArrayList

Return selection


####pickTea method

#### Goal
Let user pick which tea they want to use to filter menu

#### Input
None

#### Output
Choice of type Tea

#### Steps
Make a new Scanner object named input

Ask user if they want 1) Milk 2) Green 3) Oolong 4) Thai or 5) Slush

Initialize integer teaInt to 0

Set boolean validInput to false

Set up a while loop with condition not validInput:

  Print out asking user for integer tea choice

  If their input can be converted into an integer:

    Convert input to int and set it equal to teaInt

    If teaInt is between 1 and 5:

      Set validInput to true

    Otherwise:

      Print message telling user to put correct bounds

  Otherwise:

    Print invalid input message

Declare teaType of type Tea

If teaInt is 1:

  Make teaType Tea.MILK

Otherwise if teaInt is 2:

  Make teaType Tea.GREEN

Otherwise if its 3:

  Make teaType Tea.OOLONG

Otherwise if its 4:

  Make teaType Tea.THAI

Otherwise:

  Make teaType Tea.SLUSH

Return teaType


#### printSelect method 

#### Goal
Filter menu based on teaType

#### Input
Tea teaType

#### Output
None

#### Steps
Call loadMenu

Make an ArrayList of Drinks named drinks using menus getDrinks method

Set integer counter to 0

Make a new DecimalFormat df object of format 0.00

Loop through drinks (i starts at 0, ends at the size of drinks, and is incremented by 1):

  If the current drinks teaType is the same as teaType

    Print the drinks name, a method call to getDots and a method call to calculatePrice

    Print the drinks description and a new line

Return counter to describe how many teas the user had to choose from


#### pickFlavor method

#### Goal
Let user pick flavor they want to see the menu of

#### Input
None

#### output
Flavor flavor

#### Steps
Make a new Scanner object named input

Print 1) Mango 2) Strawberry 3) Peach 4) brown Sugar 5) Taro 6) Matcha 7) Honey to user

Initialize integer flavorInt to 0

Make boolean validInput false

Set up a while loop with condition not validInput:

  Print out asking user for integer flavor choice

  If their input can be converted into an integer:

    Convert input to int and set it equal to flavorInt

    If teaInt is between 1 and 7:

      Set validInput to true

    Otherwise:

      Print message telling user to put correct bounds

  Otherwise:

    Print invalid input message

Declare flavorType of type Flavor

If flavorInt is 1:

  Make flavorType MANGO

Otherwise if teaInt is 2:

  Make flavorType STRAWBERRY

Otherwise if its 3:

  Make flavorType PEACH

Otherwise if its 4:

  Make flavorType BROWN SUGAR

Otherwise if its 5:

  Make flavorType TARO

Otherwise if its 6:

  Make flavorType MATCHA

Otherwise:

  Make flavorType HONEY

Return flavorType


#### printSelect method 

#### Goal
Filter menu based on flavorType

#### Input
Flavor flavorType

#### Output
None

#### Steps
Call loadMenu

Make an ArrayList of Drinks named drinks using menus getDrinks method

Set integer counter to 0

Make a new DecimalFormat df object of format 0.00

Loop through drinks (i starts at 0, ends at the size of drinks, and is incremented by 1):

  If the current drinks name has flavorType in it:

    Increment counter

    Print the drinks name, a method call to getDots and a method call to calculatePrice

    Print the drinks description and a new line

Return counter to describe how many flavors the user had to choose from


#### pickFilter method

#### Goal
Let user enter word/phrase to filter search

#### Input
None

#### Output
String filter

#### Steps
Make a new Scanner object for input

Ask user for word or phrase

Set their inputted value to String filter

Return filter

#### printSelect method 

#### Goal
Filter menu based on filter

#### Input
String filter

#### Output
None

#### Steps
Make an ArrayList of Drinks named drinks using menus getDrinks method

Set integer counter to 0

Make a new DecimalFormat df object of format 0.00

Loop through drinks (i starts at 0, ends at the size of drinks, and is incremented by 1):

  If the method call to containsIgnoringCase on arguments filter and the current drinks is true:
    
    Increment counter

    Print the drinks name, a method call to getDots and a method call to calculatePrice

    Print the drinks description and a new line

Return counter to describe how many teas the user had to choose from


#### containsIgnoringCase method

#### Goal
See if any of the checks will make a search result pop up

#### Input
String filter and Drink d

#### Output
Boolean representing if there was a match

#### Steps

Make boolean contains false

Convert filter to lowercase

Convert ds name to lowercase

If the name has filter:

  Set contains to true

Otherwise if the filter has the name:

  Set contains to true

Otherwise if the function call to offByOne is true:

  Set contains to true

Return contains


#### offByOne method

#### Goal
Accounts for typos when user is entering word to filter search by allowing filter to be off by one

#### Input
String filter, Drink d

#### output
None

#### Steps
Set String name to ds name

Set smaller to filter

Set String bigger to name

Set boolean foundMatch to false

If name is shorter than filter:
  
  switch smaller and bigger around

Loop through the bigger String (I starts at 0, ends at one before biggers length, incremented by 1):

  Loop through the smaller String (j starts at 0, ends at one before smallers length, incremented by 1):

    If the jth character of smaller is the same as the ith character of bigger and the j+1th character of smaller is the same as the i+1th character of bigger:

      Set beginning to i

      Break out of inner for loop

      Set foundMatch to true

  If foundMatch is true:

    If containsOffByOne with smaller and biggers substring starting at beginning returns true:

      Return true

Return false


#### containsOffByOne method

#### Goal
Figure out if there is one letter difference between the filter and the drinks name

#### Input
Two strings, smaller and bigger

#### Output
Boolean if there is a one letter difference

#### Steps
Initialize integer sameChars to 0

Set String nowSmaller to smaller

Set String nowBigger to bigger

If nowSmaller is longer than nowBigger:

  Flip the two around

Loop through the nowSmaller phrase (i starts at 0, ends at nowSmallers length, incremented by 1):

  If the nowSmallers ith character is the same as nowBiggers ith character:

    Increment sameChars

Return the value of if sameChars and nowSmaller differ by 1 or less characters



#### printMenu method

#### Goal
Print the menu of specific tea drinks to the customer and allow them to add a drink to their cart

#### Input
Integer teaInt representing which tea type the user chose to view

#### Output
None
    
#### Steps
Make a new admin object named a

Call getDrinks getter on a and set it equal to local ArrayList drinks

Print out a 0) option to return to tea types

Loop through drinks (i starts at 0, ends at the size of drinks, incremented by 1):

  If teaInt is 1 and the tea type of the current element is BLACK:

    Print out i+1) and the name and description of the current element

  Otherwise if teaInt is 2 and the tea type of the current element is GREEN:

    Print out i+1) and the name and description of the current element

  Otherwise if teaInt is 3 and the tea type of the current element is OOLONG:

    Print out i+1) and the name and description of the current element

  Otherwise if teaInt is 4 and the tea type of the current element is THAI:

    Print out i+1) and the name and description of the current element

  Otherwise if teaInt is 5 and the tea type of the current element is SLUSH:

    Print out i+1) and the name and description of the current element

  

#### makeDrinkChoice method

#### Goal
Have user pick a drink from the list previously presented to them

#### Input
None

#### Output
Integer choice

#### Steps
Initialize boolean validInput to false

Make a new Scanner object named input

Initialize integer choice to 0

Make a new Admin instance named a

Set up a while loop with condition not validInput:

  Ask the user for their integer tea choice

  If the input can be converted to an integer:

    Set choice equal to the input

    If choice is in between and the size of drinks:

      Set validInput to ture

    Otherwise:

      Tell user the integer has to be a tea option

  Otherwise:

    Print invalid input message

    “Clear” Scanner buffer

Return choice

#### convertChoice method

#### Goal 
Takes arbiturary user choice from filtered menus and find that drinks element in drinks

#### Input
Integer choice

String menuType

Tea teaType

Flavor flavorType

String filter

#### Output
String representation of the integer of their drink

#### Steps

Define drinks as the method call to menus getDrinks method

Initialize integer counter to 0

Initialize integer finalChoice to 0

If menuType is 2:

  Loop through drinks:

    If the current drinks teaType is the same as teaType:

      Increment counter

      If counter is the same as choice, we’ve found our index:

        Set finalChoice to i

Otherwise if menuType is 3:

  Loop through drinks:

    If the current drinks name has flavorType in it:

      Increment counter

      If counter is the same as choice, we’ve found our index:

        Set finalChoice to i

Otherwise if menuType is 4:

  Loop through drinks:

    If the function call to containsIgnoring case is true:

      Increment counter

      If counter is the same as choice, we’ve found our index:

        Set finalChoice to i

Return the String representation of finalChoice



#### confirmDrink method

#### Input
Integer representing the element in the drinks ArrayList

#### Output
Boolean representing whether the customer confirmed adding the drink to their cart

#### Steps
Create a new admin instance named a

Call getDrinks on a and set it equal to local ArrayList drinks

Initialize boolean confirmed to false

Make a new Scanner object named input

Loop through drinks (i starts at 0, ends at the size of drinks and is incremented by 1):

  If i is equal to choice:

    Print drink selection to user and ask them if they want to add it to their cart

    Store their response in local string response

  If response is “Y”:

    Set confirmed to true

    Print confirmation message to user

  Otherwise:

    Let user know the drink wasn’t added to their cart

Return confirmed


#### wantsAnotherDrink method

#### Goal
Determine if customer wants to add another drink to their order

#### Input
None

#### Output
Boolean representing if the customer wants to add another drink 

#### Steps
Make a scanner object named input

Initialize local boolean keepGoing to false

Ask the user if they want to add another drink to their order

Store their response in String response

If response is equal to Y:

  Set keepGoing to true

Return keepGoing


#### innerDecision method

#### Goal
Act as a rest stop for user to make decisions mid-ordering

#### input
ArrayList of current order status, boolean last

#### output
None

#### Steps
Make a new Scanner object

Set boolean keepGoing to true

Ask user what they want to do

Set up a while loop with condition keepGoing:

  If it’s the last run:

    Print out 0) Continue to Checkout 1) View Order 2) Delete Item(s) from Cart

  Otherwise:

    Print out 0) Continue Ordering 1) View Order 2) Delete Item(s) from Cart

Make String choice the input

If choice is 0:

 Set keepGoing to false

Otherwise if choice Is 1:

  Call printOrder on order

Otherwise if choice is 2:

  Call deletionMenu on order

Otherwise

  Print invalid input message

#### pickSpecifics method

#### Goal
Let user decide on drink size, toppings and amount of the drink 

#### Input
None

#### Output
Updated Drink

#### Steps
Set Drink d to chooseDrink function call

If ds name isn’t empty:

  Make a Scanner object for input

  Set keepGoing to true

  Set up a while loop with condition keepGoing:

    Ask user for medium or large, store their response in String size

    If size is large:

      Set the drinks size to large
  
      Set keepGoing to false

    Otherwise if size is medium:

      Set keepGoing to false

    Otherwise

      Print invalid input method

If keepGoing is false:

  Ask user to confirm drink size

  Set String confirm to the next line of input

  If confirm is N

    Set keepGoing to true

  Otherwise if confirm isn’t Y

     Print invalid input message

     Set keepGoing to true
  Set ds toppings to a function call of pickToppings

Return d


#### pickToppingNum method

#### Goal 
Have user pick how many toppings they want on the current drink

#### Input
None

#### output
Int number of toppings

#### Steps
Set Drink d to chooseDrink function call

If ds name isn’t empty:

  Make a Scanner object for input

  Set keepGoing to true

  Set up a while loop with condition keepGoing:

    Ask user for number of toppings, store in numToppings

    If numToppings is between 0 and 2:

      Set the drinks size to large
  
      Set keepGoing to false

    Otherwise if size is medium:

      Set keepGoing to false
 
    Otherwise

       Print invalid input message

Return numToppings


#### pickToppings method

#### Goal
Have user pick which toppings they want on their drink

#### Input
None
#### Output
Topping array 


#### Steps
Define drinks as the method call to menus getDrinks method

Initialize integer counter to 0

Initialize integer finalChoice to 0

If menuType is 2:

  Loop through drinks:

    If the current drinks teaType is the same as teaType:

      Increment counter

      If counter is the same as choice, we’ve found our index:

        Set finalChoice to i

Otherwise if menuType is 3:

  Loop through drinks:

    If the current drinks name has flavorType in it:

      Increment counter

      If counter is the same as choice, we’ve found our index:

        Set finalChoice to i

Otherwise if menuType is 4:

  Loop through drinks:

    If the function call to containsIgnoring case is true:

      Increment counter

      If counter is the same as choice, we’ve found our index:

        Set finalChoice to i

Return the int representation of finalChoice



#### checkout method

#### Goal
Give user options to checkout or cancel order


#### input
ArrayList of Drinks named order

#### Output
None

#### Steps
Define drinks as the method call to menus getDrinks method

Initialize integer counter to 0

Initialize integer finalChoice to 0

If menuType is 2:

  Loop through drinks:

    If the current drinks teaType is the same as teaType:

      Increment counter

      If counter is the same as choice, we’ve found our index:

        Set finalChoice to i

Otherwise if menuType is 3:

  Loop through drinks:

    If the current drinks name has flavorType in it:

      Increment counter

      If counter is the same as choice, we’ve found our index:

        Set finalChoice to i

Otherwise if menuType is 4:

  Loop through drinks:

    If the function call to containsIgnoring case is true:

      Increment counter

      If counter is the same as choice, we’ve found our index:

        Set finalChoice to i

Return the String representation of finalChoice



### Admin Class

#### Goal
Create blueprint for admin objects

#### Input
ArrayList of Drink objects named drinks

#### Output
None


#### Steps
Create 5 methods: 

*Create addDrink method, which allows admin to add a drink to the menu

*Create removeDrink method, which allows admin to remove a drink from the menu

*Create printMenu method, which prints the current menu

*loadDrinks, which will load the current status of drinks list

*saveDrinks, which saves the status of drinks


#### addDrink method

#### Goal
Allow admin to add a drink to the menu

#### Input
None

#### Output
None

#### Steps
Load the drinks attribute

Make a new Scanner object named input

Initialize Tea tea to BLACK

Ask the user to name the new drink

Store the response in String name

Ask the user the tea type: 1) Black 2) Green 3) Oolong 4) Thai or 5) Slush

If the response can be converted to an integer:

  Store the response in integer teaInt

  If teaInt is 2:

    Change tea to GREEN

  Otherwise if teaInt is 3:

    Change tea to OOLONG   

  Otherwise if teaInt is 4:

    Change tea to THAI

  Otherwise if teaInt is 5:

    Change tea to SLUSH

Call Drink constructor on new Drink object newDrink on name and tea
Ask user to come up with a brief description of the drink

Store the user’s response in String description

Call setDescription on newDrink on description 

Add newDrink to drinks attribute

Save drinks attribute


#### removeDrink method

#### Goal
Let admin remove a drink from the menu

#### Input
None

#### Output
None

#### Steps
Load the drinks list

Make a Scanner object named input

Initialize local integer choice to -1

Call printMenu on the current admin object

Ask the user for the integer of the drink they want to remove

If their response can be converted to an integer:

  Store the response in choice

  If the choice is not in between 1 and the size of drinks:

    Tell user the integer must be in drinks list

    Set choice to -1

If choice isn’t -1:

  Ask the user for deletion confirmation

  Store their response in String confirmed

  If confirmed is Y:

    Remove the choice-1’th element in drinks

  Otherwise:

    Print non-removal message 

Otherwise:

  Tell user no drink was deleted

Save the drinks list


#### printMenu method

#### Goal
Print the current status of menu to the admin

#### Input
None

#### Output
None

#### Steps
Loop through the drinks list (i starts at 0, ends at drink size, incremented by 1):

  Print i+1) and the drink’s name


#### loadDrinks method

#### Goal
Load the drinks menu into memory

#### Input
None

#### Output
None

#### Steps
In a try clause:

  Make a new file input stream fileIS connecting to drinkList.dat

  Make a new object input stream objectIS connecting to fileIS

  Read the drinks arrayList as an object and cast it to a Drink array

In a catch clause with general exception:

  Print the error message


####saveDrinks method

#### Goal
Save the current status of the drinks menu 

#### Input
None

#### Output
None

#### Steps
In a try clause:

  Make a new file output stream fileOS connecting to drinkList.dat

  Make a new object output stream objectOS connecting to fileOS

  Write the drinks arrayList object

In a catch clause with general exception:

  Print the error message


*Write Drink and Topping classes - setters, getters and Constructors - following UML diagram*

*Enumerations Tea and Boba are described on UML*


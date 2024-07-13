GeneralUser.class: GeneralUser.java Menu.class
	javac GeneralUser.java

Topping.class: Topping.java Boba.class Tea.class
	javac Topping.java

Boba.class: Boba.java
	javac Boba.java

Tea.class: Tea.java
	javac Tea.java

Flavor.class: Flavor.java
	javac Flavor.java

Drink.class: Drink.java Topping.class
	javac Drink.java

Menu.class: Menu.java Drink.class Topping.class Flavor.class
	javac Menu.java

Admin.class: Admin.java 
	javac Admin.java

Customer.class: Customer.java  
	javac Customer.java

Main.class: Main.java Admin.class Customer.class
	javac Main.java

run: Main.class
	java Main

clean:
	rm *.class

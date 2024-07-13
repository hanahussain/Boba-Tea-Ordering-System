import java.util.*;
import java.io.*;

public class Main{
  ArrayList<Customer> customers;

  public Main(){
    customers = new ArrayList<Customer>();
  }

  public static void main(String[] args){
    Main m = new Main();
    m.loadCustomers();
    m.firstMenu();
    m.saveCustomers();
  } //end main method

  public void saveCustomers(){
    try{
      FileOutputStream fileOS = new FileOutputStream("customers.dat");
      ObjectOutputStream objectOS = new ObjectOutputStream(fileOS);
      objectOS.writeObject(customers);
    } catch (Exception e){
      System.out.println(e.getMessage());
    } //end try catch
  } //end saveCustomers

  public void loadCustomers(){
    try{
      FileInputStream fileIS = new FileInputStream("customers.dat");
      ObjectInputStream objectIS = new ObjectInputStream(fileIS);
      customers = (ArrayList<Customer>)objectIS.readObject();
    } catch (Exception e){
      System.out.println(e.getMessage());
    } //end try catch
  } //end loadCustomers

  public void login(){
    Scanner input = new Scanner(System.in);
    loadCustomers();
    boolean isCustomer = false;
    Admin a = new Admin();
    System.out.println("\n-------BestTea's Login------\n");
    System.out.print("Username: ");
    String username = input.nextLine();
    System.out.print("Password: ");
    String password = input.nextLine();
    for(int i = 0; i < customers.size(); i++){
      Customer currCustomer = customers.get(i);
      if(findCustomer(username, password) != -1){
         currCustomer.userMenu();
         saveCustomers(); 
         isCustomer = true;
      } //end if
    } //end for
    if(username.toLowerCase().equals(a.getUsername()) && password.equals(a.getPassword())){
      System.out.println("\nSuccessfully logged in as admin.");
      a.loadMenu();
      a.userMenu();
      a.saveMenu();
    } else {
      if(!isCustomer){ //neither admin nor customer
        System.out.println("\nInvalid login. Exiting...");
      } //end inner if
    } //end if else
  } //end login

  public void createAccount(){
    Scanner input = new Scanner(System.in);
    loadCustomers();
    boolean validUsername = false;
    boolean validPassword = false;
    String username = "";
    String password = "";
    System.out.println("----Customer Account Creation----");
    while(!validPassword){
      while(!validUsername){
        System.out.print("Enter a username for your BestTea account: ");
        username = input.nextLine();
        System.out.print("Confirm username (Y) or re-enter (N)?: ");
        String confirm = input.nextLine();
        if(confirm.toUpperCase().equals("Y")){
          validUsername = true;
        } else if(!confirm.toUpperCase().equals("N")){
          System.out.println("Invalid input. Enter Y to confirm username or N to try again.");
        } //end if else
        if(accountExists(username)){
          System.out.println("That username already exists. Please enter a new one.");
          validUsername = false;
        }
      } //end inner while
      System.out.print("Enter a password: ");
      password = input.nextLine();
      System.out.print("Confirm password (Y) or re-enter (N)?: ");
      String response = input.nextLine();
      if(response.toUpperCase().equals("Y")){
        validPassword = true;
      } else if(!response.toUpperCase().equals("N")){
        System.out.println("Invalid input. Enter Y to confirm password or N to try again.");
      } //end if else
    } //end !validPassword
    customers.add(new Customer(username, password));
    saveCustomers();
    System.out.println("Your account has been successfully created!");
  } //end createAccount

  public boolean accountExists(String username){
    for(int i = 0; i < customers.size(); i++){
      if(customers.get(i).getUsername().equals(username)){
        return true;
      } //end if
    } //end for
    return false;
  } //end accountExists

  public void deleteAccount(){
    Scanner input = new Scanner(System.in);
    boolean validUser = true;
    boolean keepGoing = true;
    boolean quit = false;
    loadCustomers();
    while(keepGoing){
      System.out.print("Enter username to be deleted, or enter 0 to quit: ");
      String choice = input.nextLine();
      if(choice.equals("0")){
        keepGoing = false;
        quit = true;
      } else if(accountExists(choice)){
        keepGoing = false;
        while(validUser){
          System.out.print("Enter password to confirm account deletion, or 0 to escape deletion menu: ");
          String password = input.nextLine();
          int index = findCustomer(choice, password);
          if(index != -1){
            System.out.println("\nYour account has been deleted.");
            validUser = false;
            customers.remove(index);
            saveCustomers();
          } else if(password.equals("0")){
            System.out.println("\nYour account was not deleted. Returning to previous menu...");
            validUser = false;
          } else {
            System.out.println("Your username and password did not match. Please enter your password again.");
          } //end inner if else
        } //end while validUser
      } else {
        System.out.println("The username you entered is not in our system...");
      } //end if else
    } //end while
  } //end deleteAccount

  public int findCustomer(String username, String password){
    for(int i = 0; i < customers.size(); i++){
      if(customers.get(i).getUsername().equals(username) && customers.get(i).getPassword().equals(password)){
        return i;
      } //end if
    } //end for
    return -1;
  } //end findCustomer

  public void firstMenu(){
    boolean keepGoing = true;
    loadCustomers();
    Scanner input = new Scanner(System.in);
    while(keepGoing){
      System.out.print("\nWelcome to BestTea's Ordering System! \n0) Quit \n1) Login \n2) Create Account \n3) De-activate Account \nEnter choice: ");
      String choice = input.nextLine();
      if(choice.equals("0")){
        keepGoing = false;
      } else if(choice.equals("1")){
        login();
        saveCustomers();
      } else if(choice.equals("2")){
        createAccount();
        saveCustomers();
      } else if(choice.equals("3")){
        deleteAccount();
      } else {
        System.out.println("Please enter 0, 1, 2, or 3 representing decision.");
      } //end if else structure
    } //end while
  } //end firstMenu
} //end Main

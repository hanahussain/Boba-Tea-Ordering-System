import java.io.*;

public abstract class GeneralUser implements Serializable {
  String username;
  String password;
  Menu menu;

  public GeneralUser(){
    this.username = "anonymous name";
    this.password = "anonumous password";
    this.menu = new Menu();
  } //end constructor

  public String getUsername(){
    return this.username;
  } //end getUsername  

  public String getPassword(){
    return this.password;
  } //end getPassword

  public void setUsername(String username){
    this.username = username;
  } //end setUsername

  public void setPassword(String password){
    this.password = password;
  } //end setPassword

  public Menu getMenu(){
    return this.menu;
  } //end getMenu

  public void setMenu(Menu menu){
    this.menu = menu;
  } //end setMenu

  public abstract void userMenu();

  public void saveMenu(){
    try {
      FileOutputStream fileOS = new FileOutputStream("menu.dat");
      ObjectOutputStream objectOS = new ObjectOutputStream(fileOS);
      objectOS.writeObject(menu);
    } catch(Exception e){
      System.out.println(e.getMessage());
    } //end try-catch
  } //end saveMenu

  public void loadMenu(){
    try{
      FileInputStream fileIS = new FileInputStream("menu.dat");
      ObjectInputStream objectIS = new ObjectInputStream(fileIS);
      menu = (Menu)objectIS.readObject();
    } catch (Exception e){
      System.out.println(e.getMessage());
    } //end try-catch
  } //end loadM
} //end GeneralUser

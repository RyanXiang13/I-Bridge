import java.io.*;
import java.util.*;

/**
 * Represents a immigrant, extending the Person class.
 */
public class Immigrant extends Person {

  /**
   * The Native language of the immigrant
   */  
  private String nativeLanguage;

  /**
   * meetings booked for this immigrant
   */  
  private ArrayList<Meeting> myMeetings = new ArrayList();

  /**
   * List of immigrants
   */  
  private static ArrayList<Immigrant> immigrantList = new ArrayList<>();
  static {
    // Load all the immigrants into memeory
    loadAllImmigrants();
  }

  
  /**
   * Constructs a Immigrant object with the specified name, email, phone number,
   * residence area and native language.
   *
   * @param name the name of the immigrant person
   * @param email the email of the immigrant person
   * @param phoneNumber the phone number of the immigrant person
   * @param residenceArea the residenc area of the immigrant person
   * @param nativeLanguage the native language of the immigrant person   
   */
  public Immigrant(String name, String email, String phoneNumber, String residenceArea, 
                   String nativeLanguage) {
    super(name, email, phoneNumber, residenceArea);
    this.nativeLanguage = nativeLanguage;
  }

  
  /**
   * Constructs a Immigrant object with the specified name,email, phone number,
   * residence area, native language, username, and pasword
   
   * @param name the name of the immigrant person
   * @param email the email of the immigrant person
   * @param phoneNumber the phone number of the immigrant person
   * @param residenceArea the residenc area of the immigrant person
   * @param nativeLanguage the native language of the immigrant person  
   * @param username username of the immigrant person  
   * @param password the user password of the immigrant person  
   */
  public Immigrant(String name, String email, String phoneNumber, String residenceArea,
                   String nativeLanguage, String username, String password) {
    super(name, email, phoneNumber, residenceArea, username, password);
    this.nativeLanguage = nativeLanguage;
    this.username = username;
    this.password = password;
  }

  
  /**
   * Gets the native language of the immigrant.
   * @return the native language of the immigrant
   */
  public String getNativeLanguage() {
    return this.nativeLanguage;
  }

  
  /**
   * Gets the meetings of the immigrant.
   * @return the list of meetings
   */
  public ArrayList<Meeting> getMyMeetings() {
    return Meeting.findMeetings(this); 
  }


  /**
   * Returns a string representation of the immigrant
   *
   * @return a string representation of the immigrant
   */
  public String toString() {
    String mySnapshot = "Student: " + this.name + "\tEmail: " + this.email + "\tPhone: " + this.phoneNumber +   
      "\nCity: " + this.residenceArea;
    return  mySnapshot;
  }

  
  /**
   * Saves the immigrant profile to the database.
   * @param immigrant the immigrant to be saved
   */
  public static boolean saveProfileToDatabase(Immigrant immigrant) {

    try {

      // Open the appropriate database for immigrant profile to save into it
      // the new immigrant profile
      File file = new File("Database/immigrant.txt");
      FileWriter fw = new FileWriter(file, true);
      fw.write(immigrant.name + "," + immigrant.email + "," + immigrant.phoneNumber + "," + 
               immigrant.residenceArea + "," + immigrant.nativeLanguage + "," + 
               immigrant.username + "," + immigrant.password + "\n");
      fw.close();
    
      // Add the new immigrant to the immigrant list after sucessfully saved in db
    immigrantList.add(immigrant);      
      
    } catch (IOException e) {
      System.out.println("File does not exist");
      return false;
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
      return false;
    }

    return true;
  }

  
  /**
   * Searches for a immigrant in the database by name and birthdate.
   *
   * @param username the username of the immigrant to search for
   *
   * @return the found immigrant, or null if not found
   */
  public static Immigrant searchImmigrant(String username) {

    try {
      for (Immigrant h : immigrantList) {
        if (username.equalsIgnoreCase(h.username))
          return h;
      }
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }
    return null;
  }

  
  /**
   * Loads the list of immigrants from the database.
   */
  public static void loadAllImmigrants() {

    // Clear currunt immigrant list
    immigrantList.clear();
    
    String line;
    try {

      // Open the appropriate database for imnmigrant data to load them into
      // 'immigrantList'
      BufferedReader br = new BufferedReader(new FileReader("Database/immigrant.txt"));
      while ((line = br.readLine()) != null) {
        String[] arr = line.split(",");
        String name = arr[0];
        String email = arr[1];
        String phoneNumber = arr[2]; 
        String residencArea = arr[3]; 
        String nativeLanguage = arr[4]; 
        String username = arr[5]; 
        String password = arr[6]; 
        immigrantList.add(new Immigrant(name, email, phoneNumber, residencArea, 
                                        nativeLanguage, username, password));
      }
      br.close();
    } catch (IOException e) {
      System.out.println("File does not exist");
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }
  }


  /**
   * Check if the username and password entered by the user can be found in immigrant list.
   *
   * @param username the username entered by the immigrant user
   * @param password the password entered by the immigrant user
   *
   * @return true if match of the username and password found, or false if not found
   */
  public static boolean IsValidLogin (String usernanme, String password) {

    for (Immigrant i : immigrantList) {
      if (i.getUsername().equalsIgnoreCase(usernanme) && i.getPassword().equalsIgnoreCase(password))
        return true;
    }
    return false;
  }


  /**
   * Check if the username and password entered by the user can be found in immigrant list.
   *
   * @param username the username entered by the immigrant user
   *
   * @return true if the username is already taken, or false if it is available
   */  
  public static boolean isUserNameAvailable (String usernanme) {

    for (Immigrant i : immigrantList) {
      if (i.getUsername().equalsIgnoreCase(usernanme)) {
        // A username match is found in the immigrant list, the the username is not
        // available for the new sign up
        return false;
      }
    }

    // The username is not used by any immigrant currently in the database, so it avaialbe
    // for the new signup
    return true;
  }
}

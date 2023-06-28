import java.io.*;
import java.util.*;

/**
 * Represents a Tutor.
 */
public class Tutor extends Person {

  // Constants to prevent use of magic numbers
  private static final int INDEX_MIN_FEE = 0;
  private static final int INDEX_MAX_FEE = 1;
  private static final int ALL_EXPERTISE_LEVEL = 0;  
  private static final String ALL_PRICES = "All Prices";    
  private static final String ALL_PRICE_RANGE = "0-999999";      

  /**
   * The course taugut by the tutor
   */
  protected String courseTeaching;

  /**
   * The fee charged by the tutor.
   */
  protected int fee;

  /**
   * The expertise of the tutor.
   */  
  protected int expertise;

  /**
   * The second language of the tutor.
   */  
  protected String secondLanguage;

  /**
   * Retrieves the course taught by the tutor.
   *
   * @return The course taught by the tutor.
   */
  public String getCourseTeaching() {
      return courseTeaching;
  }
  
  /**
   * Retrieves the fee charged by the tutor.
   *
   * @return The fee charged by the tutor.
   */
  public int getFee() {
      return fee;
  }
  
  /**
   * Retrieves the expertise of the tutor.
   *
   * @return The expertise of the tutor.
   */
  public int getExpertise() {
      return expertise;
  }
  
  /**
   * Retrieves the second language of the tutor.
   *
   * @return The second language of the tutor.
   */
  public String getSecondLanguage() {
      return secondLanguage;
  }

  /**
   * The list of tutors.
   */  
  private static ArrayList<Tutor> tutorList = new ArrayList<>();
  static {
    // Load all tutors from the database to 'tutorList'
    loadAllTutors();
  }
  

  /**
   * Defines courses available
   */
  public final static Hashtable<String, String> courseList = new Hashtable<String, String>();
  static {
    courseList.put("1", "English for beginners");
    courseList.put("2", "Advanced English");    
    courseList.put("3", "Math (G7-G12)");
    courseList.put("4", "Programing in Java/Python");
    courseList.put("5", "Physics");            
  }

  /**
   * Defines skill expertise of the tutor
   */
  public final static Hashtable<String, String> experticeLevels;
  static {
    experticeLevels = new Hashtable<String, String>();    
    experticeLevels.put("1", "All Levels");    
    experticeLevels.put("2", "Elementary Level");
    experticeLevels.put("3", "Intermediate Level");
    experticeLevels.put("4", "Expert Level");
  }  


  /**
   * Defines fee schedules
   */
  public final static Hashtable<String, String> tutoringFeeList = new Hashtable<String, String>();
  static {
    tutoringFeeList.put("1", "All prices");        
    tutoringFeeList.put("2", "0 - 20");    
    tutoringFeeList.put("3", "21 - 40");
  }    


  /**
   * Defines second languages available 
   */
  public final static Hashtable<String, String> secondLanguageList = new Hashtable<String, String>();
  static {
    secondLanguageList.put("1", "Chinese");
    secondLanguageList.put("2", "Punjabi");    
    secondLanguageList.put("3", "Arabic");
    secondLanguageList.put("4", "Persian languages");
    secondLanguageList.put("5", "Korean");    
    secondLanguageList.put("6", "Not in the list"); 
  }


  /**
   * Constructs a Tutor object with default values for name, phoneNumber, residenceArea
   * course, fee, expertice and second language.
   */  
  public Tutor()
  {
    super("", "", "", "");
    this.courseTeaching = "";
    this.fee = 0;
    this.expertise = 0;     
    this.secondLanguage = "";
  }
  
  
  /**
   * Constructs a Tutor object with the specified attributes.
   *
   * @param name           the name of the tutor
   * @param email          the email of the tutor
   * @param phoneNumber    the phone number of the tutor   
   * @param residenceArea  the residenceArea of the tutor
   * @param courseTeaching the course the tutor teaches
   * @param fee the fee charged by the tutor
   * @param expertise the expertise of the tutor
   * @param secondLanguage the second language of the tutor
   */
  public Tutor(String name, String email, String phoneNumber, String residenceArea, String courseTeaching,
               int fee, int expertise, String secondLanguage) {

    super(name, email, phoneNumber, residenceArea);
    this.courseTeaching = courseTeaching;
    this.fee = fee;
    this.expertise = expertise;
    this.secondLanguage = secondLanguage;
  }


  /**
   * Constructs a Tutor object with the specified attributes.
   *
   * @param name           the name of the tutor
   * @param email          the email of the tutor
   * @param phoneNumber    the phone number of the tutor   
   * @param residenceArea  the residenceArea of the tutor
   * @param courseTeaching the course the tutor teaches
   * @param fee the fee charged by the tutor
   * @param expertise the expertise of the tutor
   * @param secondLanguage the second language of the tutor
   * @param username the username of the tutor
   * @param password the password of the tutor   
   */
  public Tutor(String name, String email, String phoneNumber, String residenceArea, String courseTeaching,
              int fee, int expertise, String username, String password, String secondLanguage) {

    super(name, email, phoneNumber, residenceArea, username, password);
    this.courseTeaching = courseTeaching;
    this.fee = fee;    
    this.expertise = expertise;
    this.secondLanguage = secondLanguage;    
    this.username = username;
    this.password = password;
  }
  

  /**
   * Gets the meetings booked for the tutor.
   *
   * @return the list of meetings
   */
  public ArrayList<Meeting> getMyMeetings() {
    return Meeting.findMeetings(this); 
  }

  
  /**
   * Gets the list of tutors.
   *
   * @return the list of tutors
   */
  public static ArrayList<Tutor> getTutors() {
    return tutorList;
  }


  /**
   * Returns a string representation of the tutor.
   *
   * @return a string representation of the tutor
   */
  public String toString() {
    // Construct the tutor snapshot based on the type of the tutor    
    String myPresentation = "Tutor: " + this.name + "\tEmail: " + this.email + " \tPhone: " + this.phoneNumber;
    myPresentation += "\n" + this.residenceArea + "\n";

    return myPresentation;
  }

  
  /**
   * Saves the tutor's profile to the database.
   *
   * @param tutor the tutor to save
   * @return true if the  tutor profile is successfully saved into database, or
   * false if saving the profile into database fails.
   */
  public static boolean saveProfileToDatabase(Tutor tutor) {
    try {
      
      // Open tutor data base to append the 'tutor' profile into tutor database
      File file = new File("Database/tutor.txt");
      FileWriter fw = new FileWriter(file, true);

      // Persist the tutor profile into database based on the type of the tutor
      fw.write(tutor.name + "," + tutor.email + "," + tutor.phoneNumber + "," + tutor.residenceArea +
               "," + tutor.courseTeaching + "," + tutor.fee + "," + tutor.expertise + "," +
               tutor.username + "," + tutor.password + "," + tutor.secondLanguage + "\n");                

      // Close the filewrite to release resource
      fw.close();
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
   * Searches for a tutor with the given username.
   *
   * @param username the username of the tutor to search for
   * @return the found tutor or null if not found
   */
  public static Tutor searchTutor(String username) {
    try {

      for (Tutor t : tutorList) {
        if (t.username.equalsIgnoreCase(username)) {
          return t;
        }
      }
    } catch (Exception e) {
      System.out.println("Tutor does not exist");
    }
    return null;
  }

  
  /**
   * Searches for a tutor with the given name and phone number.
   *
   * @param courseTeaching   the course taught by the tutor to search for
   * @param expertise  the expertise of the tutor to search for
   * @param priceRange  the price range of the tutor to search for   
   *
   * @return the found tutor or null if not found
   */
  public static ArrayList<Tutor> searchTutor(String courseTeaching, int expertise, String priceRange) {

    ArrayList<Tutor> results = new ArrayList();    
    try {

      int[] min_max = parsingPriceRange(priceRange);
      expertise = expertise-1;
      
      // Load a fresh copy of db tutor into 'tutorList'
      for (Tutor t : tutorList) {

        if (t.courseTeaching.equalsIgnoreCase(courseTeaching) && 
            t.fee >= min_max[INDEX_MIN_FEE] && t.fee <= min_max[INDEX_MAX_FEE] &&
            (t.expertise == expertise || expertise == ALL_EXPERTISE_LEVEL)) {
          results.add(t);
        }
      }
    } catch (Exception e) {
      System.out.println("Tutor does not exist");
    }
    
    // No tutor is found database to match 'username', 'expertise' and 'priceRange' passed in
    // as parameters
    return results;
  }


  /**
   * Loads the list of tutors from the database.
   */
  public static void loadAllTutors() {

    tutorList.clear();
    String line;

    try {
      // Instantiate a BufferedReader to load tutor from database to 'tutors'
      BufferedReader br = new BufferedReader(new FileReader("Database/tutor.txt"));
      while ((line = br.readLine()) != null) {
        String[] arr = line.split(",");

        String name = arr[0];
        String email = arr[1];
        String phoneNumber = arr[2];        
        String residenceArea = arr[3];
        String courseTeaching = arr[4];
        int fee = Integer.parseInt(arr[5]);
        int expertiseLevel = Integer.parseInt(arr[6]);
        String username = arr[7];
        String password = arr[8];
        String secondLanguage = arr[9];

        // Instantiate tutor based on the type of tutor retrieved from database
        Tutor t = new Tutor(name, email, phoneNumber, residenceArea, courseTeaching, 
                            fee, expertiseLevel, username, password, secondLanguage);

        tutorList.add(t);
      }

      br.close();
    } catch (IOException e) {
      System.out.println("File does not exist");
    } catch (Exception e) {
      
      System.out.println("Error: " + e.getMessage());
    }
  }
    
   /**
   * Searches for a tutor's specility with the given name and phone number.
   *
   * @param tutorName the name of the tutor
   * @param tutorPhone the phone number of the tutor
   *
   * @return the specility of tutor if found, or 'N/A' otherwise
   */
  public static String getCourseTeaching(String tutorName, String tutorPhone)
  {
    for (Tutor t : tutorList) {
      if (t.name.equalsIgnoreCase(tutorName) && t.phoneNumber.equalsIgnoreCase(tutorPhone)) {
        return t.courseTeaching;
      }
    }

    return "Course not found";
  }


  /**
   * Check if the username and password entered by the user can be found in immigrant list.
   *
   * @param priceRange the price range entered by the immigrant user
   *
   * @return a int array that contains min and max prices
   */
  public static int[] parsingPriceRange(String priceRange) {
    
    if (priceRange.equalsIgnoreCase(ALL_PRICES)) {
      // if "All prices" is selected, makes it 0 - 999999
      priceRange = ALL_PRICE_RANGE;
    }

    // Split the price range delimited by '-' into min and max prices
    int[] min_max = new int[2];
    String[] arr = priceRange.split("-");

    // Store the min and max prices in array 'min_max'
    min_max[INDEX_MIN_FEE] = Integer.parseInt(arr[0].trim());
    min_max[INDEX_MAX_FEE] = Integer.parseInt(arr[1].trim());   

    return min_max;
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

    for (Tutor t : tutorList) {
      if (t.getUsername().equalsIgnoreCase(usernanme) && t.getPassword().equalsIgnoreCase(password)) {
        // User login credentials are found in database, it is a valid login
        return true;
      }
    }

    // User login credentials are found in database, it is NOT a valid login
    return false;
  }
  
}

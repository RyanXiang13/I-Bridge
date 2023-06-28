import java.io.*;
import java.util.*;

/**
 * Represents an meeting for a immigrant person with a tutor.
 */
public class Meeting {

  private String immigrantName;
  private String immigrantUsername;
  private String tutorName;
  private String tutorUsername;
  private String meetingDate;
  private String meetingTimeslot;
  private String meetingURL="N/A";
  private String course;

  private static ArrayList<Meeting> allMeetings = new ArrayList<>();
  static {
    
    loadAllMeetings();
  }

  /**
   * Represents the available timeslots for meetings.
   */
  public final static Hashtable<String, String> timeslots = new Hashtable<>();
  static {
    timeslots.put("1", "08:00AM");
    timeslots.put("2", "09:00AM");
    timeslots.put("3", "10:00AM");
    timeslots.put("4", "11:00AM");
    timeslots.put("5", "01:00PM");
    timeslots.put("6", "02:00PM");
    timeslots.put("7", "03:00PM");
    timeslots.put("8", "04:00PM");
  }

  /**
   * Constructs an Meeting object with the specified details.
   *
   * @param immigrantName        the name of the immigrant person
   * @param immigrantUsername      the username of the immigrant person
   * @param tutorName          the name of the tutor
   * @param tutorUsername         the username of the tutor
   * @param meetingDate     the date of the meeting
   * @param meetingTimeslot the timeslot of the meeting
   */
  public Meeting(String immigrantName, String immigrantUsername, String tutorName, String tutorUsername, 
                 String meetingDate, String meetingTimeslot, String course) {
    this.immigrantName = immigrantName;
    this.immigrantUsername = immigrantUsername;
    this.tutorName = tutorName;
    this.tutorUsername = tutorUsername;
    this.meetingDate = meetingDate;
    this.meetingTimeslot = meetingTimeslot;
    this.course = course;
  }

  /**
   * Constructs an Meeting object with the specified details.
   *
   * @param immigrantName        the name of the immigrant person
   * @param immigrantUsername      the Username of the immigrant person
   * @param tutorName          the name of the tutor
   * @param tutorUsername         the user name of the tutor
   * @param meetingDate     the date of the meeting
   * @param meetingTimeslot the timeslot of the meeting
   * @param meetingURL the URL of the meeting   
   */
  public Meeting(String immigrantName, String immigrantUsername, String tutorName, String tutorUsername, 
                 String meetingDate, String meetingTimeslot, String meetingURL, String course) {
    this.immigrantName = immigrantName;
    this.immigrantUsername = immigrantUsername;
    this.tutorName = tutorName;
    this.tutorUsername = tutorUsername;
    this.meetingDate = meetingDate;
    this.meetingTimeslot = meetingTimeslot;
    this.meetingURL = meetingURL;
    this.course = course;
  }
  
  /**
   * Gets the name of the immigrant person.
   *
   * @return the name of the immigrant person
   */
  public String getimmigrantName() {
    return this.immigrantName;
  }

  /**
   * Gets the date of birth of the immigrant person.
   *
   * @return the date of birth of the immigrant person
   */
  public String getImmigrantUsername() {
    return this.immigrantUsername;
  }

  /**
   * Gets the name of the tutor.
   *
   * @return the name of the tutor
   */
  public String getTutorName() {
    return this.tutorName;
  }

  /**
   * Gets the username of the tutor.
   *
   * @return the username of the tutor
   */
  public String getTutorUsername() {
    return this.tutorUsername;
  }

  /**
   * Gets the date of the meeting.
   *
   * @return the date of the meeting
   */
  public String getmeetingDate() {
    return this.meetingDate;
  }

  /**
   * Gets the timeslot of the meeting.
   *
   * @return the timeslot of the meeting
   */
  public String getmeetingTimeslot() {
    return this.meetingTimeslot;
  }

  
  /**
   * Gets the url of the meeting.
   *
   * @return the url of the meeting
   */
  public String getMeetingURL() {
    return this.meetingURL;
  }

    /**
   * Gets the url of the meeting.
   *
   * @return the url of the meeting
   */
  public String getCourse() {
    return this.course;
  }

  /**
   *
   * Sets the url of the meeting.
   *
   */
  public void setMeetingURL(String meetingURL) {
    this.meetingURL = meetingURL;
  }  

  
  /**
   * Returns a string representation of the meeting object.
   *
   * @return a string representation of the meeting object
   */
  public String toString() {
    return "Course: " + this.course + "\nTime: " + timeslots.get(String.valueOf(this.meetingTimeslot)) 
            + " " + this.meetingDate + "\nmeeting url: " + this.meetingURL;
  }


  /**
   * Get meeting detail for the immigrant
   *
   * @param The tutor of the meeting
   * 
   * @return the detail informaion of the meeting
   */
  public String getMeetDetailForImmigrant(Tutor meetingTutor) {

    // Start with the information from Meeting object
    String detail = "\tCourse: " + this.course + "\tTime: " + timeslots.get(String.valueOf(this.meetingTimeslot)) 
            + " " + this.meetingDate + "\n\tmeeting url: " + this.meetingURL ;
        
    // Add Tutor information of this meeting
    detail += "\n\tTutor: " + meetingTutor.getName() + "\tEmail: " + meetingTutor.getEmail();
    return detail;
  }    
   

  /**
   * Books an meeting by adding it to the list of meetings and saving it to a file.
   *
   * @param meeting the meeting to be booked
   * 
   * @return true if the meeting is booked and saved into database, or false
   * if failed to save the meeting into database    
   */
  public static boolean bookMeeting(Meeting meeting) {
    try {
      File file = new File("Database/meeting.txt");
      FileWriter fw = new FileWriter(file, true);
      fw.write(meeting.immigrantName + "," + meeting.immigrantUsername + "," + meeting.tutorName + "," +
          meeting.tutorUsername + "," + meeting.meetingDate + "," + meeting.meetingTimeslot + "," +
          meeting.meetingURL + "," + meeting.course + "\n");
      fw.close();
      allMeetings.add(meeting);
      return true;
    } catch (IOException e) {
      System.out.println("File does not exist");
    }

    return false;
  }

  /**
   * Updates an meeting by canceling the current meeting and booking the updated meeting.
   *
   * @param currentmeeting the current meeting to be updated
   * @param updatedmeeting the updated meeting
   */
  public static void updatemeeting(Meeting currentmeeting, Meeting updatedmeeting) {
    cancelMeeting(currentmeeting);
    bookMeeting(updatedmeeting);
  }

  /**
   * Cancels an meeting by removing it from the list of meetings and saving the updated list to a file.
   *
   * @param meeting the meeting to be canceled
   */
  public static boolean cancelMeeting(Meeting meeting) {
    try {
      for (Meeting a : allMeetings) {
        if (a.immigrantUsername.equalsIgnoreCase(meeting.immigrantUsername) &&
            a.tutorUsername.equalsIgnoreCase(meeting.tutorUsername) &&
            a.meetingDate.equalsIgnoreCase(meeting.meetingDate) &&
            a.meetingTimeslot.equalsIgnoreCase(meeting.meetingTimeslot)) {

          allMeetings.remove(a);
          break;
        }
      }
      
      return SaveMeetingsToDB();
    } catch (Exception ex) {
      System.out.println("Error: " + ex.getMessage());
      return false;
    }
  }

  /**
   * Finds all meetings for a immigrant person with the specified name and date of birth.
   *
   * @param username the username of the immigrant person
   * @return an ArrayList of meetings for the specified immigrant person
   */
  public static ArrayList<Meeting> findMeetings(Person person) {

    ArrayList<Meeting> myMeetings = new ArrayList<>();
    for (Meeting m : allMeetings) {
      if (person instanceof Immigrant) {
        if (m.immigrantUsername.equalsIgnoreCase(person.username)) {
          myMeetings.add(m);
        }
      } else if (person instanceof Tutor) {
        if (m.tutorUsername.equalsIgnoreCase(person.username)) {
          myMeetings.add(m);
        }
      }      

    }

    return myMeetings;
  }

  /**
   * Gets the scheduled timeslots for a tutor on a specific date.
   *
   * @param docName   the name of the tutor
   * @param docPhone  the phone number of the tutor
   * @param appDate   the date of the meetings
   * @return an ArrayList of scheduled timeslots for the specified tutor and date
   */
  public static ArrayList<String> getScheduledTimeslot(String tutorUsername, String appDate) {
    ArrayList<String> bookedTimeslots = new ArrayList<>();
    for (Meeting m : allMeetings) {
      if (m.tutorUsername.equalsIgnoreCase(tutorUsername) && m.meetingDate.equalsIgnoreCase(appDate)) {
        bookedTimeslots.add(m.meetingTimeslot);
      }
    }
    return bookedTimeslots;
  }

  /**
   * Loads all meetings from a file and populates the list of meetings.
   */
  public static void loadAllMeetings() {
    String line;
    try {
      BufferedReader br = new BufferedReader(new FileReader("Database/meeting.txt"));
      while ((line = br.readLine()) != null) {
        String[] arr = line.split(",");
        String immigrantName = arr[0];
        String immigrantDob = arr[1];
        String tutorName = arr[2];
        String doctoPhoneNum = arr[3];
        String meetingDate = arr[4];
        String meetingTimeSlot = arr[5];
        String meetingURL = arr[6];
        String course = arr[7];
        Meeting app = new Meeting(immigrantName, immigrantDob, tutorName, doctoPhoneNum,
            meetingDate, meetingTimeSlot, meetingURL, course);
        allMeetings.add(app);
      }
      br.close();
    } catch (IOException e) {
      System.out.println("File does not exist");
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  /**
   * Saves all meetings from the list to a file.
   */
  public static boolean SaveMeetingsToDB() {
    try {
      File file = new File("Database/meeting.txt");
      FileWriter fw = new FileWriter(file);
      for (Meeting m : allMeetings) {
        fw.write(m.immigrantName + "," + m.immigrantUsername + "," + m.tutorName + "," + 
                 m.tutorUsername + "," + m.meetingDate + "," + m.meetingTimeslot + "," +
                 m.meetingURL + "," + m.course + "\n");
      }
      fw.close();
    } catch (Exception e) {
      System.out.println("File does not exist");
      return false;
    }

    return true;
  }

  /**
   * Gets the number of meetings for each tutor's specialty.
   *
   * @return a Hashtable containing the number of meetings for each specialty
   */
  public static String generateMeetingUrl() {

    String meetingUrl = "http://www.ibridge.com/exchange?token=";
    UUID uuid = UUID.randomUUID();
    String strUUID = uuid.toString().substring(0, 8);
    meetingUrl += strUUID;

    return meetingUrl;
  }
}

import java.util.*;
import java.io.*;
import processing.core.PApplet;
import java.text.ParseException;
import java.text.SimpleDateFormat;

class Main {
 
  // Constant variables to prevent from using magic numbers
  private static final int MAIN_IMMIGRANT_LOGININ = 1;
  private static final int MAIN_IMMIGRANT_LOGININ_BOOKING = 1;  
  private static final int MAIN_IMMIGRANT_LOGININ_CHANGE = 2;    
  private static final int MAIN_IMMIGRANT_LOGININ_CANCEL = 3;  
  private static final int MAIN_IMMIGRANT_LOGININ_SIGN_OUT = 4;

  private static final int MAIN_TUTOR_LOGININ = 2;
  private static final int MAIN_TUTOR_LOGININ_ALL_MEETINGS = 1;      
  private static final int MAIN_TUTOR_LOGININ_ALL_STUDENTS = 2;      
  private static final int MAIN_TUTOR_LOGININ_SIGN_OUT = 3;        
  
  private static final int MAIN_SIGN_UP = 3;    
  private static final int MAIN_SIGN_UP_IMMIGRANT = 1;  
  private static final int MAIN_SIGN_UP_TUTOR = 2;
  
  private static final int MAIN_MENU_MAIN_EXIT = 4;      
    
  private static final int SECOND_LANGUAGE_NOT_IN_LIST = 6;  

  private static final String ENTER_KEY = "";
  
  private static String menuStr = "";


  /**
   * The main method is the entry point of the application.
   *
   * @param args The command-line arguments passed to the application.
   */  
  public static void main(String[] args) {
    
    loadMenusContext();

    // Display the initial menu of the applicaion
    displayMenu("MENU_APPLICATION_LOAD");    

    // Initialize Scanner object to read in user input    
    Scanner input = new Scanner(System.in);

    // While loop is used to all the users to repeatedly navigate through menus unless the 
    // selection to exit the application is selected.    
    while (true) {

      // Get a valid menu selection from the initial menu
      int menuChoice = getValidMenuSelection(input, 4);
      
      if (menuChoice == MAIN_IMMIGRANT_LOGININ) {
        // The "Immigrant Login" is selected

        // Variables to store username and password to be entered from the uesr
        String username = "";
        String password = "";
        System.out.println();
        System.out.println("Please sign in with your login credentials:\n");  

        // Repeatedly prompt the user to enter username and password till a valid username
        // and password are enterted
        while (true) {
        
          System.out.print("User name:");
          username = input.nextLine();
          System.out.print("Password:");
          password = input.nextLine();        
          if (Immigrant.IsValidLogin(username, password)) {
            // The login is validated, break out from the login loop 
            break;
          } else {

            // Login is not valid, prompt the users to reneter the username and password
            System.out.println();            
            System.out.println("Incorrect username or password. Type the correct username\n" +
                              "and password, and try again");
            System.out.println();             
          }
        }

        // Clear screen for user after login
        clearScreenAll(); 
        
        // Retrieve the Immigrant object for the login user
        Immigrant immigrant = Immigrant.searchImmigrant(username);

        // The login user repeatedly schedules/alters meetings until the 'Sign out' menu
        // option is selcted, when the app will navigate back to the main application menu
        boolean scheduleDone = false;        
        while (!scheduleDone) {    
  
          System.out.println();
          
          System.out.println("Welcome back " + immigrant.getName() + "!") ;
          System.out.println();
          System.out.println("Meeting(s) currently booked for you:") ;        
          System.out.println();
          
          // Get meetings for the login user
          ArrayList<Meeting> myMeetings = immigrant.getMyMeetings();

          // Display meetings currently booked for the users
          if (myMeetings.size() == 0) {
            System.out.println("Currently no meeting is booked");
          } else {
            //int meetingNo = 1;
            ArrayList<String> meetingDetails = new ArrayList();
            for (Meeting m : myMeetings) {
              
              Tutor tutor = Tutor.searchTutor(m.getTutorUsername().trim());
              meetingDetails.add(m.getMeetDetailForImmigrant(tutor));
            }
            printListInOrder(meetingDetails, meetingDetails.size());            
          }
          
          // Display menu for booking a meet          
          displayMenu("MENU_SCHEDULE");

          // Select a menu option from scheduling menu
          int scheduleChoice = getValidMenuSelection(input, 4);
          if (scheduleChoice == MAIN_IMMIGRANT_LOGININ_BOOKING) {
            // Menu option "Book a meeting" is selected 

            // Ask the user to enter criteria to searach for matching tutors
            System.out.println("Search tutor with following criteria:");
            System.out.println();

            System.out.println("Criteria 1: select a course:");            
            String courseSelected = selectCourse(input);
            System.out.println("Course selected: " + courseSelected);

            System.out.println();
            System.out.println("Criteria 2: select the expertise level of a tutor:");               
            int expertiseSelected = selectTutorExpertise(input);
            
            System.out.println("Expertise level selected: " +   
                               Tutor.experticeLevels.get(String.valueOf(expertiseSelected)));         
            System.out.println();

            System.out.println("Criteria 3: select the tutoring fee:");               
            String feeSelected = selectTutoringFee(input);
            System.out.println("Tutoring fee selected: " + feeSelected);                    

            System.out.println();
            // Simulating search in realword for a big database
            System.out.println("Searching...");
            try {
              Thread.sleep(1000);
            } catch (InterruptedException ex) {
              Thread.currentThread().interrupt();
            }
            System.out.println("Summary of your serach critera for tutors");          
            System.out.println("Course: " + courseSelected + "\tExpertise level: " +   
                               Tutor.experticeLevels.get(String.valueOf(expertiseSelected)) + 
                               "\tFee: " + feeSelected);

            // Get the list of the tutors matching serarch criteria 
            ArrayList<Tutor> tutors = Tutor.searchTutor(courseSelected, expertiseSelected, feeSelected);
            if (tutors.size() == 0) {
              System.out.println("Ooops, No tutor found with the search criteria you entered.");
              System.out.println("Press any key to contine.");              
              String any = input.nextLine();              
              clearScreenAll();
              continue;
            }
            // Initialize the graphic panel for tutor selection
            Sketch.setTutorList(tutors);
            PApplet.main("Sketch", args);

            System.out.println("Please select a tutor from the Tutor Browser." +
                               " Then press any key to continue: ");
            String key = input.nextLine();
            
            // After selection of tutor, the code will be back here for booking.

            // Get the Tutor object from the selected tutor
            Tutor selectedTutor = tutors.get(Sketch.getIdx());
            //Tutor selectedTutor = tutors.get(0);
            
            System.out.println("");   
            // Select a valid meeting date            
            String meetingDate = selectValidMeetingDate(input);
            
            // Find and display timeslot available to the selected tutor for the selected date
            System.out.println("Time available");
            ArrayList<String> bookedTimeSlots = Meeting.getScheduledTimeslot(selectedTutor.username, 
                                                                             meetingDate);   
            for (int i=1; i <= 8; i++) {
              String timeSlot = Meeting.timeslots.get(String.valueOf(i));
              if (bookedTimeSlots.contains(String.valueOf(i))) {
                timeSlot += "(Booked)";
              }
              System.out.print(String.valueOf(i) + ". " + timeSlot + "   ");
            }

            // Ask the user to selecte a timeslot
            System.out.print("\nPlease select a timeslot: ");  
            int timeSlotChoice = Integer.parseInt(input.nextLine());          

            // Now we have all data needed to schedule a meeting
            Meeting meeting = new Meeting(immigrant.getName(), immigrant.getUsername(), 
                                          selectedTutor.getName(), selectedTutor.getUsername(),
                                          meetingDate, String.valueOf(timeSlotChoice),       
                                          Meeting.generateMeetingUrl(), courseSelected);  
            boolean booked = Meeting.bookMeeting(meeting);

            System.out.println();
            // Tell user the status of the meeting he/she try booking
            if (booked) {
              System.out.println("You meeting has been booked: ");
            } else {
              System.out.println("Failed to bbook the meeting. Please try again.");              
            }
            
            System.out.println();            
            // Prompt user to continue with other scheduling option or sign out
            System.out.println("Press any key to continue meeting scheduleing.");
            String toDo = input.nextLine();
            clearScreenAll();
            continue;
          } else if (scheduleChoice == MAIN_IMMIGRANT_LOGININ_CHANGE) {
            // Menu option "Change a meeting" is selected

            if (myMeetings.size() == 0) {
              // Currently no meetings scheduled for this immigrant. Prompt the users
              // to enter a key to skip meeting update 
              System.out.println();            
              System.out.println("You don't have a meeting scheduled as of now. Press any key to continue.");
              String toDo = input.nextLine();
              clearScreenAll();              
              continue;
            } 

            // Ask the user to select the meeting to change/update, or skip this updating
            System.out.print("Select the number of meeting to update, or press Enter key to skip updating: ");
            int noMeetToUpdate = getValidMenuSelection(input, myMeetings.size(), ENTER_KEY);
            if (noMeetToUpdate == -1) {
              // The user selected to skip meeting update
              clearScreenAll();
              continue;
            }

            // Get the Meeting object for the selected meeting
            Meeting currentMeeting = myMeetings.get(noMeetToUpdate-1);

            // Ask the users to enter the date for the new meeting 
            String meetingDate = selectValidMeetingDate(input);        
  
            // Get and display booked timeslots for the selected date and tutor
            Tutor meetingTutor = Tutor.searchTutor(currentMeeting.getTutorUsername());
            ArrayList<String> bookedTimeSlots = Meeting.getScheduledTimeslot(meetingTutor.username, 
                                                                             meetingDate);   
            for (int i=1; i <= 8; i++) {

              String timeSlot = Meeting.timeslots.get(String.valueOf(i));
              if (bookedTimeSlots.contains(String.valueOf(i))) {
                timeSlot += "(Booked)";
              }
              System.out.print(String.valueOf(i) + ". " + timeSlot + "   ");
            }

            // Ask the user to selecte a timeslot for the new meeting
            System.out.print("\nPlease select a timeslot: ");  
            int timeSlotChoice = Integer.parseInt(input.nextLine());          

            // Now we have all data needed to create the new meeting
            Meeting Updatedmeeting = new Meeting(currentMeeting.getimmigrantName(),     
                                                 currentMeeting.getImmigrantUsername(), 
                                                 currentMeeting.getTutorName(), 
                                                 currentMeeting.getTutorUsername(),
                                                 meetingDate, String.valueOf(timeSlotChoice),       
                                                 Meeting.generateMeetingUrl()); 

            // Update the 'currentMeeting' with the new 'updatedMeeting'
            Meeting.updatemeeting (currentMeeting, Updatedmeeting);
            System.out.println("Your meeting is successfully rescheduled!");
            System.out.println("Press any key to continue meeting scheduleing.");
            String toDo = input.nextLine();
            clearScreenAll();
            continue;
          } else if (scheduleChoice == MAIN_IMMIGRANT_LOGININ_CANCEL) {
           // Menu option "Cancel a meeting" is selected

            if (myMeetings.size() == 0) {
              
              // Currently no meetings scheduled for this immigrant. Prompt the users
              // to enter a key to skip meeting cancelling 
              System.out.println();            
              System.out.println("You don't have a meeting scheduled as of now. Press any key to continue.");
              String toDo = input.nextLine();
              clearScreenAll();              
              continue;
           }
         
            // Ask the user to select the meeting to change/update, or skip this updating
            System.out.print("Select the number of meeting to update, or press Enter key to skip updating: ");
            int noMeetToUpdate = getValidMenuSelection(input, myMeetings.size(), ENTER_KEY);
            if (noMeetToUpdate == -1) {
              // The user selected to skip meeting update
              clearScreenAll();
              continue;
            }

            // Cancel the selected appointment
            Meeting meetToCanacel = myMeetings.get(noMeetToUpdate-1);
            boolean isCancelled = Meeting.cancelMeeting(meetToCanacel); 
            if (isCancelled) {
              System.out.println("The book is successfully cancelled.");
            } else {
              System.out.println("Failed to cancel the meeting. Please try again.");              
            }

            System.out.println("Press any key to continue meeting scheduleing.");
            String toDo = input.nextLine();
            //if (toDo.equalsIgnoreCase("c")) {
              clearScreenAll();
              continue;
            //} else {
            //  clearScreen();
            //  scheduleDone = true;            
            //}
            
          } else if (scheduleChoice == MAIN_IMMIGRANT_LOGININ_SIGN_OUT) {
            // 'Sign out' menu option is selected, clear screen and back to the main (initial)
            // menu of the application
            clearScreen();
            scheduleDone = true;
          }
        }
      } else if (menuChoice == MAIN_TUTOR_LOGININ) {
        // Tutor login menu option is selected

        // Variables to store username and password to be entered from the uesr
        String username = "";
        String password = "";
        System.out.println();
        System.out.println("Please sign in with your login credentials:\n");  

        // Repeatedly prompt the user to enter username and password till a valid username
        // and password are enterted
        while (true) {
        
          System.out.print("User name:");
          username = input.nextLine();
          System.out.print("Password:");
          password = input.nextLine();        
          if (Tutor.IsValidLogin(username, password)) {
            break;
          } else {
            System.out.println();            
            System.out.println("Incorrect username or password. Type the correct username\n" +
                              "and password, and try again");
            System.out.println();             
          }
        } 

        clearScreenAll(); 
        
        // Get the Tutor object for the login user
        Tutor tutor = Tutor.searchTutor(username);

        System.out.println();
        System.out.println("Welcome back " + tutor.getName() + "!") ;
        System.out.println();        

        boolean tutorActionDone = false;  
        // repeatedly continue tutor meun options after logi, till the 'Sign out' option
        // is selected, when the code will back to the main menu of the app
        while (!tutorActionDone) {    

          // Didpslay tutor menu and select a valid menu option
          displayMenu("MENU_TUTOR");
          int tutorMenuChoice = getValidMenuSelection(input, 3);
          
          if (tutorMenuChoice == MAIN_TUTOR_LOGININ_ALL_MEETINGS) {
            // 'Display all my meetings' option is selected
            
            System.out.println();            
            System.out.println("Meeting(s) currently booked for you:") ;        
            System.out.println();            
         
            // Display all the meetings booked for the tutor
            ArrayList<Meeting> myMeetings = tutor.getMyMeetings();
            if (myMeetings.size() == 0) {
              System.out.println("Currently no meeting is booked");
            } else {
              int meetingNo = 1;
              for (Meeting m : myMeetings) {
                Immigrant immigrant = Immigrant.searchImmigrant(m.getImmigrantUsername());
                System.out.println("No. " + String.valueOf(meetingNo) + ".\n" + m.toString());
                System.out.println(immigrant.toString());
                System.out.println();                
                meetingNo++;
              }
            }

            // Allow the user to press any key to continue with options of current menu
            System.out.println("Press any key to continue.");
            String toDo = input.nextLine();
            clearScreenAll();
            continue;
          } else if (tutorMenuChoice == MAIN_TUTOR_LOGININ_ALL_STUDENTS) {
            // 'Display all my students' option is selected
            
            System.out.println("The immigrants you have tutored:");
            System.out.println();
            
            // Get and display the usernames of immigrant the tutor have taught for 
            // each course so far
            ArrayList<Meeting> myMeetings = tutor.getMyMeetings();            
            if (myMeetings.size() == 0) {
              System.out.println("You do not have any student as of now.");
            } else {
              // Students with same name and course should be count only once and stored
              // in 'uniqueStudentCourseList'
              ArrayList<String> uniqueStudentCourseList = new ArrayList();
              int studentNo = 1;
              for (Meeting m : myMeetings) {
                if (uniqueStudentCourseList.size() == 0) {
                  uniqueStudentCourseList.add(m.getImmigrantUsername() + m.getCourse());
                }
                else {
                  if (uniqueStudentCourseList.contains(m.getImmigrantUsername() + m.getCourse())) {
                    continue;
                  }
                }
                Immigrant immigrant = Immigrant.searchImmigrant(m.getImmigrantUsername());
                System.out.println(String.valueOf(studentNo) + ".\t" + immigrant.getName() + "\t" 
                                   + immigrant.getEmail() + "\n\tCourse: \"" + m.getCourse() + "\"");
                studentNo++;
              }
            }
            
            System.out.println();
            // Allow the user to press any key to continue with options of current menu
            System.out.println("Press any other key to continue.");
            String toDo = input.nextLine();
            clearScreenAll();
            continue;
          } else if (tutorMenuChoice == MAIN_TUTOR_LOGININ_SIGN_OUT) {
            // 'Sign out' option is selected 

            // Clear the screen and back to the main menu of the application
            clearScreen();
            tutorActionDone = true;            
          }
        }
        
      } else if (menuChoice == MAIN_SIGN_UP) {
        // 'Sign Up' menu option is selected
        
        Tutor tutor = new Tutor();     

        // Invoke 'signUp' method to go through the sign up process
        // And let user know if the sign up is sucessful or not
        boolean isSignedUp = signUp(input);
        if (isSignedUp) {
          System.out.println();
          System.out.println("You have successfully signed up!");
          System.out.println();
          System.out.println("Please press any key to contiune.");          
          input.nextLine();
          clearScreen(); 
          
        } else {
          System.out.println("Failed");          
        }
        
      } else if (menuChoice == MAIN_MENU_MAIN_EXIT) {
        // The 'Exit' menu option of the main menu is selected, exit the application
        clearScreenAll();
        System.out.println("Thank you for using the application. Bye!");
        break;
      }
    }
  }


  /**
   * Displays a menu based on the selected menu parameter.
   *
   * @param selectedMenu the menu to be displayed
   */  
  public static void displayMenu(String selectedMenu) {
      // From the all menuStr 'menuStr', find the start and end position of the menu
      // 'selectedMenu' to be displayed.
      int start = menuStr.indexOf("[" + selectedMenu + "_START]") + ("[" + selectedMenu + "_START]").length();
      int end = menuStr.indexOf("[" + selectedMenu + "_END]", start);
  
      // Using the subString method to carve out the string for 'selectedMenu' from 'menuStr'
      String menuContent = menuStr.substring(start, end);
  
      // Plug in actual formatting code as per specified in the 'menuStr'. Ex. the
      // [BlueColor] specified in the 'menuStr' (from menu.txt file) will be replaced
      // by actual format code for blue font "\033[94m".
      menuContent = menuContent.replace("[BlueColor]", "\033[94m")
                               .replace("[ColorEnd]", "\033[0m")
                               .replace("[CyanColor]", "\033[96m")
                               .replace("[RedColor]", "\033[91m")
                               .replace("[Bold]", "\033[1m")
                               .replace("[Cyan_underscore]", "\033[96m\033[4m");
  
      // Display the menu 'selectedMenu'
      System.out.println(menuContent);
  }


  /**
   * Loads the context of menus from a file.
   */  
  public static void loadMenusContext() {
    try {
        // Initialize the Scanner object to read data from file 'menu.txt'
        Scanner fileInput = new Scanner(new File("menu.txt"));

        // Traverse the menu.txt line by line to image the content of the file
        // into String var 'menuStr', with all format of the context reserved.
        while (fileInput.hasNextLine()) {
            menuStr += fileInput.nextLine() + "\n";
        }

        // Close the scanner to release resource
        fileInput.close();
    } catch (IOException ex) {
        // Catch exception if file 'menu.txt' does not exist and print
        // out friendly message to the users
        System.out.println("The file 'menu.txt' is not found.");
    }
  }


  /**
   * Get the user's menu choice and validates it.
   *
   * @param input a Scanner object used to collect user inputs
   * @param max the maximum valid menu option
   *
   * @return the validated menu choice as an integer
   */  
  public static int getValidMenuSelection(Scanner input, int max) {

    int choice = 0;
    while (true) {
      System.out.print("Enter menu option: ");
      try {
        choice = Integer.parseInt(input.nextLine());
      } catch (Exception e) {
        System.out.println("Please enter an integer.\n");
        continue;
      }
      
      if (choice < 1 || choice > max) {
        // The user entered an invalid menu choice number
        System.out.println("Please enter an integer between 1 and " + max + ".\n");
      } else {
         // The user entered a valid menu choice number
        return choice;
      }
    }
  }

    
  /**
   * Get the user's menu choice and validates it.
   *
   * @param input a Scanner object used to collect user inputs
   * @param max the maximum valid menu option
   * @param exitLetter the user input letter that exit menu selection loop
   *
   * @return the validated menu choice as an integer, or return -1 for exit
   */  
  public static int getValidMenuSelection(Scanner input, int max, String exitLetter) {

    int choice = 0;
    while (true) {
      //System.out.print("Enter menu option: ");
      try {
        String selection = input.nextLine();
//System.out.println("Entery Key =" + (Char)selection + " exitLetter=" + (Char)exitLetter);        
        if (selection.equalsIgnoreCase(exitLetter)) {
          // The 'Enter' key is pressed, return exit number
          return -1;
        }
        
        choice = Integer.parseInt(selection);
      } catch (Exception e) {
        System.out.println("Please enter an integer.\n");
        continue;
      }
      
      if (choice < 1 || choice > max) {
        // The user entered an invalid menu choice number
        System.out.println("Please enter an integer between 1 and " + max + ".\n");
      } else {
         // The user entered a valid menu choice number
        return choice;
      }
    }
  }    

    
  /**
   * Sign up a new user, either an immigrant or a tutor.
   *
   * @param input a Scanner object used to collect user inputs
   * @return true if the sign up is successful, or false if sign up fails
   */    
  public static boolean signUp(Scanner input) {

    // Allow the users to select whether they will sign up as an immigrant or a tutor
    displayMenu("MENU_USERT_TYPE");
    int menuSelection = getValidMenuSelection(input, 2);
    
    if (menuSelection == MAIN_SIGN_UP_IMMIGRANT) {
      // The user choose to sign up as an immigrant
      
      System.out.println();
      System.out.println("You have chosen to sign up as an immigrant:") ;      

      // The following code blocks are to aske the users to enter all the 
      // information for sign up
      
      System.out.println();            
      System.out.println("Please enter the following information:") ;
      
      System.out.print("Name (First Last): ");
      String name = input.nextLine(); 
      
      System.out.print("Email: ");
      String email = input.nextLine();       
 
      System.out.print("Phone number(ddd-ddd-dddd): ");
      String phoneNumber = input.nextLine(); 
 
      System.out.print("Residence area: ");
      String residenceArea = input.nextLine(); 
 
      System.out.print("Native language: ");      
      String nativeLanguage = input.nextLine(); 

      String username = "";
      String userPassword = "";
      // Loop to allow the users to repeatedly enter username and password till the 
      // valid username and password are collected
      while (true) {
        System.out.print("User Name: ");            
        username = input.nextLine().trim(); 

        boolean isUsernameAvailable = Immigrant.isUserNameAvailable(username);
        if (username.length() == 0) {
          // The user cannot enter a username with blank space(s) only
          System.out.println("You must enter a username."); 
          continue;          
        }
        else if (!isUsernameAvailable) {
          // The user cannot use a username that is already used by other users of the app
          System.out.println("The username already exists. Please select a different username.");
          continue;
        }
        // A valid username is entered if the code execution reaches here
        break;
      }  
      
      while (true) {   
        
        System.out.print("User Password: ");
        userPassword = input.nextLine().trim(); 
        if (userPassword.length() == 0) {
          // The user cannot enter a password with blank space(s) only
          System.out.println("You must enter a password."); 
          continue;
        }
        // A valid password is entered if the code execution reaches here        
        break;
      }

      // Instantiate an Immigrant object based on the user inputs
      Immigrant immigrant = new Immigrant(name, email, phoneNumber, residenceArea, nativeLanguage,
                                     username, userPassword);
      // Save the user profile into data base
      boolean result = immigrant.saveProfileToDatabase(immigrant);
      return result;
      
    } else if (menuSelection == MAIN_SIGN_UP_TUTOR) {
      // The user choose to sign up as an immigrant
     
      System.out.println();
      System.out.println("You have chosen to sign up as a tutor :") ;   

      // The following code blocks are to aske the users to enter all the 
      // information for sign up
      
      System.out.println();            
      System.out.println("Please enter the following information:") ;
      
      System.out.print("Name (First Last): ");
      String name = input.nextLine(); 
      
      System.out.print("Email: ");
      String email = input.nextLine();       
 
      System.out.print("Phone number(ddd-ddd-dddd): ");
      String phoneNumber = input.nextLine(); 
 
      System.out.print("Residence area: ");
      String residenceArea = input.nextLine(); 

      System.out.println();
      System.out.println("Course Teaching: ");   
      String courseTeaching = selectCourse(input);; 
      System.out.println();
      
      int fee = 0;
      while (true) {
        System.out.print("Fee (C$/hour, Integer amount): ");   
        try {
          String strFee = input.nextLine();   
          fee = Integer.parseInt(strFee);
          break;
        } catch (Exception ex) {
          System.out.println();
          System.out.println("Invalid input, please enter an integer amount.");
          System.out.println();          
        }
      }

      System.out.println();
      int expertiseLevel = selectTutorSignUpExpertise(input);
      System.out.println();

      String secondLanguage = selectTutorSecondLanguage(input);
      System.out.println();
      
      String username = "";
      String userPassword = "";
      // Loop to allow the users to repeatedly enter username and password till the 
      // valid username and password are collected      
      while (true) {
        
        System.out.print("User Name: ");            
        username = input.nextLine().trim(); 

        boolean isUsernameAvailable = Immigrant.isUserNameAvailable(username);
        if (username.length() == 0) {
          // The user cannot enter a username with blank space(s) only          
          System.out.println("You must enter a username."); 
          continue;          
        }
        else if (!isUsernameAvailable) {
          // The user cannot use a username that is already used by other users of the app          
          System.out.println("The username already exists. Please select a different username.");
          continue;
        }
        
        // A valid username is entered if the code execution reaches here        
        break;
      }  
      
      while (true) {   
        
        System.out.print("User Password: ");
        userPassword = input.nextLine().trim(); 
        if (userPassword.length() == 0) {
          // The user cannot enter a password with blank space(s) only              
          System.out.println("You must enter a password."); 
          continue;
        }

        // A valid password is entered if the code execution reaches here          
        break;
      }

      // Instantiate a Tutor object based on the user inputs
      Tutor tutor = new Tutor(name, email, phoneNumber, residenceArea, courseTeaching,
                              fee, expertiseLevel, username, userPassword, secondLanguage);
      // Save the user profile into data base
      boolean result = tutor.saveProfileToDatabase(tutor);
      return result;      
    } else {
      System.out.print("Invalid menu selection.");
      return false;
    }
  }


  /**
   * Clears the console contents and displays the application description block.
   */
  private static void clearScreen() {
      // Clear console contents
      System.out.print("\033[H\033[2J");
      System.out.flush();
  
      // Redisplay application description block
      displayMenu("MENU_APPLICATION_LOAD");
  } 

    
  /**
   * Clears the entire console contents
   */
  private static void clearScreenAll() {
      // Clear console contents
      System.out.print("\033[H\033[2J");
      System.out.flush();
  } 


  /**
   * Select a course.
   *
   * @param input a Scanner object used to collect user inputs
   * @return the course selected 
   */    
  private static String selectCourse (Scanner input) {

    // Get all the courses available
    Hashtable<String, String> courseList =Tutor.courseList;

    // create a TreeMap to sort the courseList ordered by key
    TreeMap<String, String> tmCourseList = new TreeMap<String, String>(courseList);    

    // Dynamicly construct the course selection menu based on the courses availalbe
    Iterator<String> itr = tmCourseList.keySet().iterator();
    while (itr.hasNext()) {

      String key = itr.next();
      String value = courseList.get(key);
      System.out.println(key + "\t\t" + value);            
    }   

    // Ask the user to select the course
    int courseNo = getValidMenuSelection(input, 5);
    // Return the course selected
    return courseList.get(String.valueOf(courseNo));
  }


  /**
   * Select a expertise level of a tutor as a criterion in searching tutors.
   *
   * @param input a Scanner object used to collect user inputs
   * @return the expertise level of a tutor
   */      
  private static int selectTutorExpertise (Scanner input) {

    // Get all the tutor's expertise levels available for the user to select
    // as a criterion in searching tutors, this includes the options "All levels",
    // meaning no specific level is selected as a criterion
    Hashtable<String, String> levels =Tutor.experticeLevels;
    
    // create a TreeMap to sort the courseList by key
    TreeMap<String, String> tmLevels = new TreeMap<String, String>(levels);    

    // Dynamicly construct the tutor expertise level selection menu based on 
    // all the expertise levels availalbe
    Iterator<String> itr = tmLevels.keySet().iterator();
    while (itr.hasNext()) {
      String key = itr.next();
      String value = levels.get(key);
      System.out.println(key + "\t\t" + value);            
    }   

    // Ask the user to select the tutor's expertise level
    int expertiseNo = getValidMenuSelection(input, 4);

    // Return the expertise level selected
    return expertiseNo;
  }  


  /**
   * Select a expertise level of a tutor in tutor's sign up.
   *
   * @param input a Scanner object used to collect user inputs
   * @return the expertise level of a tutor
   */     
  private static int selectTutorSignUpExpertise (Scanner input) {

    System.out.println("Select your expertise level:");

    // Get all the available expertise available for a tutor in sign up.
    Hashtable<String, String> levels =Tutor.experticeLevels;
     // create a TreeMap to sort the available second language by key
    TreeMap<String, String> tmLevels = new TreeMap<String, String>(levels);    

    // Loop through the avaialable expertise levels
    Iterator<String> itr = tmLevels.keySet().iterator();
    while (itr.hasNext()) {

      // We need to skip the first 'All levels', which is an selection option 
      // for user to select for searching tutors, but not a valid option
      // here for the tutor user to sign up
      String key = itr.next();
      if (key.equals("1")) {
        continue;
      }
      
      String value = levels.get(key);
      int intKey = Integer.parseInt(key);      
      key = String.valueOf(intKey-1);      
      System.out.println(key + "\t" + value);            
    }   

    // Ask the user to select the tutor's expertise level    
    int expertiseNo = getValidMenuSelection(input, 3);

    // Return the expertise level selected
    return expertiseNo;
  }    


  /**
   * Select the second language of a tutor in booking a meeting
   *
   * @param input a Scanner object used to collect user inputs
   * @return the second language of a tutor
   */     
  private static String selectTutorSecondLanguage (Scanner input) {

    System.out.println("Select the second language (other than English) you are proficient:");
    // Get all the second languages for a tutor to select in sign up.
    Hashtable<String, String> seclangs =Tutor.secondLanguageList;

    // create a TreeMap to sort the the 'secLangs' by key
    TreeMap<String, String> tmSeclangs = new TreeMap<String, String>(seclangs);    

    // Display all the languages for the user to select    
    Iterator<String> itr = tmSeclangs.keySet().iterator();
    while (itr.hasNext()) {
      String key = itr.next();
      String value = seclangs.get(key);
      System.out.println(key + "\t\t" + value);            
    }   

    // Get a valide selection of second language
    int secLangNo = getValidMenuSelection(input, 6);
    if (secLangNo == SECOND_LANGUAGE_NOT_IN_LIST) {
      // If no selection matching, simply return empty string
      return "";
    }

    // Return the selected second language    
    return seclangs.get(String.valueOf(secLangNo));
  } 

  
  /**
   * Select the tutoring fee range
   *
   * @param input a Scanner object used to collect user inputs
   * @return the range of tutoring fee
   */     
  private static String selectTutoringFee (Scanner input) {

    System.out.println("Criteria 3: select tutoring fee:");
    // Get the list of all price ranges
    Hashtable<String, String> fees =Tutor.tutoringFeeList;
    // Create a TreeMap to sort the price rage by key
    TreeMap<String, String> tmFees = new TreeMap<String, String>(fees);    

    // Display all fee ranges for the user to select
    Iterator<String> itr = tmFees.keySet().iterator();
    while (itr.hasNext()) {
      String key = itr.next();
      String value = fees.get(key);
      System.out.println(key + "\t\t" + value);            
    }   

    // Get a valid selction of price range
    int feeNo = getValidMenuSelection(input, 3);

    // Return the index number of the selected price range
    return fees.get(String.valueOf(feeNo));
  } 

    
  /**
   * Validates a date entered from the user.
   *
   * @return the validated date as a string in the format "MM/dd/yyyy"
   */
  public static String selectValidMeetingDate(Scanner input) {
    
    String formatString = "MM/dd/yyyy"; // define the format string
    // Allow the user to repeatedly enter a meeting date till a valid meeting date
    // is entered
    while (true) {

      System.out.print("Enter the date of the meeting (mm/dd/yyyy): ");

      String date = input.nextLine(); // get date
      try {
        // Get the formatted day
        SimpleDateFormat format = new SimpleDateFormat(formatString);
        format.setLenient(false);
        format.parse(date); // parse the date and see if there is an error to be caught

        Date currentDate = new Date(); // current date
        Date enteredDate = format.parse(date); // date entered
        if (currentDate.after(enteredDate)) { // use built-in method to compare the dates
          // A meeting date should be after today
          System.out.println("Please enter a day after today.\n");
            continue;
        }
        
        return date;
      } catch (ParseException e) {
        // Invalid date entered, prompt the user to enter again
        System.out.println("Invalid date. Try again.\n"); // error message
        continue;
      }
    }
  }  

  /**
   * Recursively print the first n elements stored in a ArrayList.
   *
   * @param myList the list to be printed
   * @param n the number of elements to be printed
   * 
   * @return true if the meeting is booked and saved into database, or false
   * if failed to save the meeting into database    
   */
  // https://1drv.ms/x/s!Ahl0GW6fjj7rlgo5SKl5nQUxKl_r?e=krs63q
  public static void printListInOrder(ArrayList<String> myList, int n) {
    if (n > 1) {
      printListInOrder(myList, n-1);                
      System.out.println(String.valueOf(n) + ":" + myList.get(n-1));
      System.out.println();
    } else {
      System.out.println(String.valueOf(n) + ":" + myList.get(n-1) );        
      System.out.println();      
    }
  }  
}
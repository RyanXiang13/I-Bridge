/**
 * The Person class represents a person with basic information such as name, date of birth, and gender.
 */
class Person {
  
  /**
   * The name of the person.
   */
  protected String name;

  /**
   * The email of the person.
   */  
  protected String email;  

  /**
   * The phone number of the person.
   */    
  protected String phoneNumber;  

  /**
   * The residence area of the person.
   */
  protected String residenceArea;

  /**
   * The user name of the person for this appl.
   */  
  protected String username;

  /**
   * The user password of the person for this appl.
   */  
  protected String password;


  
  /**
   * Constructs a Person object with the specified name, email, phone number, and residence area.
   *
   * @param name   the name of the person
   * @param email    the email of the person
   * @param phoneNumber the phone number of the person
   * @param residenceArea the residence area of the person
   */
  public Person(String name, String email, String phoneNumber, String residenceArea) {
    this.name = name;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.residenceArea = residenceArea;
  }

  /**
   * Constructs a Person object with the specified name, email, phone number, and residence area,
   * username, and password
   *
   * @param name   the name of the person
   * @param email    the email of the person
   * @param phoneNumber the phone number of the person
   * @param residenceArea the residence area of the person
    * @param username the username of the person
   * @param password the password the person      
   */
  public Person(String name, String email, String phoneNumber, String residenceArea,
                String username, String password) {
    this.name = name;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.residenceArea = residenceArea;
    this.username = username;
    this.password = password;
  }  

  
  /**
   * Returns the name of the person.
   *
   * @return the name of the person
   */
  public String getName() {
    return this.name;
  }

  
  /**
   * Returns the email of the person.
   *
   * @return the email of the person
   */
  public String getEmail() {
    return this.email;
  }
  
  
  /**
   * Returns the phone number of the person.
   *
   * @return the phone number of the person
   */
  public String getPhoneNumber() {
    return this.phoneNumber;
  }


   /**
   * Gets the residence area of the person.
   *
   * @return the residence area of the person
   */
  public String getResidenceArea() {
    return this.residenceArea;
  }

  
  /**
   * Gets the username of the person.
   *
   * @return the username of the person
   */
  public String getUsername() {
    return this.username;
  }

  /**
   * Gets the password of the person.
   *
   * @return the password of the person
   */
  public String getPassword() {
    return this.password;
  }
  
  /**
   * Sets the residence area of the person.
   *
   * @param residenceArea the new residence area of the person
   */
  public void setResidenceArea(String residenceArea) {
    this.residenceArea = residenceArea;
  }
  
}

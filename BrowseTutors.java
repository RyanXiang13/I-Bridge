import processing.core.PApplet;

/**
 * Represents a browseTutors object in the sketch.
 */
public class BrowseTutors {

  private int x, y, speed;
  private String color;
  private PApplet app;
  
  /**
   * Constructs a browseTutors object with the specified parameters.
   * 
   * @param p     The PApplet object.
   * @param x     The x-coordinate of the browseTutors object.
   * @param y     The y-coordinate of the browseTutors object.
   * @param speed The speed of the browseTutors object.
   * @param color The color of the browseTutors object.
   */
  public BrowseTutors(PApplet p, int x, int y, int speed, String color) {
    this.app = p;
    this.x = x;
    this.y = y;
    this.speed = speed;
    this.color = color;
  }
  
  /**
   * Moves the browseTutors object by the specified amount.
   * 
   * @param nx The amount to move the browseTutors object along the x-axis.
   * @param ny The amount to move the browseTutors object along the y-axis.
   */
  public void move(int nx, int ny) {
    this.x += nx;
    this.y += ny;
  }

  /**
   * Returns the x-coordinate of the browseTutors object.
   * 
   * @return The x-coordinate.
   */
  public int getX() {
    return this.x;
  }
  
  /**
   * Returns the y-coordinate of the browseTutors object.
   * 
   * @return The y-coordinate.
   */
  public int getY() {
    return this.y;
  }
  
  /**
   * Returns the speed of the browseTutors object.
   * 
   * @return The speed.
   */
  public int getSpeed() {
    return speed;
  }

  /**
   * Sets the x-coordinate of the browseTutors object.
   * 
   * @param x The new x-coordinate.
   */
  public void setX(int x) {
    this.x = x;
  }

  /**
   * Sets the y-coordinate of the browseTutors object.
   * 
   * @param y The new y-coordinate.
   */
  public void setY(int y) {
    this.y = y;
  }

  /**
   * Sets the speed of the browseTutors object.
   * 
   * @param speed The new speed.
   */
  public void setSpeed(int speed) {
    this.speed = speed;
  }
  
  /**
   * Draws the browseTutors object on the sketch.
   */
  public void draw() {
    app.fill(255, 255, 255);
    app.ellipse(x, y, 150, 150);
  }

  /**
   * Checks if the browseTutors object is clicked based on the mouse coordinates.
   * 
   * @param mouseX The x-coordinate of the mouse.
   * @param mouseY The y-coordinate of the mouse.
   * @return       {@code true} if the browseTutors object is clicked, {@code false} otherwise.
   */
  public boolean isClicked(int mouseX, int mouseY) {
    float d = PApplet.dist(mouseX, mouseY, x, y);
    // Return true if the mouse is within 75px of the browseTutors object's position
    return d < 75; 
  }
}

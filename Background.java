import processing.core.PApplet;

/**
 * Represents a background object in the sketch.
 */
public class Background {

  private int x, y, speed, r, g, b;
  private PApplet app;
  
  /**
   * Constructs a background object with the specified parameters.
   * 
   * @param p     The PApplet object.
   * @param x     The x-coordinate of the background.
   * @param y     The y-coordinate of the background.
   * @param speed The speed of the background.
   * @param r     The red component of the background color.
   * @param g     The green component of the background color.
   * @param b     The blue component of the background color.
   */
  public Background(PApplet p, int x, int y, int speed, int r, int g, int b) {
    this.app = p;
    this.x = x;
    this.y = y;
    this.speed = speed;
    this.r = r;
    this.g = g;
    this.b = b;
  }
  
  /**
   * Moves the background by the specified amount.
   * 
   * @param nx The amount to move the background along the x-axis.
   * @param ny The amount to move the background along the y-axis.
   */
  public void move(int nx, int ny) {
    this.x += nx;
    this.y += ny;
  }

  /**
   * Returns the x-coordinate of the background.
   * 
   * @return The x-coordinate.
   */
  public int getX() {
    return this.x;
  }
  
  /**
   * Returns the y-coordinate of the background.
   * 
   * @return The y-coordinate.
   */
  public int getY() {
    return this.y;
  }
  
  /**
   * Returns the speed of the background.
   * 
   * @return The speed.
   */
  public int getSpeed() {
    return speed;
  }

  /**
   * Sets the x-coordinate of the background.
   * 
   * @param x The new x-coordinate.
   */
  public void setX(int x) {
    this.x = x;
  }

  /**
   * Sets the y-coordinate of the background.
   * 
   * @param y The new y-coordinate.
   */
  public void setY(int y) {
    this.y = y;
  }

  /**
   * Sets the speed of the background.
   * 
   * @param speed The new speed.
   */
  public void setSpeed(int speed) {
    this.speed = speed;
  }
  
  /**
   * Draws the background on the sketch.
   */
  public void draw() {
    app.fill(r, g, b);
    app.ellipse(x, y, 50, 50);
  }  
}

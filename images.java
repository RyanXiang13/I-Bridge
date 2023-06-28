import processing.core.PApplet;
import processing.core.PImage;
import java.awt.*;
import java.util.*;

public class images {

  private int x, y, speed;
  private String color;
  private PImage pimage;
  private PApplet app;
  
  public images(PApplet p, int x, int y, int speed, String color, PImage pimage) {
    this.app = p;
    this.x = x;
    this.y = y;
    this.speed = speed;
    this.color = color;
    this.pimage = pimage;
  }
  
  public void move(int nx, int ny) {
    this.x += nx;
    this.y += ny;
  }

  public int getX() {
    return this.x;
  }
  public int getY() {
    return this.y;
  }
  public int getSpeed() {
    return speed;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }
  
  public void draw() {
    app.image(this.pimage, x, y);
  }
  
}
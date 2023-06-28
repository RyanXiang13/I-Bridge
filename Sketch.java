import processing.core.PApplet;
import processing.core.PImage;
import java.awt.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The Sketch class represents a Processing sketch that displays tutor profiles.
 */
public class Sketch extends PApplet {
  private PApplet app = new PApplet();
  private browseTutors firstProfile, secondProfile;
  private static boolean left, right;
  private static int curIdx = 0;
  private static ArrayList<Tutor> tutors;
  private boolean selected = false;
  private PImage profilePicture, profilePictureCopy, bronzeMedal, silverMedal, goldMedal;
  private images bMedal, sMedal, gMedal, firstMedal, secondMedal, profilePicture1, profilePicture2;
  private Background a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t;
  private Background[][] backgroundLayout = {{a, b, c, d}, {e, f, g, h}, {i, j, k, l}, {m, n, o, p}, {q, r, s, t}};
  
  /**
   * Sets the size of the sketch window.
   */
  public void settings() {
    size(360, 500);
  }

  /**
   * Initializes the sketch by setting up the backgrounds, profiles, and images.
   */
  public void setup() {
    int x = 360;
    int y = -55;
    int red, green, blue;
    for (int i = 0; i < 5; i++) {
      x -= 300;
      y += 100;
      for (int j = 0; j < 4; j++) {
        red = ThreadLocalRandom.current().nextInt(0, 256);
        green = ThreadLocalRandom.current().nextInt(0, 256);
        blue = ThreadLocalRandom.current().nextInt(0, 256);
        if (i == 2 || i == 3) {
          red = 0;
          green = 0;
          blue = 0;
        }
        backgroundLayout[i][j] = new Background(this, x, y, 15, red, green, blue);
        x += 75;
      }
    }
    firstProfile = new browseTutors(this, 180, 130, 15, "black");
    secondProfile = new browseTutors(this, 300, 130, 15, "black");
    bronzeMedal = loadImage("images/bronze medal.png");
    silverMedal = loadImage("images/silver medal.png");
    goldMedal = loadImage("images/gold medal.png");
    profilePicture = loadImage("images/default pfp.png");
    profilePictureCopy = loadImage("images/default pfp.png");
    profilePicture1 = new images(this, 107, 54, 15, "black", profilePicture);
    profilePicture2 = new images(this, 107, 54, 15, "black", profilePictureCopy);
    bMedal = new images(this, 275, 100, 15, "black", bronzeMedal);
    sMedal = new images(this, 275, 100, 15, "black", silverMedal);
    gMedal = new images(this, 275, 100, 15, "black", goldMedal);

    if (tutors.get(curIdx).getExpertise() == 1) {
      firstMedal = bMedal;
    } else if (tutors.get(curIdx).getExpertise() == 2) {
      firstMedal = sMedal;
    } else {
      firstMedal = gMedal;
    }
    
    textSize(20);
  }

  /**
   * Draws the sketch by displaying backgrounds, profiles, images, and text.
   */
  public void draw() {
    fill(0);
    background(0, 0, 0);

    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 4; j++) {
        backgroundLayout[i][j].draw();
      }
    }
    firstProfile.draw();
    profilePicture1.draw();
    text(tutors.get(curIdx).getName(), 50, 230);
    text(tutors.get(curIdx).getCourseTeaching(), 50, 255);
    text("Fee: $"+tutors.get(curIdx).getFee()+"/h", 50, 280);

    if (tutors.get(curIdx).getExpertise() == 1) {
      text("Elementary Level", 50, 305);
      secondMedal = bMedal;
    } else if (tutors.get(curIdx).getExpertise() == 2) {
      text("Intermediate Level", 50, 305);
      secondMedal = sMedal;
    } else {
      text("Expert Level", 50, 305);
      secondMedal = gMedal;
    }
    secondMedal.draw();
    text("Second Language: "+tutors.get(curIdx).getSecondLanguage(), 50, 330);
    
    if (selected) {
      selected = false;
    }
    if (left) {
      secondProfile.draw();
      secondMedal.draw();
      profilePicture2.draw();
      firstProfile.move(-10, 0);
      firstMedal.move(-10, 0);
      profilePicture2.move(-10, 0);
      secondMedal.move(-10, 0);
      secondProfile.move(-10, 0);
      
      if (secondProfile.getX() >= 180) {
        secondProfile.move(-10, 0);
        secondMedal.move(-10, 0);
        profilePicture2.move(-10, 0);
      } else {
        secondProfile.setX(300);
        secondMedal.setX(300);
        profilePicture2.setX(300);
        firstProfile.setX(180);
        firstMedal.setX(275); 
        profilePicture1.setX(107);
        left = false;
      }
    } else if (right) {
      secondProfile.draw();
      secondMedal.draw();
      profilePicture2.draw();
      firstProfile.move(10, 0);
      firstMedal.move(10, 0);
      profilePicture2.move(10, 0);
      if (secondProfile.getX() <= 180) {
        secondProfile.move(20, 0);
        secondMedal.move(20, 0);
        profilePicture2.move(20, 0);
      } else {
        secondProfile.setX(0);
        secondMedal.setX(275);
        profilePicture2.setX(0);
        firstProfile.setX(180);
        firstMedal.setX(0);
        profilePicture1.setX(107);
        right = false;
      }
    }
  }

  /**
   * Handles key press events, allowing navigation between tutor profiles.
   */
  public void keyPressed() {
    if (tutors.get(curIdx).getExpertise() == 1) {
      firstMedal = bMedal;
    } else if (tutors.get(curIdx).getExpertise() == 2) {
      firstMedal = sMedal;
    } else {
      firstMedal = gMedal;
    }
    if (keyCode == LEFT && curIdx != 0) {
      int temp = curIdx-1;
      if (temp >= 0) {
        curIdx--;
        secondMedal.setX(300);
        secondMedal.setY(100);
        left = true;
      }      
    } else if (keyCode == RIGHT && curIdx != tutors.size()-1) {
      int temp = curIdx+1;
      if (temp < tutors.size()) {
        curIdx++;
        if (tutors.get(curIdx).getExpertise() == 1) {
          secondMedal = bMedal;
        } else if (tutors.get(curIdx).getExpertise() == 2) {
          secondMedal = sMedal;
        } else {
          secondMedal = gMedal;
        }
        secondMedal.setX(0);
        secondMedal.setY(100);
        right = true;
      }
    }
  }

  /**
   * Handles mouse press events, allowing selection and deselection of tutor profiles.
   */
  public void mousePressed() {
    if (firstProfile.isClicked(mouseX, mouseY)) {
      // toggle the selected flag when the person is clicked
      selected = !selected; 
    } else {
      // hide the person's info if the mouse is clicked elsewhere
      selected = false; 
    }
  }

  /**
   * Sets the list of tutors.
   * 
   * @param a The list of tutors to set.
   */
  public static void setTutorList(ArrayList<Tutor> a) {
    tutors = a;
  }

  /**
   * Returns the current index of the tutor being displayed.
   * 
   * @return The index of the current tutor.
   */
  public static int getIdx() {
    return curIdx;
  }
}

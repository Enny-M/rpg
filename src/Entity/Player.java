package Entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player extends Entity {
  GamePanel panel;
  KeyHandler keyHandler;
  boolean keyPressed = false;

  BufferedImage[][] walkingAnimation;
  BufferedImage[][] idleAnimation;

  public Player(GamePanel gp, KeyHandler kh) {
    panel = gp;
    keyHandler = kh;

    screenX = panel.screenWidth / 2 - (gp.tileSize / 2);
    screenY = panel.screenHeight / 2 - (gp.tileSize / 2);

    setDefaultValues();
    loadImages();
  }

  private void setDefaultValues() {
    worldX = 100;
    worldY = 100;
    speed = 3;
    spriteNumberWalking = 8;
    spriteNumberIdle = 3;
    direction = "down";
    walkingAnimation = new BufferedImage[4][spriteNumberWalking];
    idleAnimation = new BufferedImage[4][spriteNumberIdle];
  }

  private void loadImages() {
    String directory = "res/player/";
    String direction = "";
    String format = ".png";

    for (int i = 0; i < 4; i++) {
      // set direction
      switch (i) {
        case 0 -> direction = "up";
        case 1 -> direction = "down";
        case 2 -> direction = "left";
        case 3 -> direction = "right";
      }

      // load sprites for walking
      for (int j = 1; j <= spriteNumberWalking; j++) {
        try {
          String location = directory + "walking_" + direction + "_" + j + format;
          System.out.println("load image from location: " + location);
          walkingAnimation[i][j - 1] = ImageIO.read(new File(location));
        } catch (IOException e) {
          System.out.println("Loading walking animation failed");
        }
      }

      // load sprites for idle
      for (int j = 1; j <= spriteNumberIdle; j++) {
        try {
          String location = directory + "idle_" + direction + "_" + j + format;
          idleAnimation[i][j - 1] = ImageIO.read(new File(location));
        } catch (IOException e) {
          System.out.println("Loading idle animation failed");
        }
      }
    }
  }

  public void update() {
    double dx = 0;
    double dy = 0;

    if (keyHandler.upPressed) dy -= 1;
    if (keyHandler.downPressed) dy += 1;
    if (keyHandler.leftPressed) dx -= 1;
    if (keyHandler.rightPressed) dx += 1;

    // normalize movement
    if (dx != 0 || dy != 0) {
      keyPressed = true;
      double length = Math.sqrt(dx * dx + dy * dy);
      dx /= length;
      dy /= length;
    }

    worldX += (dx < 0) ? Math.ceil(dx * speed) : Math.floor(dx * speed);
    worldY += (dy < 0) ? Math.ceil(dy * speed) : Math.floor(dy * speed);

    // direction is always left or right when A or D are pressed -> yet no animation for 8
    // directions
    if (Math.abs(dx) > 0) {
      if (dx > 0) {
        direction = "right";
      } else {
        direction = "left";
      }
    } else {
      if (dy > 0) {
        direction = "down";
      } else {
        direction = "up";
      }
    }
  }

  public void draw(Graphics2D g2D) {
    BufferedImage image = null;

    if (keyPressed) { // if player should walk
      currentSpriteNumberIdle = 0; // reset idle animation;
      switch (direction) {
        case "up" -> image = walkingAnimation[0][currentSpriteNumberWalking];
        case "down" -> image = walkingAnimation[1][currentSpriteNumberWalking];
        case "left" -> image = walkingAnimation[2][currentSpriteNumberWalking];
        case "right" -> image = walkingAnimation[3][currentSpriteNumberWalking];
      }
    } else {
      currentSpriteNumberWalking = 0;
      switch (direction) {
        case "up" -> image = idleAnimation[0][currentSpriteNumberIdle];
        case "down" -> image = idleAnimation[1][currentSpriteNumberIdle];
        case "left" -> image = idleAnimation[2][currentSpriteNumberIdle];
        case "right" -> image = idleAnimation[3][currentSpriteNumberIdle];
      }
    }
    spriteCounter++;
    if (spriteCounter > 30) {
      if (keyPressed) {
        currentSpriteNumberWalking = (currentSpriteNumberWalking + 1) % spriteNumberWalking;
      } else {
        currentSpriteNumberIdle = (currentSpriteNumberIdle + 1) % spriteNumberIdle;
      }
      spriteCounter = 0;
    }
    g2D.drawImage(image, screenX, screenY, panel.tileSize, panel.tileSize, null);
  }
}

package Entity;

import main.GamePanel;
import main.GameSettings;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player extends Entity {
  KeyHandler keyHandler;
  boolean keyPressed = false;

  BufferedImage[][] walkingAnimation;
  BufferedImage[][] idleAnimation;

  public Player(GamePanel gp, KeyHandler kh) {
    panel = gp;
    keyHandler = kh;

    screenX = GameSettings.screenWidth / 2 - (GameSettings.tileSize / 2);
    screenY = GameSettings.screenHeight / 2 - (GameSettings.tileSize / 2);

    solidArea = new Rectangle();
    solidArea.x = 11 * GameSettings.scale;
    solidArea.y = 22 * GameSettings.scale;
    solidArea.width = 10 * GameSettings.scale;
    solidArea.height = 10 * GameSettings.scale;

    spriteNumberWalking = 8;
    spriteNumberIdle = 3;
    walkingAnimation = new BufferedImage[4][spriteNumberWalking];
    idleAnimation = new BufferedImage[4][spriteNumberIdle];

    setValues();
    loadImages();
  }

  private void setValues() { // can be altered when actually having a save function
    worldX = GameSettings.tileSize * 45;
    worldY = GameSettings.tileSize * 5;
    if(GameSettings.debug){
      speed = 10;
    }
    else{
      speed = 3;
    }
    direction = "down";
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
    double dDx = 0; // double dx == dDx
    double dDy = 0; // double dy == dDy
    keyPressed = false;
    if (keyHandler.upPressed && keyHandler.downPressed) {
      // do nothing
    } else {
      if (keyHandler.upPressed) {
        dDy -= 1;
        keyPressed = true;
        direction = "up";
      }
      if (keyHandler.downPressed) {
        dDy += 1;
        keyPressed = true;
        direction = "down";
      }
    }
    if (keyHandler.leftPressed && keyHandler.rightPressed) {
      // do nothing
    } else {
      if (keyHandler.leftPressed) {
        dDx -= 1;
        keyPressed = true;
        direction = "left";
      }
      if (keyHandler.rightPressed) {
        dDx += 1;
        keyPressed = true;
        direction = "right";
      }
    }
    // normalize movement
    if (keyPressed) {
      double length = Math.sqrt(dDx * dDx + dDy * dDy);
      dDx /= length;
      dDy /= length;
    }

    int dx = (int) ((dDx < 0) ? Math.ceil(dDx * speed) : Math.floor(dDx * speed));
    int dy = (int) ((dDy < 0) ? Math.ceil(dDy * speed) : Math.floor(dDy * speed));
    int dirX = (dx != 0)? dx/Math.abs(dx) : 0;
    int dirY = (dy != 0)? dy/Math.abs(dy) : 0;
    // move dx
    for (int i = 0; i < dirX * dx; i++) {
      if (!super.collisionAt(dirX, 0)) {
        worldX += dirX;
      }
    }
    // move dy
    for (int i = 0; i < dirY * dy; i++) {
      if (!super.collisionAt(0, dirY)) {
        worldY += dirY;
      }
    }
  }

  public void draw(Graphics2D g2D) {
    if (keyPressed) { // if player should walk
      currentSpriteNumberIdle = 0; // reset idle animation;
      drawWalking(g2D, currentSpriteNumberWalking);
    } else {
      currentSpriteNumberWalking = 0;
      drawIdle(g2D, currentSpriteNumberIdle);
    }
    spriteTimeCounterCounter++;
    if (spriteTimeCounterCounter > 10) {
      if (keyPressed) {
        currentSpriteNumberWalking = (currentSpriteNumberWalking + 1) % spriteNumberWalking;
      } else {
        currentSpriteNumberIdle = (currentSpriteNumberIdle + 1) % spriteNumberIdle;
      }
      spriteTimeCounterCounter = 0;
    }
  }

  private void drawIdle(Graphics2D g2D, int currentSpriteNumber) {
    BufferedImage image =
        switch (direction) {
          case "up" -> idleAnimation[0][currentSpriteNumber];
          case "down" -> idleAnimation[1][currentSpriteNumber];
          case "left" -> idleAnimation[2][currentSpriteNumber];
          case "right" -> idleAnimation[3][currentSpriteNumber];
          default -> null;
        };
    g2D.drawImage(image, screenX, screenY, GameSettings.tileSize, GameSettings.tileSize, null);
  }

  private void drawWalking(Graphics2D g2D, int currentSpriteNumber) {
    BufferedImage image =
        switch (direction) {
          case "up" -> walkingAnimation[0][currentSpriteNumber];
          case "down" -> walkingAnimation[1][currentSpriteNumber];
          case "left" -> walkingAnimation[2][currentSpriteNumber];
          case "right" -> walkingAnimation[3][currentSpriteNumber];
          default -> null;
        };
    g2D.drawImage(image, screenX, screenY, GameSettings.tileSize, GameSettings.tileSize, null);
  }
}

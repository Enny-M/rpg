package Entity;

import main.GamePanel;
import main.GameSettings;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
  GamePanel panel;
  // coordinates
  public int worldX, worldY; // in pixel
  public int screenX, screenY; // in pixel
  // movement
  public int speed;
  public String direction;
  // animation
  int spriteNumberWalking;
  int spriteNumberIdle;
  int currentSpriteNumberWalking = 0;
  int currentSpriteNumberIdle = 0;
  int spriteTimeCounterCounter;
  // collision
  public Rectangle solidArea;

  public void drawCollisionArea(Graphics2D g2D) {
    BufferedImage redSquare =
        new BufferedImage(
            GameSettings.tileSize, GameSettings.tileSize, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = redSquare.createGraphics();
    g.setColor(Color.RED);
    g.fillRect(solidArea.x, solidArea.y, solidArea.width, solidArea.height);
    g.dispose();
    g2D.drawImage(redSquare, screenX, screenY, null);
  }

  public boolean collisionAt(int dx, int dy) {
    int newWorldX = worldX + dx;
    int newWorldY = worldY + dy;

    int leftTile = (newWorldX + solidArea.x) / GameSettings.tileSize;
    int rightTile = (newWorldX + solidArea.x + solidArea.width) / GameSettings.tileSize;
    int topTile = (newWorldY + solidArea.y) / GameSettings.tileSize;
    int bottomTile = (newWorldY + solidArea.y + solidArea.height) / GameSettings.tileSize;

    int leftTopX = leftTile * GameSettings.tileSize;
    int leftTopY = topTile * GameSettings.tileSize;

    Rectangle collisionArea = new Rectangle(solidArea);

    for (int col = leftTile; col <= rightTile; col++) {
      collisionArea.x = newWorldX - leftTopX + solidArea.x;
      collisionArea.x -= (col - leftTile) * GameSettings.tileSize;
      for (int row = topTile; row <= bottomTile; row++) {

        collisionArea.y = newWorldY - leftTopY + solidArea.y;
        collisionArea.y -= (row - topTile) * GameSettings.tileSize;
        if (panel.scene.collision(col, row, collisionArea))return true;
          ;
      }
    }
    return false;
  }
}

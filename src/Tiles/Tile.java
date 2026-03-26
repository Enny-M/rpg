package Tiles;

import main.GameSettings;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {
  BufferedImage image;
  Rectangle solidArea[] = new Rectangle[0];
  boolean collision = false;
  BufferedImage redSquare;

  public void drawCollisionArea(Graphics2D g2D, int screenX, int screenY) {
    for (int i = 0; i < solidArea.length; i++) {
      if(redSquare == null)
      redSquare =
          new BufferedImage(
              GameSettings.tileSize, GameSettings.tileSize, BufferedImage.TYPE_INT_ARGB);

      Graphics2D g = redSquare.createGraphics();
      g.setColor(Color.RED);
      g.fillRect(solidArea[i].x, solidArea[i].y, solidArea[i].width, solidArea[i].height);
      g.dispose();
      g2D.drawImage(redSquare, screenX, screenY, null);
    }
  }

  public boolean collision(Rectangle colArea){
    for(int i = 0; i < solidArea.length; i++){
      if(solidArea[i].intersects(colArea)) return true;
    }
    return false;
  }
}

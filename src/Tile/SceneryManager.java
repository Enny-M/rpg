package Tile;

import Entity.Player;
import main.GamePanel;

import java.awt.*;

public class SceneryManager {
  GamePanel panel;
  Map currentMap;

  public SceneryManager(GamePanel gp) {
    panel = gp;
    currentMap = new HomeVillage(gp);
  }

  public void draw(Graphics2D g2D, Player player) {

    // camera position
    double cameraX = player.worldX - player.screenX;
    double cameraY = player.worldY - player.screenY;

    // visible tile range
    int startCol = (int) (cameraX / panel.tileSize);
    int startRow = (int) (cameraY / panel.tileSize);

    int endCol = startCol + (panel.screenWidth / panel.tileSize) + 2;
    int endRow = startRow + (panel.screenHeight / panel.tileSize) + 2;

    for (int col = startCol; col < endCol; col++) {
      for (int row = startRow; row < endRow; row++) {
        if (col >= 0 && col < currentMap.maxMapCol && row >= 0 && row < currentMap.maxMapRow) {

          int screenX = (int) (col * panel.tileSize - cameraX);
          int screenY = (int) (row * panel.tileSize - cameraY);

          currentMap.draw(g2D, col, row, screenX, screenY);
        }
      }
    }
  }
}

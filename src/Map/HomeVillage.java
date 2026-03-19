package Map;

import java.awt.*;
import java.io.*;

import Tile.DefaultTileSet;
import main.GamePanel;


public class HomeVillage extends Map {
  Integer[][] layer_0;
  Integer[][] layer_1;
  Integer[][] layer_2;
  Integer[][] foreground_0;
  int numberBackgroundLayers = 3;
  int numberForegroundLayers = 1;

  HomeVillage(GamePanel gp) {
    maxMapCol = 50;
    maxMapRow = 50;

    location = "res/maps/home_village_";
    format = ".csv";

    panel = gp;
    tiles = new DefaultTileSet();

    layer_0 = new Integer[maxMapCol][maxMapRow];
    layer_1 = new Integer[maxMapCol][maxMapRow];
    layer_2 = new Integer[maxMapCol][maxMapRow];
    foreground_0 = new Integer[maxMapCol][maxMapRow];
    loadLayers();
  }


  private void loadLayers() {
    for (int i = 0; i < numberBackgroundLayers+numberForegroundLayers; i++) {
      try (BufferedReader reader =
          new BufferedReader(new InputStreamReader(new FileInputStream(location + i + format)))) {
        for (int row = 0; row < maxMapRow; row++) {
          String line = reader.readLine();
          String[] numbers = line.split(" ");

          System.out.println(row + " " + numbers.length);
          for (int column = 0; column < maxMapCol; column++) {
            switch (i) {
              case 0 -> layer_0[column][row] = Integer.parseInt(numbers[column]);
              case 1 -> layer_1[column][row] = Integer.parseInt(numbers[column]);
              case 2 -> layer_2[column][row] = Integer.parseInt(numbers[column]);
              case 3 -> foreground_0[column][row] = Integer.parseInt(numbers[column]);
            }
          }
        }
      } catch (Exception e) {
        System.out.println("Reading map file failed: " + location + i + format);
      }
    }
  }

  @Override
  public int getMaxCol() {
    return maxMapCol;
  }

  @Override
  public int getMaxRow() {
    return maxMapRow;
  }

  @Override
  public void drawBackground(Graphics2D g2D, int worldTileX, int worldTileY, int screenX, int screenY) {
    int tileNumber = 0;
    for (int i = 0; i < numberBackgroundLayers; i++) {
      switch (i) {
        case 0 -> tileNumber = layer_0[worldTileX][worldTileY];
        case 1 -> tileNumber = layer_1[worldTileX][worldTileY];
        case 2 -> tileNumber = layer_2[worldTileX][worldTileY];
      }
      if (tileNumber == 0) continue;
      g2D.drawImage(tiles.getImageAt(tileNumber), screenX, screenY, panel.tileSize, panel.tileSize, null);
    }
  }

  @Override
  public void drawForeground(
      Graphics2D g2D, int worldTileX, int worldTileY, int screenX, int screenY) {
    int tileNumber = 0;
    for (int i = 0; i < numberForegroundLayers; i++) {
      switch (i) {
        case 0 -> tileNumber = foreground_0[worldTileX][worldTileY];
      }
      if (tileNumber == 0) continue;
      g2D.drawImage(
          tiles.getImageAt(tileNumber), screenX, screenY, panel.tileSize, panel.tileSize, null);
    }
    }
}

package Tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

public class HomeVillage extends Map {
  Integer[][] layer_0;
  String location = "res/maps/home_village_";
  String format = ".csv";
  Integer[][] layer_1;
  Integer[][] layer_2;
  Integer[][] layer_3;
  int numberLayers = 4;

  HomeVillage(GamePanel gp) {
    panel = gp;
    numberTiles = 689;
    maxMapCol = 50;
    maxMapRow = 20;
    tile = new Tiles[numberTiles];
    loadImages();
    layer_0 = new Integer[maxMapCol][maxMapRow];
    layer_1 = new Integer[maxMapCol][maxMapRow];
    layer_2 = new Integer[maxMapCol][maxMapRow];
    layer_3 = new Integer[maxMapCol][maxMapRow];
    loadLayers();
  }

  @Override
  protected void loadImages() {
    String directory = "res/default_tiles/";
    String format = ".png";
    for (int i = 0; i < numberTiles; i++) {
      try {
        String location = directory + i + format;
        System.out.println("load image from location: " + location);
        tile[i] = new Tiles();
        tile[i].image = ImageIO.read(new File(location));
      } catch (IOException e) {
        System.out.println("Loading tiles failed");
      }
    }
  }

  private void loadLayers() {
    for (int i = 0; i < numberLayers; i++) {
      try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(location + i + format)))) {
        for (int row = 0; row < maxMapRow; row++) {
          String line = reader.readLine();
          String[] numbers = line.split(" ");

          System.out.println(row +" "+ numbers.length);
          for (int column = 0; column < maxMapCol; column++) {
            switch (i) {
              case 0 -> layer_0[column][row] = Integer.parseInt(numbers[column]);
              case 1 -> layer_1[column][row] = Integer.parseInt(numbers[column]);
              case 2 -> layer_2[column][row] = Integer.parseInt(numbers[column]);
              case 3 -> layer_3[column][row] = Integer.parseInt(numbers[column]);
            }
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Reading map file failed: " + location + i + format);
      }
    }
  }

  @Override
  public void draw(Graphics2D g2D, int worldTileX, int worldTileY, int screenX, int screenY) {
    int tileNumber = 0;
    for (int i = 0; i < numberLayers; i++) {
      switch (i) {
        case 0 -> tileNumber = layer_0[worldTileX][worldTileY];
        case 1 -> tileNumber = layer_1[worldTileX][worldTileY];
        case 2 -> tileNumber = layer_2[worldTileX][worldTileY];
        case 3 -> tileNumber = layer_3[worldTileX][worldTileY];
      }
      if (tileNumber == 0) continue;
      g2D.drawImage(tile[tileNumber].image, screenX, screenY, panel.tileSize, panel.tileSize, null);
    }
  }
}

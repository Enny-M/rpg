package Tile;

import main.GamePanel;

import java.awt.*;

abstract class Map {
  int numberTiles;
  Tiles[] tile;
  GamePanel panel;

  public int maxMapCol, maxMapRow;

  protected void loadImages() {}

  public void draw(Graphics2D g2D, int worldTileX, int worldTileY, int screenX, int screenY) {}
}

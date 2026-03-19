package Map;

import Tile.TileSet;
import main.GamePanel;

import java.awt.*;


public abstract class Map {
  GamePanel panel;
  TileSet tiles;
  public int maxMapCol;
  public int maxMapRow;
  String location;
  String format;

  public abstract int getMaxCol();
  public abstract int getMaxRow();
  public abstract void drawBackground(Graphics2D g2D, int worldTileX, int worldTileY, int screenX, int screenY);
  public abstract void drawForeground(Graphics2D g2D, int worldTileX, int worldTileY, int screenX, int screenY);
}

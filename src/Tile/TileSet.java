package Tile;


import java.awt.image.BufferedImage;

public abstract class TileSet {
    int numberTiles;
    Tiles[] tile;
    String directory;
    String format;

    protected abstract void loadImages();
    public abstract BufferedImage getImageAt(int i);
    public abstract boolean getCollisionAt(int i);
}

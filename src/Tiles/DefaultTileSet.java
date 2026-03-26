package Tiles;

public class DefaultTileSet extends TileSet{
    public DefaultTileSet() {
        numberTiles = 689;
        tiles = new Tile[numberTiles];
        directory = "res/default_tiles/";
        format = ".png";
        loadImages();
        loadCollisions("collision.csv");
    }
}

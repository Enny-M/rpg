package Tile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class DefaultTileSet extends TileSet {
    public DefaultTileSet(){
        numberTiles = 689;
        tile= new Tiles[numberTiles];
        directory = "res/default_tiles/";
        format = ".png";
        loadImages();
    }

    @Override
    protected void loadImages() {
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

    public BufferedImage getImageAt(int i){
        return tile[i].image;
    }

    public boolean getCollisionAt(int i){
        return tile[i].collision;
    }
}

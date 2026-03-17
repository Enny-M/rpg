package Tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TileManager {
    GamePanel panel;
    Tiles[] tile;
    int numberTiles = 486;

    public TileManager(GamePanel gp){
        panel = gp;

        tile = new Tiles[numberTiles];
    }

    public void getTileImage(){
        String directory = "assets/tiles/default_tiles";
        String format = ".png";
        for(int i = 1; i <= numberTiles; i++){
            try{
                String location = directory + i + format;
                System.out.println("load image from location: " + location);
                tile[i-1].image = ImageIO.read(new File(location));
            }
            catch(IOException e){
                System.out.println("Loading tiles failed");
            }
        }
    }

    public void draw(Graphics2D g2D){
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;
    }
}

package Tiles;

import main.GameSettings;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class TileSet {
    int numberTiles;
    Tile[] tiles;
    String directory;
    String format;

    public void loadImages(){
        for (int i = 0; i < numberTiles; i++) {
            try {
                String location = directory + i + format;
                System.out.println("load image from location: " + location);
                tiles[i] = new Tile();
                tiles[i].image = ImageIO.read(new File(location));
            } catch (IOException e) {
                System.out.println("Loading tiles failed");
            }
        }
    }
    public void loadCollisions(String filename){
        String location = directory + filename;

        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(new FileInputStream(location)))) {
            for (int i = 1; i < numberTiles; i++) {
                String line = reader.readLine();
                String[] values = line.trim().split(" ");

                tiles[i].collision = "1".equals(values[0]);
                if(tiles[i].collision) extractRectanglesFromString(values, i);
            }
        } catch (Exception e) {
            System.out.println("Reading collision file in default tile set failed");
        }
    }

    private void extractRectanglesFromString(String[] values, int tileNumber){
        int rectCount = (values.length - 1) / 4;
        tiles[tileNumber].solidArea = new Rectangle[rectCount];

            for (int r = 0; r < rectCount; r++) {
                System.out.println(tileNumber + " number of Rectangles: " + rectCount);
                Rectangle rect = new Rectangle();
                rect.x = Integer.parseInt(values[1 + r * 4]) * GameSettings.scale;
                rect.y = Integer.parseInt(values[2 + r * 4]) * GameSettings.scale;
                rect.width = Integer.parseInt(values[3 + r * 4]) * GameSettings.scale;
                rect.height = Integer.parseInt(values[4 + r * 4]) * GameSettings.scale;

                tiles[tileNumber].solidArea[r] = rect;
            }
    }

    public BufferedImage getImageInOriginalSizeAt(int i){
        return tiles[i].image;
    }

    public void  drawCollision(Graphics2D g2D, int tileNumber, int screenX, int screenY){
        tiles[tileNumber].drawCollisionArea(g2D, screenX, screenY);
    }

    public boolean collision(int tileNumber, Rectangle colRect){
        return tiles[tileNumber].collision(colRect);
    }
}

package Maps;

import Tiles.TileSet;
import main.GameSettings;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Map {
    TileSet tiles;
    int maxMapCol = 0;
    int maxMapRow = 0;
    String location;
    String format;

    int numberBackgroundLayers;
    int numberMiddlegroundLayers;
    int numberForegroundLayers;

    Integer [][][] background;
    Integer [][][] middleground;
    Integer [][][] foreground;

    public int getMaxCol(){ return maxMapCol;}
    public int getMaxRow(){ return maxMapRow;}


    public void loadLayers() {
        String filename;
        for (int i = 0; i < numberBackgroundLayers; i++) {
            filename= location + i + format;
            loadMap(background[i], filename);
        }
        for (int i = 0; i < numberMiddlegroundLayers; i++) {
            filename= location + (numberBackgroundLayers +i) + format;
            loadMap(middleground[i], filename);
        }
        for (int i = 0; i < numberForegroundLayers; i++) {
            filename= location + (numberBackgroundLayers + numberMiddlegroundLayers+i)+ format;
            loadMap(foreground[i], filename);
        }
    }

    private void loadMap(Integer[][] map, String filename){
        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(new FileInputStream(filename)))) {
            for (int row = 0; row < maxMapRow; row++) {
                String line = reader.readLine();
                String[] numbers = line.split(" ");

                System.out.println(row + " " + numbers.length);
                for (int column = 0; column < maxMapCol; column++) {
                    map[column][row] = Integer.parseInt(numbers[column]);
                }
            }
        } catch (Exception e) {
            System.out.println("Reading map file failed: " + filename);
        }
    }

    public void drawBackgroundLayers(
            Graphics2D g2D, int worldTileX, int worldTileY, int screenX, int screenY) {
        draw(background, numberBackgroundLayers, g2D, worldTileX, worldTileY, screenX, screenY, false);
    }

    public void drawMiddlegroundLayers(
            Graphics2D g2D, int worldTileX, int worldTileY, int screenX, int screenY) {
        draw(middleground, numberMiddlegroundLayers, g2D, worldTileX, worldTileY, screenX, screenY, false);
    }
    public void drawForegroundLayers(
            Graphics2D g2D, int worldTileX, int worldTileY, int screenX, int screenY) {
        draw(foreground, numberForegroundLayers, g2D, worldTileX, worldTileY, screenX, screenY, false);
    }
    private void draw(Integer [][][] map, int layers, Graphics2D g2D, int worldTileX, int worldTileY, int screenX, int screenY, boolean debug){
        int tileNumber;
    for (int i = 0; i < layers; i++) {
      tileNumber = map[i][worldTileX][worldTileY];
      if (tileNumber == 0) continue;
      if (debug) {
        tiles.drawCollision(g2D, tileNumber, screenX, screenY);
      } else {
        g2D.drawImage(
            tiles.getImageInOriginalSizeAt(tileNumber),
            screenX,
            screenY,
            GameSettings.tileSize,
            GameSettings.tileSize,
            null);
      }
            }
    }

    public void debug(Graphics2D g2D, int worldTileX, int worldTileY, int screenX, int screenY){
        draw(background, numberBackgroundLayers, g2D, worldTileX, worldTileY, screenX, screenY, true);
        draw(middleground, numberMiddlegroundLayers, g2D, worldTileX, worldTileY, screenX, screenY, true);
        draw(foreground, numberForegroundLayers, g2D, worldTileX, worldTileY, screenX, screenY, true);
    }

    public boolean collision(int worldTileX, int worldTileY, Rectangle solidArea){
        if(worldTileX >= maxMapCol || worldTileY >= maxMapRow) return true;
        int tileNumber;
        for (int i = 0; i < numberBackgroundLayers; i++) {
            tileNumber = background[i][worldTileX][worldTileY];
            if(tileNumber != 0 && tiles.collision(tileNumber, solidArea)) return true;
        }
        for (int i = 0; i < numberMiddlegroundLayers; i++) {
            tileNumber = middleground[i][worldTileX][worldTileY];
            if(tileNumber != 0 && tiles.collision(tileNumber, solidArea)) return true;
        }
        for (int i = 0; i < numberForegroundLayers; i++) {
            tileNumber = foreground[i][worldTileX][worldTileY];
            if(tileNumber != 0 && tiles.collision(tileNumber, solidArea)) return true;
        }
        return false;
    }
}

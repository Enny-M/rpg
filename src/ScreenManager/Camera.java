package ScreenManager;

import Entity.Player;
import Maps.Map;
import main.GameSettings;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Camera {
    static double cameraX;
    static double cameraY;

    // visible tile range
    static int startCol;
    static int startRow;

    static int endCol;
    static int endRow;

    static BufferedImage blackSquare;

    static{
        blackSquare = new BufferedImage(GameSettings.tileSize, GameSettings.tileSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = blackSquare.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GameSettings.tileSize, GameSettings.tileSize);
        g.dispose();
    }


    public static void update(Player player) {
        cameraX = player.worldX - player.screenX;
        cameraY = player.worldY - player.screenY;

        startCol = (int) (cameraX / GameSettings.tileSize) -2; //-2 for better rendering and no weird edges
        startRow = (int) (cameraY / GameSettings.tileSize) -2;

        endCol = startCol + (GameSettings.screenWidth / GameSettings.tileSize) + 4; //4-2 = 2 for better rendering
        endRow = startRow + (GameSettings.screenHeight / GameSettings.tileSize) + 4;
    }

    private static void drawBlack(Graphics2D g2D,int screenX, int screenY){
        g2D.drawImage(blackSquare, screenX, screenY, null);
    }

    public static void drawForeground(Graphics2D g2D, Map currentMap) {
        draw(g2D, currentMap, 2);
    }

    public static void drawMiddlegound(Graphics2D g2D, Map currentMap) {
        draw(g2D, currentMap, 1);
    }

    public static void drawBackground(Graphics2D g2D, Map currentMap) {
        draw(g2D, currentMap, 0);
    }

    public static void drawCollision(Graphics2D g2D, Map currentMap){
        draw(g2D, currentMap, 42);
    }

    private static void draw(Graphics2D g2D, Map currentMap, int layer) {
        for (int col = startCol; col < endCol; col++) {
            for (int row = startRow; row < endRow; row++) {
                int screenX = (int) (col * GameSettings.tileSize - cameraX);
                int screenY = (int) (row * GameSettings.tileSize - cameraY);

                if (col >= 0 && col < currentMap.getMaxCol() && row >= 0 && row < currentMap.getMaxRow()) {
                    switch (layer){
                        case 0 -> currentMap.drawBackgroundLayers(g2D, col, row, screenX, screenY);
                        case 1 -> currentMap.drawMiddlegroundLayers(g2D, col, row, screenX, screenY);
                        case 2 -> currentMap.drawForegroundLayers(g2D, col, row, screenX, screenY);
                        case 42 -> currentMap.debug(g2D, col, row, screenX, screenY);
                    }
                }
                else{
                   drawBlack(g2D, screenX, screenY);
                }
            }
        }
    }
}

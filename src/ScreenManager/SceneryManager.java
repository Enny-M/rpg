package ScreenManager;

import Entity.Player;
import Maps.HomeVillage;
import Maps.Map;
import main.GameSettings;

import java.awt.*;

public class SceneryManager {
    Map currentMap;
    Camera camera;

    public SceneryManager() {
        currentMap = new HomeVillage();
    }

    public void update(Player player){
        camera.update(player);
    }

    public void draw(Graphics2D graphic, Player player){
        camera.drawBackground(graphic, currentMap);
        player.draw(graphic);
        camera.drawForeground(graphic, currentMap);

        if(GameSettings.debug){
            camera.drawCollision(graphic, currentMap);
            player.drawCollisionArea(graphic);
        }
    }

    public boolean collision(int worldTileX, int worldTileY, Rectangle solidArea){

        return currentMap.collision(worldTileX, worldTileY, solidArea);
    }
}

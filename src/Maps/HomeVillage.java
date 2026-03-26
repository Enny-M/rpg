package Maps;

import Tiles.DefaultTileSet;

public class HomeVillage extends Map{
    public HomeVillage(){
        maxMapCol = 50;
        maxMapRow = 50;
        numberBackgroundLayers = 3;
        numberMiddlegroundLayers = 0;
        numberForegroundLayers = 2;

        location = "res/maps/home_village_";
        format = ".csv";
        tiles = new DefaultTileSet();

        background = new Integer[numberBackgroundLayers][maxMapCol][maxMapRow];
        middleground = new Integer[numberMiddlegroundLayers][maxMapCol][maxMapRow];
        foreground = new Integer[numberForegroundLayers][maxMapCol][maxMapRow];

        loadLayers();
    }
}

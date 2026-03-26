package main;

public class GameSettings {
    public static final int originalTileSize = 32;
    public static int scale = 3;
    public static final int tileSize = originalTileSize * scale;

    static final int maxScreenCol = 16;
    static final int maxScreenRow = 9;

    public static int defaultScreenWidth = maxScreenCol * originalTileSize * scale;
    public static int defaultScreenHeight = maxScreenRow * originalTileSize * scale;
    public static int screenWidth = defaultScreenWidth;
    public static int screenHeight = defaultScreenHeight;

    public static int fps = 60;


    public static boolean fullScreen = true;
    public static boolean debug = true;
}

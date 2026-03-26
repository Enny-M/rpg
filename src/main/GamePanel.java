package main;

import Entity.Player;
import ScreenManager.SceneryManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class GamePanel extends JPanel implements Runnable{
    BufferedImage tempScreen;
    Graphics2D screenGraphic;

    Thread gameThread;
    KeyHandler keyHandler = new KeyHandler();
    public SceneryManager scene = new SceneryManager();
    Player player = new Player(this, keyHandler);

    public GamePanel() {
        this.setPreferredSize(new Dimension(GameSettings.defaultScreenWidth, GameSettings.defaultScreenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setupGame(){
        tempScreen = new BufferedImage(GameSettings.defaultScreenWidth, GameSettings.defaultScreenHeight, BufferedImage.TYPE_INT_ARGB); //draw everything to tempScreen
        screenGraphic = (Graphics2D)tempScreen.getGraphics();
        if(GameSettings.fullScreen) setFullScreen(); //full screen can be enabled here
    }

    private void setFullScreen(){
        //get local screen device
        GraphicsEnvironment gE = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gD = gE.getDefaultScreenDevice();
        gD.setFullScreenWindow(Main.window);
        //get full screen width and height
        GameSettings.screenWidth = Main.window.getWidth();
        GameSettings.screenHeight = Main.window.getHeight();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        double interval = 1_000_000_000. / GameSettings.fps; // in nanosecond
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / interval;
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                drawToTempScreen();
                drawToScreen();
                delta--;
            }
        }
    }

    public void update() {
        player.update();
        scene.update(player);
    }

    public void drawToTempScreen(){ //draw method to the temporal buffered image
        scene.draw(screenGraphic, player);
    }
    public void drawToScreen(){ //draw method to the screen
        Graphics graphic = getGraphics();
        graphic.drawImage(tempScreen, 0, 0, GameSettings.screenWidth, GameSettings.screenHeight, null);
        graphic.dispose();
    }
}

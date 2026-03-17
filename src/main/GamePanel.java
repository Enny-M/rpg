package main;

import Entity.Player;
import Tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    //SCREEN SETTINGS
    final int originalTileSize = 32; //32 x 32 Pixel
    final int scale = 3;
    public final int tileSize = scale * originalTileSize;

    final int maxScreenCol = 16;
    final int maxScreenRow = 12;

    final int screenWidth = maxScreenCol * tileSize;
    final int screenHeight = maxScreenRow * tileSize;


    Thread gameThread;
    KeyHandler keyHandler = new KeyHandler();
    Player mc = new Player(this, keyHandler);
    TileManager tiles = new TileManager(this);

    int fps = 60;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double interval = 1_000_000_000 /fps; //in nanosecond
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / interval;
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update(){
        mc.update();
    }

    public void paintComponent(Graphics graphic){
        super.paintComponent(graphic);

        Graphics2D g2D  = (Graphics2D)graphic;
        tiles.draw(g2D); //first layer
        mc.draw(g2D);

        g2D.dispose();

    }
}

package main;

import javax.swing.*;

public class Main {
    private static final String gameName = "Mythara";
    public static JFrame window;

    public static void main(String[] args) {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle(gameName);

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack(); // to see the Panel

        window.setLocationRelativeTo(null); // window at the center of the screen
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}

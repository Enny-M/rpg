package main;

import javax.swing.*;

// TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
  private static final String gameName = "Mythara";

  public static void main(String[] args) {
    JFrame window = new JFrame();
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setResizable(false);
    window.setTitle(gameName);

    GamePanel gamePanel = new GamePanel();
    window.add(gamePanel);
    window.pack(); // to see the Panel

    window.setLocationRelativeTo(null); // window at the center of the screen
    window.setVisible(true);

    gamePanel.startGameThread();
  }
}

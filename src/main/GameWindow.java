package main;

import javax.swing.JFrame;

public class GameWindow {
    private JFrame frame;

    public GameWindow(GamePanel panel) {
        frame = new JFrame();
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(3);
        frame.setLocationRelativeTo(null);
        frame.add(panel);
        frame.setVisible(true);
    }
}

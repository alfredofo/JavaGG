package javagg;

import java.awt.Dimension;
import javax.swing.JFrame;

public class MainFrame extends JFrame {

    public static final int FRAME_WIDTH = 1024;
    public static final int FRAME_HEIGHT = 600;
    public static final Dimension FRAME_SIZE = new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
    public static MainPanel mainPanel;
    public static DifficultPanel difficultPanel;
    public static GamePanel gamePanel;

    public MainFrame() {
        this.setSize(FRAME_SIZE);
        this.setResizable(false);
        this.setTitle("Forse l'ultimo gioco");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        mainPanel = new MainPanel();
        mainPanel.setVisible(true);
        difficultPanel = new DifficultPanel();
        difficultPanel.setVisible(false);
        gamePanel = new GamePanel();
        gamePanel.setVisible(false);

        this.getContentPane().add(mainPanel);
        this.getContentPane().add(difficultPanel);
        this.getContentPane().add(gamePanel);
        
        this.setVisible(true);

    }

}

package javagg;

import javagg.util.Resources;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainPanel extends JPanel {

    private final Image backGround;
    private final Rectangle newGame;
    private final Rectangle exitGame;
    private final boolean pressed;

    public MainPanel() {
        this.setSize(MainFrame.FRAME_SIZE);
        backGround = Resources.getImage("/image/b_main.png");

        this.newGame = new Rectangle(422, 191, 182, 42);
        this.exitGame = new Rectangle(422, 340, 182, 42);

        this.pressed = false;

        this.addMouseListener(new GiocaListener());
        this.addMouseMotionListener(new GiocaListener());
        this.setVisible(false);

    }

    @Override
    public void paint(Graphics g) {
        update(g);
    }

    @Override
    public void update(Graphics g) {
        g.drawImage(backGround, 0, 0, getParent());


    }

    private class GiocaListener extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
            Point p = mouseEvent.getPoint();
            System.out.println(p);
            if (newGame.contains(p)) {
                MainFrame.difficultPanel.setVisible(true);
                MainFrame.mainPanel.setVisible(false);
            }
            if (exitGame.contains(p)) {
                System.exit(0);
            }

        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
            Point p = mouseEvent.getPoint();
            if (newGame.contains(p)||exitGame.contains(p)) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            } else {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }

    }

}

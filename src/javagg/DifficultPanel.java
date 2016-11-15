package javagg;

import javagg.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javagg.util.swaPnl;

public class DifficultPanel extends JPanel {

    private final BufferedImage backgrounD;
    private final Rectangle easyRect;
    private final Rectangle mediumRect;
    private final Rectangle hardRect;
    private final Rectangle backRect;

    boolean pressed;

    public DifficultPanel() {

        this.setSize(MainFrame.FRAME_SIZE);
        backgrounD = Resources.getImage("/image/b_lvl.png");

        this.easyRect = new Rectangle(422, 191, 182, 42);
        this.mediumRect = new Rectangle(422, 268, 182, 42);
        this.hardRect = new Rectangle(422, 340, 182, 42);
        this.backRect = new Rectangle(32, 487, 107, 26);                        //x,y,lung,alt

        this.pressed = false;

        this.addMouseListener(new ListenerMouse());
        this.addMouseMotionListener(new ListenerMouse());
        this.setVisible(false);

    }

    @Override
    public void paint(Graphics g) {
        update(g);

    }

    @Override
    public void update(Graphics g) {
        g.drawImage(backgrounD, 0, 0, getParent());
    }

    public class ListenerMouse extends MouseAdapter {

        public void mouseReleased(MouseEvent mouseEvent) {
            Point p = mouseEvent.getPoint();

            pressed = true;
            if (easyRect.contains(p)) {
                //livello facile
                MainFrame.difficultPanel.setVisible(false);
                MainFrame.gamePanel.setVisible(true);

            } else if (mediumRect.contains(p)) {
                //livello medio
            } else if (hardRect.contains(p)) {
                //livello hardd
            } else if (backRect.contains(p)) {
                MainFrame.difficultPanel.setVisible(false);
                MainFrame.mainPanel.setVisible(true);

//swaPnl swap = new swaPnl(MainFrame.difficultPanel,MainFrame.mainPanel);
            } else {

            }

        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
            Point p = mouseEvent.getPoint();
            if (easyRect.contains(p) || mediumRect.contains(p) || hardRect.contains(p) || backRect.contains(p)) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            } else {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }
}

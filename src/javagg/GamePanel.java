package javagg;

import java.awt.Cursor;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javagg.util.Resources;
import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.*;
import java.util.*;

class GamePanel extends JPanel implements ActionListener {

    private final BufferedImage background_e; // 1800x600
    private final BufferedImage background_m;
    private final BufferedImage background_h;
    private final Rectangle backGame;

    Image punto = Resources.getImage("/image/Punto.png");
    Image punto_up = Resources.getImage("/image/Punto_up.png");
    Image punto_dn = Resources.getImage("/image/Punto_dn.png");
    Image punto_dx = Resources.getImage("/image/Punto_dx.png");
    Image punto_sx = Resources.getImage("/image/Punto_sx.png");

    Image obj = punto;

    final private int BKMIN_X = 0, BKMAX_X = 10000; // Min and Max of  background
    final private int BKMIN_Y = 0, BKMAX_Y = 570;
    public int bk_x = 350; // background x and y coordinates 395x535
    public int bk_y = 476;
    public int rz_x = 350;
    public int rz_y = 482;// 600x 615y

    static final int height = 10;
    static final int width = 10;
    public float vDelta;
    public float rbDelta;
    public float rbDegDelta;
    public float gDelta;
    public boolean bounce = false;
    public int yPos;

    static int direction = 0;

    static boolean moveableRight = true;
    static boolean moveableLeft = true;
    static boolean moveableDown = false;
    static boolean moveableUp = false;
    boolean jumpright = true;

    static boolean jump = false;
    private final Timer time;

    static boolean pause = false;
    int run = 0;

    public GamePanel() {

        background_e = Resources.getImage("/image/BBIG.jpg");
        background_m = Resources.getImage("/image/medBG_m.png");
        background_h = Resources.getImage("/image/hardBG_m.png");

        this.backGame = new Rectangle(914, 18, 182, 42);

        //this.setSize(MainFrame.FRAME_SIZE);
        this.addMouseListener(new GamePanel.GiocaListener());
        this.addMouseMotionListener(new GamePanel.GiocaListener());

        yPos = rz_y;
        vDelta = 0;
        gDelta = 0.25f;
        rbDegDelta = 2.25f;

        setLayout(null);

        time = new Timer(15, this);
        time.start();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent kp) {

                if (kp.getKeyCode() == KeyEvent.VK_RIGHT) {
                    direction = 2;
                }
                if ((kp.getKeyCode() == KeyEvent.VK_LEFT)) {
                    direction = 3;
                }

                if (kp.getKeyCode() == KeyEvent.VK_SPACE & rz_y <= 482) {

                    vDelta = -8;
                    rbDelta = vDelta;
                    bounce = true;

                }
            }

        });

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (direction == 2) {
            right();
        }
        if (direction == 3) {
            left();
        }

        direction = 0;
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        requestFocus();
        setFocusable(true);

        setBackground(g2d);

        Jump();

        g2d.drawImage(obj, rz_x, yPos, this);
        repaint();
    }

    void Jump() // Jump mechanism
    {
        int height = getHeight();
        // No point if we've not been sized...
        if (height > 0) {
            // Are we bouncing...
            if (bounce) {
                // Add the vDelta to the yPos
                // vDelta may be postive or negative, allowing
                // for both up and down movement...
                yPos += vDelta;
                // Add the gravity to the vDelta, this will slow down
                // the upward movement and speed up the downward movement...
                // You may wish to place a max speed to this
                vDelta += gDelta;
                // If the sprite is not on the ground...
                if (yPos >= rz_y) { // + GamePanel.height >= height
                    // Seat the sprite on the ground
                    yPos = rz_y; // height - GamePanel.height
                    // If the re-bound delta is 0 or more then we've stopped
                    // bouncing...
                    if (rbDelta >= 0) {
                        // Stop bouncing...
                        bounce = false;
                    } else {
                        // Add the re-bound degregation delta to the re-bound delta
                        rbDelta += rbDegDelta;
                        // Set the vDelta...
                        vDelta = rbDelta;
                    }
                }
            }
        }

    }

    void left() {
        if (moveableLeft == true & bk_x > BKMIN_X + 250) {  // 250
            bk_x -= 40;

        }// end if
    }

    void right() {
        if (moveableRight == true & bk_x < BKMAX_X - 900) { //900
            bk_x += 40;

        }
    }

    void setBackground(Graphics g2d) {
        g2d.drawImage(background_e, 200 - bk_x, 0, null);
    }

    public class GiocaListener extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
            Point p = mouseEvent.getPoint();
            System.out.println(p);
            if (backGame.contains(p)) {
                MainFrame.gamePanel.setVisible(false);
                MainFrame.difficultPanel.setVisible(true);
            }

        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
            Point p = mouseEvent.getPoint();
            if (backGame.contains(p)) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            } else {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }

    }

}

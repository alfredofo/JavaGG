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
    Image punto2up = Resources.getImage("/image/Punto_up.png");
    Image punto2dn = Resources.getImage("/image/Punto_dn.png");
    Image punto2dx = Resources.getImage("/image/Punto_dx.png");
    Image punto2sx = Resources.getImage("/image/Punto_sx.png");

    Image obj = punto;

    final private int BKMIN_X = 0, BKMAX_X = 10000; // Min and Max of  background
    final private int BKMIN_Y = 0, BKMAX_Y = 570;
    public int bk_x = 350; // background x and y coordinates 395x535
    public int bk_y = 476;
    public int rz_x = 350;
    public int rz_y = 480;// 600x 615y

    static final int height = 10;
    static final int width = 10;
    public float vDelta;
    public float rbDelta;
    public float rbDegDelta;
    public float gDelta;

    public boolean bounce = false;

    public int yPos;
    public int xPos;

    static int direction = 0;

    static boolean moveableRight = false;
    static boolean moveableLeft = false;
    static boolean moveableDown = false;
    static boolean moveableUp = false;
    boolean jumpright = true;

    static boolean jump = false;
    private final Timer time;

    static boolean pause = false;

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

        time = new Timer(30, this);
        time.start();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent kp) {
                if (kp.getKeyCode() == KeyEvent.VK_RIGHT) {
                    direction = 2;
                    moveableRight = true;
                }
                if ((kp.getKeyCode() == KeyEvent.VK_LEFT)) {
                    direction = 3;
                    moveableLeft = true;
                }
                if (kp.getKeyCode() == KeyEvent.VK_SPACE) {
                    vDelta = -8;
                    rbDelta = vDelta;
                    bounce = true;

                }

            }
        }
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (direction == 2) {
            right();
        }
        if (direction == 3) {
            left();
        }
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

        // checkStatus();
        // isGrounded();
        repaint();
    }

    void Jump() {
        if (bounce) {
            yPos += vDelta;                                                     // Add the vDelta to the yPos
            vDelta += gDelta;
            if (yPos >= rz_y) {                      // + GamePanel.height >= height   
                yPos = rz_y;                                // height - GamePanel.height
                if (rbDelta >= 0) {
                    bounce = false;
                } else {
                    rbDelta += rbDegDelta;
                    vDelta = rbDelta;
                }
            }
        }
        if (yPos <= BKMIN_Y) {
            vDelta = +8;
            rbDelta = vDelta;
        }
    }

    void left() { // 3
        if (bk_x > BKMIN_X + 250) {  // 250 ... moveableLeft == true && // && isGrounded()==true
            bk_x -= 26;
            moveableLeft = false;
        }
    }

    void right() { // 2
        if (bk_x < BKMAX_X - 900) { //900 ... moveableRight == true && //  && isGrounded()==true
            bk_x += 26;
            moveableRight = false;
        }
    }

    public void checkStatus() {
        int ck;
        if (moveableLeft == true) {
            ck = 3;
            System.out.println(ck);
        }
        if (moveableRight == true) {
            ck = 2;
            System.out.println(ck);
        }
    }

    boolean isGrounded() {
        if (yPos == rz_y) {
            System.out.println("isGrounded");
            return true;
        } else {
            System.out.println("is!=Grounded");
            return false;
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

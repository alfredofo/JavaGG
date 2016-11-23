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

//    static final int height = 10;
//    static final int width = 10;
//    public float vDelta;
//    public float rbDelta;
//    public float rbDegDelta;
//    public float gDelta;
//    public boolean bounce = false;
//    public int yPos;

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

//        yPos = getPreferredSize().height - height ;
//        vDelta = 0;
//        gDelta = 0.25f;
//        rbDegDelta = 2.25f;
        
        setLayout(null);

        time = new Timer(15, this);
        time.start();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent kp) {

//                if (kp.getKeyCode() == KeyEvent.VK_DOWN) {
//                    direction = 1;
//                }
                if (kp.getKeyCode() == KeyEvent.VK_RIGHT) {
                    direction = 2;
                }
                if ((kp.getKeyCode() == KeyEvent.VK_LEFT)) {
                    direction = 3;
                }
//                if (kp.getKeyCode() == KeyEvent.VK_UP) {
//                    direction = 4;
//                }
                if (kp.getKeyCode() == KeyEvent.VK_SPACE & rz_y <= 482) {
                    if (jump == false) {
                        jump = true;
                        moveableDown = true;
                        if (direction == 2) {
                            jumpright = true;
                        }
                        if (direction == 3) {
                            jumpright = false;
                        }
                    }
                }
            }

//            @Override
//            public void keyReleased(KeyEvent kr) {  // 0=still 1=up , 2=right , 3=left , 4=down
//                if (direction == 1) {
//                    obj = punto_dn;
//                }
//                if (direction == 2) {
//                    obj = punto_dx;
//                }
//                if (direction == 3) {
//                    obj = punto_sx;
//                }
//                if (direction == 4) {
//                    obj = punto_up;
//                }
//
//            }
        });

    }

    @Override
    public void actionPerformed(ActionEvent e) { // 0=still 1=up , 2=right , 3=left , 4=down
//        if (direction == 1) {
//            up();
//        }
        if (direction == 2) {
            right();
        }
        if (direction == 3) {
            left();
        }
//        if (direction == 4) {
//            down();
//        }
        direction = 0;
    }

    /*    @Override
    public void paint(Graphics g) {
        update(g);
    }

    @Override
    public void update(Graphics g) {
        g.drawImage(this.background_e, -270, 0, getParent());
        // TODO switch background a seconda del livello .... scrollingside

    }
     */
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        requestFocus();
        setFocusable(true);

        setBackground(g2d);
        // collisioni 

        Jump();

        g2d.drawImage(obj, rz_x, rz_y, this);
        repaint();
    }

    void Jump() // Jump mechanism
    {

        if (moveableDown == true) {
            if (jump == true & rz_y >= 357) // max altezza jump
            {

                rz_y -= 8;
                if (rz_y <= 357) {
                    jump = false;
                }
            }
            if (jump == false & rz_y < 482) // min altezza jump
            {
                rz_y += 8;
            }
        }

    }// end up

    void left() {
        if (moveableLeft == true & bk_x > BKMIN_X + 250) {  // 250
            bk_x -= 40; // decrease xcoord while moving left 8

        }// end if
    }

    void right() {
        if (moveableRight == true & bk_x < BKMAX_X - 900) { //900
            bk_x += 40; // increasing xcoord while moving right 8

        }// end if
    }

//    void up() {
//        if (moveableUp == true) {
//            rz_y += 8;
//        }
//    }
//
//    void down() {
//        if (moveableDown == true) {
//            rz_y -= 8;
//        }
//    }
    void setBackground(Graphics g2d) {
        g2d.drawImage(background_e, 150 - bk_x, 0, null);
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

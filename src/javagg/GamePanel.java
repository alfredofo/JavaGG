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
import java.awt.*;  // inizio 
import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.*;
import java.util.*; //fine

class GamePanel extends JPanel implements ActionListener {

    private final BufferedImage background_e; // 1800x600
    private final BufferedImage background_m;
    private final BufferedImage background_h;
    private final Rectangle backGame;

    // protected Image rz_background = new ImageIcon("/image/easyBG_m.png").getImage(); // Background Image
    Image punto = Resources.getImage("/image/Punto.png");
    Image punto_up = Resources.getImage("/image/Punto_up.png");
    Image punto_dn = Resources.getImage("/image/Punto_dn.png");
    Image punto_dx = Resources.getImage("/image/Punto_dx.png");
    Image punto_sx = Resources.getImage("/image/Punto_sx.png");

    Image obj = punto;

    final private int BKMIN_X = 0, BKMAX_X = 18000; // Min and Max of  background
    public int bk_x = -50; // background x and y coordinates 395x535
    public int bk_y = 300;
    public int rz_x = 850; // character x and y coordinates
    public int rz_y = 405;// 600x 615y

    static int direction = 0; // 0=still 1=up , 2=right , 3=left , 4=down

    static boolean moveableRight = true; // variable for collision detection
    static boolean moveableLeft = true;
    static boolean moveableDown = true;
    static boolean moveableUp = true;
    boolean jumpright = false;

    static boolean jump = true; // For jump
    private final Timer time;

    static boolean pause = false;
    int run = 0;

    public GamePanel() {

        background_e = Resources.getImage("/image/easyBG_m.png");
        background_m = Resources.getImage("/image/medBG_m.png");
        background_h = Resources.getImage("/image/hardBG_m.png");

        this.backGame = new Rectangle(914, 18, 182, 42);

        this.setSize(MainFrame.FRAME_SIZE);

        this.addMouseListener(new GamePanel.GiocaListener());
        this.addMouseMotionListener(new GamePanel.GiocaListener());

        ////////////////////////////////////////////////////////////////////////
        setLayout(null);

        time = new Timer(30, this); // starting a timer and passing the actionlistener for the running animation
        time.start(); // starting

        addKeyListener(new KeyAdapter() // Movement of razmazio
        {
            @Override
            public void keyPressed(KeyEvent kp) {
                if (kp.getKeyCode() == KeyEvent.VK_DOWN) {
                    direction = 1;
                }
                if (kp.getKeyCode() == KeyEvent.VK_RIGHT
                        & moveableRight == true) {
                    direction = 2; // right
                }
                if ((kp.getKeyCode() == KeyEvent.VK_LEFT)
                        & moveableLeft == true) {
                    direction = 3; // left
                }
                if (kp.getKeyCode() == KeyEvent.VK_UP) {
                    direction = 4; // up
                }
//                if (kp.getKeyCode() == KeyEvent.VK_SPACE) {
//                    if (jump == false & rz_y == 0) // if character standing of
//                    // platform 615
//                    {
//                        jump = true;
//                        moveableDown = true;
//                        if (direction == 2) {
//                            jumpright = true;
//                        }
//                        if (direction == 3) {
//                            jumpright = false;
//                        }
//                    }
//                }
            } // end keyPressed

            @Override
            public void keyReleased(KeyEvent kr) {  // 0=still 1=up , 2=right , 3=left , 4=down
                if (direction == 4) { // 1
                    obj = punto_up;
                }
                if (direction == 3) { // 2
                    obj = punto_sx;
                }
                if (direction == 2) { // 3 
                    obj = punto_dx;
                }
                if (direction == 1) { // 4 
                    obj = punto_dn;
                }
                direction = 0;  // set still image

            } // end anonymous class and KeyListener

        });

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

        requestFocus(); // get focus after changing card
        setFocusable(true);

        // setting background points and cash in the game
        setBackground(g2d);

        // checking jump collision and enemy death
        Jump();

        if (rz_y == 0 & direction != 3 & direction != 2) // to turn razmazio
        // in normal still --- rz_y 615
        // state after jump
        {
            if (obj == punto) {
                obj = punto;
            }
            if (obj == punto) {
                obj = punto;
            }
        }
        g2d.drawImage(obj, rz_x, rz_y, this); // Drawing the character image

        repaint();
    } // end paintComponent

    // /////////////////////////////// DIRECTION CONDITIONS
    // \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    void Jump() // Jump mechanism
    {

        if (moveableDown == true) {
            if (jump == true & rz_y >= 450) // For upward motion during jump 450
            {
                if (jumpright == true) {
                    obj = punto;
                } else {
                    obj = punto;
                }

                rz_y--;
                if (rz_y <= 450) {
                    jump = false;
                }
            }
            if (jump == false & rz_y < 615) // For downward motion during jump rz_y 615
            {
                if (jumpright == true) {
                    obj = punto;
                } else {
                    obj = punto;
                }
                rz_y++;
            }
        }

    }// end up

    void left() {
        if (moveableLeft == true & bk_x > BKMIN_X) {
            bk_x -= 8; // decrease xcoord while moving left

        }// end if
    }// end left

    void right() {
        if (moveableRight == true & bk_x < BKMAX_X) {
            bk_x += 8; // increasing xcoord while moving right
        }
        rz_x++; // end if
    }// end right

    void up() {
        if (moveableUp == true) {
            bk_y -= 8;
        }
        rz_y++;
    }

    void down() {
        if (moveableDown == true) {
            bk_y += 8;
        }
        rz_y--;
    }

    // ////////////////////////////////////// SETTER FUNCTIONS
    // \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    void setBackground(Graphics g2d) {
        g2d.drawImage(background_e, bk_x, 0, null); // Drawing background ..... rz_background
        // relative to
        // character
    }// end setBackground

// end class {
///////////////////////////////////////////////////////////////////////////
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

    @Override
    public void actionPerformed(ActionEvent e) { // 0=still 1=up , 2=right , 3=left , 4=down
        if (direction == 1) {
            up();
        }
        if (direction == 2) {
            left();
        }
        if (direction == 3) {
            right();
        }
        if (direction == 4) {
            down();
        }
    }

}

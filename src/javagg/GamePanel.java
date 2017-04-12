package javagg;

import Illogic.Character;
import Illogic.hotSide;
import Illogic.Obbj;
import Illogic.GenObj;
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
import javax.swing.Timer;
import java.awt.event.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener {

    int i, k;
    private int min;
    private int max;

    // immagini
    private final BufferedImage background_e; // 1800x600
    private final BufferedImage background_m;
    private final BufferedImage background_h;
    private final Rectangle backGame;
    public BufferedImage square = Resources.getImage("/image/square1.png");
    public BufferedImage puntoo = Resources.getImage("/image/Punto.png");
    public BufferedImage tri = Resources.getImage("/image/tri.png");
    Image punto = Resources.getImage("/image/Punto.png");
    Image puntoR = Resources.getImage("/image/Punto_r.png");
    Image objR = puntoR;
    Image obj = punto;
    Image obj_e = square;
    Obbj enemy = new Obbj(square, 45, 45);
    hotSide enemyt = new hotSide(45, 45);

    Character character = new Character(puntoo, 45, 45);

    private static GenObj generaObj;
    private int enemymin, enemymax;
    private ArrayList<Obbj> arrayS;
    private ArrayList<hotSide> arrayHS;

    final private int BKMIN_X = 0, BKMAX_X = 10000;
    final private int BKMIN_Y = 0, BKMAX_Y = 570;
    private int bk_x = 350;
    private int bk_y = 480;
    public int rz_x = 350;
    public int rz_y = 480;
    public int yPos;
    public int xPos;

    public float vDelta;
    public float rbDelta;
    public float rbDegDelta;
    public float gDelta;
    int checkR = 0;
    public boolean bounce = false;

    static int direction = 0;
    static int plusminus = 0;
    static boolean operation = false;
    int theLast;
    int round;
    static boolean moveableRight = false;
    static boolean moveableLeft = false;
    static boolean moveableDown = false;
    static boolean moveableUp = false;

    private final Timer time;

    static boolean pause = false;
    private int currentPos;
    public int current = 0;
    private int q;

    public GamePanel() {

        background_e = Resources.getImage("/image/BBIG.jpg");
        background_m = Resources.getImage("/image/medBG_m.png");
        background_h = Resources.getImage("/image/hardBG_m.png");
        this.backGame = new Rectangle(914, 18, 182, 42);

        this.addMouseListener(new GamePanel.GiocaListener());
        this.addMouseMotionListener(new GamePanel.GiocaListener());

        yPos = rz_y;
        //xPos = rz_x;
        vDelta = 0;
        gDelta = 0.25f;
        rbDegDelta = 2.25f;

        generaObj = new GenObj();
        arrayS = generaObj.getArrayS();
        arrayHS = generaObj.getArrayHS();
        setLayout(null);

        time = new Timer(15, this); //15
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
                if (kp.getKeyCode() == KeyEvent.VK_UP) {
                    vDelta = -8;
                    rbDelta = vDelta;
                    bounce = true;
                }
                if (kp.getKeyCode() == KeyEvent.VK_1) {
                    character.restoreHP();
                    time.start();
                }
                if (kp.getKeyCode() == KeyEvent.VK_PLUS) {

                }
                if (kp.getKeyCode() == KeyEvent.VK_MINUS) {

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
        collision();
        drawGenObj(g);
        drawString(g);

        g2d.drawImage(obj, character.rz_x, character.yPos, 50, 50, this);

        repaint();
    }

    void Jump() {
        if (character.yPos <= BKMIN_Y) {
            vDelta = +4;
            rbDelta = vDelta;
        }
        if (bounce) {
            character.yPos += vDelta;
            vDelta += gDelta;
            if (character.yPos >= rz_y) {
                character.yPos = rz_y;
                if (rbDelta >= 0) {
                    bounce = false;
                } else {
                    rbDelta += rbDegDelta;
                    vDelta = rbDelta;
                }
            }
        }
    }

    void left() { // 3
        if (bk_x > BKMIN_X + 250) {
            bk_x -= 6; //6
            currentPos = bk_x;
            for (i = 0; i < arrayS.size(); i++) {
                arrayS.get(i).movimento(false);
            }
            for (k = 0; k < arrayHS.size(); k++) {
                arrayHS.get(k).movimento(false);
            }//enemy.movimento(false);
            moveableLeft = false;
        }
    }

    void right() { // 2
        if (bk_x < BKMAX_X - 900) {
            bk_x += 6;
            currentPos = bk_x;
            for (i = 0; i < arrayS.size(); i++) {
                arrayS.get(i).movimento(true);
            }
            for (k = 0; k < arrayHS.size(); k++) {
                arrayHS.get(k).movimento(true);
            }//enemy.movimento(true);
            moveableRight = false;
        }
    }

    public void collision() {
        if (character.alive == false) {
            time.stop();
        }
        for (int u = 0; u < arrayHS.size(); u++) { // hotArea
            if (character.getBordiC().intersects(arrayHS.get(u).getBordiT()) == true) {
                character.currentPow--;
            }
        }
        for (q = 0; q < arrayS.size(); q++) {
            if (character.getBordiC().intersects(arrayS.get(q).getBordi()) == true) {
                if (arrayS.get(q).r == arrayS.get(round).r) {
                    round = q;
                    arrayS.remove(q);
                    character.currentPow += 10;
                } else if (character.getPow() > 0) {
                    character.currentPow--;
                } else if (character.getPow() < 0) {
                    System.out.println("VITA 0");
                    // time.stop();
                    //bounce = false;
                } else if (character.getPow() > 0 && arrayS.isEmpty()) {
                    System.out.println("WIN");
                }
            }
        }
    }

    private void drawString(Graphics g) {
        g.setFont(new Font("Tahoma", Font.BOLD, 30));
        g.setColor(Color.red);
        g.drawString(+arrayS.get(round).n_1 + arrayS.get(round).OP() + arrayS.get(round).n_2 + "=", 900, 60);

    }

    private void drawStop(Graphics g) {
        g.setFont(new Font("Tahoma", Font.BOLD, 50));
        g.setColor(Color.black);
        g.drawString("This is the End", 450, 150);
    }

    private void draWin(Graphics g) {
        g.setFont(new Font("Tahoma", Font.BOLD, 50));
        g.setColor(Color.black);
        g.drawString("This is the End", 450, 150);
    }

    private void drawGenObj(Graphics g) {
        for (i = 0; i < arrayS.size(); i++) {
            if (arrayS.get(i).alive) {
                arrayS.get(i).draw(g);
            }
        }
    }

    private void drawHotS(Graphics g) {
        for (int w = 0; w < arrayHS.size(); w++) {
            arrayHS.get(w).drawT(g);
        }
    }

    public void drawM(Graphics g) {
        for (i = 0; i < arrayS.size(); i++) {
            g.drawImage(puntoR, (i * 32), 535, 34, 34, null);
        }
    }

    public void drawA(Graphics g) {
        g.setFont(new Font("Tahoma", Font.BOLD, 18));
        g.setColor(Color.red);
        //g.drawString(arrayA.get(i)., (checkR * 45) + 13, 560);
    }

    public void drawLife(Graphics g) {
        if (character.getPow() != 0 && character.getPow() > 0) {
            if (character.getPow() < 150) {
                int alpha = 150;
                Color myColour = new Color(255, 0, 0, alpha);
                g.setColor(myColour);
                g.drawRect(10, 20, 880, 20);
                g.fillRect(10, 20, character.getPow(), 20);
            } else {
                int alpha = 150; // 50% transparent 127
                Color myColour = new Color(0, 0, 0, alpha);
                g.setColor(myColour);
                g.drawRect(10, 20, 880, 20);
                g.fillRect(10, 20, character.getPow(), 20);
            }
        } else {
            int alpha = 150; // 50% transparent 127
            Color myColour = new Color(0, 0, 0, alpha);
            g.setColor(myColour);
            g.drawRect(10, 20, 880, 20);
        }
    }

    public int getPos() {
        return this.currentPos;
    }

    void setBackground(Graphics g2d) {
        g2d.drawImage(background_e, 200 - bk_x, 0, null);

        drawM(g2d);
        drawLife(g2d);
        drawHotS(g2d);
        if (character.getPow() < 0) {
            drawStop(g2d);

//            MainFrame.gamePanel.setVisible(false);
//            MainFrame.endPanel.setVisible(true);
//            character.restoreHP();
        } else if (character.alive && arrayS.isEmpty()) {
            draWin(g2d);

        }
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

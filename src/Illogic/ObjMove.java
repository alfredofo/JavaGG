package Illogic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;
import javagg.util.Resources;

public class ObjMove {

    public int _x;
    public int _y;
    protected int larghezza;
    protected int altezza;
    public boolean alive;
    protected int velocita;
    public int Magic;
    public int n_1;
    public int n_2;
    public int r;
    public int z;
    public BufferedImage square = Resources.getImage("/image/square1.png");
    public int resu;
    private String op;
    final Font f = new Font("Tahoma", Font.BOLD, 20);

    public ObjMove(BufferedImage img, int pLarghezza, int pAltezza) {
        img = square;
        this.larghezza = pLarghezza;
        this.altezza = pAltezza;
        this._x = getX();
        this._y = getY();
        n_1 = getNuno();
        n_2 = getNdue();
        this.r = switchOp();
        alive = true;
    }

    public void movimento(boolean direction) {
        if (direction) {
            this._x -= 5; // destra
        } else {
            this._x += 5; // sinistra
        }
    }

    public Rectangle getBordi() {
        return new Rectangle(this._x, this._y, this.larghezza, this.altezza);
    }

    public void draw(Graphics g) {
        g.drawImage(square, this._x, this._y, 45, 45, null);
        g.setColor(Color.red);
        g.setFont(f);
        //centredString(r, r, r, g);
        g.drawString(Integer.toString(r), this._x + 17, this._y + 20);
    }

    public void centredString(String s, int w, int h, Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int a = (w - fm.stringWidth(s)) / 2;
        int b = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())));
        g.drawString(s, a, b);
    }

//    public void drawRes(Graphics g) {
//        g.setFont(new Font("Tahoma", Font.BOLD, 20));
//        g.setColor(Color.red);
//        g.drawString(Integer.toString(r), this._x + 17, this._y + 20);
//    }
    public int getLarghezza() {
        return this.larghezza;
    }

    public int getAltezza() {
        return this.altezza;
    }

    public int getX() {
        for (int i = 1; i < 1500; i += 300) {
            _x = ThreadLocalRandom.current().nextInt(i) + 300;
        }

        //_x = ThreadLocalRandom.current().nextInt(400,7500);
        // System.out.println("Illogic.ObjMove.getX()"+_x);
        return this._x;
    }

    public int getY() {
        for (int i = 1; i < 450; i += 4.5) {
            _y = ThreadLocalRandom.current().nextInt(i);
        }
//_y = (int) (Math.random() * 450);
        return this._y;
    }

    private int getNuno() {
        int n1;
        n1 = ThreadLocalRandom.current().nextInt(1, 10); // limite sup == difficoltà
        return n1;
    }

    private int getNdue() {
        int n2;
        n2 = ThreadLocalRandom.current().nextInt(1, 10);
        return n2;
    }

    private int getOpPlus(int a, int b) {
        int res;
        res = n_1 + n_2;
        return res;
    }

    private int getOpMinus(int a, int b) {
        int res;
        res = n_1 - n_2;
        return res;
    }

    private int getOpD(int a, int b) {
        int res;
        res = n_1 / n_2;
        return res;
    }

    private int getOpP(int a, int b) {
        int res;
        res = n_1 * n_2;
        return res;
    }

    public int switchOp() {

        z = ThreadLocalRandom.current().nextInt(1, 4);
        if (z == 1) {
            resu = getOpPlus(n_1, n_2);
            z = 1;
        }
        if (z == 2) {
            resu = getOpMinus(n_1, n_2);
            z = 2;
        }
        if (z == 3) {
            resu = getOpP(n_1, n_2);
            z = 3;
        }
        if (z == 4) {
            resu = getOpD(n_1, n_2);
            z = 4;
        }
        //System.out.println("switchOp()" + resu);
        return this.resu;
    }

    public String OP() {
        String a = "+";
        String b = "-";
        String c = "*";
        String d = "/";
        if (z == 1) {
            op = a;
        }
        if (z == 2) {
            op = b;
        }
        if (z == 3) {
            op = c;
        }
        if (z == 4) {
            op = d;
        }
        return op;

    }

}

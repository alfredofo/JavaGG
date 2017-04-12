package Illogic;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import javagg.GamePanel;
import javagg.MainFrame;

public class Character extends ObjMove {

    final private int BKMIN_X = 0, BKMAX_X = 10000;
    final private int BKMIN_Y = 0, BKMAX_Y = 570;

    public int bk_x = 350;
    public int bk_y = 480;

    public int rz_x = 350;
    public int rz_y = 480;

    public float vDelta;
    public float rbDelta;
    public float rbDegDelta;
    public float gDelta;

    public boolean bounce = false;

    public int yPos;
    public int xPos;
    public int currentPos;

    public int pow;
    public int currentPow = 880;

    public Character(BufferedImage img, int pLarghezza, int pAltezza) {
        super(img, pLarghezza, pAltezza);
        yPos = rz_y;
        xPos = 350;
        vDelta = 0;
        gDelta = 0.25f;
        rbDegDelta = 2.25f;
        alive = true;
    }

    public void Jump() {
        if (yPos <= BKMIN_Y) {
            vDelta = +4;
            rbDelta = vDelta;
        }
        if (bounce) {
            yPos += vDelta;
            vDelta += gDelta;
            if (yPos >= rz_y) {
                yPos = rz_y;
                if (rbDelta >= 0) {
                    bounce = false;
                } else {
                    rbDelta += rbDegDelta;
                    vDelta = rbDelta;
                }
            }
        }
    }

    public boolean isGrounded() {
        if (yPos == rz_y) {
            System.out.println("isGrounded");
            return true;
        } else {
            System.out.println("is!=Grounded");
            return false;
        }
    }

    public Rectangle getBordiC() {
        return new Rectangle(this.xPos, this.yPos, 30, 30);
    }

    public int getPow() {
        return this.currentPow;
    }

    public boolean checkLife() {
        if (currentPow == 0) {
            return false;
        } else {
            return true;
        }

    }

    public void restoreHP() {
        this.currentPow = 880;
    }

}

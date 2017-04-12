package Illogic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class hotSide {

    public int x;
    public int y;

    public hotSide(int a, int b) {
        x = getX();
        y = getY();
    }

    public void movimento(boolean direction) {
        if (direction) {
            this.x -= 5; // destra
        } else {
            this.x += 5; // sinistra
        }
    }

    public Rectangle getBordiT() {
        return new Rectangle(this.x, this.y, 175, 175);
    }

    public void drawT(Graphics g) {
        //System.out.println("x" + x + "y " + y);
        int alpha = 150;
        Color myColour = new Color(255, 0, 0, alpha);
        g.setColor(myColour);
        g.fillRect(x, y, 175, 175);
    }

    public int getX() {
        x = ThreadLocalRandom.current().nextInt(450, 7500);
        return this.x;
    }

    public int getY() {
        y = ThreadLocalRandom.current().nextInt(100, 450);
        return this.y;
    }
}

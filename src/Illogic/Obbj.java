package Illogic;

import java.awt.image.BufferedImage;

public class Obbj extends ObjMove {

    private int magicNumber;

    public Obbj(BufferedImage img, int pLarghezza, int pAltezza) {
        super(img, pLarghezza, pAltezza);
    }

    public BufferedImage getSquare() {
        return square;
    }
 


}

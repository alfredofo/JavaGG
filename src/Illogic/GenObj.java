package Illogic;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import javagg.util.Resources;

public class GenObj {

    public BufferedImage square = Resources.getImage("/image/square.png");

    private ArrayList<Obbj> arrayObbj;
    private ArrayList<hotSide> arrayhotSide;

    public GenObj() {
        arrayObbj = new ArrayList<>();

        for (int i = 0; i <2; i++) {
            Obbj obbj = new Obbj(square, 45, 45);
            arrayObbj.add(i, obbj);
            //OrderX();
        }

    }

    public void OrderX() {
        Collections.sort(arrayObbj, (_x1, _x2) -> _x1._x - _x2._x);
    }

    public ArrayList<Obbj> getArrayS() {
        return arrayObbj;
    }

    public ArrayList<hotSide> getArrayHS() {
        arrayhotSide = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            hotSide hside = new hotSide(1, 1);
            arrayhotSide.add(i, hside);
        }
        return arrayhotSide;
    }
}

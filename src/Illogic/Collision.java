package Illogic;

public class Collision {

    public static boolean collisione(Character Giocatore, ObjMove pOggettiInMovimento) {
        if (Giocatore.getBordiC().intersects(pOggettiInMovimento.getBordi())) {
            return true;
        }
        return false;
    }
    
}


package Bomb;

import entityGerarchy.NotMovingAnimatedEntity;
import main.GamePanel;
import Animation.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


public class Bomb extends NotMovingAnimatedEntity {

    public static ArrayList<Bomb> bombs = new ArrayList<>();
    private boolean passedInto;

    public static Bomb getInstance(int worldX, int worldY, Observer observer) {

        for(Bomb b: bombs) {
            if(b.getWorldPositionX() == worldX && b.getWorldPositionY() == worldY) {
                return b;
            }
        }

        Bomb b = new Bomb(worldX, worldY, observer);
        bombs.add(b);
        return b;

    }

    private Bomb(int worldX, int worldY, Observer observer) {
        super(worldX, worldY, GamePanel.tileSize, GamePanel.tileSize, 0, 0, "res/Bomb", 15, observer);

        passedInto = false;

        animation = new CycledReversedAnimation(animation, this, 4);

    }

    public static void removeBomb(Bomb bomb) {
        bombs.remove(bomb);
    }

    public boolean isPassedInto() {
        return passedInto;
    }

    public void hasPassedInto() {
        passedInto = true;
    }

    public void draw(Graphics2D g2) {

        g2.drawImage(animation.getCurrentImage(), worldPositionX, worldPositionY, GamePanel.tileSize, GamePanel.tileSize, null);

    }

    @Override
    public void update(Observable o, Object arg) {

        setChanged();
        notifyObservers(arg);

    }
}

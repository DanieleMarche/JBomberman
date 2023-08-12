
package Flames;

import Animation.CycledReversedAnimation;
import entityGerarchy.NotMovingAnimatedEntity;
import main.GamePanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Flame extends NotMovingAnimatedEntity {

    public static ArrayList<Flame> flames = new ArrayList<>();

    protected FlameType flameType;

    public Flame(int worldPositionX, int worldPositionY,FlameType flameType, Observer observer) {
        super(worldPositionX, worldPositionY, GamePanel.tileSize, GamePanel.tileSize, 0, 0, flameType.getAnimationPath(), 10, observer);
        this.flameType = flameType;
        animation = new CycledReversedAnimation(flameType.getAnimationPath(), 6, this, 2);
        flames.add(this);

    }

    public static void removeFlame(Flame flame) {
        flames.remove(flame);
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


package Flames;

import Animation.CycledReversedAnimation;
import Controllers.CollisionDetector;
import entityGerarchy.Dieable;
import entityGerarchy.NotMovingAnimatedEntity;
import main.GameView;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class Flame extends NotMovingAnimatedEntity {

    protected FlameType flameType;

    public Flame(int worldPositionX, int worldPositionY, FlameType flameType, Observer observer) {
        super(worldPositionX, worldPositionY, GameView.tileSize, GameView.tileSize, GameView.tileSize / 2, GameView.tileSize / 2, GameView.tileSize / 4, GameView.tileSize / 4, flameType.getAnimationPath(), 0,  5, observer);
        this.flameType = flameType;
        loopAnimation = new CycledReversedAnimation(flameType.getAnimationPath(), 10, this, 2);

        collisionChecker = movingEntity -> {

                if(CollisionDetector.checkcollisionBetweenMovingentityAndOtherEntity(movingEntity, this)) {
                    if(movingEntity instanceof Dieable)
                        ((Dieable) movingEntity).die();
                }

        };

    }

    public void draw(Graphics2D g2) {

        g2.drawImage(loopAnimation.getCurrentImage(), worldPositionX, worldPositionY, GameView.tileSize, GameView.tileSize, null);

    }

    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }
}

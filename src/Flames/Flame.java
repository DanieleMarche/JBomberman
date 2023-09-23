
package Flames;

import Animation.CycledReversedAnimation;
import Controllers.FlameController;
import EntityModelGerarchy.NotMovingAnimatedEntity;
import main.GameView;

/**
 * The Flame class represents a flame object in the game. Flames are generated as part of an explosion and can have
 * different types based on their appearance and behavior.
 *
 * @Author Daniele Marchetilli
 */
public class Flame extends NotMovingAnimatedEntity {

    protected FlameType flameType;

    /**
     * Creates a Flame object at the specified world position with the given flame type.
     *
     * @param worldPositionX The X-coordinate of the world position.
     * @param worldPositionY The Y-coordinate of the world position.
     * @param flameType      The type of flame, determining its appearance and behavior.
     */
    public Flame(int worldPositionX, int worldPositionY, FlameType flameType) {
        super(worldPositionX, worldPositionY, GameView.tileSize, GameView.tileSize, GameView.tileSize / 2,
                GameView.tileSize / 2, GameView.tileSize / 4, GameView.tileSize / 4, flameType.getAnimationPath(), 0, 5, FlameController.getInstance());
        this.flameType = flameType;
        loopAnimation = new CycledReversedAnimation(flameType.getAnimationPath(), 5, this, 2);
        animatedEntityThread.startThread();
    }


}


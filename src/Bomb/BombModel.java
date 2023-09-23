package Bomb;

import Bomberman.PlayerModel;
import Controllers.AssetManager;
import Animation.CycledReversedAnimation;
import Controllers.ControllersGerarchy.BombController;
import Explosion.Explosion;
import EntityModelGerarchy.NotMovingAnimatedEntity;

/**
 * The Bomb class represents a bomb entity in the Bomberman game. Bombs can be placed by the player
 * and explode after a certain time, affecting the game world and entities within the player explosion radius.
 * It extends the NotMovingAnimatedEntity class.
 *
 * @author Daniele Marchetilli
 *
 */
public class BombModel extends NotMovingAnimatedEntity {

    /** The player who placed the bomb. */
    private final PlayerModel playerModel;

    /** A flag indicating whether the player is already passed through the bomb. */
    private boolean passedInto;

    private static final String animationPath = "res/Bomb";

    /**
     * Constructs a Bomb object at the specified world coordinates.
     *
     * @param worldX The x-coordinate of the bomb in the game world.
     * @param worldY The y-coordinate of the bomb in the game world.
     * @param playerModel The player who placed the bomb.
     */
    public BombModel(int worldX, int worldY, PlayerModel playerModel) {
        super(worldX, worldY, animationPath, 5, BombController.getInstance());

        passedInto = false;
        this.playerModel = playerModel;

        // Initialize the bomb's animation as a CycledReversedAnimation
        loopAnimation = new CycledReversedAnimation(loopAnimation, this, 4);
        animatedEntityThread.startThread();
    }

    /**
     * Gets the player who placed the bomb.
     *
     * @return The player who placed the bomb.
     */
    public PlayerModel getPlayer() {
        return playerModel;
    }

    /**
     * Checks if the player can pass through the bomb.
     *
     * @return True if the player has already passed throw the bomb, false otherwise.
     */
    public boolean isPassedInto() {
        return passedInto;
    }

    /**
     * Sets the flag indicating that other entities can pass through the bomb to the value true.
     */
    public void hasPassedInto() {
        passedInto = true;
    }

    /**
     * Activates the explosion caused by the bomb. This method is called when the bomb's timer expires.
     * It increases the player's remaining bombs and creates an explosion at the bomb's location.
     */
    public void explode() {
        playerModel.increaseRemainingBombsAtSameTime();
        animatedEntityThread = null;
        AssetManager.getInstance().addExplosion(new Explosion(getCol(), getRow(), PlayerModel.getExplosionRadius()));
    }

}


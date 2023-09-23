package PowerUp;

import Bomberman.PlayerModel;
import EntityModelGerarchy.NotMovingAnimatedEntity;
import main.Scored;

/**
 * The PowerUp class represents a power-up item in the game that provides various benefits to the player when collected.
 *
 * @Author Daniele Marchetilli
 */
public class PowerUpModel extends NotMovingAnimatedEntity implements Scored {

    protected PowerUpType type;

    /**
     * Constructs a PowerUp object at the specified position with the given power-up type.
     *
     * @param x    The X-coordinate of the power-up's position.
     * @param y    The Y-coordinate of the power-up's position.
     * @param type The type of power-up to create.
     */
    public PowerUpModel(int x, int y, PowerUpType type) {
        super(x, y, type.getAnimationPath(), 10, PowerUpsController.getInstance());
        this.type = type;
        visible = false;
    }

    /**
     * Activates the power-up for the player and updates the player's attributes accordingly.
     *
     * @param playerModel The player object that collects the power-up.
     */
    public void powerUpActivate(PlayerModel playerModel) {
        switch (type) {
            case MORE_SPEED -> playerModel.increaseSpeed();
            case MORE_BOMB_AT_SAME_TIME -> playerModel.increaseBombAtSameTime();
            case MORE_FLAME -> playerModel.increaseExplosionRadius();
        }
        playerModel.addScore(this);
    }

    /**
     * Retrieves the type of the power-up.
     *
     * @return The type of the power-up.
     */
    public PowerUpType getType() {
        return type;
    }

    /**
     * Retrieves the number of points awarded to the player for collecting this power-up.
     *
     * @return The number of points awarded for collecting this power-up.
     */
    @Override
    public int getPoints() {
        return type.getPoints();
    }

}


package PowerUp;

import Animation.AnimationMessages;
import Bomberman.PlayerModel;
import Controllers.AudioManager;
import Controllers.CollisionDetector;
import Controllers.ControllersGerarchy.CollectionOfEntitiesController;
import EntityModelGerarchy.MovingEntity;

import java.util.ArrayList;
import java.util.Observable;

/**
 *
 * The PowerUpsController class manages power-up entities in the Bomberman game.
 * It extends the CollectionOfEntitiesController class and handles updates related to power-ups,
 * such as collision checking with the player, repainting the game, and handling game outcome messages.
 *
 * @Author Daniele Marchetilli
 */
public class PowerUpsController extends CollectionOfEntitiesController<PowerUpModel> {

    /**
     * The singleton instance of the PowerUpsController class.
     */
    private static PowerUpsController instance = null;

    /**
     * Get the singleton instance of the PowerUpsController class.
     *
     * @return The PowerUpsController instance.
     */
    public static PowerUpsController getInstance() {
        if (instance == null) {
            instance = new PowerUpsController();
        }
        return instance;
    }

    /**
     * Private constructor to ensure a single instance of PowerUpsController.
     * Use {@link #getInstance()} to obtain the instance.
     */
    private PowerUpsController() {
        super();

        // Define the collisionChecker behavior for power-up entities.
        collisionChecker = (MovingEntity player) -> {
            if (player instanceof PlayerModel) {
                ArrayList<PowerUpModel> powerUpsCopyModel = new ArrayList<>(entities);
                powerUpsCopyModel.forEach(powerUpModel -> {
                    if (CollisionDetector.checkcollisionBetweenMovingentityAndOtherEntity(player, powerUpModel) &&
                            powerUpModel.isVisible()) {
                        AudioManager.getInstance().play("res/sounds/Super_Bomberman_Sound_Effects/item-get.wav");
                        powerUpModel.powerUpActivate((PlayerModel) player);
                        update(powerUpModel, AnimationMessages.REMOVE_ELEMENT);
                    }
                });
            }
        };
    }

    /**
     * Update method called when observing changes in power-up entities.
     *
     * @param o   The Observable object (a power-up) that triggered the update.
     * @param arg The argument representing the type of animation message or the power-up entity itself.
     */
    @Override
    public void update(Observable o, Object arg) {
        PowerUpModel powerUpModel;
        switch ((AnimationMessages) arg) {
            case REPAINT_GAME -> {
                powerUpModel = (PowerUpModel) o;
                repaint(powerUpModel);
            }
            case REMOVE_ELEMENT -> {
                powerUpModel = (PowerUpModel) o;
                entities.remove(powerUpModel);
                repaint(powerUpModel);
            }
            case GAME_WON, GAME_LOST -> entities.clear();
        }
    }
}


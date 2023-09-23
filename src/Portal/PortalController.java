package Portal;

import Animation.AnimationMessages;
import Controllers.AssetManager;
import Controllers.CollisionDetector;
import Controllers.ControllersGerarchy.SingleEntityController;
import Bomberman.PlayerModel;

import java.util.Observable;

/**
 *
 * The PortalController class manages the portal entity in the Bomberman game.
 * It extends the SingleEntityController class and handles updates related to the portal,
 * such as collision checking with the player, repainting the game, and handling game outcome messages.
 *
 * @Author  Daniele Marchetilli
 */
public class PortalController extends SingleEntityController<PortalModel> {

    /**
     * The singleton instance of the PortalController class.
     */
    private static PortalController instance = null;

    /**
     * Get the singleton instance of the PortalController class.
     *
     * @return The PortalController instance.
     */
    public static PortalController getInstance() {
        if (instance == null) instance = new PortalController();
        return instance;
    }

    /**
     * Private constructor to ensure a single instance of PortalController.
     * Use {@link #getInstance()} to obtain the instance.
     */
    private PortalController() {
        super();

        // Define the collisionChecker behavior for portal entities.
        collisionChecker = movingEntity -> {
            if (movingEntity instanceof PlayerModel && AssetManager.getInstance().getEnemies().isEmpty() &&
                    CollisionDetector.checkPresenceInSameMapCell(movingEntity, entity)) {
                if (entity.isVisible()) {
                    setChanged();
                    notifyObservers(AnimationMessages.GAME_WON);
                }
            }
        };
    }

    /**
     * Update method called when observing changes in the portal entity.
     *
     * @param o   The Observable object (the portal) that triggered the update.
     * @param arg The argument representing the type of animation message or the portal entity itself.
     */
    @Override
    public void update(Observable o, Object arg) {
        PortalModel portalModel = (PortalModel) o;
        switch ((AnimationMessages) arg) {
            case REPAINT_GAME -> {
                setChanged();
                notifyObservers(portalModel);
            }
            case GAME_WON, GAME_LOST -> PortalModel.removeInstance();
        }
    }
}


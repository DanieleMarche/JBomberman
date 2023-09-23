package Controllers;

import Animation.AnimationMessages;
import Controllers.ControllersGerarchy.CollectionOfEntitiesController;
import Flames.Flame;
import EntityModelGerarchy.Dieable;

import java.util.ArrayList;
import java.util.Observable;

/**
 *
 *
 * The FlameController class manages flames in the Bomberman game.
 * It extends the CollectionOfEntitiesController class and is responsible for
 * handling and updating flame entities during gameplay.
 *
 * @Author Daniele Marchetilli
 */
public class FlameController extends CollectionOfEntitiesController<Flame> {

    /**
     * The singleton instance of the FlameController class.
     */
    private static FlameController instance = null;

    /**
     * Get the singleton instance of the FlameController class.
     *
     * @return The FlameController instance.
     */
    public static FlameController getInstance() {
        if (instance == null) {
            instance = new FlameController();
        }
        return instance;
    }

    /**
     * Private constructor to ensure a single instance of FlameController.
     * Use {@link #getInstance()} to obtain the instance.
     */
    private FlameController() {
        super();

        // Define the collisionChecker behavior for flame entities.
        collisionChecker = movingEntity -> {
            ((ArrayList<Flame>)entities.clone()).forEach(flame -> {
                if (CollisionDetector.checkcollisionBetweenMovingentityAndOtherEntity(movingEntity, flame)) {
                    if (movingEntity instanceof Dieable)
                        ((Dieable) movingEntity).die();
                }
            });
        };
    }

    /**
     * Update method called when observing changes in flame entities.
     *
     * @param o   The Observable object (a flame) that triggered the update.
     * @param arg The argument representing the type of animation message.
     */
    @Override
    public void update(Observable o, Object arg) {
        Flame flame;
        switch ((AnimationMessages) arg) {
            case REMOVE_ELEMENT -> {
                flame = (Flame) o;
                entities.remove(flame);
                repaint(flame);
            }
            case REPAINT_GAME -> {
                flame = (Flame) o;
                repaint(flame);
            }
        }
    }
}

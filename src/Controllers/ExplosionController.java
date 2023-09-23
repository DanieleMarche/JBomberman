package Controllers;

import Animation.*;

import Controllers.ControllersGerarchy.CollectionOfEntitiesController;
import Controllers.ControllersGerarchy.Controller;
import Explosion.Explosion;
import Flames.Flame;

import java.util.*;

/**
 * The ExplosionController class manages explosions in the Bomberman game.
 * It is responsible for tracking and updating explosions that occur during gameplay.
 * This class extends the base Controller class and observes explosion-related events.
 *
 * @Author Daniele Marchetilli
 */
public class ExplosionController extends Controller {

    /**
     * The singleton instance of the ExplosionController class.
     */
    private static ExplosionController instance = null;

    /**
     * A list of explosions currently active in the game.
     */
    private ArrayList<Explosion> explosions;

    /**
     * Get the singleton instance of the ExplosionController class.
     *
     * @return The ExplosionController instance.
     */
    public static ExplosionController getInstance() {
        if (instance == null) {
            instance = new ExplosionController();
        }
        return instance;
    }

    /**
     * Private constructor to ensure a single instance of ExplosionController.
     * Use {@link #getInstance()} to obtain the instance.
     */
    private ExplosionController() {}

    /**
     * Set the list of explosions currently active in the game.
     *
     * @param explosions An ArrayList of Explosion objects representing active explosions.
     */
    public void setExplosions(ArrayList<Explosion> explosions) {
        this.explosions = explosions;
    }

    /**
     * Update method called when observing changes in explosions.
     *
     * @param o   The Observable object (an explosion) that triggered the update.
     * @param arg The argument representing the type of animation message.
     */
    @Override
    public void update(Observable o, Object arg) {
        Explosion explosion;
        if (Objects.requireNonNull((AnimationMessages) arg) == AnimationMessages.REMOVE_ELEMENT) {
            explosion = (Explosion) o;
            explosions.remove(explosion);
        }
    }
}




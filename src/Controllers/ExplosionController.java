package Controllers;

import Animation.*;

import Controllers.ControllersGerarchy.CollectionOfEntitiesController;
import Controllers.ControllersGerarchy.Controller;
import Explosion.Explosion;
import Flames.Flame;

import java.util.*;

public class ExplosionController extends Controller {

    private static ExplosionController instance = null;

    ArrayList<Explosion> explosions;

    public static ExplosionController getInstance() {
        if (instance == null) {
            instance = new ExplosionController();
        }
        return instance;
    }

    private ExplosionController() {
        explosions = AssetManager.getInstance().getExplosions();
    }

    @Override
    public void update(Observable o, Object arg) {
        Explosion explosion;
        if (Objects.requireNonNull((AnimationMessages) arg) == AnimationMessages.REMOVE_ELEMENT) {
            explosion = (Explosion) o;
            explosions.remove(explosion);
        }

    }
}



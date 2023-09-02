package Controllers;

import Animation.AnimationMessages;
import Controllers.ControllersGerarchy.CollectionOfEntitiesController;
import Flames.Flame;
import entityGerarchy.Dieable;

import java.util.Observable;

public class FlameController extends CollectionOfEntitiesController<Flame> {

    private static FlameController instance = null;

    public static  FlameController getInstance() {
        if(instance == null) {
            instance = new FlameController();
        }
        return instance;
    }

    private  FlameController() {

        super(AssetManager.getInstance().getFlames());

        collisionChecker = movingEntity -> {
            entities.forEach(flame -> {
                if(CollisionDetector.checkcollisionBetweenMovingentityAndOtherEntity(movingEntity, flame)) {
                    if(movingEntity instanceof Dieable)
                        ((Dieable) movingEntity).die();
                }

            });

        };
    }

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
            case GAME_LOST, GAME_WON -> entities.clear();

        }
    }
}

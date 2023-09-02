package Controllers;

import Animation.AnimationMessages;
import Controllers.ControllersGerarchy.SingleEntityController;
import Portal.Portal;
import enemy.Enemy;
import Bomberman.Player;

import java.util.Observable;

public class PortalController extends SingleEntityController<Portal> {

    private static PortalController instance = null;

    public static PortalController getInstance() {
        if(instance ==null) instance = new PortalController();
        return instance;

    }

    private PortalController() {
        super();
        collisionChecker = movingEntity -> {
            if(movingEntity instanceof Player && AssetManager.getInstance().getEnemies().isEmpty() && CollisionDetector.checkCollisionBasedOnTheGrid(movingEntity, entity)) {
                if(Portal.getInstance().isVisibility()) {
                    setChanged();
                    notifyObservers(AnimationMessages.GAME_WON);
                }

            }
        };
    }

    @Override
    public void update(Observable o, Object arg) {
         Portal portal = (Portal) o;
         switch((AnimationMessages) arg) {
             case REPAINT_GAME -> {
                 setChanged();
                 notifyObservers(portal);
             }
             case GAME_WON, GAME_LOST -> Portal.removeInstance();
         }

    }

}

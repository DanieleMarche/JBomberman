package Controllers;

import Animation.AnimationMessages;
import Controllers.ControllersGerarchy.CollectionOfEntitiesController;
import enemy.Enemy;
import Bomberman.Player;

import java.util.Observable;

public class EnemyController extends CollectionOfEntitiesController<Enemy> {

    private static EnemyController instance = null;

    public static EnemyController getInstance() {
        if(instance == null) instance = new EnemyController();
        return instance;
    }

    private EnemyController() {
        super(AssetManager.getInstance().getEnemies());

        collisionChecker = (movingEntity) -> {
            entities.forEach(enemy -> {
                if (CollisionDetector.checkcollisionBetweenMovingentityAndOtherEntity(movingEntity, enemy)) {
                    if(movingEntity instanceof Player) {
                        ((Player) movingEntity).die();
                    }
                }
            });
        };
    }

    @Override
    public void update(Observable o, Object arg) {

        switch((AnimationMessages) arg) {
            case REMOVE_ELEMENT -> {
                Enemy enemy = (Enemy) o;
                entities.remove(enemy);
                Player.getInstance().addScore(enemy);
                setChanged();
                notifyObservers(enemy);
            }
            case REPAINT_GAME ->    {
                Enemy enemy = (Enemy) o;
                setChanged();
                notifyObservers(enemy);
            }
            case GAME_LOST, GAME_WON -> entities.clear();

        }
    }
}

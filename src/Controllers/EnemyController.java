package Controllers;

import Animation.AnimationMessages;
import Bomberman.PlayerModel;
import Controllers.ControllersGerarchy.CollectionOfEntitiesController;
import enemy.EnemyModel;

import java.util.Observable;

public class EnemyController extends CollectionOfEntitiesController<EnemyModel> {

    private static EnemyController instance = null;

    public static EnemyController getInstance() {
        if(instance == null) instance = new EnemyController();
        return instance;
    }

    private EnemyController() {
        super();

        collisionChecker = (movingEntity) -> {
            entities.forEach(enemy -> {
                if (CollisionDetector.checkcollisionBetweenMovingentityAndOtherEntity(movingEntity, enemy)) {
                    if(movingEntity instanceof PlayerModel) {
                        ((PlayerModel) movingEntity).die();
                    }
                }
            });
        };
    }

    @Override
    public void update(Observable o, Object arg) {

        switch((AnimationMessages) arg) {
            case REMOVE_ELEMENT -> {
                EnemyModel enemyModel = (EnemyModel) o;
                entities.remove(enemyModel);
                PlayerModel.getInstance().addScore(enemyModel);

                setChanged();
                notifyObservers();

            }
            case REPAINT_GAME ->    {
                EnemyModel enemyModel = (EnemyModel) o;
                setChanged();
                notifyObservers(enemyModel);
            }
            case GAME_LOST, GAME_WON -> entities.clear();

        }
    }
}

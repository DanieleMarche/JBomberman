package Controllers.ControllersGerarchy;

import Animation.AnimationMessages;
import Bomb.BombModel;
import Bomberman.PlayerModel;
import Controllers.CollisionDetector;
import enemy.EnemyModel;
import main.GameView;

import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;

public class BombController extends CollectionOfEntitiesController<BombModel> {

    private static BombController instance = null;

    public static BombController getInstance() {
        if (instance == null) {
            instance = new BombController();
        }
        return instance;
    }

    private BombController () {
        super();

        collisionChecker = movingEntity -> {
            for (BombModel bombModel : (ArrayList<BombModel>)entities.clone()) {

                if (movingEntity instanceof PlayerModel) {
                    if (CollisionDetector.checkcollisionBetweenMovingentityAndOtherEntity(movingEntity, bombModel) && bombModel.isPassedInto()) {
                        movingEntity.activateCollision();

                    }

                    if (!CollisionDetector.checkcollisionBetweenMovingentityAndOtherEntity(movingEntity, bombModel) && !bombModel.isPassedInto() && movingEntity.equals(bombModel.getPlayer())) {
                        bombModel.hasPassedInto();
                    }
                }

                if (movingEntity instanceof EnemyModel && CollisionDetector.checkcollisionBetweenMovingentityAndOtherEntity(movingEntity, bombModel)) {
                    Rectangle intersection = bombModel.getBounds().union(movingEntity.getBounds());
                    if(intersection.height * intersection.width == (GameView.tileSize*GameView.tileSize)*2) {
                        movingEntity.activateCollision();
                    }
                }

            }
        };
    }

    /**
     *Updates the bomb controller.
     @param o The observable object.
     @param arg The argument.
     */
    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof AnimationMessages) {
            BombModel bombModel;

            switch((AnimationMessages) arg) {

                case REPAINT_GAME -> {
                    bombModel = (BombModel) o;
                    repaint(bombModel);
                }
                case REMOVE_ELEMENT -> {
                    bombModel = (BombModel) o;
                    bombModel.explode();
                    entities.remove(bombModel);
                    repaint(bombModel);
                }

            }
        }

    }

}

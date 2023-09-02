package Controllers;

import Animation.AnimationMessages;
import Bomb.Bomb;
import Controllers.ControllersGerarchy.CollectionOfEntitiesController;
import enemy.Enemy;
import main.GameView;
import Bomberman.Player;

import java.awt.*;
import java.util.Observable;

public class BombController extends CollectionOfEntitiesController<Bomb> {

    private static BombController instance = null;

    public static BombController getInstance() {
        if (instance == null) {
            instance = new BombController();
        }
        return instance;
    }

    private BombController () {
        super(AssetManager.getInstance().getBombs());

        collisionChecker = movingEntity -> {
            for (Bomb bomb : entities) {

                if (movingEntity instanceof Player) {
                    if (CollisionDetector.checkcollisionBetweenMovingentityAndOtherEntity(movingEntity, bomb) && bomb.isPassedInto()) {
                        movingEntity.activateCollision();

                    }

                    if (!CollisionDetector.checkcollisionBetweenMovingentityAndOtherEntity(movingEntity, bomb) && !bomb.isPassedInto() && movingEntity.equals(bomb.getPlayer())) {
                        bomb.hasPassedInto();
                    }
                }

                if (movingEntity instanceof Enemy && CollisionDetector.checkcollisionBetweenMovingentityAndOtherEntity(movingEntity, bomb)) {
                     Rectangle intersection = bomb.getBounds().union(movingEntity.getBounds());
                     if(intersection.height * intersection.width == (GameView.tileSize*GameView.tileSize)*2) {
                         movingEntity.activateCollision();
                     }
                }

            }


        };
    }


    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof AnimationMessages) {
            Bomb bomb;

            switch((AnimationMessages) arg) {

                case REPAINT_GAME -> {
                    bomb = (Bomb) o;
                    repaint(bomb);
                }
                case REMOVE_ELEMENT -> {
                    bomb = (Bomb) o;
                    bomb.explode();
                    entities.remove(bomb);
                    repaint(bomb);
                }

            }
        }



    }
}

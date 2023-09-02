package enemy;

import Controllers.AssetManager;
import Controllers.AudioManager;
import Controllers.CollisionDetector;
import Controllers.EnemyController;
import entityGerarchy.MovingAliveEntityState;
import main.GameView;
import Tile.Map;

import java.util.Observable;

public class Puropen extends Enemy{

    public Puropen(int row, int col) {
        super(col * GameView.tileSize, row * GameView.tileSize + GameView.tileSize / 2, 2, GameView.tileSize, GameView.tileSize + GameView.tileSize / 2, 0, 24, "res/Enemies/Puropen", 0, 10, 1, EnemyController.getInstance());

        movingInstructions = () -> {
            if(getState() == MovingAliveEntityState.ALIVE) {
                if(!isMoving()) {

                    CollisionDetector.checkCollisionWithTiles(this, AssetManager.getInstance().getMap());

                    if(isCollision()) {

                        changeRandomlyDirection(AssetManager.getInstance().getMap());
                        deActivateCollision();

                    }

                    setMoving();


                }else {

                    collisionDetector.checkCollisionWhenMoving(this);

                    if(isCollision()) {
                        stopMoving();
                        changeRandomlyDirection(AssetManager.getInstance().getMap());
                    }

                    else moveOneBlock();

                }
            }
            animate.updateAnimation();

        };

    }



    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }

    @Override
    public int getPoints() {
        return 100;
    }

}

package enemy;

import Controllers.AssetManager;
import Controllers.CollisionDetector;
import Controllers.EnemyController;
import EntityModelGerarchy.Direction;
import EntityModelGerarchy.MovingAliveEntityState;
import main.GameView;

/**
 * The Puropen class represents a specific type of enemy in the Bomberman game. Puropen enemies have 1 life and a moderate speed.
 * They change direction at intersections and are part of the enemy hierarchy, extending the Enemy class.
 *
 * @Author Daniele Marchetilli
 * @see EnemyModel
 */
public class Puropen extends EnemyModel {

    /**
     * Creates a Puropen enemy at the specified row and column coordinates on the game map.
     *
     * @param row The row coordinate for the Puropen spawn point.
     * @param col The column coordinate for the Puropen spawn point.
     */
    public Puropen(int row, int col) {
        super(col * GameView.tileSize, row * GameView.tileSize + GameView.tileSize / 2, 2, GameView.tileSize,
                GameView.tileSize + GameView.tileSize / 2, 0, 24, "res/Enemies/Puropen", 0, 10, 1, EnemyController.getInstance());

        // Define moving instructions for Puropen behavior
        movingInstructions = () -> {
            if (getState() == MovingAliveEntityState.ALIVE) {
                if (!isMoving()) {

                    CollisionDetector.checkCollisionWithTiles(this, AssetManager.getInstance().getMap());

                    AssetManager.getInstance().getEnemies().forEach(enemy -> {
                        if(CollisionDetector.checkCollisionBasedOnTheGrid(this, enemy)) activateCollision();
                    });

                    if (isCollision()) {
                        Direction oldDirection = direction;
                        changeRandomlyDirection(AssetManager.getInstance().getMap());
                        if (!direction.equals(oldDirection)) deActivateCollision();
                    }

                    setMoving();

                } else {

                    collisionDetector.checkCollisionWhenMoving(this);

                    if (isCollision()) {
                        stopMoving();
                        changeRandomlyDirection(AssetManager.getInstance().getMap());

                    } else moveOneBlock();

                }
            }
        };

        movingThread.setMovingInstructions(movingInstructions);

        movingThread.startThread();
        animatedEntityThread.startThread();
    }

    /**
     * Get the points awarded for defeating a Puropen enemy.
     *
     * @return The points awarded for defeating a Puropen enemy (100 points).
     */
    @Override
    public int getPoints() {
        return 100;
    }
}


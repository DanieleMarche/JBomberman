package enemy;

import Animation.ReversedLoopAnimation;
import Controllers.AssetManager;
import Controllers.CollisionDetector;
import Controllers.EnemyController;
import EntityModelGerarchy.Direction;
import EntityModelGerarchy.MovingAliveEntityState;
import main.GameView;
import Tile.Map;

/**
 * This class defines an Enemy called Denkyus that has 2 lifes and can decide to change direction at every cross with
 * more than 2 free ways.
 *
 * @Author Daniele Marchetilli
 */
public class Denkyus extends EnemyModel {

    /**The animations directory path */
    private static final String animationPath = "res/Enemies/denkyu";


    public Denkyus(int row, int col) {
        super(col * GameView.tileSize, row * GameView.tileSize + GameView.tileSize / 2, 1, GameView.tileSize, GameView.tileSize + GameView.tileSize / 2, 0, 24, animationPath, 0, 10, 2, EnemyController.getInstance());

        //Sets the animations to reversed loop animation
        frontLoopAnimation = new ReversedLoopAnimation(frontLoopAnimation, this);
        backLoopAnimation = new ReversedLoopAnimation(backLoopAnimation, this);
        leftSideLoopAnimation = new ReversedLoopAnimation(leftSideLoopAnimation, this);
        rightSideLoopAnimation = new ReversedLoopAnimation(rightSideLoopAnimation, this);

        //defines its moving instructions
        movingInstructions = () -> {
            Map map = AssetManager.getInstance().getMap();
            if(getState() == MovingAliveEntityState.ALIVE || getState() == MovingAliveEntityState.INVULNERABILITY) {

                if(getState() == MovingAliveEntityState.INVULNERABILITY) {
                    invulnerabilityTimeCounter++;
                    if(invulnerabilityTimeCounter == invulnerabilityTime) {
                        state = MovingAliveEntityState.ALIVE;
                        invulnerabilityTimeCounter = 0;
                    }
                }

                if(!isMoving()) {

                    CollisionDetector.checkCollisionWithTiles(this, map);

                    if(isCollision()) {

                        Direction oldDirection = direction;

                        changeRandomlyDirection(map);

                        if(!direction.equals(oldDirection)) deActivateCollision();

                    }

                    if(CollisionDetector.getFreeSurroundings(this, map).length > 2) {
                        changeRandomlyDirection(map);
                    }

                    setMoving();

                }else {

                    collisionDetector.checkCollisionWhenMoving(this);

                    if(isCollision()) {
                        stopMoving();
                        changeRandomlyDirection(map);
                    } else moveOneBlock();

                }
            }

        };

        movingThread.setMovingInstructions(movingInstructions);

        movingThread.startThread();
        animatedEntityThread.startThread();

    }


    /**
     * This function if denkyus is not in the invulnerability state decrease its lifes or chenge its status to dying.
     */
    @Override
    public void die() {
        if (state != MovingAliveEntityState.INVULNERABILITY) {
            if(lives == 0) {
                state = MovingAliveEntityState.DYING;
                currentLoopAnimation = damageLoopAnimation;
            } else {
                lives--;
                state = MovingAliveEntityState.INVULNERABILITY;
            }

        }

    }

    /**
     * return the points of this Scored
     * @return the points of this scored
     */
    @Override
    public int getPoints() {
        return 200;
    }
}

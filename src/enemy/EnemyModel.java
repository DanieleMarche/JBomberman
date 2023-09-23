package enemy;

import Animation.AnimationMessages;
import Animation.CycledAnimation;
import Controllers.ControllersGerarchy.BombController;
import Controllers.*;
import Utils.MethodUtils;
import EntityModelGerarchy.Direction;
import EntityModelGerarchy.MovingAliveEntityState;
import EntityModelGerarchy.MovingLiveEntity;
import main.GameView;
import main.Scored;
import Tile.Map;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Author: [Your Name]
 *
 * The Enemy class represents an abstract entity in the Bomberman game that can move and interact with the environment.
 * It extends the MovingLiveEntity class and implements the Scored interface.
 * Enemy entities have a certain number of lives, can become invulnerable for a short time, and can change their direction.
 *
 * @see MovingLiveEntity
 * @see Scored
 */
public abstract class EnemyModel extends MovingLiveEntity implements Scored {

    /**
     * The number of lives of the enemy.
     */
    protected int lives;

    /**
     * The duration of invulnerability time when the enemy is damaged.
     */
    protected static int invulnerabilityTime = 120;

    /**
     * Counter to track the remaining invulnerability time.
     */
    protected int invulnerabilityTimeCounter;

    /**
     * Creates an Enemy instance with the specified attributes.
     *
     * @param worldPositionX       The X-coordinate of the enemy's position in the game world.
     * @param worldPositionY       The Y-coordinate of the enemy's position in the game world.
     * @param speed               The movement speed of the enemy.
     * @param width               The width of the enemy's sprite.
     * @param height              The height of the enemy's sprite.
     * @param solidAreaDefaultX   The default X-coordinate for the solid area.
     * @param solidAreaDefaultY   The default Y-coordinate for the solid area.
     * @param directoryName       The directory path where the enemy's sprites are located.
     * @param defautlSpriteNum    The default sprite number for animation.
     * @param animationSpeed      The speed of animation.
     * @param lifes               The initial number of lives for the enemy.
     * @param observer            The observer to notify when the enemy's state changes.
     */
    public EnemyModel(int worldPositionX, int worldPositionY, int speed, int width, int height,
                      int solidAreaDefaultX, int solidAreaDefaultY, String directoryName,
                      int defautlSpriteNum, int animationSpeed, int lifes, Observer observer) {
        super(worldPositionX, worldPositionY, speed, height, width, solidAreaDefaultX, solidAreaDefaultY,
                GameView.tileSize, GameView.tileSize, directoryName, defautlSpriteNum, animationSpeed);

        this.lives = lifes;
        state = MovingAliveEntityState.ALIVE;
        animate = () -> {
            currentDisplayingImage = currentLoopAnimation.getCurrentImage();
            currentLoopAnimation.setNextSprite();
        };

        collisionDetector.addCollisionCheckerWhenMoving(BombController.getInstance());
        collisionDetector.addCollisionCheckerWhenMoving(FlameController.getInstance());

        animatedEntityThread.setAnimate(animate);

        damageLoopAnimation = new CycledAnimation("res/Enemies/enemy-dying", 0, 3, this, 1);
        invulnerabilityTimeCounter = 0;

        addObserver(observer);
    }

    /**
     * Checks if an enemy is present at the specified row and column coordinates.
     *
     * @param row The row coordinate to check.
     * @param col The column coordinate to check.
     * @param e   The list of enemies to check against.
     * @return true if an enemy is present at the specified coordinates; otherwise, false.
     */
    public static boolean checkEnemyPresence(int row, int col, ArrayList<EnemyModel> e) {
        for (EnemyModel enemyModel : e) {
            if (enemyModel.getCol() == col && enemyModel.getRow() == row) return true;
        }
        return false;
    }

    /**
     * Handles the enemy's death by reducing lives or transitioning to a dying state.
     */
    public void die() {
        if (lives < 1) {
            AudioManager.getInstance().play("res/sounds/Super_Bomberman_Sound_Effects/enemy-dies_1.wav");
            state = MovingAliveEntityState.DYING;
            currentLoopAnimation = damageLoopAnimation;
            height = spritesHeight = damageLoopAnimation.getCurrentImage().getHeight();
            width = spritesWidth = damageLoopAnimation.getCurrentImage().getWidth();
            worldPositionX -= 3;
            worldPositionY -= (height - GameView.originalTileSize) * GameView.tileScale;
        } else {
            lives--;
        }
    }

    /**
     * Changes the direction of the enemy randomly, considering available free surroundings on the map.
     *
     * @param map The game map where the enemy is located.
     */
    public void changeRandomlyDirection(Map map) {
        Direction futureDirection = direction;
        do {
            Direction[] possibleDirections = CollisionDetector.getFreeSurroundings(this, map);
            if (possibleDirections.length == 0) break;
            futureDirection = MethodUtils.pickRandomElement(possibleDirections);
        } while (futureDirection == this.getDirection());
        setDirection(futureDirection);
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg == AnimationMessages.REMOVE_ELEMENT) animatedEntityThread.stopThread();
        setChanged();
        notifyObservers(arg);
    }
}


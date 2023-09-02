package enemy;

import Animation.CycledAnimation;
import Bomberman.Player;
import Controllers.*;
import Utils.MethodUtils;
import entityGerarchy.Direction;
import entityGerarchy.MovingAliveEntityState;
import entityGerarchy.MovingLiveEntity;
import main.GameView;
import main.Scored;
import Tile.Map;

import java.util.ArrayList;
import java.util.Observer;

public abstract class Enemy extends MovingLiveEntity implements Scored {

    protected boolean collided;

    protected boolean directionChanged;
    protected int lives;

    protected static int invulnerabilityTime = 120;

    protected int invulnerabilityTimeCounter;

    public Enemy(int worldPositionX, int worldPositionY, int speed, int width, int height, int solidAreaDefaultX, int solidAreaDefaultY, String directoryName, int defautlSpriteNum, int animationSpeed, int lifes, Observer observer) {
        super(worldPositionX, worldPositionY, speed, height, width, solidAreaDefaultX, solidAreaDefaultY, GameView.tileSize, GameView.tileSize, directoryName, defautlSpriteNum, animationSpeed);
        collided = false;
        this.lives = lifes;
        state = MovingAliveEntityState.ALIVE;
        animate = () -> {
            updateCallCounter++;
            if(updateCallCounter % currentLoopAnimation.getAnimationSpeed() == 0) {
                currentLoopAnimation.setNextSprite();
                updateCallCounter = 0;
            }

        };

        collisionDetector.addCollisionCheckerWhenMoving(BombController.getInstance());
        collisionDetector.addCollisionCheckerWhenMoving(FlameController.getInstance());



        damageLoopAnimation = new CycledAnimation("res/Enemies/enemy-dying", 0, 6, this, 1);
        invulnerabilityTimeCounter = 0;
        addObserver(observer);
    }

    public static boolean checkEnemyPresence(int row, int col, ArrayList<Enemy> e) {
        for (Enemy enemy:
             e) {
            if(enemy.getCol() == col && enemy.getRow() == row) return true;
        }

        return false;
    }


    public void die() {
        if(lives < 1) {
            AudioManager.getInstance().play("res/sounds/Super_Bomberman_Sound_Effects/enemy-dies_1.wav");
            state = MovingAliveEntityState.DYING;
            currentLoopAnimation = damageLoopAnimation;
            height = spritesHeight = damageLoopAnimation.getCurrentImage().getHeight();
            width = spritesWidth = damageLoopAnimation.getCurrentImage().getWidth();
            worldPositionX -= 3;
            worldPositionY -= (height - GameView.originalTileSize ) * GameView.tileScale;
        } else{
            lives --;
        }

    }

    public boolean isDirectionChanged() {
        return directionChanged;
    }

    public void setDirectionChanged(boolean directionChanged) {
        this.directionChanged = directionChanged;
    }

    public boolean isCollided() {
        return collided;
    }

    public void setCollided(boolean collided) {
        this.collided = collided;
    }

    public void changeRandomlyDirection(Map map) {
        Direction futureDirection = direction;

        do{
            Direction[] possibleDirections = CollisionDetector.getFreeSurroundings(this, map);
            if(possibleDirections.length == 0) break;
            futureDirection = MethodUtils.pickRandomElement(possibleDirections);

        }while(futureDirection == this.getDirection());
        setDirection(futureDirection);
    }

    public void goBack() {
        switch(direction) {
            case UP -> setDirection(Direction.DOWN);
            case DOWN -> setDirection(Direction.UP);
            case LEFT -> setDirection(Direction.RIGHT);
            case RIGHT -> setDirection(Direction.LEFT);
        }
    }

}

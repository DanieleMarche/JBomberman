package entityGerarchy;

import Animation.*;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class MovingEntity extends Entity{
    protected int speed;

    protected Animation currentAnimation;

    protected Animation frontAnimation;
    protected Animation backAnimation;
    protected Animation rightSideAnimation;
    protected Animation leftSideAnimation;
    protected Animation deathAnimation;

    protected int spritesWidth;
    protected int spritesHeight;

    protected boolean collisionOn;

    protected Direction direction;

    public MovingEntity(int worldPositionX, int worldPositionY, int speed,int width, int height, int solidAreaDefaultX, int solidAreaDefaultY, String directoryName, String filesName, int numSprites, int defaultSprite) {
        super(worldPositionX, worldPositionY, width, height, solidAreaDefaultX, solidAreaDefaultY);
        this.speed = speed;
        ArrayList<BufferedImage> a = new ArrayList<>();

        frontAnimation = new Animation("/" + directoryName + "/front/" + filesName + "_front_0", 3, 1);
        backAnimation = new Animation("/" + directoryName + "/back/" + filesName + "_back_0", 3, 1);
        rightSideAnimation = new Animation("/" + directoryName + "/right/" + filesName+ "_right_0", 3, 1);
        leftSideAnimation = new Animation("/" + directoryName + "/left/" + filesName+ "_left_0", 3, 1);

        currentAnimation = frontAnimation;

        spritesWidth = frontAnimation.getCurrentImage().getWidth();
        spritesHeight = frontAnimation.getCurrentImage().getHeight();

    }

    public void setDirection(Direction direction) {
        this.direction = direction;
        updateCurrentAnimation();
    }

    public Direction getDirection() {
        return direction;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isCollisionOn() {
        return collisionOn;
    }

    public void deActivateCollision() {
        collisionOn = false;
    }

    public void activateCollision() {
        collisionOn = true;
    }


    public void moveUp() {
        worldPositionY -= speed;
        setChanged();
        notifyObservers();
    }

    public void moveDown() {
        worldPositionY += speed;
        setChanged();
        notifyObservers();
    }

    public void moveLeft() {
        worldPositionX -= speed;
        setChanged();
        notifyObservers();
    }

    public void moveRight() {
        worldPositionX += speed;
        setChanged();
        notifyObservers();
    }

    public void updateCurrentAnimation() {
        switch(direction) {
            case UP -> currentAnimation = backAnimation;
            case DOWN -> currentAnimation = frontAnimation;
            case LEFT -> currentAnimation = leftSideAnimation;
            case RIGHT ->  currentAnimation = rightSideAnimation;
        }
    }

    public Animation getCurrentAnimation() {
        return currentAnimation;
    }

    public Animation getFrontAnimation() {
        return frontAnimation;
    }

    public Animation getBackAnimation() {
        return backAnimation;
    }

    public Animation getRightSideAnimation() {
        return rightSideAnimation;
    }

    public Animation getLeftSideAnimation() {
        return leftSideAnimation;
    }

    public int getRow() {
        return (worldPositionY + solidArea.y) / GamePanel.tileSize;
    }

    public int getCol(){
        return (worldPositionX + solidArea.x) / GamePanel.tileSize;
    }

    public Animation getDeathAnimation() {
        return deathAnimation;
    }

    public void increaseSpeed() {
        speed++;
    }
}


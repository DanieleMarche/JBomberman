package entityGerarchy;

import Animation.*;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Observer;

public abstract class MovingEntity extends Entity implements Observer{
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

    public MovingEntity(int worldPositionX, int worldPositionY, int speed,int width, int height, int solidAreaDefaultX, int solidAreaDefaultY, String directoryName, int defautlSpriteNum, int animationSpeed) {
        super(worldPositionX, worldPositionY, width, height, solidAreaDefaultX, solidAreaDefaultY);
        this.speed = speed;

        frontAnimation = new Animation(directoryName + "/front", defautlSpriteNum, animationSpeed, this);
        backAnimation = new Animation(directoryName + "/back", defautlSpriteNum, animationSpeed, this);
        rightSideAnimation = new Animation(directoryName + "/right", defautlSpriteNum, animationSpeed, this);
        leftSideAnimation = new Animation(directoryName + "/left", defautlSpriteNum, animationSpeed, this);

        currentAnimation = frontAnimation;

        spritesWidth = frontAnimation.getCurrentImage().getWidth();
        spritesHeight = frontAnimation.getCurrentImage().getHeight();

    }

    public void setDirection(Direction direction) {
        this.direction = direction;
        updateCurrentAnimation();
    }

    public int getSpritesWidth() {
        return spritesWidth;
    }

    public int getSpritesHeight() {
        return spritesHeight;
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
        notifyObservers(AnimationMessages.REPAINT);
    }

    public void moveDown() {
        worldPositionY += speed;
        setChanged();
        notifyObservers(AnimationMessages.REPAINT);
    }

    public void moveLeft() {
        worldPositionX -= speed;
        setChanged();
        notifyObservers(AnimationMessages.REPAINT);
    }

    public void moveRight() {
        worldPositionX += speed;
        setChanged();
        notifyObservers(AnimationMessages.REPAINT);
    }

    public void updateCurrentAnimation() {
        switch(direction) {
            case UP -> currentAnimation = backAnimation;
            case DOWN -> currentAnimation = frontAnimation;
            case LEFT -> currentAnimation = leftSideAnimation;
            case RIGHT ->  currentAnimation = rightSideAnimation;
        }
        setChanged();
        notifyObservers(AnimationMessages.REPAINT);
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
        return (getBounds().y) / GamePanel.tileSize;
    }

    public int getCol(){
        return (getBounds().x) / GamePanel.tileSize;
    }

    public Animation getDeathAnimation() {
        return deathAnimation;
    }

    public void increaseSpeed() {
        speed++;
    }
}


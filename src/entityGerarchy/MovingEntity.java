package entityGerarchy;

import Animation.*;
import Controllers.CollisionDetector;
import main.GameView;

import java.awt.*;
import java.util.Observer;

public abstract class MovingEntity extends Entity implements Observer{
    protected int speed;

    protected LoopAnimation currentLoopAnimation;

    public Animate animate;

    protected LoopAnimation frontLoopAnimation;
    protected LoopAnimation backLoopAnimation;
    protected LoopAnimation rightSideLoopAnimation;
    protected LoopAnimation leftSideLoopAnimation;
    protected LoopAnimation damageLoopAnimation;

    protected int spritesWidth;
    protected int spritesHeight;

    protected boolean collision;
    protected Direction direction;
    protected boolean moving;
    private int pixelCounter;

    protected int updateCallCounter;

    protected MovingInstructions movingInstructions;

    protected CollisionDetector collisionDetector;

    public MovingEntity(int worldPositionX, int worldPositionY, int speed, int height, int width, int solidAreaDefaultX, int solidAreaDefaultY, int solidAreaHeight, int solidAreaWidth, String directoryName, int defautlSpriteNum, int animationSpeed) {
        super(worldPositionX, worldPositionY, height, width, solidAreaHeight, solidAreaWidth, solidAreaDefaultX, solidAreaDefaultY);
        this.speed = speed;

        frontLoopAnimation = new LoopAnimation(directoryName + "/front", defautlSpriteNum, animationSpeed, this);
        backLoopAnimation = new LoopAnimation(directoryName + "/back", defautlSpriteNum, animationSpeed, this);
        rightSideLoopAnimation = new LoopAnimation(directoryName + "/right", defautlSpriteNum, animationSpeed, this);
        leftSideLoopAnimation = new LoopAnimation(directoryName + "/left", defautlSpriteNum, animationSpeed, this);

        currentLoopAnimation = frontLoopAnimation;

        direction = Direction.DOWN;

        spritesWidth = frontLoopAnimation.getCurrentImage().getWidth();
        spritesHeight = frontLoopAnimation.getCurrentImage().getHeight();

        collisionDetector = new CollisionDetector();

        movingInstructions = () -> {};

        moving = false;
        pixelCounter = 0;
        updateCallCounter = 0;

    }

    public MovingInstructions getMovingInstructions() {
        return movingInstructions;
    }

    public void decreasePixelCounter() {pixelCounter -= speed;}

    public void increasePixelCounter () {
        pixelCounter += speed;
    }

    public void resetPixelCounter() {
        pixelCounter = 0;
    }

    public int getPixelCounter() {
        return pixelCounter;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
        updateCurrentAnimation();
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving() {
        moving = true;
    }

    public void stopMoving() {
        moving = false;
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

    public boolean isCollision() {
        return collision;
    }
    public void deActivateCollision() {
        collision = false;
    }

    public void activateCollision() {
        collision = true;
    }


    public void moveUp() {
        worldPositionY -= speed;
        setChanged();
        notifyObservers(AnimationMessages.REPAINT_GAME);
    }

    public void moveDown() {
        worldPositionY += speed;
        setChanged();
        notifyObservers(AnimationMessages.REPAINT_GAME);
    }

    public void moveLeft() {
        worldPositionX -= speed;
        setChanged();
        notifyObservers(AnimationMessages.REPAINT_GAME);
    }

    public void moveRight() {
        worldPositionX += speed;
        setChanged();
        notifyObservers(AnimationMessages.REPAINT_GAME);
    }

    protected void moveOneBlock() {

        increasePixelCounter();

        move();

        if(getPixelCounter() == GameView.tileSize) {
            stopMoving();
            resetPixelCounter();
        }

    }

    protected void moveBack() {
        decreasePixelCounter();

        move();

        if(getPixelCounter() == 0) {
            stopMoving();
            resetPixelCounter();
        }
    }

    protected void move() {
        if (!isCollision()) {
            switch (getDirection()) {
                case UP -> moveUp();
                case DOWN -> moveDown();
                case LEFT -> moveLeft();
                case RIGHT -> moveRight();
            }
        }
    }

    public void updateCurrentAnimation() {
        switch(direction) {
            case UP -> currentLoopAnimation = backLoopAnimation;
            case DOWN -> currentLoopAnimation = frontLoopAnimation;
            case LEFT -> currentLoopAnimation = leftSideLoopAnimation;
            case RIGHT ->  currentLoopAnimation = rightSideLoopAnimation;
        }
        setChanged();
        notifyObservers(AnimationMessages.REPAINT_GAME);
    }

    public LoopAnimation getCurrentAnimation() {
        return currentLoopAnimation;
    }
    public int getRow() {
        return (getBounds().y - GameView.topBarHeight + GameView.tileSize / 2) / GameView.tileSize;
    }
    public int getCol(){
        return (getBounds().x + GameView.tileSize / 2) / GameView.tileSize;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(currentLoopAnimation.getCurrentImage(), worldPositionX, worldPositionY, spritesWidth * GameView.tileScale, spritesHeight * GameView.tileScale, null);
    }
}


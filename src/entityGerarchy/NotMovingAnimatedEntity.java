package entityGerarchy;

import main.GamePanel;

import java.util.Observer;

import Animation.*;

public abstract class NotMovingAnimatedEntity extends Entity implements Observer {

    protected Animation animation;

    public Animate animate;

    protected int updateCallCounter;

    public NotMovingAnimatedEntity(int worldPositionX, int worldPositionY, int rectangleWidth, int rectangleHeight, int solidAreaDefaultX, int solidAreaDefaultY, String directoryPath, int animationSpeed, Observer o) {
        super(worldPositionX, worldPositionY, rectangleWidth, rectangleHeight, solidAreaDefaultX, solidAreaDefaultY);
        animation = new Animation(directoryPath, animationSpeed, this);
        updateCallCounter = 0;
        addObserver(o);

        animate = () -> {
            updateCallCounter++;

            if(updateCallCounter % animation.getAnimationSpeed() == 0) {
                animation.setNextSprite();
                updateCallCounter = 0;
            }

        };


    }

    public NotMovingAnimatedEntity(int worldPositionX, int worldPositionY, int rectangleWidth, int rectangleHeight, int solidAreaDefaultX, int solidAreaDefaultY, Animation animation, Observer o) {
        super(worldPositionX, worldPositionY, rectangleWidth, rectangleHeight, solidAreaDefaultX, solidAreaDefaultY);
        this.animation = animation;
        addObserver(o);

    }
    public Animation getAnimation() {
        return animation;
    }

    @Override
    public int getRow() {
        return getBounds().y / GamePanel.tileSize;
    }

    @Override
    public int getCol() {
        return getBounds().x / GamePanel.tileSize;
    }
}

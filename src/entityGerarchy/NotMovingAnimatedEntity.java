package entityGerarchy;

import main.GameView;

import java.util.Observer;

import Animation.*;

public abstract class NotMovingAnimatedEntity extends Entity implements Observer {

    protected LoopAnimation loopAnimation;

    public Animate animate;

    protected int updateCallCounter;

    public NotMovingAnimatedEntity(int worldPositionX, int worldPositionY, String directoryPath, int animationSpeed, Observer o) {
        super(worldPositionX, worldPositionY);
        loopAnimation = new LoopAnimation(directoryPath, animationSpeed, this);
        updateCallCounter = 0;
        addObserver(o);

        animate = () -> {
            updateCallCounter++;

            if(updateCallCounter % loopAnimation.getAnimationSpeed() == 0) {
                loopAnimation.setNextSprite();
                updateCallCounter = 0;
            }

        };


    }

    public NotMovingAnimatedEntity(int worldPositionX, int worldPositionY, int height, int width, int rectangleWidth, int rectangleHeight, int solidAreaDefaultX, int solidAreaDefaultY, LoopAnimation loopAnimation, Observer o) {
        super(worldPositionX, worldPositionY, height, width, rectangleHeight,rectangleWidth , solidAreaDefaultX, solidAreaDefaultY);
        this.loopAnimation = loopAnimation;
        updateCallCounter = 0;
        addObserver(o);
        animate = () -> {
            updateCallCounter++;

            if(updateCallCounter % loopAnimation.getAnimationSpeed() == 0) {
                loopAnimation.setNextSprite();
                updateCallCounter = 0;
            }

        };

    }

    public NotMovingAnimatedEntity(int worldPositionX, int worldPositionY, int height, int width, int rectangleWidth, int rectangleHeight, int solidAreaDefaultX, int solidAreaDefaultY, String animationPath, int defaultSpriteNum, int animationSpeed,  Observer o) {
        super(worldPositionX, worldPositionY, height, width, rectangleHeight,rectangleWidth , solidAreaDefaultX, solidAreaDefaultY);
        this.loopAnimation = new LoopAnimation(animationPath, animationSpeed, this);;
        addObserver(o);

        updateCallCounter = 0;
        animate = () -> {
            updateCallCounter++;

            if(updateCallCounter % loopAnimation.getAnimationSpeed() == 0) {
                loopAnimation.setNextSprite();
                updateCallCounter = 0;
            }

        };

    }
    public LoopAnimation getAnimation() {
        return loopAnimation;
    }

    @Override
    public int getRow() {
        return (getBounds().y - GameView.topBarHeight) / GameView.tileSize;
    }

    @Override
    public int getCol() {
        return getBounds().x / GameView.tileSize;
    }
}

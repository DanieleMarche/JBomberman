package EntityModelGerarchy;

import main.GameView;

import java.util.Observable;
import java.util.Observer;

import Animation.*;
import main.Pausable;
import Thread.*;

/**
 * The NotMovingAnimatedEntity class represents an animated entity that does not move on its own.
 * It extends the Entity class and can be used for entities with static animations.
 *
 * @Author Daniele Marchetilli
 */
public abstract class NotMovingAnimatedEntity extends Entity implements Observer, Pausable {

    protected AnimatedEntityThread animatedEntityThread;

    protected LoopAnimation loopAnimation;

    public Animate animate;

    public NotMovingAnimatedEntity(int worldPositionX, int worldPositionY, String directoryPath, int animationSpeed, Observer o) {
        super(worldPositionX, worldPositionY);
        loopAnimation = new LoopAnimation(directoryPath, animationSpeed, this);
        currentDisplayingImage = loopAnimation.getCurrentImage();

        animate = () -> {
            currentDisplayingImage = loopAnimation.getCurrentImage();
            loopAnimation.setNextSprite();
        };

        animatedEntityThread = new AnimatedEntityThread(loopAnimation, animate);
        visible = true;
        addObserver(o);

    }

    public NotMovingAnimatedEntity(int worldPositionX, int worldPositionY, int height, int width, int rectangleWidth, int rectangleHeight, int solidAreaDefaultX, int solidAreaDefaultY, String animationPath, int defaultSpriteNum, int animationSpeed,  Observer o) {
        super(worldPositionX, worldPositionY, height, width, rectangleHeight,rectangleWidth , solidAreaDefaultX, solidAreaDefaultY);
        this.loopAnimation = new LoopAnimation(animationPath, animationSpeed, this);

        animate = () -> {
            currentDisplayingImage = loopAnimation.getCurrentImage();
            loopAnimation.setNextSprite();
        };

        animatedEntityThread = new AnimatedEntityThread(loopAnimation, animate);

        addObserver(o);

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

    public void pause() {
        animatedEntityThread.pause();
    }

    public void resume() {
        animatedEntityThread.resume();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg == AnimationMessages.REMOVE_ELEMENT) {
            animatedEntityThread.stopThread();
        }
        setChanged();
        notifyObservers(arg);
    }
}

package Animation;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import static Utils.ImageUtils.loadPNGsFromDirectory;

/**
 * The LoopAnimation class represents a basic animation that cycles through a list of
 * BufferedImages in a loop. It provides functionality to manage the animation, including
 * changing frames, setting default frames, and more.
 *
 * This class extends the Observable class, allowing it to notify its observers when the
 * animation changes.
 *
 * @author Daniele
 *
 */
public class LoopAnimation extends Observable {

    /** The list of BufferedImages representing the animation frames. */
    protected ArrayList<BufferedImage> sprites;

    /** The total number of frames in the animation. */
    protected final int numSprites;

    /** The current index of the sprite being displayed. */
    protected int currentSpriteIndex;

    /** The default sprite number to start the animation. */
    protected int defaultSpriteNum;

    /** The speed at which the animation should play. This number indicate after how many update call should this
     * animation change sprite. */
    protected int animationSpeed;

    protected Animate instructions;

    /**
     * Constructs a LoopAnimation with a list of sprites, animation speed, and an observer.
     *
     * @param sprites          The list of BufferedImages representing animation frames.
     * @param animationSpeed   The speed at which the animation should play.
     * @param o                The Observer object to notify when the animation changes.
     */
    public LoopAnimation(ArrayList<BufferedImage> sprites, int animationSpeed, Observer o) {
        this.sprites = sprites;
        this.animationSpeed = animationSpeed;
        numSprites = sprites.size();
        currentSpriteIndex = 0;
        defaultSpriteNum = 0;
        addObserver(o);
    }

    /**
     * Constructs a LoopAnimation with a list of sprites, default sprite number, animation speed, and an observer.
     *
     * @param sprites          The list of BufferedImages representing animation frames.
     * @param defaultSpriteNum The default sprite number to start the animation.
     * @param animationSpeed   The speed at which the animation should play.
     * @param o                The Observer object to notify when the animation changes.
     */
    public LoopAnimation(ArrayList<BufferedImage> sprites, int defaultSpriteNum, int animationSpeed, Observer o) {
        this.sprites = sprites;
        this.animationSpeed = animationSpeed;
        numSprites = sprites.size();
        currentSpriteIndex = 0;
        this.defaultSpriteNum = defaultSpriteNum;
        addObserver(o);
    }

    /**
     * Constructs a LoopAnimation with a directory path, default sprite number, animation speed, and an observer.
     * It loads sprites from the specified directory.
     *
     * @param directoryPath    The path to the directory containing animation sprites.
     * @param defaultSpriteNum The default sprite number to start the animation.
     * @param animationSpeed   The speed at which the animation should play.
     * @param o                The Observer object to notify when the animation changes.
     */
    public LoopAnimation(String directoryPath, int defaultSpriteNum, int animationSpeed, Observer o) {
        sprites = (ArrayList<BufferedImage>) loadPNGsFromDirectory(directoryPath);
        this.animationSpeed = animationSpeed;
        numSprites = sprites.size();
        currentSpriteIndex = 0;
        this.defaultSpriteNum = defaultSpriteNum;
        addObserver(o);
    }

    /**
     * Constructs a LoopAnimation with a directory path, animation speed, and an observer.
     * It loads sprites from the specified directory.
     *
     * @param directoryPath    The path to the directory containing animation sprites.
     * @param animationSpeed   The speed at which the animation should play.
     * @param o                The Observer object to notify when the animation changes.
     */
    public LoopAnimation(String directoryPath, int animationSpeed, Observer o) {
        sprites = (ArrayList<BufferedImage>) loadPNGsFromDirectory(directoryPath);
        this.animationSpeed = animationSpeed;
        numSprites = sprites.size();
        currentSpriteIndex = 0;
        addObserver(o);
    }

    /**
     * Gets the animation speed.
     *
     * @return The animation speed.
     */
    public int getAnimationSpeed() {
        return animationSpeed;
    }

    /**
     * Gets the list of BufferedImages representing animation frames.
     *
     * @return The list of BufferedImages representing animation frames.
     */
    public ArrayList<BufferedImage> getSprites() {
        return sprites;
    }

    /**
     * Gets the index of the default sprite.
     *
     * @return The index of the default sprite.
     */
    public int getDefaultSpriteIndex() {
        return defaultSpriteNum;
    }

    /**
     * Gets the current BufferedImage representing the current frame of the animation.
     *
     * @return The current BufferedImage representing the current frame of the animation.
     */
    public BufferedImage getCurrentImage() {
        return sprites.get(currentSpriteIndex);
    }

    /**
     * Gets the index of the current sprite.
     *
     * @return The index of the current sprite.
     */
    public int getCurrentSpriteIndex() {
        return currentSpriteIndex;
    }

    /**
     * Sets the index of the current sprite to the next number. If the next number
     * exceeds the index bounds, it is set to the first frame.
     */
    public void setNextSprite() {
        if (currentSpriteIndex == numSprites - 1) {
            currentSpriteIndex = 0;
        } else {
            currentSpriteIndex += 1;
        }
        setChanged();
        notifyObservers(AnimationMessages.REPAINT_GAME);
    }

    /**
     * Sets the current sprite index to the default sprite number specified when
     * constructing the Animation instance.
     */
    public void setDefaultSprite() {
        currentSpriteIndex = defaultSpriteNum;
        setChanged();
        notifyObservers(AnimationMessages.REPAINT_GAME);
    }

    /**
     * Sets the current sprite index to the previous number. If the current index is
     * already at 0, it sets it to the last index of the array of images.
     */
    public void setPreviousSprite() {
        if (currentSpriteIndex == 0) {
            currentSpriteIndex = numSprites - 1;
        } else {
            currentSpriteIndex -= 1;
        }
        setChanged();
        notifyObservers(AnimationMessages.REPAINT_GAME);
    }


}



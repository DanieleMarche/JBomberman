package Animation;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Observer;

/**
 * The CycledAnimation class represents an animation that cycles through a list of
 * BufferedImages for a specified number of cycles and then sends a message to the observer to remove the entity which
 * uses the animation.
 * It extends the LoopAnimation class and provides additional functionality for cycling animations.
 *
 * @author Daniele Marchetilli
 *
 */
public class CycledAnimation extends LoopAnimation {

    /** The number of cycles remaining for this animation. */
    protected int cycles;

    /**
     * Constructs a CycledAnimation with a list of sprites, animation speed, observer, and cycles.
     *
     * @param sprites          The list of BufferedImages representing animation frames.
     * @param animationSpeed   The speed at which the animation should play.
     * @param o                The Observer object to notify when the animation changes.
     * @param cycles           The number of times the animation should repeat.
     */
    public CycledAnimation(ArrayList<BufferedImage> sprites, int animationSpeed, Observer o, int cycles) {
        super(sprites, animationSpeed, o);
        this.cycles = cycles;
    }

    /**
     * Constructs a CycledAnimation with a list of sprites, default sprite number, animation speed,
     * observer, and cycles.
     *
     * @param sprites          The list of BufferedImages representing animation frames.
     * @param defaultSpriteNum The default sprite number to start the animation.
     * @param animationSpeed   The speed at which the animation should play.
     * @param o                The Observer object to notify when the animation changes.
     * @param cycles           The number of times the animation should repeat.
     */
    public CycledAnimation(ArrayList<BufferedImage> sprites, int defaultSpriteNum, int animationSpeed, Observer o, int cycles) {
        super(sprites, defaultSpriteNum, animationSpeed, o);
        this.cycles = cycles;
    }

    /**
     * Constructs a CycledAnimation with a directory path, default sprite number, animation speed,
     * observer, and cycles. It loads sprites from the specified directory.
     *
     * @param directoryPath    The path to the directory containing animation sprites.
     * @param defaultSpriteNum The default sprite number to start the animation.
     * @param animationSpeed   The speed at which the animation should play.
     * @param o                The Observer object to notify when the animation changes.
     * @param cycles           The number of times the animation should repeat.
     */
    public CycledAnimation(String directoryPath, int defaultSpriteNum, int animationSpeed, Observer o, int cycles) {
        super(directoryPath, defaultSpriteNum, animationSpeed, o);
        this.cycles = cycles;
    }

    /**
     * Constructs a CycledAnimation with a directory path, animation speed, observer, and cycles.
     * It loads sprites from the specified directory.
     *
     * @param directoryPath    The path to the directory containing animation sprites.
     * @param animationSpeed   The speed at which the animation should play.
     * @param o                The Observer object to notify when the animation changes.
     * @param cycles           The number of times the animation should repeat.
     */
    public CycledAnimation(String directoryPath, int animationSpeed, Observer o, int cycles) {
        super(directoryPath, animationSpeed, o);
        this.cycles = cycles;
    }

    /**
     * Sets the next sprite in the animation sequence. If the current sprite index reaches the end
     * of the list, it updates the number of cycles and repeats the animation if necessary.
     */
    @Override
    public void setNextSprite() {
        setChanged();
        if (currentSpriteIndex == numSprites - 1) {
            cycles -= 1;
            if (cycles == 0) {
                notifyObservers(AnimationMessages.REMOVE_ELEMENT);
                return;
            }
            currentSpriteIndex = 0;
        } else {
            currentSpriteIndex += 1;
        }
        notifyObservers(AnimationMessages.REPAINT_GAME);
    }
}

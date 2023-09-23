package Animation;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observer;

/**
 * The ReversedLoopAnimation class represents an animation that plays in a loop, but every cicle it reverses the order
 * of its frames.
 * It extends the LoopAnimation class and provides the functionality to reverse the animation frames.
 *
 * @author Daniele Marchetilli
 *
 */
public class ReversedLoopAnimation extends LoopAnimation {

    /**
     * Constructs a ReversedLoopAnimation with a list of sprites, animation speed, and an observer.
     *
     * @param sprites          The list of BufferedImages representing animation frames.
     * @param animationSpeed   The speed at which the animation should play.
     * @param o                The Observer object to notify when the animation changes.
     */
    public ReversedLoopAnimation(ArrayList<BufferedImage> sprites, int animationSpeed, Observer o) {
        super(sprites, animationSpeed, o);
    }

    /**
     * Constructs a ReversedLoopAnimation with a list of sprites, default sprite number, animation speed,
     * and an observer.
     *
     * @param sprites          The list of BufferedImages representing animation frames.
     * @param defaultSpriteNum The default sprite number to start the animation.
     * @param animationSpeed   The speed at which the animation should play.
     * @param o                The Observer object to notify when the animation changes.
     */
    public ReversedLoopAnimation(ArrayList<BufferedImage> sprites, int defaultSpriteNum, int animationSpeed, Observer o) {
        super(sprites, defaultSpriteNum, animationSpeed, o);
    }

    /**
     * Constructs a ReversedLoopAnimation with a directory path, default sprite number, animation speed,
     * and an observer. It loads sprites from the specified directory.
     *
     * @param directoryPath    The path to the directory containing animation sprites.
     * @param defaultSpriteNum The default sprite number to start the animation.
     * @param animationSpeed   The speed at which the animation should play.
     * @param o                The Observer object to notify when the animation changes.
     */
    public ReversedLoopAnimation(String directoryPath, int defaultSpriteNum, int animationSpeed, Observer o) {
        super(directoryPath, defaultSpriteNum, animationSpeed, o);
    }

    /**
     * Constructs a ReversedLoopAnimation with a directory path, animation speed, and an observer.
     * It loads sprites from the specified directory.
     *
     * @param directoryPath    The path to the directory containing animation sprites.
     * @param animationSpeed   The speed at which the animation should play.
     * @param o                The Observer object to notify when the animation changes.
     */
    public ReversedLoopAnimation(String directoryPath, int animationSpeed, Observer o) {
        super(directoryPath, animationSpeed, o);
    }

    /**
     * Constructs a ReversedLoopAnimation from an existing LoopAnimation and an observer.
     * It reverses the animation frames of the provided animation.
     *
     * @param animation The LoopAnimation to create the ReversedLoopAnimation from.
     * @param o         The Observer object to notify when the animation changes.
     */
    public ReversedLoopAnimation(LoopAnimation animation, Observer o) {
        super(animation.getSprites(), 0, 10, o);
    }

    /**
     * Sets the next sprite in the animation sequence. If the current sprite index reaches the end
     * of the list, it reverses the animation frames and resets the current sprite index to 1.
     */
    @Override
    public void setNextSprite() {
        if (currentSpriteIndex == numSprites - 1) {
            Collections.reverse(sprites);
            currentSpriteIndex = 1;
        } else {
            currentSpriteIndex += 1;
        }
        setChanged();
        notifyObservers(AnimationMessages.REPAINT_GAME);
    }
}

package Animation;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observer;
/**
 * The CycledReversedAnimation class represents an animation that cycles through a list of
 * BufferedImages first in normal order and then in reversed order and so on.
 * When the animation is repeated for the specified number of cycles it sens a message to its observer to remove the
 * entity which uses this animation.
 * It extends the CycledAnimation class and provides the functionality to reverse the animation frames.
 *
 * @author Daniele Marchetilli
 *
 */
public class CycledReversedAnimation extends CycledAnimation {

    /**
     * Constructs a CycledReversedAnimation with a list of sprites, animation speed, observer,
     * and the number of cycles.
     *
     * @param sprites          The list of BufferedImages representing animation frames.
     * @param animationSpeed   The speed at which the animation should play.
     * @param o                The Observer object to notify when the animation changes.
     * @param cycles           The number of times the animation should repeat.
     */
    public CycledReversedAnimation(ArrayList<BufferedImage> sprites, int animationSpeed, Observer o, int cycles) {
        super(sprites, animationSpeed, o, cycles);
    }

    /**
     * Constructs a CycledReversedAnimation with a list of sprites, default sprite number, animation speed,
     * observer, and the number of cycles.
     *
     * @param sprites          The list of BufferedImages representing animation frames.
     * @param defaultSpriteNum The default sprite number to start the animation.
     * @param animationSpeed   The speed at which the animation should play.
     * @param o                The Observer object to notify when the animation changes.
     * @param cycles           The number of times the animation should repeat.
     */
    public CycledReversedAnimation(ArrayList<BufferedImage> sprites, int defaultSpriteNum, int animationSpeed, Observer o, int cycles) {
        super(sprites, defaultSpriteNum, animationSpeed, o, cycles);
    }

    /**
     * Constructs a CycledReversedAnimation with a directory path, default sprite number, animation speed,
     * observer, and the number of cycles. It loads sprites from the specified directory.
     *
     * @param directoryPath    The path to the directory containing animation sprites.
     * @param defaultSpriteNum The default sprite number to start the animation.
     * @param animationSpeed   The speed at which the animation should play.
     * @param o                The Observer object to notify when the animation changes.
     * @param cycles           The number of times the animation should repeat.
     */
    public CycledReversedAnimation(String directoryPath, int defaultSpriteNum, int animationSpeed, Observer o, int cycles) {
        super(directoryPath, defaultSpriteNum, animationSpeed, o, cycles);
    }

    /**
     * Constructs a CycledReversedAnimation with a directory path, animation speed, observer,
     * and the number of cycles. It loads sprites from the specified directory.
     *
     * @param directoryPath    The path to the directory containing animation sprites.
     * @param animationSpeed   The speed at which the animation should play.
     * @param o                The Observer object to notify when the animation changes.
     * @param cycles           The number of times the animation should repeat.
     */
    public CycledReversedAnimation(String directoryPath, int animationSpeed, Observer o, int cycles) {
        super(directoryPath, animationSpeed, o, cycles);
    }

    /**
     * Constructs a CycledReversedAnimation from an existing LoopAnimation, observer, and the number of cycles.
     *
     * @param loopAnimation The LoopAnimation to create the CycledReversedAnimation from.
     * @param observer      The Observer object to notify when the animation changes.
     * @param cycles        The number of times the animation should repeat.
     */
    public CycledReversedAnimation(LoopAnimation loopAnimation, Observer observer, int cycles) {
        super(loopAnimation.getSprites(), loopAnimation.getDefaultSpriteIndex(), loopAnimation.getAnimationSpeed(), observer, cycles);
    }

    /**
     * Sets the next sprite in the animation sequence. If the current sprite index reaches the end
     * of the list, it reverses the animation frames and updates the number of cycles.
     */
    @Override
    public void setNextSprite() {
        setChanged();
        if (currentSpriteIndex == numSprites - 1) {
            Collections.reverse(sprites);
            cycles -= 1;
            if (cycles == 0) {
                notifyObservers(AnimationMessages.REMOVE_ELEMENT);
                return;
            }
            currentSpriteIndex = 1;
        } else {
            currentSpriteIndex++;
        }
        notifyObservers(AnimationMessages.REPAINT_GAME);
    }
}

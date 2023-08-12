package Animation;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observer;

public class CycledReversedAnimation extends CycledAnimation{

    public CycledReversedAnimation(ArrayList<BufferedImage> sprites, int animationSpeed, Observer o, int cycles) {
        super(sprites, animationSpeed, o, cycles);
    }

    public CycledReversedAnimation(ArrayList<BufferedImage> sprites, int defaultSpriteNum, int animationSpeed, Observer o, int cycles) {
        super(sprites, defaultSpriteNum, animationSpeed, o, cycles);
    }

    public CycledReversedAnimation(String directoryPath, int defaultSpriteNum, int animationSpeed, Observer o, int cycles) {
        super(directoryPath, defaultSpriteNum, animationSpeed, o, cycles);
    }

    public CycledReversedAnimation(String directoryPath, int animationSpeed, Observer o, int cycles) {
        super(directoryPath, animationSpeed, o, cycles);
    }

    public CycledReversedAnimation(Animation animation, Observer observer, int cycles) {
        super(animation.getSprites(), animation.getDefaultSpriteNum(), animation.getAnimationSpeed(), observer, cycles);
    }

    @Override
    public void setNextSprite() {

        setChanged();
        if (currentSprite == numSprites - 1) {
            Collections.reverse(sprites);
            cycles -= 1;
            if(cycles == 0) {
                notifyObservers(AnimationMessages.REMOVE);
                return;
            }
            currentSprite = 1;

        } else {
            currentSprite++;
        }

        notifyObservers(AnimationMessages.REPAINT);
    }
}

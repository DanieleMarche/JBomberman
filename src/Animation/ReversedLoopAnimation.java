package Animation;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observer;

public class ReversedLoopAnimation extends LoopAnimation {
    public ReversedLoopAnimation(ArrayList<BufferedImage> sprites, int animationSpeed, Observer o) {
        super(sprites, animationSpeed, o);
    }

    public ReversedLoopAnimation(ArrayList<BufferedImage> sprites, int defaultSpriteNum, int animationSpeed, Observer o) {
        super(sprites, defaultSpriteNum, animationSpeed, o);
    }

    public ReversedLoopAnimation(String directoryPath, int defaultSpriteNum, int animationSpeed, Observer o) {
        super(directoryPath, defaultSpriteNum, animationSpeed, o);
    }

    public ReversedLoopAnimation(String directoryPath, int animationSpeed, Observer o) {
        super(directoryPath, animationSpeed, o);
    }

    public ReversedLoopAnimation(LoopAnimation animation, Observer o) {
        super(animation.sprites,0, 10, o);
    }

    @Override
    public void setNextSprite() {
        if (currentSprite == numSprites - 1) {
            Collections.reverse(sprites);
            currentSprite = 1;
        } else {
            currentSprite += 1;
        }
        setChanged();
        notifyObservers(AnimationMessages.REPAINT_GAME);
    }
}

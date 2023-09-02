package Animation;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Observer;

public class CycledAnimation extends LoopAnimation {

    protected int cycles;

    public CycledAnimation(ArrayList<BufferedImage> sprites, int animationSpeed, Observer o, int cycles) {
        super(sprites, animationSpeed, o);
        this.cycles = cycles;
    }

    public CycledAnimation(ArrayList<BufferedImage> sprites, int defaultSpriteNum, int animationSpeed, Observer o, int cycles) {
        super(sprites, defaultSpriteNum, animationSpeed, o);
        this.cycles = cycles;
    }

    public CycledAnimation(String directoryPath, int defaultSpriteNum, int animationSpeed, Observer o, int cycles) {
        super(directoryPath, defaultSpriteNum, animationSpeed, o);
        this.cycles = cycles;
    }

    public CycledAnimation(String directoryPath, int animationSpeed, Observer o, int cycles) {
        super(directoryPath, animationSpeed, o);
        this.cycles = cycles;
    }

    @Override
    public void setNextSprite() {

        setChanged();
        if (currentSprite == numSprites - 1) {
            cycles -= 1;
            if(cycles == 0) {
                notifyObservers(AnimationMessages.REMOVE_ELEMENT);
                return;
            }
            currentSprite = 0;
        } else {
            currentSprite += 1;
        }
        notifyObservers(AnimationMessages.REPAINT_GAME);

    }

}

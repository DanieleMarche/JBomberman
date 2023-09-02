package Animation;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import static Utils.ImageUtils.loadPNGsFromDirectory;

public class LoopAnimation extends Observable {

    protected ArrayList<BufferedImage> sprites;
    protected final int numSprites;
    protected int currentSprite;
    protected int defaultSpriteNum;
    protected int animationSpeed;

    public LoopAnimation(ArrayList<BufferedImage> sprites, int animationSpeed, Observer o) {
        this.sprites = sprites;
        this.animationSpeed = animationSpeed;
        numSprites = sprites.size();
        currentSprite = 0;
        defaultSpriteNum = 0;
        addObserver(o);
    }

    public LoopAnimation(ArrayList<BufferedImage> sprites, int defaultSpriteNum, int animationSpeed, Observer o) {
        this.sprites = sprites;
        this.animationSpeed = animationSpeed;
        numSprites = sprites.size();
        currentSprite = 0;
        this.defaultSpriteNum = defaultSpriteNum;
        addObserver(o);
    }

    public LoopAnimation(String directoryPath, int defaultSpriteNum, int animationSpeed, Observer o) {
        sprites = (ArrayList<BufferedImage>) loadPNGsFromDirectory(directoryPath);
        this.animationSpeed = animationSpeed;
        numSprites = sprites.size();
        currentSprite = 0;
        this.defaultSpriteNum = defaultSpriteNum;
        addObserver(o);
    }

    public LoopAnimation(String directoryPath, int animationSpeed, Observer o) {
        sprites = (ArrayList<BufferedImage>) loadPNGsFromDirectory(directoryPath);
        this.animationSpeed = animationSpeed;
        numSprites = sprites.size();
        currentSprite = 0;
        addObserver(o);
    }

    public int getAnimationSpeed() {
        return animationSpeed;
    }

    public ArrayList<BufferedImage> getSprites() {
        return sprites;
    }

    public int getDefaultSpriteIndex() {
        return defaultSpriteNum;
    }

    public BufferedImage getCurrentImage() {
        return sprites.get(currentSprite);
    }

    public int getCurrentSpriteIndex() {
        return currentSprite;
    }

    public void setNextSprite() {
        if (currentSprite == numSprites - 1) {
            currentSprite = 0;
        } else {
            currentSprite += 1;
        }
        setChanged();
        notifyObservers(AnimationMessages.REPAINT_GAME);
    }

    public void setDefaultSprite() {
        currentSprite = defaultSpriteNum;
        setChanged();
        notifyObservers(AnimationMessages.REPAINT_GAME);
    }

    public void setPreviousSprite() {
        if (currentSprite == 0) {
            currentSprite = numSprites - 1;
        } else {
            currentSprite -= 1;
        }
        setChanged();
        notifyObservers(AnimationMessages.REPAINT_GAME);
    }
}


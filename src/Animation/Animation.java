package Animation;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Stream;

import static Utils.ImageUtils.loadPNGsFromDirectory;

public class Animation extends Observable {

    protected ArrayList<BufferedImage> sprites;
    protected final int numSprites;
    protected int currentSprite;
    protected int defaultSpriteNum;
    protected int animationSpeed;

    public Animation(ArrayList<BufferedImage> sprites, int animationSpeed, Observer o) {
        this.sprites = sprites;
        this.animationSpeed = animationSpeed;
        numSprites = sprites.size();
        currentSprite = 0;
        defaultSpriteNum = 0;
        addObserver(o);
    }

    public Animation(ArrayList<BufferedImage> sprites, int defaultSpriteNum, int animationSpeed, Observer o) {
        this.sprites = sprites;
        this.animationSpeed = animationSpeed;
        numSprites = sprites.size();
        currentSprite = 0;
        this.defaultSpriteNum = defaultSpriteNum;
        addObserver(o);
    }

    public Animation(String directoryPath, int defaultSpriteNum, int animationSpeed, Observer o) {
        sprites = (ArrayList<BufferedImage>) loadPNGsFromDirectory(directoryPath);
        this.animationSpeed = animationSpeed;
        numSprites = sprites.size();
        currentSprite = 0;
        this.defaultSpriteNum = defaultSpriteNum;
        addObserver(o);
    }

    public Animation(String directoryPath, int animationSpeed, Observer o) {
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

    public int getNumSprites() {
        return numSprites;
    }

    public int getDefaultSpriteNum() {
        return defaultSpriteNum;
    }

    public BufferedImage getCurrentImage() {
        return sprites.get(currentSprite);
    }

    public int getCurrentSprite() {
        return currentSprite;
    }

    public boolean isLastSprite() {
        return currentSprite == numSprites - 1;
    }

    public boolean isFirstSprite() {
        return currentSprite == 0;
    }

    public void setNextSprite() {
        if (currentSprite == numSprites - 1) {
            currentSprite = 0;
        } else {
            currentSprite += 1;
        }
        setChanged();
        notifyObservers(AnimationMessages.REPAINT);
    }

    public void setDefaultSprite() {
        currentSprite = defaultSpriteNum;
        setChanged();
        notifyObservers(AnimationMessages.REPAINT);
    }

    public void setPreviousSprite() {
        if (currentSprite == 0) {
            currentSprite = numSprites - 1;
        } else {
            currentSprite -= 1;
        }
        setChanged();
        notifyObservers(AnimationMessages.REPAINT);
    }
}


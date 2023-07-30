package Animation;

import entityGerarchy.Animate;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Animation extends Sprites implements Animate {

    private int currentSprite;
    private final int defaultSpriteNum;

    public Animation(ArrayList<BufferedImage> sprites, int defaultSpriteNum) {
        super(sprites);
        this.defaultSpriteNum = defaultSpriteNum;
        currentSprite = 0;

    }

    public Animation (String path, int numSprites, int defaultSpriteNum) {
        super(path, numSprites);
        this.defaultSpriteNum = defaultSpriteNum;
    }

    public BufferedImage getCurrentImage() {
        return sprites.get(currentSprite);
    }

    public int getCurrentSprite() {
        return currentSprite;
    }

    public int getDefaultSpriteNum() {
        return defaultSpriteNum;
    }

    public boolean isLastSprite() {
        return currentSprite == sprites.size() - 1;
    }

    @Override
    public void setNextSprite() {
        if(currentSprite == numSprites - 1) currentSprite = 0;
        else currentSprite += 1;
        setChanged();
        notifyObservers();

    }

    @Override
    public void setDefaultSprite() {
        currentSprite = defaultSpriteNum;
        setChanged();
        notifyObservers();
    }

    @Override
    public void setPreviousSprite() {
        if(currentSprite == 0) currentSprite = numSprites - 1;
        else currentSprite -= 1;
        setChanged();
        notifyObservers();
    }

}

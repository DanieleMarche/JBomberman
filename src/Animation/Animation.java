package Animation;

import entityGerarchy.Animate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

public class Animation extends Sprites implements Animate {

    private final int numSprites;
    private int currSprite;
    private final int defaultSpriteNum;

    public Animation(ArrayList<BufferedImage> sprites, int defaultSpriteNum) {
        super(sprites);
        this.defaultSpriteNum = defaultSpriteNum;
        numSprites = sprites.size();
        currSprite = 0;

    }

    public Animation (String path, int numSprites, int defaultSpriteNum) {
        super(path, numSprites);
        this.numSprites = numSprites;
        this.defaultSpriteNum = defaultSpriteNum;
    }

    public int getNumSprites() {
        return numSprites;
    }

    public BufferedImage getCurrentImage() {
        return sprites.get(currSprite);
    }

    public int getCurrSprite() {
        return currSprite;
    }

    @Override
    public void setNextSprite() {
        if(currSprite == numSprites - 1) currSprite = 0;
        else currSprite += 1;
        setChanged();
        notifyObservers();

    }

    @Override
    public void setDefaultSprite() {
        currSprite = defaultSpriteNum;
        setChanged();
        notifyObservers();
    }

    @Override
    public void setPreviousSprite() {
        if(currSprite == 0) currSprite = numSprites - 1;
        else currSprite -= 1;
        setChanged();
        notifyObservers();
    }

}

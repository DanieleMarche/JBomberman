package tile;
import Animation.*;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class AnimatedMutlipleSpritesTile extends Tile {

    protected ArrayList<Animation> animations;
    protected int numSprite;

    public AnimatedMutlipleSpritesTile(boolean collision, boolean destructible, boolean getOnFire, int numSprite) {
        super(collision, destructible, getOnFire);
        animations = new ArrayList<>();
        this.numSprite = numSprite;
    }

    public ArrayList<Animation> getAnimations() {
        return animations;
    }

    public Animation getAnimation(int index) {
        return animations.get(index);
    };

}

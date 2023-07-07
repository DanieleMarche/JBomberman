package tile;

import Animation.Animation;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DestructibleBlock extends AnimatedMutlipleSpritesTile{

    public static ArrayList<DestructibleBlock> destructibleBlocks = new ArrayList<>();

    private Animation currentAnimation;

    private final int x;
    private final int y;

    private boolean exploded;

    public static DestructibleBlock getIstance(int x, int y, int numAnimation) {
        for(DestructibleBlock db: destructibleBlocks){
            if(db.x == x && db.y == y) {
                return db;
            }
        }
        DestructibleBlock db = new DestructibleBlock(x, y, numAnimation);
        destructibleBlocks.add(db);
        return db;
    }

    private DestructibleBlock(int x, int y, int numAnimation) {
        super(true, true, false, 4);
        this.y = y;
        this.x = x;



        exploded = false;

        animations.add(new Animation("/Blocks/destructable_block" + "/" + "destructable_block" + "_0", 4, 0));
        animations.add(new Animation("/Blocks/destructable_block/with_shadow" + "/" + "destructible_block_shadow" + "_0", 4, 0));
        animations.add(new Animation("/Explosion/destructible_block_explosion/destructible_block_explosion_0", 6, 0));

        for(Animation a: animations) {
            a.addObserver(TileManager.instance);
        }

        currentAnimation = animations.get(numAnimation);

        collision = true;
        destructible = true;
        getOnFire = true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isExploded() {
        return exploded;
    }

    public void explode() {
        currentAnimation = animations.get(2);
        exploded = true;
    }

    public Animation getCurrentAnimation() {
        return currentAnimation;
    }

    public BufferedImage getSprite() {
        return currentAnimation.getCurrentImage();
    }

}

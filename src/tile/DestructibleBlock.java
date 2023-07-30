package tile;

import Animation.Animation;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DestructibleBlock extends AnimatedMutlipleSpritesTile{

    public static ArrayList<DestructibleBlock> destructibleBlocks = new ArrayList<>();

    private Animation currentAnimation;

    private final int col;
    private final int row;

    private boolean exploded;

    public static DestructibleBlock getIstance(int col, int row, int numAnimation) {
        for(DestructibleBlock db: destructibleBlocks){
            if(db.col == col && db.row == row) {
                return db;
            }
        }
        DestructibleBlock db = new DestructibleBlock(col, row, numAnimation);
        destructibleBlocks.add(db);
        return db;
    }

    private DestructibleBlock(int col, int row, int numAnimation) {
        super(true, true, false, 4);
        this.row = row;
        this.col = col;

        exploded = false;

        animations.add(new Animation("/Blocks/destructable_block" + "/" + "destructable_block" + "_0", 4, 0));
        animations.add(new Animation("/Blocks/destructable_block/with_shadow" + "/" + "destructible_block_shadow" + "_0", 4, 0));
        animations.add(new Animation("/Explosion/destructible_block_explosion/destructible_block_explosion_0", 6, 5));

        for(Animation a: animations) {
            a.addObserver(TileManager.instance);
        }

        currentAnimation = animations.get(numAnimation);

        collision = true;
        destructible = true;
        getOnFire = true;
    }

    public static boolean exist(int col, int row) {
        for (DestructibleBlock db : destructibleBlocks) {
            if (db.getCol() == col && db.getRow() == row) {
                return true;
            }
        }
        return false;
    }

    public static void removeDestructibleBlock(int col, int row) {
        destructibleBlocks.removeIf(db -> db.getCol() == col && db.getRow() == row);
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
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

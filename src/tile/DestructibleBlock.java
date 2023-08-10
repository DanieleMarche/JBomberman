package tile;

import Animation.CycledAnimation;
import tile.tileGerarchy.AnimatedTile;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


public class DestructibleBlock extends AnimatedTile {

    public static ArrayList<DestructibleBlock> destructibleBlocks = new ArrayList<>();

    private boolean exploded;
    public DestructibleBlock(int row, int col, boolean imageType, Observer observer) {
        super(TileType.WALKABLE_BLOCK, row, col, true, true, true, findPath(imageType));

        exploded = false;

        destructibleBlocks.add(this);
        addObserver(observer);

    }

    public static String findPath(boolean type) {

        if(type) return "res/Blocks/destructable_block/with_shadow";

        return "res/Blocks/destructable_block";

    }

    public static void removeDestructibleBlock(DestructibleBlock destructibleBlock) {
        destructibleBlocks.remove(destructibleBlock);
    }

    public boolean isExploded() {
        return exploded;
    }

    public void explode() {
        exploded = true;
        animation = new CycledAnimation("res/Explosion/Destructible_block_explosion", 10, this, 1);
    }

    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }
}

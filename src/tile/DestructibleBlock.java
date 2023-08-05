package tile;

import entityGerarchy.NotMovingAnimatedEntity;
import main.GamePanel;

import java.awt.*;
import java.util.ArrayList;

public class DestructibleBlock extends AnimatedTile {

    public static ArrayList<DestructibleBlock> destructibleBlocks = new ArrayList<>();

    private boolean exploded;

    public DestructibleBlock(int row, int col, boolean imageType) {
        super(row, col, true, true, true, findPath(imageType), 4);

        exploded = false;

        destructibleBlocks.add(this);

    }

    public static String findPath(boolean type) {

        if(type) {
            return "/Blocks/destructable_block/with_shadow" + "/" + "destructible_block_shadow" + "_0";
        }
        return "/Blocks/destructable_block" + "/" + "destructable_block" + "_0";

    }

    public boolean isExploded() {
        return exploded;
    }

    public void explode() {
        exploded = true;
    }

}

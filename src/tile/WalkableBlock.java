package tile;

import Animation.Sprites;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class WalkableBlock extends NotAnimatedMutlipleSpriteTile {

    private static final String directoryPath = "/Blocks/walkable_block";
    private static final String imageNames = "walkable_block";
    public static WalkableBlock walkableBlock = null;

    public static WalkableBlock getIstance () {
        if(walkableBlock == null) {

            walkableBlock = new WalkableBlock();

        }
        return walkableBlock;
    }

    private WalkableBlock () {

        super(false, false, true);

        sprites = new Sprites(directoryPath + "/" + imageNames + "_0", 3);

    }

    @Override
    public BufferedImage getImage(int index) {
        return sprites.getImage(index);
    }
}

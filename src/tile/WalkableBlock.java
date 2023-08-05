package tile;

import Animation.Sprites;
import entityGerarchy.NotMovingEntity;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class WalkableBlock extends SingleImageTile {

    private static final String directoryPath = "/Blocks/walkable_block/walkable_block_0";

    public WalkableBlock (int row, int col, boolean isUnderLimitSolidOrDestructibleBlocks) {

        super(row, col, false, false, true, findImagePath(isUnderLimitSolidOrDestructibleBlocks));

    }

    public WalkableBlock (int row, int col) {

        super(row, col, false, false, true, directoryPath + "1.png");

    }

    private static String findImagePath(boolean isUnderLimitSolidOrDestructibleBlocks) {
        if(isUnderLimitSolidOrDestructibleBlocks) {
            return directoryPath + "2.png";
        }else {
            return directoryPath + "3.png";
        }
    }
}

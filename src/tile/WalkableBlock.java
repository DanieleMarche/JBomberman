package tile;

import Animation.Sprites;
import entityGerarchy.NotMovingEntity;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class WalkableBlock extends NotMovingEntity {

    private static final String directoryPath = "/Blocks/walkable_block/walkable_block_0";

    public WalkableBlock (int positionX, int positionY, boolean isUnderLimitSolidOrDestructibleBlocks) {

        super(positionX, positionY, findImagePath(isUnderLimitSolidOrDestructibleBlocks));

    }

    public WalkableBlock (int positionX, int positionY) {

        super(positionX, positionY, directoryPath + "1.png");

    }

    private static String findImagePath(boolean isUnderLimitSolidOrDestructibleBlocks) {
        if(isUnderLimitSolidOrDestructibleBlocks) {
            return directoryPath + "2.png";
        }else {
            return directoryPath + "3.png";
        }
    }

    @Override
    public BufferedImage getImage(int index) {
        return sprites.getImage(index);
    }
}

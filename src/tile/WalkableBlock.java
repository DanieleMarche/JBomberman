package tile;
import tile.tileGerarchy.SingleImageTile;

public class WalkableBlock extends SingleImageTile {

    private static final String directoryPath = "res/Blocks/walkable_block/walkable_block_0";

    public WalkableBlock (int row, int col, boolean isUnderLimitSolidOrDestructibleBlocks) {

        super(TileType.WALKABLE_BLOCK, row, col, false, false, true, findImagePath(isUnderLimitSolidOrDestructibleBlocks));

    }

    public WalkableBlock (int row, int col) {

        super(TileType.WALKABLE_BLOCK, row, col, false, false, true, directoryPath + "1.png");

    }

    private static String findImagePath(boolean isUnderLimitSolidOrDestructibleBlocks) {
        if(isUnderLimitSolidOrDestructibleBlocks) {
            return directoryPath + "2.png";
        }else {
            return directoryPath + "3.png";
        }
    }
}

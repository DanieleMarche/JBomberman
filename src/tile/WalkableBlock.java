package tile;
import Utils.ImageUtils;
import tile.tileGerarchy.SingleImageTile;

public class WalkableBlock extends SingleImageTile {

    private static final String directoryPath = "res/Blocks/walkable_block/walkable_block_0";

    public WalkableBlock(int row, int col, WalkableBlockType walkableBlockType) {

        super(TileType.WALKABLE_BLOCK, row, col, false, false, true, assignImage(walkableBlockType));

    }

    public static void reassignType(Map map, WalkableBlock walkableBlock) {
        int row = walkableBlock.row;
        int col = walkableBlock.col;

        switch (map.getTile(row - 1, col).getTileType()) {
            case WALKABLE_BLOCK -> walkableBlock.image = ImageUtils.loadImage(directoryPath + "1.png");
            case DESTRUCTIBLE_BLOCK -> walkableBlock.image = ImageUtils.loadImage(directoryPath + "3.png");
            case LIMIT_BLOCK, SOLID_BLOCK -> walkableBlock.image = ImageUtils.loadImage(directoryPath + "2.png");
        }

    }

    private static String assignImage(WalkableBlockType walkableBlockType) {
        switch (walkableBlockType) {
            case UNDER_LIMIT_OR_SOLID_BLOCK -> {
                return directoryPath + "2.png";
            }
            case UNDER_DESTRUCTIBLE_BLOCK -> {
                return directoryPath + "3.png";
            }
            default -> {
                return directoryPath + "1.png";
            }
        }
    }


enum WalkableBlockType{
    UNDER_LIMIT_OR_SOLID_BLOCK,
    UNDER_DESTRUCTIBLE_BLOCK,
    UNDER_WALKABLE_BLOCK;
    }
}

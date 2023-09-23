package Tile;
import Tile.tileGerarchy.TileType;
import Utils.ImageUtils;
import Tile.tileGerarchy.SingleImageTile;


/**
 * The `WalkableBlock` class represents a walkable block tile in the game map. Walkable blocks can have different appearances
 * based on their type and the tiles above them.
 *
 * @Author Daniele Marchetilli
 */
public class WalkableBlock extends SingleImageTile {

    private static final String directoryPath = "res/Blocks/walkable_block/walkable_block_0";

    public WalkableBlock(int row, int col, WalkableBlockType walkableBlockType) {

        super(TileType.WALKABLE_BLOCK, row, col, assignImage(walkableBlockType));

    }

    public static void reassignType(Map map, WalkableBlock walkableBlock) {
        int row = walkableBlock.getRow();
        int col = walkableBlock.getCol();

        switch (map.getTile(row - 1, col).getTileType()) {
            case WALKABLE_BLOCK -> walkableBlock.currentDisplayingImage = ImageUtils.loadImage(directoryPath + "1.png");
            case DESTRUCTIBLE_BLOCK -> walkableBlock.currentDisplayingImage = ImageUtils.loadImage(directoryPath + "3.png");
            case LIMIT_BLOCK, SOLID_BLOCK -> walkableBlock.currentDisplayingImage = ImageUtils.loadImage(directoryPath + "2.png");
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

    /**
     * This function looks at the map matrix to decide what WalkableBlock to instantiate.
     * @param row The row of the tile to replace.
     * @param col The row of the tile to replace.
     * @return The new WalkableBlock Tile.
     */
    public static WalkableBlock getWalkableBlock(Map map, int row, int col) {
        WalkableBlock walkableBlock;
        if(map.getMap()[row - 1] [col] instanceof DestructibleBlock ) walkableBlock = new WalkableBlock(row, col, WalkableBlock.WalkableBlockType.UNDER_DESTRUCTIBLE_BLOCK);
        else if(map.getMapTileType(row - 1, col) == TileType.LIMIT_BLOCK || map.getMapTileType(row - 1, col) == TileType.SOLID_BLOCK) walkableBlock = new WalkableBlock(row, col, WalkableBlock.WalkableBlockType.UNDER_LIMIT_OR_SOLID_BLOCK);
        else walkableBlock = new WalkableBlock(row, col, WalkableBlock.WalkableBlockType.UNDER_WALKABLE_BLOCK);
        return walkableBlock;
    }

    /**
     * The `WalkableBlockType` enum defines different types of walkable blocks based on their appearance and the tiles above them.
     */
enum WalkableBlockType{
    UNDER_LIMIT_OR_SOLID_BLOCK,
    UNDER_DESTRUCTIBLE_BLOCK,
    UNDER_WALKABLE_BLOCK;
    }
}

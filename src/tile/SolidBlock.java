package tile;

import tile.tileGerarchy.SingleImageTile;

public class SolidBlock extends SingleImageTile {

    private static final String imagePath = "res/Blocks/solid_block.png";

    SolidBlock(int row, int col) {
        super(TileType.SOLID_BLOCK, row, col, true, true, false, imagePath);
    }
}

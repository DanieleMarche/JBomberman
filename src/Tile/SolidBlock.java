package Tile;

import Tile.tileGerarchy.SingleImageTile;
import Tile.tileGerarchy.TileType;

/**
 * The `SolidBlock` class represents solid blocks in the game, which cannot be destroyed or traversed by players or bombs.
 * These blocks are typically used to create boundaries and obstacles within the game map.
 *
 * @Author Daniele Marchetilli
 */
public class SolidBlock extends SingleImageTile {

    private static final String imagePath = "res/Blocks/solid_block.png";

    SolidBlock(int row, int col) {
        super(TileType.SOLID_BLOCK, row, col, imagePath);
    }
}

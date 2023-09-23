package Tile.tileGerarchy;

import Utils.ImageUtils;
import main.GameView;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The SingleImageTile class represents a tile in the game with a single static image.
 *
 * @Author Daniele Marchetilli
 */
public abstract class SingleImageTile extends Tile {

    /**
     * Constructs a SingleImageTile object with the specified tile type, row, column, and image.
     *
     * @param tileType The type of the tile.
     * @param row      The row index of the tile.
     * @param col      The column index of the tile.
     * @param image    The image associated with the tile.
     */
    public SingleImageTile(TileType tileType, int row, int col, BufferedImage image) {
        super(tileType, row, col);
        this.currentDisplayingImage = image;
    }

    /**
     * Constructs a SingleImageTile object with the specified tile type, row, column, and image path.
     *
     * @param tileType  The type of the tile.
     * @param row       The row index of the tile.
     * @param col       The column index of the tile.
     * @param imagePath The path to the image for this tile.
     */
    public SingleImageTile(TileType tileType, int row, int col, String imagePath) {
        super(tileType, row, col);
        currentDisplayingImage = ImageUtils.loadImage(imagePath);
    }


}


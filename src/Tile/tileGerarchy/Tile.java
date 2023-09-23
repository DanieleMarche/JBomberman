package Tile.tileGerarchy;

import Animation.Drawable;
import EntityModelGerarchy.Entity;
import main.GameView;

/**
 * The Tile class represents a basic element on the game map grid.
 *
 * @Author [Your Name]
 */
public abstract class Tile extends Entity implements Drawable {
    protected TileType tileType;

    /**
     * Constructs a Tile object with the specified tile type, row, and column.
     *
     * @param tileType The type of the tile.
     * @param row      The row index of the tile.
     * @param col      The column index of the tile.
     */
    public Tile(TileType tileType, int row, int col) {
        super(col * GameView.tileSize, row * GameView.tileSize);
        this.tileType = tileType;
        visible = true;
    }

    /**
     * Retrieves the type of this tile.
     *
     * @return The type of the tile.
     */
    public TileType getTileType() {
        return tileType;
    }

    /**
     * Retrieves the column index of this tile on the game map grid.
     *
     * @return The column index of the tile.
     */
    public int getCol() {
        return worldPositionX / GameView.tileSize;
    }

    /**
     * Retrieves the row index of this tile on the game map grid.
     *
     * @return The row index of the tile.
     */
    public int getRow() {
        return (getBounds().y - GameView.topBarHeight) / GameView.tileSize;
    }
}


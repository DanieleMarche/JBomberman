package Tile.tileGerarchy;

import Animation.Drawable;
import main.GameView;
import Tile.TileType;

import java.util.Observable;

public abstract class Tile extends Observable implements Drawable {

    protected TileType tileType;

    protected int col;

    protected int row;

    public Tile(TileType tileType, int row, int col) {
        this.tileType = tileType;
        this.row = row;
        this.col = col;
    }

    public TileType getTileType() {
        return tileType;
    }

    public int getPositionXOnScreen() {
        return col * GameView.tileSize;
    }

    public int getPositionYOnScreen() {
        return (row * GameView.tileSize) + GameView.topBarHeight;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
}

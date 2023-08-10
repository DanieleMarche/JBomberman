package tile.tileGerarchy;

import Animation.Drawable;
import main.GamePanel;
import tile.TileType;

import java.util.Observable;

public abstract class Tile extends Observable implements Drawable {

    protected TileType tileType;
    protected boolean solid;
    protected boolean explodable;
    protected boolean getFire;

    protected int col;

    protected int row;

    public Tile(TileType tileType, int row, int col, boolean solid, boolean explodable, boolean getFire) {
        this.tileType = tileType;
        this.row = row;
        this.col = col;
        this.solid = solid;
        this.explodable = explodable;
        this.getFire = getFire;
    }

    public boolean isSolid() {
        return solid;
    }

    public boolean isExplodable() {
        return explodable;
    }

    public boolean doesGetFire() {
        return getFire;
    }

    public int getPositionXOnScreen() {
        return col * GamePanel.tileSize;
    }

    public int getPositionYOnScreen() {
        return row * GamePanel.tileSize;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
}

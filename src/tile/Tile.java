package tile;

import Animation.Drawable;
import main.GamePanel;

public abstract class Tile implements Drawable {
    protected boolean solid;
    protected boolean explodable;
    protected boolean getFire;

    protected int col;

    protected int row;

    public Tile(int row, int col, boolean solid, boolean explodable, boolean getFire) {
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

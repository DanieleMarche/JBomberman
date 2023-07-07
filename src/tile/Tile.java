package tile;

import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

public abstract class Tile extends Observable {
    protected boolean collision;
    protected boolean destructible;
    protected boolean getOnFire;

    public Tile(boolean collision, boolean destructible, boolean getOnFire) {
        this.collision = collision;
        this.destructible = destructible;
        this.getOnFire = getOnFire;
    }

    public boolean doesGetOnFire() {
        return getOnFire;
    }

    public boolean getCollision() {
        return collision;
    }

    public boolean isDestructible() {
        return destructible;
    }
}

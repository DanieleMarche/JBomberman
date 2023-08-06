package entityGerarchy;

import Animation.Drawable;

import java.awt.*;
import java.util.Observable;

public abstract class Entity extends Observable implements Drawable {

    protected int worldPositionX, worldPositionY;

    protected Rectangle solidArea;

    public Entity(int worldPositionX, int worldPositionY,int rectangleWidth, int rectangleHeight, int solidAreaDefaultX, int solidAreaDefaultY) {
        this.worldPositionX = worldPositionX;
        this.worldPositionY = worldPositionY;
        this.solidArea = new Rectangle(solidAreaDefaultX, solidAreaDefaultY, rectangleWidth, rectangleHeight);
    }

    public abstract int getRow();

    public abstract int getCol();

    public Rectangle getBounds() {
        return new Rectangle(worldPositionX + solidArea.x, worldPositionY + solidArea.y, solidArea.width, solidArea.height);
    }

    public int getWorldPositionX() {
        return worldPositionX;
    }

    public int getWorldPositionY() {
        return worldPositionY;
    }

    public Rectangle getSolidArea() {
        return solidArea;
    }

}

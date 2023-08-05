package entityGerarchy;

import Animation.Drawable;

import java.awt.*;
import java.util.Observable;

public abstract class Entity extends Observable implements Drawable {

    protected int worldPositionX, worldPositionY;

    protected Rectangle solidArea;
    protected  int solidAreaDefaultX, solidAreaDefaultY;

    public Entity(int worldPositionX, int worldPositionY,int rectangleWidth, int rectangleHeight, int solidAreaDefaultX, int solidAreaDefaultY) {
        this.worldPositionX = worldPositionX;
        this.worldPositionY = worldPositionY;
        this.solidArea = new Rectangle(solidAreaDefaultX, solidAreaDefaultY, rectangleWidth, rectangleHeight);
        this.solidAreaDefaultX = solidAreaDefaultX;
        this.solidAreaDefaultY = solidAreaDefaultY;
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

    public int getSolidAreaDefaultX() {
        return solidAreaDefaultX;
    }

    public int getSolidAreaDefaultY() {
        return solidAreaDefaultY;
    }

}

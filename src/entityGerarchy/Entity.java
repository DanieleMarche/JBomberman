package entityGerarchy;

import Animation.Drawable;
import Controllers.CollisionDetector;
import Controllers.ControllersGerarchy.CollisionChecker;
import main.GameView;

import java.awt.*;
import java.util.Observable;

public abstract class Entity extends Observable implements Drawable {

    protected int worldPositionX, worldPositionY;
    protected int height, width;

    /**
     * This property defines the part of the entity that is solid.
     */
    private final Rectangle solidArea;

    protected CollisionChecker collisionChecker;

    /**
     * This contstructor takes only the upper left angle of the entity, and the default height and width are the tile size specified inside the GamePanel
     * @param worldPositionX the upper point of the entity.
     * @param worldPositionY the most left point of the entity.
     */
    public Entity(int worldPositionX, int worldPositionY) {
        this.worldPositionX = worldPositionX;
        this.worldPositionY = worldPositionY + GameView.topBarHeight;
        height = GameView.tileSize;
        width = GameView.tileSize;
        this.solidArea = new Rectangle(0, 0, GameView.tileSize, GameView.tileSize);
        collisionChecker = movingEntity -> {};
    }

    /**
     * This constructior specifies the height and the width of the entity and it supposes that the solid area of this
     * entity is different from the area displayed on the screen, so it must be defined too.
     * @param worldPositionX the upper point of the entity.
     * @param worldPositionY the most left point of the entity.
     * @param height the height of the entity
     * @param width the width of the entity
     * @param solidAreaDefaultX the most left point of the solid area from the worldPositionX point
     * @param solidAreaDefaultY the upper point of the solid area from the worldPositionY point
     */
    public Entity(int worldPositionX, int worldPositionY, int height, int width,  int solidAreaHeight, int solidAreaWidth, int solidAreaDefaultX, int solidAreaDefaultY) {
        this.worldPositionX = worldPositionX;
        this.worldPositionY = worldPositionY + GameView.topBarHeight;
        this.height = height;
        this.width = width;
        this.solidArea = new Rectangle(solidAreaDefaultX, solidAreaDefaultY, solidAreaWidth, solidAreaHeight);
        collisionChecker = movingEntity -> {};
    }

    public CollisionChecker getCollisionChecker() {
        return collisionChecker;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
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

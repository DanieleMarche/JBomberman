package EntityModelGerarchy;

import Animation.AnimationMessages;
import Animation.Drawable;
import main.GameView;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Optional;

public abstract class Entity extends Observable implements Drawable {

    protected int worldPositionX, worldPositionY;
    protected int height, width;

    protected boolean visible;

    protected BufferedImage currentDisplayingImage;

    /**
     * This property defines the part of the entity that is solid inside the sprite.
     */
    private final Rectangle solidArea;

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
        visible = true;
    }


    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public abstract int getRow();

    public abstract int getCol();

    /**
     * This functions calculates the position of the solid area on the game map.
     * @return the solid area of this entity positioned on the map.
     */
    public Rectangle getBounds() {
        return new Rectangle(worldPositionX + solidArea.x, worldPositionY + solidArea.y, solidArea.width, solidArea.height);
    }

    @Override
    public Point getPosition() {
        return new Point(worldPositionX, worldPositionY);
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisiblility(boolean b) {
        visible = b;
        setChanged();
        notifyObservers(AnimationMessages.REPAINT_GAME);
    }

    public Optional<BufferedImage> getImage() {
        if(visible) return Optional.of(currentDisplayingImage);
        return Optional.empty();
    }

}

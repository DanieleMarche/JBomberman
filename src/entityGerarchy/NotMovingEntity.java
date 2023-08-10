package entityGerarchy;

import main.GamePanel;
import java.awt.image.BufferedImage;
import static Utils.ImageUtils.loadImage;

public abstract class NotMovingEntity extends Entity{

    protected BufferedImage image;

    public NotMovingEntity(int worldPositionX, int worldPositionY, int rectangleWidth, int rectangleHeight, int solidAreaDefaultX, int solidAreaDefaultY, String imagePath) {
        super(worldPositionX, worldPositionY, rectangleWidth, rectangleHeight, solidAreaDefaultX, solidAreaDefaultY);
        this.image = loadImage(imagePath);
    }

    public NotMovingEntity(int worldPositionX, int worldPositionY, int rectangleWidth, int rectangleHeight, BufferedImage image) {
        super(worldPositionX, worldPositionY, rectangleWidth, rectangleHeight, worldPositionX, worldPositionY);
        this.image = image;
    }

    @Override
    public int getCol() {
        return worldPositionX / GamePanel.tileSize;
    }

    @Override
    public int getRow() {
        return worldPositionY / GamePanel.tileSize;
    }

    public BufferedImage getImage() {
        return image;
    }

}

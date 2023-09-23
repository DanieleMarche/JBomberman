package EntityModelGerarchy;

import main.GameView;
import java.awt.image.BufferedImage;
import static Utils.ImageUtils.loadImage;

/**
 * This abstract class defines an entity which can't move its position.
 */
public abstract class NotMovingEntity extends Entity{

    public NotMovingEntity(int worldPositionX, int worldPositionY, String imagePath) {
        super(worldPositionX, worldPositionY);
        this.currentDisplayingImage = loadImage(imagePath);
    }

    public NotMovingEntity(int worldPositionX, int worldPositionY, int height, int width, int rectangleWidth, int rectangleHeight, BufferedImage image) {
        super(worldPositionX, worldPositionY, height, width, rectangleHeight, rectangleWidth, worldPositionX, worldPositionY);
        this.currentDisplayingImage = image;
    }

    @Override
    public int getCol() {
        return worldPositionX / GameView.tileSize;
    }

    @Override
    public int getRow() {
        return (worldPositionY - GameView.topBarHeight) / GameView.tileSize ;
    }

}

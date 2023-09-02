package entityGerarchy;

import main.GameView;
import java.awt.image.BufferedImage;
import static Utils.ImageUtils.loadImage;

public abstract class NotMovingEntity extends Entity{

    protected BufferedImage image;

    public NotMovingEntity(int worldPositionX, int worldPositionY, String imagePath) {
        super(worldPositionX, worldPositionY);
        this.image = loadImage(imagePath);
    }

    public NotMovingEntity(int worldPositionX, int worldPositionY, int height, int width, int rectangleWidth, int rectangleHeight, BufferedImage image) {
        super(worldPositionX, worldPositionY, height, width, rectangleHeight, rectangleWidth, worldPositionX, worldPositionY);
        this.image = image;
    }

    @Override
    public int getCol() {
        return worldPositionX / GameView.tileSize;
    }

    @Override
    public int getRow() {
        return (worldPositionY - GameView.topBarHeight) / GameView.tileSize ;
    }

    public BufferedImage getImage() {
        return image;
    }

}

package entityGerarchy;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public abstract class NotMovingEntity extends Entity{

    protected BufferedImage image;

    public NotMovingEntity(int worldPositionX, int worldPositionY, int rectangleWidth, int rectangleHeight, int solidAreaDefaultX, int solidAreaDefaultY, String imagePath) {
        super(worldPositionX, worldPositionY, rectangleWidth, rectangleHeight, solidAreaDefaultX, solidAreaDefaultY);
        try {
            this.image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        }catch(IOException e) {
            e.printStackTrace();
        }

    }
    public BufferedImage getImage() {
        return image;
    }


}

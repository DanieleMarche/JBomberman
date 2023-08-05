package entityGerarchy;

import player.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import Animation.*;

public abstract class NotMovingAnimatedEntity extends Entity {

    protected Animation animation;

    public NotMovingAnimatedEntity(int worldPositionX, int worldPositionY, int rectangleWidth, int rectangleHeight, int solidAreaDefaultX, int solidAreaDefaultY, String imageNotCompletedPath, int numSprites) {
        super(worldPositionX, worldPositionY, rectangleWidth, rectangleHeight, solidAreaDefaultX, solidAreaDefaultY);
        animation = new Animation(imageNotCompletedPath, numSprites, 0);

    }
    public Animation getAnimation() {
        return animation;
    }
}

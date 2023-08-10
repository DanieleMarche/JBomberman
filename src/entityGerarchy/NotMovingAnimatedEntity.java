package entityGerarchy;

import main.GamePanel;
import player.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Observer;

import Animation.*;

public abstract class NotMovingAnimatedEntity extends Entity implements Observer {

    protected Animation animation;

    public NotMovingAnimatedEntity(int worldPositionX, int worldPositionY, int rectangleWidth, int rectangleHeight, int solidAreaDefaultX, int solidAreaDefaultY, String directoryPath, int animationSpeed, Observer o) {
        super(worldPositionX, worldPositionY, rectangleWidth, rectangleHeight, solidAreaDefaultX, solidAreaDefaultY);
        animation = new Animation(directoryPath, animationSpeed, this);
        addObserver(o);

    }

    public NotMovingAnimatedEntity(int worldPositionX, int worldPositionY, int rectangleWidth, int rectangleHeight, int solidAreaDefaultX, int solidAreaDefaultY, Animation animation, Observer o) {
        super(worldPositionX, worldPositionY, rectangleWidth, rectangleHeight, solidAreaDefaultX, solidAreaDefaultY);
        this.animation = animation;
        addObserver(o);

    }
    public Animation getAnimation() {
        return animation;
    }

    @Override
    public int getRow() {
        return getBounds().y / GamePanel.tileSize;
    }

    @Override
    public int getCol() {
        return getBounds().x / GamePanel.tileSize;
    }
}

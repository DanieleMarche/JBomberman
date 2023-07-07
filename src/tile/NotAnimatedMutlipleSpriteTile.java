package tile;

import Animation.Sprites;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class NotAnimatedMutlipleSpriteTile extends Tile{
    protected Sprites sprites;

    public NotAnimatedMutlipleSpriteTile(boolean collision, boolean destructible, boolean getOnFire) {
        super(collision, destructible, getOnFire);
    }

    protected BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract BufferedImage getImage(int index);
}

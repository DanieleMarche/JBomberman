package Animation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Observable;
import java.util.stream.Stream;

public class Sprites extends Observable {
    protected ArrayList<BufferedImage> sprites;

    public Sprites(ArrayList<BufferedImage> sprites) {
        this.sprites = sprites;
    }

    public Sprites(String path, int numSprites) {
        sprites = loadImages(path, numSprites);
    }

    private ArrayList<BufferedImage> loadImages (String path, int numSprites) {
        ArrayList<BufferedImage> a = new ArrayList<>();
        for(int i = 1; i <= numSprites; i++) {
            a.add(loadImage(path + i + ".png"));
        }

        return a;
    }

    private BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BufferedImage getImage(int index) {
        return sprites.get(index);
    }

    public Stream<BufferedImage> getSpritesStream() {
        return sprites.stream();
    }


}

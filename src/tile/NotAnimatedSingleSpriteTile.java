package tile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class NotAnimatedSingleSpriteTile extends Tile{

    protected BufferedImage image;

    public NotAnimatedSingleSpriteTile(String imagePath, boolean getOnFire) {
        super(false, false, getOnFire);
        this.image = loadImage(imagePath);
    }

    public NotAnimatedSingleSpriteTile(String imagePath, boolean collision, boolean destructible, boolean getOnFire) {
        super(collision, destructible, getOnFire);
        this.image = loadImage(imagePath);
    }

    public BufferedImage getImage (){
        return image;
    }

    protected BufferedImage loadImage (String path){
        BufferedImage i;
        try{
            i = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
            return i;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




}

package Animation;

import tile.LimitBlock;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ImageUtils {
    public static BufferedImage loadImage(String imagePath) {
        try {
            // Carica l'immagine utilizzando ImageIO
            URL imageUrl = LimitBlock.class.getResource(imagePath);
            BufferedImage image = ImageIO.read(imageUrl);
            return image;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static BufferedImage mirrorImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage mirroredImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int originalPixel = image.getRGB(x, y);
                int mirroredX = width - x - 1; // Calcola la coordinata X specchiata
                mirroredImage.setRGB(mirroredX, y, originalPixel);
            }
        }

        return mirroredImage;
    }
}

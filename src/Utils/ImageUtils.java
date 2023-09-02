package Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static Utils.FileUtils.getAllPNGFileNamesInDirectory;

public class ImageUtils {

    public static BufferedImage convertNonTransparentToWhite(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        IntStream.range(0, height).parallel().forEach(y -> {
            IntStream.range(0, width).forEach(x -> {
                int pixel = image.getRGB(x, y);
                int alpha = (pixel >> 24) & 0xFF;

                // Check if the pixel is not transparent
                if (alpha != 0) {
                    pixel = (255 << 24) | (255 << 16) | (255 << 8) | 255; // Set to white color
                }

                resultImage.setRGB(x, y, pixel);
            });
        });

        return resultImage;
    }

    public static BufferedImage scaleImage(BufferedImage image, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImage.createGraphics();
        g2.drawImage(image, 0, 0, width, height, null);
        g2.dispose();
        return resizedImage;
    }


    public static List<BufferedImage> loadPNGsFromDirectory(String path) {
        List<String> pngFileNames = getAllPNGFileNamesInDirectory(path);

        return pngFileNames.stream()
                .map(ImageUtils::loadImage)
                .collect(Collectors.toList());
    }

    public static BufferedImage loadImage(String imagePath) {
        try {
            File imageFile = new File(imagePath);
            return ImageIO.read(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static BufferedImage mirrorImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage mirroredImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        IntStream.range(0, height)
                .forEach(y -> IntStream.range(0, width)
                        .forEach(x -> mirroredImage.setRGB(width - x - 1, y, image.getRGB(x, y))));

        return mirroredImage;
    }
}

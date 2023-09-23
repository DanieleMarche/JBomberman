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

/**
 * The `ImageUtils` class contains utility methods for handling image processing and loading operations
 * within the game.
 *
 * @Author Daniele Marchetilli
 */
public class ImageUtils {

    public static BufferedImage combineImages(List<BufferedImage> images, List<Point> positions, int width, int height) {
        // Crea un BufferedImage vuoto con la dimensione specificata
        BufferedImage combinedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = combinedImage.createGraphics();

        // Disegna le immagini sulla BufferedImage combinata alle posizioni specificate
        for (int i = 0; i < images.size(); i++) {
            BufferedImage image = images.get(i);
            Point position = positions.get(i);
            g2d.drawImage(image, position.x, position.y, null);
        }

        g2d.dispose(); // Rilascia le risorse grafiche

        return combinedImage;
    }



    /**
     * Converts non-transparent pixels in the given image to white pixels (255, 255, 255, 255).
     *
     * @param image The input image to process.
     * @return A new BufferedImage with non-transparent pixels converted to white.
     */
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


    /**
     * Scales the given image to the specified width and height.
     *
     * @param image  The input image to scale.
     * @param width  The target width of the scaled image.
     * @param height The target height of the scaled image.
     * @return A new scaled BufferedImage.
     */
    public static BufferedImage scaleImage(BufferedImage image, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImage.createGraphics();
        g2.drawImage(image, 0, 0, width, height, null);
        g2.dispose();
        return resizedImage;
    }


    /**
     * Loads a list of PNG images from the specified directory.
     *
     * @param path The directory path containing PNG image files.
     * @return A list of loaded BufferedImage objects.
     */
    public static List<BufferedImage> loadPNGsFromDirectory(String path) {
        List<String> pngFileNames = getAllPNGFileNamesInDirectory(path);

        return pngFileNames.stream()
                .map(ImageUtils::loadImage)
                .collect(Collectors.toList());
    }

    /**
     * Loads an image from the specified file path.
     *
     * @param imagePath The path to the image file.
     * @return The loaded BufferedImage, or null if loading fails.
     */
    public static BufferedImage loadImage(String imagePath) {
        try {
            File imageFile = new File(imagePath);
            return ImageIO.read(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Mirrors the given image horizontally.
     *
     * @param image The input image to mirror.
     * @return A new BufferedImage with horizontally mirrored pixels.
     */
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

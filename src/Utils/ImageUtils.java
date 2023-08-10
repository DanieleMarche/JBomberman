package Utils;

import tile.LimitBlock;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static Utils.FileUtils.getAllPNGFileNamesInDirectory;

public class ImageUtils {

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

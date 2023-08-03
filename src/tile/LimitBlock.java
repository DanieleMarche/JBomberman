package tile;

import Animation.*;
import entityGerarchy.NotMovingEntity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class LimitBlock extends NotMovingEntity {

    public LimitBlock(int positionX, int positionY) {
        super(positionX, positionY, GamePanel.tileSize, GamePanel.tileSize,  0, 0, findImage(positionX, positionY));
    }

    private static BufferedImage findImage(int positionX, int positionY) {
        int worldRow = positionY / GamePanel.tileSize;
        int worldCol = positionX / GamePanel.tileSize;
        String directoryPath = "/Blocks/limit/";

        BufferedImage image = null;

        if (worldRow == 0) {
            directoryPath += "up/limit_up_0";
            switch (worldCol) {
                case 0 -> image = loadImage(directoryPath + "1" + ".png");
                case 1 -> image = loadImage(directoryPath + "2" + ".png");
                case 14 -> image = mirrorImage(Objects.requireNonNull(loadImage(directoryPath + "1" + ".png")));
                case 13 -> image = mirrorImage(Objects.requireNonNull(loadImage(directoryPath + "2" + ".png")));
                default -> image = loadImage(directoryPath + "3" + ".png");
            }
        }
        else if (worldRow == 12) {
            directoryPath += "down/limit_down_0";
            switch (worldCol) {
                case 0 -> image = loadImage(directoryPath + "1" + ".png");
                case 1 -> image = loadImage(directoryPath + "2" + ".png");
                case 14 -> image = mirrorImage(Objects.requireNonNull(loadImage(directoryPath + "1" + ".png")));
                case 13 -> image = mirrorImage(Objects.requireNonNull(loadImage(directoryPath + "2" + ".png")));
                default -> image = loadImage(directoryPath + "3" + ".png");
            }
        } else {
            directoryPath += "left/limit_left_0";
            switch (worldCol) {
                case 0 -> image = loadImage(directoryPath + "2" + ".png");
                case 1 -> image = loadImage(directoryPath + "1" + ".png");
                case 14 -> image = mirrorImage(Objects.requireNonNull(loadImage(directoryPath + "2" + ".png")));
                case 13 -> image = mirrorImage(Objects.requireNonNull(loadImage(directoryPath + "1" + ".png")));
            }
        }
        return image;

    }


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

    private LimitBlock() {
        
        super( true, false, false);

        String directoryPath = "/Blocks/limit";

        limitBlocksUp = loadLimitBlocks(3, directoryPath + "/up", "/limit_up", 2);
        limitBlocksDown = loadLimitBlocks(3, directoryPath + "/down","/limit_down", 2);
        limitBlocksLeft = loadLimitBlocks(2, directoryPath + "/left", "/limit_left", 0);

        limitBlocksRight = limitBlocksLeft.stream().map(LimitBlock::mirrorImage).collect(Collectors.toCollection(ArrayList::new));

        ArrayList<BufferedImage> a = new ArrayList<>();
        a.addAll(limitBlocksUp);
        a.addAll(limitBlocksLeft);
        a.addAll(limitBlocksRight);
        a.addAll(limitBlocksDown);

        sprites = new Sprites(a);
    }

    @Override
    public BufferedImage getImage(int index) {
        return sprites.getImage(index);
    }
}

package tile;

import Animation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class LimitBlock extends NotAnimatedMutlipleSpriteTile {

    public static LimitBlock limitBlock = null;

    public static ArrayList<BufferedImage> limitBlocksUp;
    public static ArrayList<BufferedImage> limitBlocksDown;
    public static ArrayList<BufferedImage> limitBlocksLeft;
    public static Collection<BufferedImage> limitBlocksRight;

    public static LimitBlock getIstance() {
        if(limitBlock == null) {
            limitBlock = new LimitBlock();

        }

        return limitBlock;
    }

    private ArrayList<BufferedImage> loadLimitBlocks(int nImages, String directoryPath, String fileName, int mImages) {
        ArrayList<BufferedImage> lb = new ArrayList<>();

        for (int i = 1; i <= nImages; i++) {
            lb.add(loadImage(directoryPath + fileName + "_0" + i + ".png"));
            
        }

        if (mImages > 0) {
            for (int i = 1; i <= mImages; i++) {
                lb.add(mirrorImage(Objects.requireNonNull(loadImage(directoryPath + fileName + "_0" + i + ".png"))));
            }
        }

        return lb;
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

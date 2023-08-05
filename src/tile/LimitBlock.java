package tile;

import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.stream.Collectors;

public class LimitBlock extends SingleImageTile {

    public LimitBlock(int row, int col) {
        super(row, col, true, false, false, findImage(row, col));
    }

    private static BufferedImage findImage(int worldRow, int worldCol) {
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

    private static BufferedImage findLimitUpOrDown(int worldCol, String directoryPath) {
        BufferedImage image;
        switch (worldCol) {
            case 0 -> image = loadImage(directoryPath + "1" + ".png");
            case 1 -> image = loadImage(directoryPath + "2" + ".png");
            case 14 -> image = mirrorImage(Objects.requireNonNull(loadImage(directoryPath + "1" + ".png")));
            case 13 -> image = mirrorImage(Objects.requireNonNull(loadImage(directoryPath + "2" + ".png")));
            default -> image = loadImage(directoryPath + "3" + ".png");
        }
        return image;
    }
}

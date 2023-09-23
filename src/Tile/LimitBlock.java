package Tile;

import Tile.tileGerarchy.SingleImageTile;
import Tile.tileGerarchy.TileType;

import java.awt.image.BufferedImage;
import java.util.Objects;
import static Utils.ImageUtils.loadImage;
import static Utils.ImageUtils.mirrorImage;

/**
 * The LimitBlock class represents limit blocks that define the boundaries of the game map.
 * These blocks prevent players and entities from moving beyond the map's edges.
 *
 * @Author Daniele Marchetilli
 */
public class LimitBlock extends SingleImageTile {

    /**
     * Constructs a LimitBlock object at the specified row and column on the game map.
     *
     * @param row The row index of the limit block.
     * @param col The column index of the limit block.
     */
    public LimitBlock(int row, int col) {
        super(TileType.LIMIT_BLOCK, row, col, findImage(row, col));
    }

    /**
     * Determines the image for the limit block based on its position within the game map.
     *
     * @param worldRow The row index of the limit block in the game world.
     * @param worldCol The column index of the limit block in the game world.
     * @return The image for the limit block.
     */
    private static BufferedImage findImage(int worldRow, int worldCol) {
        String directoryPath = "res/Blocks/limit/";

        BufferedImage image = null;

        switch (worldRow) {
            case 0 -> {
                directoryPath += "up/limit_up_0";
                image = findLimitUpOrDown(worldCol, directoryPath);
            }
            case 12 -> {
                directoryPath += "down/limit_down_0";
                image = findLimitUpOrDown(worldCol, directoryPath);
            }
            default -> {
                directoryPath += "left/limit_left_0";
                switch (worldCol) {
                    case 0 -> image = loadImage(directoryPath + "2" + ".png");
                    case 1 -> image = loadImage(directoryPath + "1" + ".png");
                    case 14 -> image = mirrorImage(Objects.requireNonNull(loadImage(directoryPath + "2" + ".png")));
                    case 13 -> image = mirrorImage(Objects.requireNonNull(loadImage(directoryPath + "1" + ".png")));
                }
            }
        }

        return image;
    }

    /**
     * Determines the image for limit blocks that are oriented vertically (up or down).
     *
     * @param worldCol      The column index of the limit block in the game world.
     * @param directoryPath The directory path for the limit block images.
     * @return The image for the limit block.
     */
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


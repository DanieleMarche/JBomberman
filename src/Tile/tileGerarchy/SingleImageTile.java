package Tile.tileGerarchy;

import Utils.ImageUtils;
import main.GameView;
import Tile.TileType;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class SingleImageTile extends Tile {

    protected BufferedImage image;

    public SingleImageTile(TileType tileType, int row, int col, BufferedImage image) {
        super(tileType, row, col);
        this.image = image;
    }

    public SingleImageTile(TileType tileType, int row, int col, String imagePath) {
        super(tileType, row, col);

        image = ImageUtils.loadImage(imagePath);
    }

    public BufferedImage getImage() {
        return image;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(image, getPositionXOnScreen(), getPositionYOnScreen(), GameView.tileSize, GameView.tileSize, null);
    }
}

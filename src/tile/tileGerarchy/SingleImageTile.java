package tile.tileGerarchy;

import Animation.ImageUtils;
import main.GamePanel;
import tile.TileType;
import tile.tileGerarchy.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class SingleImageTile extends Tile {

    protected BufferedImage image;

    public SingleImageTile(TileType tileType, int row, int col, boolean solid, boolean explodable, boolean getFire, BufferedImage image) {
        super(tileType, row, col, solid, explodable, getFire);
        this.image = image;
    }

    public SingleImageTile(TileType tileType, int row, int col, boolean solid, boolean explodable, boolean getFire, String imagePath) {
        super(tileType, row, col, solid, explodable, getFire);

        image = ImageUtils.loadImage(imagePath);
    }

    public BufferedImage getImage() {
        return image;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(image, getPositionXOnScreen(), getPositionYOnScreen(), GamePanel.tileSize, GamePanel.tileSize, null);
    }
}

package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public abstract class SingleImageTile extends Tile{

    protected BufferedImage image;

    public SingleImageTile(int row, int col, boolean solid, boolean explodable, boolean getFire, BufferedImage image) {
        super(row, col, solid, explodable, getFire);
        this.image = image;
    }

    public SingleImageTile(int row, int col, boolean solid, boolean explodable, boolean getFire, String imagePath) {
        super(row, col, solid, explodable, getFire);

        image = loadImage(imagePath);
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

    public BufferedImage getImage() {
        return image;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(image, getPositionXOnScreen(), getPositionYOnScreen(), GamePanel.tileSize, GamePanel.tileSize, null);
    }
}

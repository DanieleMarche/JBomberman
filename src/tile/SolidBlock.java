package tile;

import entityGerarchy.NotMovingEntity;
import main.GamePanel;

import java.awt.*;

public class SolidBlock extends NotMovingEntity {

    private static final String imagePath = "/Blocks/solid_block.png";

    SolidBlock(int positionX, int positionY) {
        super(positionX, positionY, GamePanel.tileSize, GamePanel.tileSize, 0, 0, imagePath);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(image, worldPositionX, worldPositionY, GamePanel.tileSize, GamePanel.tileSize, null);
    }
}

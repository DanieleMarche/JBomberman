package tile;

import entityGerarchy.NotMovingEntity;
import main.GamePanel;

import java.awt.*;

public class SolidBlock extends SingleImageTile {

    private static final String imagePath = "/Blocks/solid_block.png";

    SolidBlock(int row, int col) {
        super(row, col, true, true, false, imagePath);
    }
}

package Controllers;

import Animation.*;
import Controllers.ControllersGerarchy.AnimatedTileController;
import main.GamePanel;
import tile.DestructibleBlock;
import tile.Map;
import tile.TileType;
import tile.WalkableBlock;

import java.util.Observable;

public class DestructibleBlocksController extends AnimatedTileController {

    private Map map;

    public DestructibleBlocksController(GamePanel gamePanel) {
        super(gamePanel, DestructibleBlock.destructibleBlocks);

    }

    public void setMap(Map map) {
        this.map = map;
    }


    @Override
    public void update(Observable o, Object arg) {
        DestructibleBlock db = (DestructibleBlock) o;
        switch((AnimationMessages) arg) {
            case REPAINT -> gamePanel.repaint(db.getPositionXOnScreen(), db.getPositionYOnScreen(), GamePanel.tileSize, GamePanel.tileSize);
            case REMOVE -> {
                db.removeDestructibleBlock();
                map.replaceTile(db.getRow(), db.getCol(), TileType.WALKABLE_BLOCK);
            }

        }
    }
}

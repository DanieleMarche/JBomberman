package Controllers;

import Animation.*;
import Controllers.ControllersGerarchy.AnimatedEntityController;
import main.GamePanel;
import tile.DestructibleBlock;
import tile.Map;
import tile.TileType;

import java.util.Iterator;
import java.util.Observable;

public class DestructibleBlocksController extends AnimatedEntityController {

    private Map map;

    private static int spriteCounter = 0;

    public DestructibleBlocksController(GamePanel gamePanel) {
        super(gamePanel);

    }

    public void setMap(Map map) {
        this.map = map;
    }

    @Override
    public void updateAnimation() {
        spriteCounter ++;
        if(spriteCounter % DestructibleBlock.destructibleBlocks.get(0).getAnimation().getAnimationSpeed() == 0) {

            Iterator<DestructibleBlock> iterator = DestructibleBlock.destructibleBlocks.iterator();

            while(iterator.hasNext()) {

                DestructibleBlock db = iterator.next();

                Animation animation = db.getAnimation();

                if(db.isExploded() && animation.isLastSprite()) {

                    iterator.remove();
                    map.setTile(db.getRow(), db.getCol(), TileType.WALKABLE_BLOCK);
                }
                else {
                    animation.setNextSprite();
                }

            }
            spriteCounter = 0;
        }
    }


    @Override
    public void update(Observable o, Object arg) {
        DestructibleBlock db = (DestructibleBlock) o;
        switch((AnimationMessages) arg) {
            case REPAINT -> gamePanel.repaint(db.getPositionXOnScreen(), db.getPositionYOnScreen(), GamePanel.tileSize, GamePanel.tileSize);
        }
    }
}

package Controllers;

import Animation.Animation;
import Controllers.ControllersGerarchy.AnimatedEntityController;
import main.GamePanel;
import tile.DestructibleBlock;
import tile.Map;

import java.util.ArrayList;
import java.util.Iterator;

public class DestructibleBlocksController extends AnimatedEntityController {

    private final Map map;

    private static int spriteCounter = 0;

    public DestructibleBlocksController(GamePanel gamePanel, Map map) {
        super(gamePanel);
        for(DestructibleBlock db: DestructibleBlock.destructibleBlocks) {
            db.getAnimation().addObserver(gamePanel);
        }
        this.map = map;

    }

    @Override
    public void updateAnimation() {
        spriteCounter ++;
        if(spriteCounter % 10 == 0) {

            Iterator<DestructibleBlock> iterator = DestructibleBlock.destructibleBlocks.iterator();

            while(iterator.hasNext()) {

                DestructibleBlock db = iterator.next();

                Animation animation = db.getAnimation();

                if(db.isExploded() && animation.isLastSprite()) {

                    iterator.remove();
                    map.setTile(db.getRow(), db.getCol(), 0);
                }
                else {
                    animation.setNextSprite();
                }

            }
            spriteCounter = 0;
        }
    }


}

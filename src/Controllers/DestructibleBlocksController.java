package Controllers;

import Animation.Animation;
import Controllers.ControllersGerarchy.AnimatedEntityController;
import main.GamePanel;
import tile.DestructibleBlock;
import tile.TileManager;

import java.util.ArrayList;
import java.util.Iterator;

public class DestructibleBlocksController extends AnimatedEntityController {

    private final ArrayList<DestructibleBlock> destructibleBlocks;

    private final TileManager tileManager;

    private static int spriteCounter = 0;

    public DestructibleBlocksController(GamePanel gamePanel, TileManager tileManager) {
        super(gamePanel);
        this.destructibleBlocks = DestructibleBlock.destructibleBlocks;
        for(DestructibleBlock db: destructibleBlocks) {
            for(Animation a: db.getAnimations()) {
                a.addObserver(gamePanel);
            }
        }
        this.tileManager = tileManager;

    }

    @Override
    public void updateAnimation() {
        spriteCounter ++;
        if(spriteCounter % 10 == 0) {

            Iterator<DestructibleBlock> iterator = destructibleBlocks.iterator();

            while(iterator.hasNext()) {

                DestructibleBlock db = iterator.next();

                Animation animation = db.getCurrentAnimation();

                if(db.isExploded() && animation.isLastSprite()) {

                    iterator.remove();
                    tileManager.setTile(db.getRow(), db.getCol(), 0);
                }
                else {
                    animation.setNextSprite();
                }

            }
            spriteCounter = 0;
        }
    }


}

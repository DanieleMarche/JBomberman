package Controllers;

import Animation.Animation;
import Controllers.ControllersGerarchy.AnimatedEntityController;
import main.GamePanel;
import tile.DestructibleBlock;

import java.util.ArrayList;
import java.util.Iterator;

public class DestructibleBlocksController extends AnimatedEntityController {

    private final ArrayList<DestructibleBlock> destructibleBlocks;

    private static int spriteCounter = 0;

    public DestructibleBlocksController(GamePanel gamePanel) {
        super(gamePanel);
        this.destructibleBlocks = DestructibleBlock.destructibleBlocks;
        for(DestructibleBlock db: destructibleBlocks) {
            for(Animation a: db.getAnimations()) {
                a.addObserver(gamePanel);
            }
        }

    }

    @Override
    public void updateAnimation() {
        spriteCounter ++;
        if(spriteCounter % 10 == 0) {

            for(DestructibleBlock db: destructibleBlocks) {
                db.getCurrentAnimation().setNextSprite();
            }
            spriteCounter = 0;
        }
    }


}

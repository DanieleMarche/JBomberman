package Controllers;

import Animation.Animation;
import Bomb.Bomb;
import Controllers.ControllersGerarchy.AnimatedEntityController;
import main.GamePanel;
import player.Player;

import java.util.Iterator;

public class BombController extends AnimatedEntityController {
    private final Player player;
    private final ExplosionController explosionController;


    private int spriteCoutner;
    public BombController (GamePanel gamePanel, Player player, ExplosionController explosionController) {
        super(gamePanel);
        this.player = player;
        this.explosionController = explosionController;
        spriteCoutner = 0;

        collisionChecker = movingEntity -> {
            for(Bomb bomb: gamePanel.getBombs()) {

                if(checkCollisionMovingAndNotMoving(movingEntity, bomb) && bomb.isPassedInto()) movingEntity.activateCollision();

                if(!checkCollisionMovingAndNotMoving(movingEntity, bomb) && !bomb.isPassedInto()) bomb.hasPassedInto();

            }

        };
    }

    @Override
    public void updateAnimation() {

        Iterator<Bomb> i = gamePanel.getBombs().iterator();

        while(i.hasNext()) {
            Bomb b = i.next();
            Animation a = b.getCurrentAnimation();

            spriteCoutner++;
            if (spriteCoutner % 10 == 0) {

                if (a.getCurrSprite() < a.getNumSprites() - 1) {
                    a.setNextSprite();
                } else {
                    if(b.isExploded()) i.remove();
                    a.setDefaultSprite();
                }
                spriteCoutner = 0;
            }

            b.increaseSeconds();
            if(b.getSeconds() == b.getExplosionTime()) {
                if(!b.isExploded()) explode(b);
            }
        }

    }

    public void dropBomb () {
        if(player.getRemainingBombsAtSameTime() > 0) {
            player.decreaseRemainingBombsAtSameTime();

            Bomb b = new Bomb((((player.getWorldPositionX() + 10) / GamePanel.tileSize) * GamePanel.tileSize), (((player.getWorldPositionY() + 48) / GamePanel.tileSize) * GamePanel.tileSize));
            b.addObserver(gamePanel);

            gamePanel.addBomb(b);
        }
    }

    public void explode  (Bomb bomb)  {
        bomb.explode();
        player.increaseRemainingBombsAtSameTime();
        //explosionController.activateExplosion(bomb.getWorldPositionX() + 24, bomb.getWorldPositionY() + 24);
    }

}

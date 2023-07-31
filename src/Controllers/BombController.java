package Controllers;

import Animation.Animation;
import Bomb.Bomb;
import Controllers.ControllersGerarchy.AnimatedEntityController;
import main.GamePanel;
import player.Player;

import java.util.ArrayList;
import java.util.Iterator;

public class BombController extends AnimatedEntityController {
    private final Player player;
    private final ExplosionController explosionController;

    private int spriteCounter;
    public BombController (GamePanel gamePanel, Player player, ExplosionController explosionController) {
        super(gamePanel);
        this.player = player;
        this.explosionController = explosionController;
        spriteCounter = 0;

        collisionChecker = movingEntity -> {
            for(Bomb bomb: Bomb.bombs) {

                if(checkCollisionMovingAndNotMoving(movingEntity, bomb) && bomb.isPassedInto()) movingEntity.activateCollision();

                if(!checkCollisionMovingAndNotMoving(movingEntity, bomb) && !bomb.isPassedInto()) bomb.hasPassedInto();

            }

        };
    }

    @Override
    public void updateAnimation() {

        for (Bomb b : Bomb.bombs) {
            Animation a = b.getAnimation();

            spriteCounter++;
            if (spriteCounter % 20 == 0) {

                a.setNextSprite();

                spriteCounter = 0;
            }

            b.increaseSeconds();
            if (b.getSeconds() == b.getExplosionTime()) {
                if (!b.isExploded()) explode(b);
            }
        }

        Bomb.bombs.removeIf(b -> b.isExploded());

    }

    public void dropBomb () {
        if(player.getRemainingBombsAtSameTime() > 0) {
            player.decreaseRemainingBombsAtSameTime();

            Bomb b = Bomb.getInstance((((player.getWorldPositionX() + 10) / GamePanel.tileSize) * GamePanel.tileSize), (((player.getWorldPositionY() + 48) / GamePanel.tileSize) * GamePanel.tileSize));
            b.addObserver(gamePanel);

            gamePanel.addBomb(b);
        }
    }

    public void explode  (Bomb bomb)  {
        bomb.explode();
        player.increaseRemainingBombsAtSameTime();
        explosionController.activateExplosion(bomb.getWorldPositionX(), bomb.getWorldPositionY());
    }

}

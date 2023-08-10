package Controllers;

import Animation.AnimationMessages;
import Animation.CycledReversedAnimation;
import Bomb.Bomb;
import Controllers.ControllersGerarchy.AnimatedEntityController;
import main.GamePanel;
import player.Player;

import java.util.ArrayList;
import java.util.Observable;

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

                if(checkCollisionMovingAndNotMoving(movingEntity, bomb) && bomb.isPassedInto()) {
                    movingEntity.activateCollision();

                }

                if(!checkCollisionMovingAndNotMoving(movingEntity, bomb) && !bomb.isPassedInto()) {
                    bomb.hasPassedInto();

                }

            }

        };
    }

    @Override
    public void updateAnimation() {

        spriteCounter++;

        ArrayList<Bomb> bombCopy = new ArrayList<>(Bomb.bombs);

        bombCopy.forEach(bomb -> {
            CycledReversedAnimation a = (CycledReversedAnimation) bomb.getAnimation();
            if(spriteCounter % a.getAnimationSpeed() == 0) {
                a.setNextSprite();
            }
        });

    }

    public void dropBomb () {
        if(player.getRemainingBombsAtSameTime() > 0) {
            player.decreaseRemainingBombsAtSameTime();

            Bomb b = Bomb.getInstance((player.getCol() * GamePanel.tileSize), (player.getRow() * GamePanel.tileSize) + 2, this);

            gamePanel.addBomb(b);
        }
    }

    public void explode  (Bomb bomb)  {
        player.increaseRemainingBombsAtSameTime();
        explosionController.activateExplosion(bomb.getCol(), bomb.getRow());
        Bomb.removeBomb(bomb);
    }

    @Override
    public void update(Observable o, Object arg) {

        Bomb bomb = (Bomb) o;

        switch((AnimationMessages) arg) {
            case REPAINT -> gamePanel.repaint(bomb.getWorldPositionX(), bomb.getWorldPositionY(), GamePanel.tileSize, GamePanel.tileSize);
            case REMOVE -> explode(bomb);
        }

    }
}

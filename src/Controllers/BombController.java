package Controllers;

import Animation.AnimationMessages;
import Bomb.Bomb;
import Controllers.ControllersGerarchy.AnimatedEntityController;
import main.GamePanel;
import player.Player;

import java.util.Observable;

public class BombController extends AnimatedEntityController<Bomb> {
    private final Player player;
    private final ExplosionController explosionController;

    public BombController (GamePanel gamePanel, Player player, ExplosionController explosionController) {
        super(gamePanel, Bomb.bombs);
        this.player = player;
        this.explosionController = explosionController;

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

    public void dropBomb () {
        if(player.getRemainingBombsAtSameTime() > 0) {
            player.decreaseRemainingBombsAtSameTime();

            Bomb.getInstance((player.getCol() * GamePanel.tileSize), (player.getRow() * GamePanel.tileSize) + 2, this);
        }
    }

    public void explode  (Bomb bomb)  {
        Bomb.removeBomb(bomb);
        player.increaseRemainingBombsAtSameTime();
        explosionController.activateExplosion(bomb.getCol(), bomb.getRow());

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

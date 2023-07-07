package Controllers;

import Controllers.ControllersGerarchy.EntityController;
import PowerUp.PowerUp;
import entityGerarchy.MovingEntity;
import main.GamePanel;
import player.Player;

import java.util.Iterator;

public class PowerUpsController extends EntityController {

    public PowerUpsController(GamePanel gamePanel) {
        super(gamePanel);

        collisionChecker = (MovingEntity me) -> {
            if(me instanceof Player) {

                Iterator<PowerUp> i = gamePanel.getPowerUps().iterator();

                while(i.hasNext()) {
                    PowerUp pUp = i.next();

                    if(checkCollisionMovingAndNotMoving(me, pUp)) {
                        switch(pUp.getType()) {
                            case MORE_SPEED -> me.increaseSpeed();
                            case MORE_BOMB_AT_SAME_TIME -> ((Player) me).increaseBombAtSameTime();
                            case MORE_BOMB_RADIUS -> ((Player) me).increaseExplosionRadius();
                        }

                        i.remove();
                    }
                }
            }
        };
    }

}

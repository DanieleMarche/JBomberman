package Controllers;

import Controllers.ControllersGerarchy.AnimatedEntityController;
import Explosion.Explosion;
import Explosion.Flame;
import entityGerarchy.MovingEntity;
import main.GamePanel;
import player.Player;
import tile.TileManager;

import java.util.*;

public class ExplosionController extends AnimatedEntityController {
    private final Player player;
    private final TileManager tileManager;

    public ExplosionController(GamePanel gamePanel, Player player, TileManager tileManager) {
        super(gamePanel);
        this.player = player;
        this.tileManager = tileManager;

        collisionChecker = (MovingEntity me) -> {for(Explosion e: gamePanel.getExplosions()) {
            for(ArrayList<Flame> flames: e.getFlameRadius()) {
                for(Flame f: flames) {
                    if(checkCollisionMovingAndNotMoving(me, f)) {
                        System.out.println("Collisione tra fiamme e bomberman");
                    }
                }

            }
        }
    };
    }


    public void updateAnimation() {

        Iterator<Explosion> i = gamePanel.getExplosions().iterator();

        while(i.hasNext()) {
            Explosion e = i.next();

            if(e.update()) {
                e.removeDestructibleBlocks(tileManager);
                i.remove();

            }
        }
    }

    public void activateExplosion (int worldX, int worldY) {
        gamePanel.addExplosion(new Explosion(worldX, worldY, player.getExplosionRadius(), tileManager, gamePanel));
    }
}

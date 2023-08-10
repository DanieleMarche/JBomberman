package Controllers;

import Animation.*;
import Controllers.ControllersGerarchy.AnimatedEntityController;
import Explosion.Explosion;
import Flames.Flame;
import entityGerarchy.MovingEntity;
import main.GamePanel;
import player.Player;
import tile.Map;

import java.util.*;

import static Explosion.Explosion.explosions;

public class ExplosionController extends AnimatedEntityController {
    private final Player player;
    private final Map map;

    private int secondCounter;

    public ExplosionController(GamePanel gamePanel, Player player, Map map) {
        super(gamePanel);
        this.player = player;
        this.map = map;
        secondCounter = 0;

        collisionChecker = (MovingEntity me) -> {
            for (Explosion e : explosions) {
                for (Flame flame : e.getFlameRadius()) {
                    if (checkCollisionMovingAndNotMoving(me, flame)) {

                    }
                }
            }
        };
    }

    public void activateExplosion (int col, int row) {
        explosions.add(new Explosion(col, row, player.getExplosionRadius(), map, this));
    }

    @Override
    public void updateAnimation() {
        secondCounter++;
        ArrayList<Explosion> explosionsCopy = new ArrayList<>(explosions);
        explosionsCopy.forEach(explosion -> {
            explosion.getFlameRadius().forEach(flame -> {
                Animation a = flame.getAnimation();
                if(secondCounter % a.getAnimationSpeed() == 0) {
                    a.setNextSprite();
                }
            });
        });

    }


    @Override
    public void update(Observable o, Object arg) {
        Explosion explosion = (Explosion) o;
        switch ((AnimationMessages)arg) {
            case REPAINT -> explosion.getFlameRadius().forEach(flame -> {
                gamePanel.repaint(flame.getWorldPositionX(), flame.getWorldPositionY(), GamePanel.tileSize, GamePanel.tileSize);
            });
            case REMOVE -> explosion.removeExplosion(explosion);
        }
    }
}

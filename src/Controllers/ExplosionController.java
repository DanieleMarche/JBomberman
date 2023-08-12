package Controllers;

import Animation.*;

import Controllers.ControllersGerarchy.AnimatedEntityController;
import Explosion.Explosion;
import Flames.Flame;
import main.GamePanel;
import player.Player;
import tile.Map;

import java.util.*;

import static Explosion.Explosion.explosions;

public class ExplosionController extends AnimatedEntityController<Flame> {

    private GamePanel gamePanel;
    private final Player player;
    private final Map map;

    private final FlameController flameController;

    public ExplosionController(GamePanel gamePanel, Player player, Map map, FlameController flameController) {
        super(gamePanel, Flame.flames);
        this.gamePanel = gamePanel;
        this.player = player;
        this.map = map;
        this.flameController = flameController;
    }

    public void activateExplosion(int col, int row) {
        explosions.add(new Explosion(col, row, player.getExplosionRadius(), map, this, flameController));
    }

    @Override
    public void update(Observable o, Object arg) {
        Explosion explosion = (Explosion) o;
        switch ((AnimationMessages) arg) {
            case REMOVE -> explosion.removeExplosion(explosion);

            //case REPAINT -> explosion.getExplosionFlames().forEach(flame -> gamePanel.repaint(flame.getWorldPositionX(), flame.getWorldPositionY(), GamePanel.tileSize, GamePanel.tileSize));
        }

    }
}



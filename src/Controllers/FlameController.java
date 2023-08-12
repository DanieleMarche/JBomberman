package Controllers;

import Animation.AnimationMessages;
import Controllers.ControllersGerarchy.AnimatedEntityController;
import Flames.Flame;
import main.GamePanel;

import java.util.Observable;
import java.util.Observer;

public class FlameController extends AnimatedEntityController<Flame> {
    public FlameController(GamePanel gamePanel) {
        super(gamePanel, Flame.flames);
    }

    @Override
    public void update(Observable o, Object arg) {
        Flame flame = (Flame) o;
        switch ((AnimationMessages) arg) {
            case REMOVE -> {
                Flame.removeFlame(flame);
                gamePanel.repaint(flame.getWorldPositionX(), flame.getWorldPositionY(), GamePanel.tileSize, GamePanel.tileSize);
            }
            case REPAINT -> gamePanel.repaint(flame.getWorldPositionX(), flame.getWorldPositionY(), GamePanel.tileSize, GamePanel.tileSize);
        }
    }
}

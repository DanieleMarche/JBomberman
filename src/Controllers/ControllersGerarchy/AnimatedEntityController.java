package Controllers.ControllersGerarchy;

import Controllers.ControllersGerarchy.EntityController;
import main.GamePanel;

public abstract class AnimatedEntityController extends EntityController {

    public AnimatedEntityController(GamePanel gamePanel) {
        super(gamePanel);
    }
    public abstract void updateAnimation();

}

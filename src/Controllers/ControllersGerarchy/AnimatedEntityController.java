package Controllers.ControllersGerarchy;

import Animation.Animate;
import Controllers.ControllersGerarchy.EntityController;
import main.GamePanel;

public abstract class AnimatedEntityController extends EntityController implements Animate {

    public AnimatedEntityController(GamePanel gamePanel) {
        super(gamePanel);
    }

}

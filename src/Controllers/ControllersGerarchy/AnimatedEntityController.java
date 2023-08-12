package Controllers.ControllersGerarchy;

import Animation.Animate;
import Controllers.ControllersGerarchy.EntityController;
import entityGerarchy.NotMovingAnimatedEntity;
import main.GamePanel;

import java.util.ArrayList;
import java.util.Collection;

public abstract class AnimatedEntityController<T extends NotMovingAnimatedEntity> extends EntityController {

    private final ArrayList<T> notMovingAnimatedEntities;

    public AnimatedEntityController(GamePanel gamePanel, ArrayList<T> collection) {
        super(gamePanel);
        notMovingAnimatedEntities = collection;
    }

    public void updateAnimation() {
        ArrayList<T> arrayListCopy = new ArrayList<>(notMovingAnimatedEntities);

        arrayListCopy.forEach(element -> element.animate.updateAnimation());
    }

}

package Controllers.ControllersGerarchy;

import entityGerarchy.Entity;
import entityGerarchy.MovingEntity;
import entityGerarchy.NotMovingAnimatedEntity;
import entityGerarchy.NotMovingEntity;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public abstract class EntityController<T extends Entity> extends Controller {
    protected CollisionChecker collisionChecker;

    public EntityController(){
    }

    public CollisionChecker getCollisionChecker() {
        return collisionChecker;
    }

    protected void repaint(T nmae) {
        setChanged();
        notifyObservers(nmae);
    }

}

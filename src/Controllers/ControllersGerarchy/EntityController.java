package Controllers.ControllersGerarchy;

import EntityModelGerarchy.Entity;

/**
 * An abstract base class for controllers that manage entities of a specific type.
 *
 * @param <T> The type of entities managed by this controller.
 */
public abstract class EntityController<T extends Entity> extends Controller {

    /**
     * The collision checker used by this controller to check collisions involving entities.
     */
    protected CollisionChecker collisionChecker;

    /**
     * Constructs a new instance of the EntityController class.
     */
    public EntityController() {
    }

    /**
     * Gets the collision checker used by this controller.
     *
     * @return The collision checker.
     */
    public CollisionChecker getCollisionChecker() {
        return collisionChecker;
    }

    /**
     * Repaints the specified entity by notifying observers with the entity as the argument.
     *
     * @param entity The entity to repaint.
     */
    protected void repaint(T entity) {
        setChanged();
        notifyObservers(entity);
    }
}

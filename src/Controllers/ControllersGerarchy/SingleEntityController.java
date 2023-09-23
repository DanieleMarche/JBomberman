package Controllers.ControllersGerarchy;

import EntityModelGerarchy.Entity;

/**
 * An abstract base class for controllers that manage a single entity of a specific type.
 *
 * @param <T> The type of entity managed by this controller.
 */
public abstract class SingleEntityController<T extends Entity> extends EntityController<T> {

    /**
     * The single entity managed by this controller.
     */
    protected T entity;

    /**
     * Constructs a new instance of the SingleEntityController class with an empty collision checker.
     * (No collision checking by default)
     */
    public SingleEntityController() {
        collisionChecker = movingEntity -> {};
    }

    /**
     * Sets the single entity managed by this controller.
     *
     * @param entity The entity to be managed.
     */
    public void setEntity(T entity) {
        this.entity = entity;
    }
}

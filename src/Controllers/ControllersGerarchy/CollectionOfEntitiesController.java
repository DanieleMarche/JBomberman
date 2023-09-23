package Controllers.ControllersGerarchy;

import EntityModelGerarchy.Entity;

import java.util.ArrayList;

/**
 * An abstract base class for controllers that manage collections of entities of a specific type.
 *
 * @param <T> The type of entities managed by this controller.
 */
public abstract class CollectionOfEntitiesController<T extends Entity> extends EntityController<T> {

    /**
     * The collection of entities managed by this controller.
     */
    protected ArrayList<T> entities;

    /**
     * Constructs a new instance of the CollectionOfEntitiesController class.
     */
    public CollectionOfEntitiesController() {
        super();
    }

    /**
     * Sets the collection of entities managed by this controller.
     *
     * @param entities The collection of entities to be managed.
     */
    public void setEntities(ArrayList<T> entities) {
        this.entities = entities;
    }
}


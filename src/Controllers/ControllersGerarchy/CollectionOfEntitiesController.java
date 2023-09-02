package Controllers.ControllersGerarchy;

import entityGerarchy.Entity;
import entityGerarchy.NotMovingAnimatedEntity;

import java.util.ArrayList;
import java.util.Collection;

public abstract class CollectionOfEntitiesController<T extends Entity> extends EntityController<T> {

    protected final ArrayList<T> entities;

    public CollectionOfEntitiesController(Collection<T> collection) {
        super();
        entities = (ArrayList<T>) collection;
    }

}

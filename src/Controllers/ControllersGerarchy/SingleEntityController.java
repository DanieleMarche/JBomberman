package Controllers.ControllersGerarchy;

import entityGerarchy.Entity;

public abstract class SingleEntityController<T extends Entity> extends EntityController<T> {

    protected T entity;

    public SingleEntityController() {
        collisionChecker = movingEntity -> {};
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }
}

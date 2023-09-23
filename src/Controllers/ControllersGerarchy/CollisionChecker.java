package Controllers.ControllersGerarchy;

import EntityModelGerarchy.MovingEntity;

/**
 * An interface for classes that check collisions involving moving entities.
 */
public interface CollisionChecker {
    /**
     * Checks collisions involving a moving entity and performs the actions if a collision is detected.
     *
     * @param movingEntity The moving entity for which collision is checked.
     */
    void checkCollision(MovingEntity movingEntity);
}

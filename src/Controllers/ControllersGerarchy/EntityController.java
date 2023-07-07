package Controllers.ControllersGerarchy;

import entityGerarchy.Entity;
import entityGerarchy.MovingEntity;
import main.GamePanel;

public abstract class EntityController {
    protected GamePanel gamePanel;
    public CollisionChecker collisionChecker;

    public EntityController(GamePanel gamePanel){

        this.gamePanel = gamePanel;

    }

    protected boolean checkCollisionMovingAndNotMoving(MovingEntity movingEntity, Entity e) {
        movingEntity.getSolidArea().x += movingEntity.getWorldPositionX();
        movingEntity.getSolidArea().y += movingEntity.getWorldPositionY();

        e.getSolidArea().x += e.getWorldPositionX();
        e.getSolidArea().y += e.getWorldPositionY();

        switch(movingEntity.getDirection()) {

            case UP -> movingEntity.getSolidArea().y -= movingEntity.getSpeed();

            case DOWN -> movingEntity.getSolidArea().y += movingEntity.getSpeed();

            case RIGHT -> movingEntity.getSolidArea().x += movingEntity.getSpeed();

            case LEFT -> movingEntity.getSolidArea().x -= movingEntity.getSpeed();
        }

        boolean res = movingEntity.getSolidArea().intersects(e.getSolidArea());

        movingEntity.getSolidArea().x = movingEntity.getSolidAreaDefaultX();
        movingEntity.getSolidArea().y = movingEntity.getSolidAreaDefaultY();

        e.getSolidArea().x = e.getSolidAreaDefaultX();
        e.getSolidArea().y = e.getSolidAreaDefaultY();

        return res;
    };

}

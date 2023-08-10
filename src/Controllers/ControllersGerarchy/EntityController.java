package Controllers.ControllersGerarchy;

import entityGerarchy.Entity;
import entityGerarchy.MovingEntity;
import main.GamePanel;

import java.awt.*;
import java.util.Observer;

public abstract class EntityController implements Observer {
    protected GamePanel gamePanel;
    protected CollisionChecker collisionChecker;

    public EntityController(GamePanel gamePanel){

        this.gamePanel = gamePanel;

    }

    protected boolean checkCollisionMovingAndNotMoving(MovingEntity me, Entity e) {

        Rectangle movingEntityBounds = (Rectangle) me.getBounds().clone();

        switch(me.getDirection()) {
            case UP -> movingEntityBounds.y -= me.getSpeed();
            case DOWN -> movingEntityBounds.y += me.getSpeed();
            case LEFT -> movingEntityBounds.x -= me.getSpeed();
            case RIGHT -> movingEntityBounds.x += me.getSpeed();
        }

        return movingEntityBounds.intersects(e.getBounds());
    }

    public CollisionChecker getCollisionChecker() {
        return collisionChecker;
    }

}

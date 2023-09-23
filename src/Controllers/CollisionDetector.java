package Controllers;

import Controllers.ControllersGerarchy.CollisionChecker;
import Controllers.ControllersGerarchy.EntityController;
import EntityModelGerarchy.*;
import Tile.Map;
import Tile.tileGerarchy.TileType;
import Tile.tileGerarchy.Tile;

import java.awt.*;
import java.util.ArrayList;

/**
 * The CollisionDetector class is responsible for managing collision detection in the game.
 * It provides methods to check collisions between various game entities and tiles.
 * When this class is istanced it is possible to add collision checkers to check when the entity is moving
 * and collision checkers to check when the entity is not moving.
 * @Author Daniele Marchetilli
 */
public class CollisionDetector {

    /**The collision checker the entity is moving*/
    private final ArrayList<CollisionChecker> collisionCheckersWhenMoving;

    /** The collision checkers to check when the entity is not moving */
    private final ArrayList<CollisionChecker> collisionCheckersWhenNotMoving;

    /*The constructor of this class */
    public CollisionDetector() {

        collisionCheckersWhenMoving = new ArrayList<>();
        collisionCheckersWhenNotMoving = new ArrayList<>();

    }

    /**
     * @param movingEntity
     * This functions takes a Moving Entity as a parameter and checks if it is colliding with a NotMovingEntity inside
     * the map matrix of the tileManager;
     **/
    public static void checkCollisionWithTiles(MovingEntity movingEntity, Map map) {

        int[] collisionCoordinates = getNextCohordinates(movingEntity);

        int row = collisionCoordinates[0];
        int col = collisionCoordinates[1];

        TileType tile = map.getMapTileType(row, col);
        if(tile.isSolid()) {
            movingEntity.activateCollision();
        }
        else {
            movingEntity.deActivateCollision();
        }
    }

    public static int[] getNextCohordinates(MovingEntity movingEntity) {

        int movingEntityRow = movingEntity.getRow();
        int movingEntityCol = movingEntity.getCol();

        int[] collisionCoordinates;

        switch (movingEntity.getDirection()) {
            case UP -> collisionCoordinates = new int[]{movingEntityRow - 1, movingEntityCol};
            case DOWN -> collisionCoordinates = new int[]{movingEntityRow + 1, movingEntityCol};
            case LEFT -> collisionCoordinates = new int[]{movingEntityRow, movingEntityCol - 1};
            case RIGHT -> collisionCoordinates = new int[]{movingEntityRow, movingEntityCol + 1};
            default -> throw new IllegalStateException("Unexpected value: " + movingEntity.getDirection());
        }
        return collisionCoordinates;
    }

    public static boolean checkCollisionBasedOnTheGrid(Entity e1, Entity e2) {
        int[] e1NextCohordinates = getNextCohordinates((MovingEntity) e1);
        return e2.getRow() == e1NextCohordinates[0] && e2.getCol() == e1NextCohordinates[1];
    }

    /**
     * This function takes as a parameter two entities and checks if them both are on the same map tile.
     * @param e1 The first entity.
     * @param e2 The Second entity.
     * @return True if the two entities shares the same cell, False otherwise.
     */
    public static boolean checkPresenceInSameMapCell(Entity e1, Entity e2) {
        return e1.getCol() == e2.getCol() && e1.getRow() == e2.getRow();
    }

    /**
     * This funcition takes as parameters an Entity and a Map and returns the tiles surrounding the entity that are not
     * solid.
     * @param e
     * @param map
     * @return The free surroundings of the entity;
     */
    public static Direction[] getFreeSurroundings(Entity e, Map map) {
        ArrayList<Direction> directions = new ArrayList<>();
        Tile[] tiles = map.getSourroundings(e);

        for(int i = 0; i<tiles.length; i++) {
            if(!tiles[i].getTileType().isSolid()) {
                switch (i) {
                    case 0 -> directions.add(Direction.UP);
                    case 1 -> directions.add(Direction.DOWN);
                    case 2 -> directions.add(Direction.LEFT);
                    case 3 -> directions.add(Direction.RIGHT);
                }
            }

        }

        return directions.toArray(new Direction[0]);

    }

    /**
     * This function checks if the solid area of a moving entity and an entity intersects.
     * @param me the moving entity to check the collision.
     * @param e the entity to check the collision.
     * @return true if the solid areas intersects, false otherwise.
     */
    public static boolean checkcollisionBetweenMovingentityAndOtherEntity(MovingEntity me, Entity e) {

            Rectangle movingEntityBounds = (Rectangle) me.getBounds().clone();

            switch(me.getDirection()) {
                case UP -> movingEntityBounds.y -= me.getSpeed();
                case DOWN -> movingEntityBounds.y += me.getSpeed();
                case LEFT -> movingEntityBounds.x -= me.getSpeed();
                case RIGHT -> movingEntityBounds.x += me.getSpeed();
            }
            return movingEntityBounds.intersects(e.getBounds());

    }

    public void addCollisionCheckerWhenMoving(EntityController e) {
        collisionCheckersWhenMoving.add(e.getCollisionChecker());
    }

    public void addCollisionCheckerWhenNotMoving(EntityController e) {
        collisionCheckersWhenNotMoving.add(e.getCollisionChecker());
    }

    public void checkCollisionWhenMoving(MovingEntity me) {
        collisionCheckersWhenMoving.forEach(collisionChecker -> collisionChecker.checkCollision(me));
    }

    public void checkCollisionWhenNotMoving(MovingEntity me) {
        collisionCheckersWhenNotMoving.forEach(collisionChecker -> collisionChecker.checkCollision(me));
    }


}

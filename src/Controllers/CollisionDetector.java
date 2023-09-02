package Controllers;

import Controllers.ControllersGerarchy.CollisionChecker;
import Controllers.ControllersGerarchy.EntityController;
import entityGerarchy.*;
import Tile.Map;
import Tile.TileType;
import Tile.tileGerarchy.Tile;

import java.awt.*;
import java.util.ArrayList;

public class CollisionDetector {

    private final ArrayList<CollisionChecker> entitiesToCheckWhenMoving;

    private final ArrayList<CollisionChecker> entitiesToCheckWhenNotMoving;

    public CollisionDetector() {

        entitiesToCheckWhenMoving = new ArrayList<>();
        entitiesToCheckWhenNotMoving = new ArrayList<>();

    }

    /**
     * @param movingEntity
     * This functions takes a Moving Entity as a parameter and checks if it is colliding with a NotMovingEntity inside
     * the map matrix of the tileManager;
     **/
    public static void checkCollisionWithTiles(MovingEntity movingEntity, Map map) {

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

    public static boolean checkCollisionBasedOnTheGrid(Entity e1, Entity e2) {
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
        entitiesToCheckWhenMoving.add(e.getCollisionChecker());
    }

    public void addCollisionCheckerWhenNotMoving(EntityController e) {
        entitiesToCheckWhenNotMoving.add(e.getCollisionChecker());
    }

    public void checkCollisionWhenMoving(MovingEntity me) {
        entitiesToCheckWhenMoving.forEach(collisionChecker -> collisionChecker.checkCollision(me));
    }

    public void checkCollisionWhenNotMoving(MovingEntity me) {
        entitiesToCheckWhenNotMoving.forEach(collisionChecker -> collisionChecker.checkCollision(me));
    }


}

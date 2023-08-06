package Controllers;

import Controllers.ControllersGerarchy.CollisionChecker;
import entityGerarchy.MovingEntity;
import tile.Map;
import tile.tileGerarchy.Tile;

public class CollisionDetector {

    private final Map map;
    private final CollisionChecker bombCollisionChecker;

    public CollisionDetector(Map map, BombController bombController) {

        this.map = map;
        this.bombCollisionChecker = bombController.getCollisionChecker();
    }

    /**
     * @param movingEntity
     * This functions takes a Moving Entity as a parameter and checks if it is colliding with a NotMovingEntity inside
     * the map matrix of the tileManager;
     **/
    public void checkCollisionWithTiles(MovingEntity movingEntity) {

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

        Tile tile = map.getTile(row, col);

        if(tile.isSolid()) {
            movingEntity.activateCollision();
        }
        else {
            movingEntity.deActivateCollision();
        }


    }

    public void checkCollisionWithNotMovingEntities(MovingEntity me) {
        bombCollisionChecker.checkCollision(me);
    }


}

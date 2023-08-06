package Controllers;

import entityGerarchy.MovingEntity;
import tile.Map;
import tile.tileGerarchy.Tile;

public class CollisionDetector {

    DestructibleBlocksController destructibleBlocksController;
    private final Map map;

    public CollisionDetector(Map map, DestructibleBlocksController destructibleBlocksController) {

        this.map = map;
        this.destructibleBlocksController = destructibleBlocksController;
    }

    public void checkCollision(MovingEntity me) {
        checkTile(me);
    }

    /**
     * @param movingEntity
     * This functions takes a Moving Entity as a parameter and checks if it is colliding with a NotMovingEntity inside
     * the map matrix of the tileManager;
     **/
    public void checkTile(MovingEntity movingEntity) {

        int movingEntityRow = movingEntity.getRow();
        int movingEntityCol = movingEntity.getCol();

        int[] collisionCoordinates = null;
        switch (movingEntity.getDirection()) {
            case UP -> collisionCoordinates = new int[]{movingEntityRow - 1, movingEntityCol};
            case DOWN -> collisionCoordinates = new int[]{movingEntityRow + 1, movingEntityCol};
            case LEFT -> collisionCoordinates = new int[]{movingEntityRow, movingEntityCol - 1};
            case RIGHT -> collisionCoordinates = new int[]{movingEntityRow, movingEntityCol + 1};
        }

        if (collisionCoordinates != null) {
            int row = collisionCoordinates[0];
            int col = collisionCoordinates[1];

            Tile tile = map.getTile(row, col);

            if(tile != null && tile.isSolid()) {
                movingEntity.activateCollision();
            }
            else {
                movingEntity.deActivateCollision();
            }
        }

    }
}

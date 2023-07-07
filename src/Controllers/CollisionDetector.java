package Controllers;

import entityGerarchy.MovingEntity;
import main.GamePanel;
import tile.TileManager;
import tile.Tile;

public class CollisionDetector {
    private final TileManager tileManager;

    public CollisionDetector(TileManager tileManager) {
        this.tileManager = tileManager;
    }

    public void checkTile(MovingEntity movingEntity) {
        int entityLeftWorldX = movingEntity.getWorldPositionX() + movingEntity.getSolidArea().x;
        int entityRightWorldX = movingEntity.getWorldPositionX() + movingEntity.getSolidArea().x + movingEntity.getSolidArea().width * GamePanel.tileScale;
        int entityTopWorldY = movingEntity.getWorldPositionY() + movingEntity.getSolidArea().y;
        int entityBottomWorldY = movingEntity.getWorldPositionY() + movingEntity.getSolidArea().y + movingEntity.getSolidArea().height * GamePanel.tileScale;

        int entityLeftCol = entityLeftWorldX / GamePanel.tileSize;
        int entityRightCol = entityRightWorldX / GamePanel.tileSize;
        int entityTopRow = entityTopWorldY / GamePanel.tileSize;
        int entityBottomRow = entityBottomWorldY / GamePanel.tileSize;

        int[][] tileNums = new int[2][2];

        switch (movingEntity.getDirection()) {
            case UP -> {
                entityTopRow = (entityTopWorldY - movingEntity.getSpeed()) / GamePanel.tileSize;
                tileNums[0] = new int[]{entityTopRow, entityLeftCol};
                tileNums[1] = new int[]{entityTopRow, entityRightCol};
            }
            case DOWN -> {
                entityBottomRow = (entityBottomWorldY + movingEntity.getSpeed()) / GamePanel.tileSize;
                tileNums[0] = new int[]{entityBottomRow, entityLeftCol};
                tileNums[1] = new int[]{entityBottomRow, entityRightCol};
            }
            case LEFT -> {
                entityLeftCol = (entityLeftWorldX - movingEntity.getSpeed()) / GamePanel.tileSize;
                tileNums[0] = new int[]{entityTopRow, entityLeftCol};
                tileNums[1] = new int[]{entityBottomRow, entityLeftCol};
            }
            case RIGHT -> {
                entityRightCol = (entityRightWorldX + movingEntity.getSpeed()) / GamePanel.tileSize;
                tileNums[0] = new int[]{entityTopRow, entityRightCol};
                tileNums[1] = new int[]{entityBottomRow, entityRightCol};
            }
        }


        for (int[] tileNum : tileNums) {
            if (tileNum.length == 2) {
                int row = tileNum[0];
                int col = tileNum[1];
                Tile tile = tileManager.getTile(tileManager.getMapTileNum(row, col));
                if (tile != null && tile.getCollision()) {
                    movingEntity.activateCollision();

                    break;
                }
            }
        }

    }

}

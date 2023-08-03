package tile;

import Animation.Animation;
import entityGerarchy.NotMovingAnimatedEntity;
import main.GamePanel;
import player.Player;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DestructibleBlock extends NotMovingAnimatedEntity {

    public static ArrayList<DestructibleBlock> destructibleBlocks = new ArrayList<>();

    private boolean exploded;

    public static DestructibleBlock getIstance(int x, int y, TileManager tileManager) {
        for(DestructibleBlock db: destructibleBlocks){
            if(db.worldPositionX == x && db.worldPositionY == y) {
                return db;
            }
        }
        DestructibleBlock db = new DestructibleBlock(x, y, tileManager);
        destructibleBlocks.add(db);
        return db;
    }

    private DestructibleBlock(int positionX, int positionY, TileManager tileManager) {
        super(positionX, positionY, GamePanel.tileSize, GamePanel.tileSize, 0, 0, findPath(positionX, positionY, tileManager), 4);

        exploded = false;

    }

    public static String findPath(int x, int y, TileManager tileManager) {
        int row = y / GamePanel.tileSize;
        int col = x / GamePanel.tileSize;

        if(tileManager.getMapTileNum(row - 1, col) == 3 || tileManager.getMapTileNum(row - 1, col) == 1) {
            return "/Blocks/destructable_block/with_shadow" + "/" + "destructible_block_shadow" + "_0";
        }
        return "/Blocks/destructable_block" + "/" + "destructable_block" + "_0";

    }

    public boolean isExploded() {
        return exploded;
    }

    public void explode() {
        exploded = true;
    }


    public BufferedImage getSprite() {
        return currentAnimation.getCurrentImage();
    }
}

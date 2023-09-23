package Controllers;

import Animation.*;
import Controllers.ControllersGerarchy.TilesController;
import Bomberman.PlayerModel;
import Tile.DestructibleBlock;
import Tile.tileGerarchy.TileType;

import java.util.Observable;

/**
 * The DestructibleBlocksController class is responsible for managing destructible blocks
 * in the Bomberman game. It extends the TilesController class and handles updates related
 * to destructible blocks, including their removal, power-up visibility, and more.
 *
 * @Author Daniele Marchetilli
 */
public class DestructibleBlocksController extends TilesController<DestructibleBlock> {

    /**
     * The singleton instance of the DestructibleBlocksController class.
     */
    public static DestructibleBlocksController instance = null;

    /**
     * Private constructor to ensure a single instance of DestructibleBlocksController.
     * Use {@link #getInstance()} to obtain the instance.
     */
    private DestructibleBlocksController() {
        super();
    }

    /**
     * Get the singleton instance of the DestructibleBlocksController class.
     *
     * @return The DestructibleBlocksController instance.
     */
    public static DestructibleBlocksController getInstance() {
        if (instance == null) {
            instance = new DestructibleBlocksController();
        }
        return instance;
    }

    /**
     * Update method called when observing changes in destructible blocks.
     *
     * @param o   The Observable object (a destructible block) that triggered the update.
     * @param arg The argument representing the type of animation message.
     */
    @Override
    public void update(Observable o, Object arg) {
        DestructibleBlock db;
        switch ((AnimationMessages) arg) {
            case REPAINT_GAME -> {
                db = (DestructibleBlock) o;
                setChanged();
                notifyObservers(db);
            }

            case REMOVE_ELEMENT -> {

                db = (DestructibleBlock) o;
                tiles.remove(db);
                PlayerModel.getInstance().addScore(db);

                AssetManager.getInstance().getMap().replaceTile(db.getRow(), db.getCol(), TileType.WALKABLE_BLOCK);

            }

            case GAME_WON, GAME_LOST -> tiles.clear();
        }
    }
}

package Controllers;

import Animation.*;
import Controllers.ControllersGerarchy.TilesController;
import Bomberman.Player;
import Tile.DestructibleBlock;
import Tile.Map;
import Portal.Portal;
import Tile.TileType;

import java.util.Observable;

public class DestructibleBlocksController extends TilesController<DestructibleBlock> {

    public static DestructibleBlocksController instance = null;

    public static DestructibleBlocksController getInstance(){
        if(instance == null) {
            instance = new DestructibleBlocksController();
        }
        return instance;
    }

    private DestructibleBlocksController() {
        super(AssetManager.getInstance().getDestructibleBlocks());
    }

    @Override
    public void update(Observable o, Object arg) {
        DestructibleBlock db;
        switch((AnimationMessages) arg) {
            case REPAINT_GAME -> {
                db = (DestructibleBlock) o;
                setChanged();
                notifyObservers(db);
            }

            case REMOVE_ELEMENT -> {
                db = (DestructibleBlock) o;
                tiles.remove(db);
                Player.getInstance().addScore(db);
                AssetManager.getInstance().getMap().replaceTile(db.getRow(), db.getCol(), TileType.WALKABLE_BLOCK);
                if (db.hasPortal()) {
                    Portal.getInstance().setVisibility(true);
                }
            }
            case GAME_WON, GAME_LOST -> tiles.clear();

        }
    }
}

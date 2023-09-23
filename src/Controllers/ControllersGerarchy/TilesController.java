package Controllers.ControllersGerarchy;

import Controllers.MapController;
import Tile.tileGerarchy.Tile;

import java.util.ArrayList;

/**
 * An abstract base class for controllers that manage a collection of tiles of a specific type.
 *
 * @param <T> The type of tiles managed by this controller.
 */
public abstract class TilesController<T extends Tile> extends Controller {

    /**
     * The collection of tiles managed by this controller.
     */
    protected ArrayList<T> tiles;

    /**
     * Constructs a new instance of the TilesController class and adds MapController as an observer.
     */
    public TilesController() {
        addObserver(MapController.getInstance());
    }

    /**
     * Sets the collection of tiles managed by this controller.
     *
     * @param tiles The collection of tiles to be managed.
     */
    public void setTiles(ArrayList<T> tiles) {
        this.tiles = tiles;
    }
}


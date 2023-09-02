package Controllers.ControllersGerarchy;

import Controllers.MapController;
import Tile.tileGerarchy.Tile;

import java.util.ArrayList;

public abstract class TilesController<T extends Tile> extends Controller {
    protected ArrayList<T> tiles;

    public TilesController(ArrayList<T> collection) {

        tiles = collection;
        addObserver(MapController.getInstance());
    }

}

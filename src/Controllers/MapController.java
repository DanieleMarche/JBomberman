package Controllers;

import Controllers.ControllersGerarchy.Controller;
import Tile.tileGerarchy.Tile;

import java.util.Observable;

public class MapController extends Controller {

    private static MapController instance = null;

    public static MapController getInstance() {
        if(instance == null) instance = new MapController();
        return instance;
    }

    public MapController() {
    }

    @Override
    public void update(Observable o, Object arg) {
        Tile tile = (Tile) arg;
        setChanged();
        notifyObservers(tile);
    }
}

package Controllers;

import Controllers.ControllersGerarchy.Controller;
import Tile.tileGerarchy.Tile;

import java.util.Observable;

/**
 *
 * The MapController class is responsible for managing and updating the game map.
 * It extends the Controller class and observes changes in individual tiles of the map.
 *
 * @Author Daniele Marchetilli.
 */
public class MapController extends Controller {

    /**
     * The singleton instance of the MapController class.
     */
    private static MapController instance = null;

    /**
     * Get the singleton instance of the MapController class.
     *
     * @return The MapController instance.
     */
    public static MapController getInstance() {
        if (instance == null) instance = new MapController();
        return instance;
    }

    /**
     * Private constructor to ensure a single instance of MapController.
     * Use {@link #getInstance()} to obtain the instance.
     */
    private MapController() {
    }

    /**
     * Update method called when observing changes in the game map.
     *
     * @param o   The Observable object (a tile) that triggered the update.
     * @param arg The argument representing the modified tile.
     */
    @Override
    public void update(Observable o, Object arg) {
        Tile tile = (Tile) arg;
        setChanged();
        notifyObservers(tile);
    }
}


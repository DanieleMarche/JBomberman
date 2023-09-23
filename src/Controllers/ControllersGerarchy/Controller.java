package Controllers.ControllersGerarchy;

import java.util.Observable;
import java.util.Observer;

/**
 * This class is the base for every controller of the game. It is only an obserber and an obaservable.
 *
 * It leaves the implementation of the update method free to its inheritors.
 *
 * @Author Daniele Marchetilli
 * @Version 1.0
 */
public abstract class Controller extends Observable implements Observer {


}

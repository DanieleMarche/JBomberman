package Controllers;

import Controllers.ControllersGerarchy.Controller;
import main.GameModel;
import main.GameView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;

/**
 *
 * The LevelControllers class manages a collection of controllers associated with a game level.
 * It provides a convenient way to initialize and handle multiple controllers for a specific game level.
 *
 * @Author Daniele Marchetilli
 */
public class LevelControllers {

    /**
     * A list of controllers associated with the game level.
     */
    private ArrayList<Controller> controllers;

    /**
     * Constructs a new LevelControllers instance with the specified controllers.
     *
     * @param gamePanel    The GameView associated with the game.
     * @param gameModel    The GameModel representing the game's internal state.
     * @param controllers  A variable number of Controller instances to associate with the level.
     */
    public LevelControllers(GameView gamePanel, GameModel gameModel, Controller... controllers) {
        this.controllers = new ArrayList<>();
        Collections.addAll(this.controllers, controllers);

        // Add gamePanel and gameModel as observers to each controller.
        this.controllers.forEach(controller -> ((Observable) controller).addObserver(gamePanel));
        this.controllers.forEach(controller -> ((Observable) controller).addObserver(gameModel));
    }

    /**
     * Remove all controller instances associated with the game level.
     */
    public void removeAllInstances() {
        controllers.clear();
    }
}


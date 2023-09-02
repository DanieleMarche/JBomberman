package Controllers;

import Controllers.ControllersGerarchy.Controller;
import main.GameModel;
import main.GameView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;

public class LevelController {

    private ArrayList<Controller> controllers;

    public LevelController(GameView gamePanel, GameModel gameModel, Controller... controllers) {

        this.controllers = new ArrayList<>();

        Collections.addAll(this.controllers, controllers);

        this.controllers.forEach(controller -> ((Observable)controller).addObserver(gamePanel));
        this.controllers.forEach(controller -> ((Observable)controller).addObserver(gameModel));

    }

    public void removeAllInstances() {
        controllers.clear();
    }
}

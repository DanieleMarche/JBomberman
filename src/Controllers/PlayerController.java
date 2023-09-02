package Controllers;

import Controllers.ControllersGerarchy.EntityController;
import Bomberman.Player;
import Animation.*;
import Controllers.ControllersGerarchy.SingleEntityController;

import java.util.Observable;

public class PlayerController extends SingleEntityController<Player> {

    private static PlayerController instance = null;

    public static PlayerController getInstance() {
        if(instance == null) instance = new PlayerController();
        return instance;
    }

    @Override
    public void update(Observable o, Object arg) {
        Player player;
        if(arg instanceof  AnimationMessages) {
            switch ((AnimationMessages) arg) {
                case REPAINT_GAME -> {
                    setChanged();
                    notifyObservers();
                }

                case REMOVE_ELEMENT -> {
                    player = (Player) o;
                    Player.removeInstance();
                    setChanged();
                    notifyObservers(player);
                }
                case GAME_WON -> Player.removeInstance();
                case GAME_LOST -> {
                    Player.removeInstance();
                    setChanged();
                    notifyObservers(arg);
                }

            }
        }
    }
}


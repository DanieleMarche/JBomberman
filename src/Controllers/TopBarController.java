package Controllers;

import Animation.AnimationMessages;
import Controllers.ControllersGerarchy.Controller;
import TopBar.*;
import Bomberman.Player;

import java.util.Observable;

public class TopBarController extends Controller {

    private static TopBarController instance = null;

    private final TopBar topBar;

    public static TopBarController getInstance() {
        if(instance == null) instance = new TopBarController();
        return instance;
    }

    private TopBarController() {
        topBar = TopBar.getInstance();
    }

    @Override
    public void update(Observable o, Object arg) {
        switch ((AnimationMessages) arg) {
            case REPAINT_TOPBRAID -> {
                if(o instanceof LevelTimer) {
                    topBar.setTimer(((LevelTimer) o).getRemainingTime());
                }
                if(o instanceof Player) {
                    topBar.setCurrentLifeNumber(Player.getLifes());
                    topBar.setScore(Player.getTotalScore());
                }
                setChanged();
                notifyObservers(topBar);
            }

            case GAME_WON, GAME_LOST -> TopBar.removeInstance();
        }
    }
}

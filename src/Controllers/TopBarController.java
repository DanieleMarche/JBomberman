package Controllers;

import Animation.AnimationMessages;
import Controllers.ControllersGerarchy.Controller;
import TopBar.*;
import Bomberman.PlayerModel;

import java.util.Observable;

/**
 *
 * The TopBarController class is responsible for managing and updating the top bar in the Bomberman game.
 * It extends the Controller class and handles updates related to the top bar, such as repainting the top bar elements,
 * including the timer, player's life count, and score.
 *
 * @Author Daniele Marchetilli
 */
public class TopBarController extends Controller {

    /**
     * The singleton instance of the TopBarController class.
     */
    private static TopBarController instance = null;

    /**
     * The reference to the top bar component.
     */
    private TopBar topBar;

    /**
     * Get the singleton instance of the TopBarController class.
     *
     * @return The TopBarController instance.
     */
    public static TopBarController getInstance() {
        if (instance == null) instance = new TopBarController();
        return instance;
    }

    /**
     * Private constructor to ensure a single instance of TopBarController.
     * Use {@link #getInstance()} to obtain the instance.
     */
    private TopBarController() {
    }

    /**
     * Set the top bar component for management by the controller.
     *
     * @param t The TopBar component to be managed by the controller.
     */
    public void setTopBar(TopBar t) {
        this.topBar = t;
    }

    /**
     * Update method called when observing changes in top bar-related objects, such as LevelTimer and Player.
     *
     * @param o   The Observable object that triggered the update (LevelTimer or Player).
     * @param arg The argument representing the type of animation message or the updated object.
     */
    @Override
    public void update(Observable o, Object arg) {
        switch ((AnimationMessages) arg) {
            case REPAINT_TOPBAR -> {
                if (o instanceof LevelTimer) {
                    topBar.setTimer(((LevelTimer) o).getRemainingTime());
                }
                if (o instanceof PlayerModel) {
                    topBar.setCurrentLifeNumber(PlayerModel.getLifes());
                    topBar.setScore(PlayerModel.getTotalScore());
                }
                setChanged();
                notifyObservers(topBar);
            }

            case GAME_LOST -> {
                setChanged();
                notifyObservers(arg);
            }

        }
    }
}


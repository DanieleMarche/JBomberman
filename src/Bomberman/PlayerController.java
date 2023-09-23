package Bomberman;

import Animation.*;
import Controllers.ControllersGerarchy.SingleEntityController;

import java.util.Observable;

/**
 *
 * The PlayerController class is responsible for managing the player character in the Bomberman game.
 * It extends the SingleEntityController class and handles updates related to the player entity,
 * such as repainting the game, removing the player, and handling game outcome messages.
 *
 * @Author Daniele Marchetilli
 */
public class PlayerController extends SingleEntityController<PlayerModel> {

    /**
     * The singleton instance of the PlayerController class.
     */
    private static PlayerController instance = null;

    /**
     * Get the singleton instance of the PlayerController class.
     *
     * @return The PlayerController instance.
     */
    public static PlayerController getInstance() {
        if (instance == null) instance = new PlayerController();
        return instance;
    }

    /**
     * Update method called when observing changes in the player entity.
     *
     * @param o   The Observable object (the player) that triggered the update.
     * @param arg The argument representing the type of animation message or the player entity itself.
     */
    @Override
    public void update(Observable o, Object arg) {
        PlayerModel playerModel;
        if (arg instanceof AnimationMessages) {
            switch ((AnimationMessages) arg) {
                case REPAINT_GAME -> {
                    playerModel = (PlayerModel) o;
                    setChanged();
                    notifyObservers();
                }

                case REMOVE_ELEMENT -> {
                    playerModel = (PlayerModel) o;
                    PlayerModel.removeInstance();
                    setChanged();
                    notifyObservers();
                }

                case GAME_WON -> PlayerModel.removeInstance();

                case GAME_LOST -> {
                    PlayerModel.removeInstance();
                    setChanged();
                    notifyObservers(arg);
                }
            }
        }
    }
}



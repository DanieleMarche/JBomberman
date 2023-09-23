package Controllers;

import Animation.AnimationMessages;
import Controllers.ControllersGerarchy.Controller;
import main.GameModel;
import main.GameView;

import java.util.Observable;

/**
 * The GameStateController class manages the game state and controls input handling.
 * It extends the Controller class and is responsible for updating the game's state
 * and handling user input.
 *
 * @Author Daniele Marchetilli
 */
public class GameStateController extends Controller {

    /**
     * The KeyHandler instance for handling user input.
     */
    private final KeyHandler keyH;

    /**
     * The GameModel representing the game's internal state.
     */
    private final GameModel gamePanel;

    /**
     * Constructs a new GameStateController with references to the GameModel and GameView.
     *
     * @param gameModel The GameModel representing the game's internal state.
     * @param gameView  The GameView associated with the game.
     */
    public GameStateController(GameModel gameModel, GameView gameView) {
        gamePanel = gameModel;
        this.keyH = KeyHandler.getInstance();
        addObserver(gameView);
    }

    /**
     * Updates the game's control based on user input.
     * If the player has requested to stop playing, it toggles the game between PAUSE and PLAY states.
     */
    public void updateControl() {
        if (keyH.stopPlayingAsked()) {
            switch (gamePanel.getGameState()) {
                case PAUSE -> gamePanel.setGameState(GameModel.GameState.PLAY);
                case PLAY -> gamePanel.setGameState(GameModel.GameState.PAUSE);
            }
            keyH.deactivateStopPlaying();
        }
    }

    /**
     * Updates the observer(s) of this GameStateController with game-related messages.
     *
     * @param o   The Observable object (GameStateController) that triggered the update.
     * @param arg The argument representing the type of animation message.
     */
    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        switch ((AnimationMessages) arg) {
            case GAME_WON -> notifyObservers(AnimationMessages.GAME_WON);
            case GAME_LOST -> notifyObservers(AnimationMessages.GAME_LOST);
        }
    }
}


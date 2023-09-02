package Controllers;

import Animation.AnimationMessages;
import Controllers.ControllersGerarchy.Controller;
import main.GameModel;
import main.GameView;

import java.util.Observable;

public class GameStateController extends Controller {

    private final KeyHandler keyH;

    private final GameModel gamePanel;


    public GameStateController(GameModel gameModel, GameView gameView) {
        gamePanel = gameModel;
        this.keyH = KeyHandler.getInstance();
        addObserver(gameView);
    }


    public void updateControl() {
        if(keyH.stopPLayingAsked()) {
            switch (gamePanel.getGameState()) {
                case PAUSE -> gamePanel.setGameState(GameModel.GameState.PLAY);
                case PLAY -> gamePanel.setGameState(GameModel.GameState.PAUSE);
            }
            keyH.deactivateStopPlaying();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        switch((AnimationMessages) arg) {
            case GAME_WON -> notifyObservers(AnimationMessages.GAME_WON);
            case GAME_LOST -> notifyObservers(AnimationMessages.GAME_LOST);
        }
    }
}

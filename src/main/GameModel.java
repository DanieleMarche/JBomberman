package main;

import Animation.AnimationMessages;
import Controllers.ControllersGerarchy.BombController;
import Bomberman.PlayerController;
import Bomberman.PlayerModel;
import Controllers.*;
import Portal.PortalController;
import PowerUp.PowerUpsController;
import Tile.Map;
import TopBar.LevelTimer;
import User.UserModel;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import java.util.Observable;
import java.util.Observer;

public class GameModel extends Observable implements Runnable, Observer {

    private Thread gameThread;
    //Game state
    private GameState gameState;

    private LevelControllers levelControllers;
    private GameStateController gameStateController;

    private Clip gameSong;

    private long songPausedPosition;

    private UserModel user;

    private GameLevel level;

    private AssetManager assetManager;

    private GameView gameView;

    private LevelTimer levelTimer;

    public GameModel(GameView gameView, GameLevel gameLevel) {
        this.user = UserModel.getInstance();
        this.level = user.getLivelloRaggiunto();
        this.gameView = gameView;
        this.level = gameLevel;

    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        switch (gameState) {
            case PLAY -> {

                if(!gameSong.isRunning()) {
                    gameSong.setMicrosecondPosition(songPausedPosition);
                    gameSong.start();
                }
                assetManager.resume();
                this.gameState = GameState.PLAY;
            }

            case PAUSE -> {
                this.gameState = GameState.PAUSE;
                songPausedPosition = gameSong.getMicrosecondPosition();
                gameSong.stop();
                assetManager.pause();
            }

            case WON -> {
                AudioManager.getInstance().play("res/sounds/Super_Bomberman_Sound_Effects/10. Triumph Fanfare.wav");
                int totalScore = PlayerModel.getTotalScore() * (assetManager.getLevelTimer().getRemainingTime()/60);
                switch(level) {
                    case LEVEL_1 -> {
                        if(user.getMaxScoreLiv1() < totalScore) {
                            user.setMaxScoreLiv1(totalScore);
                        }
                    }
                    case LEVEL_2 -> {
                        if(user.getMaxScoreLiv2() < totalScore) {
                            user.setMaxScoreLiv2(totalScore);
                        }
                    }
                }
                user.setLivelloSuperato(true);
                user.increaseLivelliVinti();
                setChanged();
                notifyObservers(AnimationMessages.GAME_WON);
                endGameThread();

            }

            case LOST -> {
                setChanged();
                user.increaseLivelliPersi();
                notifyObservers(AnimationMessages.GAME_LOST);
                endGameThread();
            }
        }
    }

    public void startGameThread() {

        levelTimer = LevelTimer.getInstance();
        levelTimer.setDurationInSeconds(3 * 60);
        levelTimer.start();

        gameState = GameState.PLAY;
        gameStateController = new GameStateController(this, gameView);
        addObserver(gameStateController);
        gameThread = null;

        assetManager = AssetManager.getInstance();
        gameThread = new Thread(this);

        gameSong = AudioManager.getInstance().play("res/sounds/Super_Bomberman_Sound_Effects/03. Triumph Fanfare.wav");

        assetManager.setMap(new Map(user.getLivelloRaggiunto()));

        songPausedPosition = 0L;

        PortalController.getInstance().addObserver(this);
        PlayerController.getInstance().addObserver(this);
        LevelTimer.getInstance().addObserver(this);
        assetManager.getLevelTimer().addObserver(this);

        levelControllers = new LevelControllers(
                gameView,
                this,
                FlameController.getInstance(),
                PortalController.getInstance(),
                PowerUpsController.getInstance(),
                ExplosionController.getInstance(),
                BombController.getInstance(),
                PlayerController.getInstance(),
                EnemyController.getInstance(),
                TopBarController.getInstance());

        gameSong.addLineListener(new LineListener() {
            @Override
            public void update(LineEvent event) {
                if (event.getType() == LineEvent.Type.STOP) {
                    // La prima clip è terminata, avvia la seconda clip
                    gameSong = AudioManager.getInstance().play("res/sounds/Super_Bomberman_Sound_Effects/04. World 1 - Peace Town, Green Village, Black Bomberman's Castle.wav");
                }
            }
        });

        gameThread.start();
    }

    public void endGameThread() {
        gameThread = null;
        levelTimer.stop();
        LevelTimer.removeInstance();
        AssetManager.removeInstance();
        PlayerModel.resetLifes();
        levelControllers.removeAllInstances();
        KeyHandler.removeInstance();
        gameSong.stop();
    }

    @Override
    public void run() {

        int fps = 60;
        double drawInterval = (double) 1000000000 / fps; // 0.01666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {

            update(assetManager);

            try {

                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(AssetManager assetManager) {

        gameStateController.updateControl();

    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof AnimationMessages) {
            switch ((AnimationMessages) arg) {
                case GAME_WON -> setGameState(GameState.WON);
                case GAME_LOST -> setGameState(GameState.LOST);
            }
        }

    }

    public enum GameState {
        PLAY,
        PAUSE,
        WON,
        LOST
    }
}

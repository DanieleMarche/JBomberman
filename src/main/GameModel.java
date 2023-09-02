package main;

import Animation.AnimationMessages;
import Bomb.Bomb;
import Bomberman.Player;
import Controllers.*;
import Flames.Flame;
import Tile.Map;
import TopBar.LevelTimer;
import User.UserModel;
import enemy.Enemy;
import Tile.DestructibleBlock;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class GameModel extends Observable implements Runnable, Observer {

    private Thread gameThread;
    //Game state
    private GameState gameState;

    private LevelController levelController;
    private GameStateController gameStateController;

    private Clip gameSong;

    private long songPausedPosition;
    private LevelTimer levelTimer;

    private UserModel user;

    private GameLevel level;

    private AssetManager assetManager;

    private GameView gameView;

    public GameModel(GameView gameView, UserModel user) {
        this.user = user;
        this.level = user.getLivelloRaggiunto();
        this.gameView = gameView;

    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        switch (gameState) {
            case PLAY -> {
                if(gameState != GameState.WON) {
                    if(!gameSong.isRunning()) {
                        gameSong.setMicrosecondPosition(songPausedPosition);
                        gameSong.start();
                    }

                    if(levelTimer.isPaused()) levelTimer.resume();
                }
                this.gameState = gameState;
            }

            case PAUSE -> {
                this.gameState = GameState.PAUSE;
                songPausedPosition = gameSong.getMicrosecondPosition();
                gameSong.stop();
                levelTimer.pause();
            }

            case WON -> {
                AudioManager.getInstance().play("res/sounds/Super_Bomberman_Sound_Effects/10. Triumph Fanfare.wav");
                int totalScore = Player.getTotalScore() * (levelTimer.getRemainingTime()/60);
                switch(level) {
                    case LEVEL_1 -> {
                        if(user.getMaxScoreLiv1() < Player.getTotalScore()) {
                            user.setMaxScoreLiv1(totalScore);
                        }
                    }
                    case LEVEL_2 -> {
                        if(user.getMaxScoreLiv2() < Player.getTotalScore()) {
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

        gameState = GameState.PLAY;
        gameStateController = new GameStateController(this, gameView);
        addObserver(gameStateController);
        gameThread = null;

        assetManager = AssetManager.getInstance();
        gameThread = new Thread(this);

        gameSong = AudioManager.getInstance().play("res/sounds/Super_Bomberman_Sound_Effects/03. Triumph Fanfare.wav");

        assetManager.setMap(new Map(user.getLivelloRaggiunto()));

        songPausedPosition = 0L;

        levelTimer = new LevelTimer();

        PortalController.getInstance().addObserver(this);
        PlayerController.getInstance().addObserver(this);
        levelTimer.addObserver(this);

        levelController = new LevelController(
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
        AssetManager.removeInstance();
        levelController.removeAllInstances();
        levelTimer.stop();
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

        switch (gameState) {
            case PLAY -> {

                ((ArrayList<DestructibleBlock>)assetManager.getDestructibleBlocks().clone()).forEach(destructibleBlock -> destructibleBlock.animate.updateAnimation());

                ((ArrayList<Flame>) assetManager.getFlames().clone()).forEach(flame -> flame.animate.updateAnimation());

                ((ArrayList<Bomb>) assetManager.getBombs().clone()).forEach(bomb -> bomb.animate.updateAnimation());

                Player.getInstance().getMovingInstructions().followMovingInstruction();

                ((ArrayList<Enemy>)assetManager.getEnemies().clone()).forEach(enemy -> enemy.getMovingInstructions().followMovingInstruction());

            }

            case PAUSE -> {
                //do nothing
            }

        }

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

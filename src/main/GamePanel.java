package main;

import Bomb.Bomb;
import Controllers.*;
import Explosion.Explosion;
import PowerUp.PowerUp;
import player.Player;
import tile.DestructibleBlock;
import tile.Map;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import static Explosion.Explosion.explosions;

public class GamePanel extends JPanel implements Runnable {
    //how many times to scale the tiles
    public static final int originalTileSize = 16;
    public static final int tileScale = 3;
    public static final int tileSize = originalTileSize * tileScale;
    public static int maxScreenColumns = 15;
    public static final int maxScreenRows = 13;
    public static final int screenWidth = maxScreenColumns * tileSize; //768
    public static final int screenHeight = maxScreenRows * tileSize; //576

    private Thread gameThread;

    private final Player player;
    private final Map map;

    //Controllers

    private final DestructibleBlocksController destructibleBlocksController;
    private final BombController bombController;
    private final ExplosionController explosionController;
    private final PlayerController playerController;
    private final GameStateController gameStateController;


    private final ArrayList<PowerUp> powerUps;
    private final ArrayList<Bomb> bombs;

    //World settings
    public final static int maxWorldCol = 15;
    public final static int maxWorldRow = 13;
    public static final int wordlWidth = tileSize * maxWorldCol;
    public static final  int worldHeight = tileSize * maxWorldRow;

    //Game state
    private int gameState;
    public static final int playState = 1;
    public static final int pauseState = 2;


    public GamePanel() {

        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.gray);

        gameState = playState;

        AssetSetter assetSetter = new AssetSetter(this);

        powerUps = new ArrayList<>();
        bombs = Bomb.bombs;

        assetSetter.setObject();

        //Controllers
        KeyHandler keyH = new KeyHandler();
        addKeyListener(keyH);
        gameStateController = new GameStateController(keyH, this);


        player = Player.getInstance(96, 24, 3, this);

        destructibleBlocksController = new DestructibleBlocksController(this);

        map = Map.getInstance(this, "/maps/level_1.txt", destructibleBlocksController);
        destructibleBlocksController.setMap(map);




        explosionController = new ExplosionController(this, player, map);
        bombController = new BombController(this, player, explosionController);

        CollisionDetector collisionDetector = new CollisionDetector(map, bombController);


        playerController = new PlayerController(player, keyH, bombController, collisionDetector, this);
        player.addObserver(playerController);

        setDoubleBuffered(true);
        setFocusable(true);

    }

    public ArrayList<PowerUp> getPowerUps() {
        return powerUps;
    }

    public void addBomb (Bomb b) {bombs.add(b);}
    public void addPowerUp(PowerUp powerUp) {
        this.powerUps.add(powerUp);
    }

    //GameState methods
    public void setPauseState() {
        gameState = pauseState;
    }

    public void setPlayState() {
        gameState = playState;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void endGameThread() {
        gameThread.interrupt();
    }

    @Override
    public void run() {

        int fps = 60;
        double drawInterval = (double) 1000000000 / fps; // 0.01666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {

            update();

            //repaint();

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

    public void update() {

        gameStateController.checkChangeState();

        if(gameState == playState) {

            playerController.update();

            destructibleBlocksController.updateAnimation();

            bombController.updateAnimation();

            explosionController.updateAnimation();

        }
        if(gameState == pauseState) {
            //nothing
        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        map.draw(g2);

        //powerUps.forEach(pUps -> pUps.draw(g2, player));

        bombs.forEach(b -> b.draw(g2));

        DestructibleBlock.destructibleBlocks.forEach(db -> db.draw(g2));

        explosions.forEach(e -> e.draw(g2));

        player.draw(g2);

        g2.dispose();
    }
}
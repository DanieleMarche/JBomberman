package main;

import Animation.AnimationMessages;
import Controllers.*;
import TopBar.TopBar;
import User.UserModel;
import entityGerarchy.Entity;
import Bomberman.Player;
import Tile.*;

import Tile.tileGerarchy.Tile;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class GameView extends JPanel implements Observer {

    //how many times to scale the tiles
    public static final int topBarHeight = 96;
    public static final int originalTileSize = 16;
    public static final int tileScale = 3;

    public static final int tileSize = originalTileSize * tileScale;
    public static int maxScreenColumns = 15;
    public static final int maxScreenRows = 13;
    public static final int screenWidth = maxScreenColumns * tileSize; //768
    public static final int screenHeight = (maxScreenRows * tileSize) + topBarHeight; //576

    //World settings
    public final static int maxWorldCol = 15;
    public final static int maxWorldRow = 13;
    public static final int wordlWidth = tileSize * maxWorldCol;
    public static final  int worldHeight = tileSize * maxWorldRow;

    CardLayout cardLayout;
    JFrame parentFrame;
    JPanel parentPanel;
    UserModel usermodel;

    public GameView(CardLayout cardLayout, JFrame parentFrame, JPanel parentPanel, UserModel usermodel) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.gray);
        addKeyListener(KeyHandler.getInstance());
        requestFocusInWindow();

        this.cardLayout = cardLayout;
        this.parentPanel = parentPanel;
        this.parentFrame = parentFrame;
        this.usermodel = usermodel;


        setDoubleBuffered(true);
        setFocusable(true);
        requestFocus();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        AssetManager assetManager = AssetManager.getInstance();

        if(assetManager.getMap() != null) assetManager.getMap().draw(g2);

        if(assetManager.getPortal() != null) assetManager.getPortal().draw(g2);

        assetManager.getBombs().forEach(b -> b.draw(g2));

        assetManager.getDestructibleBlocks().forEach(db -> db.draw(g2));

        assetManager.getPowerUps().forEach(pUps -> pUps.draw(g2));

        assetManager.getFlames().forEach(flame -> flame.draw(g2));

        assetManager.getEnemies().forEach(enemy -> enemy.draw(g2));

        Player.getInstance().draw(g2);

        TopBar.getInstance().draw(g2);

        g2.dispose();

    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof Entity) {
            repaint(((Entity) arg).getWorldPositionX(), ((Entity) arg).getWorldPositionY(), ((Entity) arg).getWidth(), ((Entity) arg).getHeight());
        }
        if(arg instanceof Tile) {
            repaint(((Tile) arg).getPositionXOnScreen(), ((Tile) arg).getPositionYOnScreen(), GameView.tileSize, GameView.tileSize);
        }
        if(arg instanceof TopBar) {
            repaint(new Rectangle(0,0, GameView.screenWidth, GameView.topBarHeight));
        }
        if(arg == null) {
            repaint(0, 0, GameView.screenWidth, GameView.screenHeight);
        }

        if(arg instanceof AnimationMessages) {
            switch((AnimationMessages) arg) {
                case GAME_WON -> {
                    parentPanel.add(new LevelWonPanel(cardLayout, parentFrame, parentPanel), "LevelWonPanel");
                    cardLayout.show(parentPanel, "LevelWonPanel");
                }

                case GAME_LOST -> {
                    parentPanel.add(new LostPanel(cardLayout, parentFrame, parentPanel, usermodel), "LostScreen");
                    cardLayout.show(parentPanel, "LostScreen");
                }
            }
        }


    }

}
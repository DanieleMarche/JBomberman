package main;

import Animation.AnimationMessages;
import Animation.Drawable;
import Controllers.*;
import TopBar.TopBar;
import User.UserModel;
import EntityModelGerarchy.Entity;
import Bomberman.PlayerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

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
    JFrame mainFrame;
    JPanel mainPanel;
    UserModel usermodel;

    public GameView(CardLayout cardLayout, JFrame mainFrame, JFrame parentFrame, JPanel mainPanel, UserModel usermodel) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.gray);
        addKeyListener(KeyHandler.getInstance());
        requestFocusInWindow();

        this.cardLayout = cardLayout;
        this.mainFrame = mainFrame;
        this.mainPanel = mainPanel;
        this.parentFrame = parentFrame;
        this.usermodel = usermodel;

        setDoubleBuffered(true);
        setFocusable(true);
        requestFocus();

    }

    private void drawElement(Drawable e, Graphics2D g2) {
        Optional<BufferedImage> image = e.getImage();
        Point position = e.getPosition();
        image.ifPresent(i -> g2.drawImage(i, position.x, position.y, i.getWidth() * GameView.tileScale, i.getHeight() * GameView.tileScale, null));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        AssetManager assetManager = AssetManager.getInstance();

        if(assetManager.getMap() != null) {
            Arrays.stream(assetManager.getMap().getMap())
                    .flatMap(Arrays::stream)
                    .toList().forEach(tile -> drawElement(tile, g2));
        }

        if(assetManager.getPortal() != null) drawElement(assetManager.getPortal(), g2);

        assetManager.getBombs().forEach(b -> drawElement(b, g2));

        assetManager.getPowerUps().forEach(pUps -> {drawElement(pUps, g2);});

        assetManager.getFlames().forEach(flame -> drawElement(flame, g2));

        assetManager.getEnemies().forEach(enemy -> drawElement(enemy, g2));

        drawElement(PlayerModel.getInstance(), g2);

        drawElement(assetManager.getTopBar(), g2);

        g2.dispose();

    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof Entity) {
            repaint(((Entity) arg).getWorldPositionX(), ((Entity) arg).getWorldPositionY(), ((Entity) arg).getWidth(), ((Entity) arg).getHeight());
        }
        if(arg instanceof TopBar) {
            repaint(new Rectangle(0,0, GameView.screenWidth, GameView.topBarHeight));
        }
        if(arg == null) {
            repaint();
        }

        if(arg instanceof AnimationMessages) {
            switch((AnimationMessages) arg) {
                case GAME_WON -> {
                    parentFrame.dispose();
                    mainFrame.setVisible(true);
                    cardLayout.show(mainPanel, "LevelWonPanel");
                }

                case GAME_LOST -> {
                    parentFrame.dispose();
                    mainFrame.setVisible(true);
                    cardLayout.show(mainPanel, "LostScreen");
                }
            }
        }


    }

}
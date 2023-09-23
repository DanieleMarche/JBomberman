package main;

import User.UserModel;
import main.GameModel;
import main.GameView;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private GameModel gameModel;
    private GameView gameView;

    private UserModel user;

    public GameFrame(JFrame mainFrame, JPanel mainPanel, CardLayout mainCardLayout) {
        user = UserModel.getInstance();
        setTitle("JBomberman");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(GameView.screenWidth, GameView.screenHeight);
        setResizable(false); // Impedisci il ridimensionamento della finestra
        setLocationRelativeTo(null);
        gameView = new GameView(mainCardLayout, mainFrame,this, mainPanel, UserModel.getInstance());// Centra la finestra sullo schermo

        add(gameView);
        startGame();
        setVisible(true);
    }

    public GameFrame(JFrame mainFrame, JPanel mainPanel, CardLayout mainCardLayout, GameLevel level) {
        user = UserModel.getInstance();
        setTitle("JBomberman");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(GameView.screenWidth, GameView.screenHeight);
        setResizable(false); // Impedisci il ridimensionamento della finestra
        setLocationRelativeTo(null);
        gameView = new GameView(mainCardLayout, mainFrame,this, mainPanel, UserModel.getInstance());// Centra la finestra sullo schermo

        add(gameView);
        startGame(level);
        setVisible(true);
    }

    public void startGame() {
        gameModel = new GameModel(gameView, user.getLivelloRaggiunto());
        gameModel.startGameThread();
    }

    public void startGame(GameLevel level) {
        gameModel = new GameModel(gameView, level);
        gameModel.startGameThread();
    }
}

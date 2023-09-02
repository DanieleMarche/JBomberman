package main;

import javax.swing.*;
import java.awt.*;

import User.UserController;
import User.UserModel;
import Utils.MethodUtils;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class JBomberMan {

    public static GameModel gameModel;


    public static void main(String[] args) {
        JFrame window = new JFrame();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("JBomberman");

        CardLayout cardLayout = new CardLayout();
        JPanel contentPanel = new JPanel(cardLayout);

        HashMap<String, Object> gameDataMap = (HashMap<String, Object>) MethodUtils.readJsonMapFromFile("res/user-data.json");

        UserCreationPanel ucp = new UserCreationPanel(cardLayout, window, contentPanel);




        if (gameDataMap.isEmpty()) {
            contentPanel.add(ucp, "UserCreationPanel");
            cardLayout.show(contentPanel,"UserCreationPanel");
        }

        UserController.getInstance().loadUserData();

        GameView gamePanel =  new GameView(cardLayout, window, contentPanel, UserModel.getInstance());

        SuperBombermanMenuPanel sbmp = new SuperBombermanMenuPanel(cardLayout, window, contentPanel, UserModel.getInstance(), gamePanel);
        contentPanel.add(sbmp, "IntroScreen");
        cardLayout.show(contentPanel, "IntroScreen");





        contentPanel.add(gamePanel, "GameView");



        window.add(contentPanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}


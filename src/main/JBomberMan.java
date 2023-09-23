package main;

import javax.swing.*;
import java.awt.*;

import User.UserModel;
import Utils.MethodUtils;
import main.MenuPages.*;

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


        if (gameDataMap.isEmpty()) {
            UserCreationPanel ucp = new UserCreationPanel(cardLayout, window, contentPanel);
            contentPanel.add(ucp, "UserCreationPanel");
            cardLayout.show(contentPanel,"UserCreationPanel");
        } else{
            UserModel.getInstance().loadUserData();
            SuperBombermanMenuPanel sbmp = new SuperBombermanMenuPanel(cardLayout, window, contentPanel, UserModel.getInstance());
            contentPanel.add(sbmp, "IntroScreen");
            cardLayout.show(contentPanel, "IntroScreen");
        }

        contentPanel.add(new LevelsPage(cardLayout, window, contentPanel), "LevelsPage");

        contentPanel.add(new Options(cardLayout, window, contentPanel), "OptionsPage");

        contentPanel.add(new Statistiche(cardLayout, contentPanel, UserModel.getInstance()), "StatisticsPage");

        contentPanel.add(new LevelWonPanel(cardLayout, window, contentPanel), "LevelWonPanel");

        contentPanel.add(new LostPanel(cardLayout, window, contentPanel, UserModel.getInstance()), "LostScreen");



        window.add(contentPanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}


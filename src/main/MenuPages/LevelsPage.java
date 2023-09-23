package main.MenuPages;

import User.UserModel;
import main.GameFrame;
import main.GameLevel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LevelsPage extends JPanel {

    public LevelsPage(CardLayout cardLayout, JFrame parentFrame, JPanel parentPanel) {
        setLayout(new GridLayout(2, 1));

        JButton livello1Button = new JButton("Gioca Livello 1");
        JButton livello2Button = new JButton("Gioca Livello 2");

        if(UserModel.getInstance().getLivelloRaggiunto().getLevelCode() < 2) {
            livello2Button.setEnabled(false);
        }

        livello1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameFrame(parentFrame, parentPanel, cardLayout, GameLevel.LEVEL_1);
                parentFrame.setVisible(false);
            }
        });

        livello2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameFrame(parentFrame, parentPanel, cardLayout, GameLevel.LEVEL_2);
                parentFrame.setVisible(false);
            }
        });

        UserModel.getInstance().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("livelloRaggiunto".equals(evt.getPropertyName())) {
                    // Aggiorna il testo dello startButton quando cambia il livello raggiunto
                    livello2Button.setEnabled(true);
                }
            }
        });

        add(livello1Button);
        add(livello2Button);

        JButton backButton = new JButton("Torna al Menu Principale");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(parentPanel, "IntroScreen"); // Cambia al pannello principale
            }
        });

        add(backButton);
    }
}
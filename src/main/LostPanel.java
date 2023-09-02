package main;

import User.UserModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LostPanel extends JPanel {
    private JLabel titleLabel;
    private JButton restartButton;
    private JButton mainMenuButton;

    public LostPanel(CardLayout cardLayout, JFrame parentFrame, JPanel parentPanel, UserModel usermodel) {
        setLayout(new BorderLayout());

        titleLabel = new JLabel("Hai perso");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        restartButton = new JButton("Ricomincia livello " + usermodel.getLivelloRaggiunto().getLevelCode() + ".");
        mainMenuButton = new JButton("Torna al menu principale");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(restartButton);
        buttonPanel.add(mainMenuButton);
        add(buttonPanel, BorderLayout.CENTER);

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(parentPanel, "IntroScreen");
            }
        });
    }
}


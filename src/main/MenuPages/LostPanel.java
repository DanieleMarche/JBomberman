package main.MenuPages;
import User.UserModel;
import main.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LostPanel extends JPanel {
    private JLabel titleLabel;
    private JButton restartButton;
    private JButton mainMenuButton;

    public LostPanel(CardLayout cardLayout, JFrame parentFrame, JPanel parentPanel, UserModel usermodel) {
        setLayout(new GridBagLayout()); // Use GridBagLayout for centering

        titleLabel = new JLabel("Hai perso");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        restartButton = new JButton("Ricomincia livello " + usermodel.getLivelloRaggiunto().getLevelCode() + ".");
        mainMenuButton = new JButton("Torna al menu principale");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(restartButton);
        buttonPanel.add(mainMenuButton);

        // Create a GridBagConstraints to center components
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0; // Make components expand horizontally
        gbc.insets = new Insets(10, 10, 10, 10); // Add some padding

        // Add titleLabel at the top
        gbc.anchor = GridBagConstraints.PAGE_START;
        add(titleLabel, gbc);

        // Add buttonPanel in the center
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameFrame(parentFrame, parentPanel, cardLayout);
                parentFrame.setVisible(false);
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



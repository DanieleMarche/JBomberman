package main.MenuPages;

import User.UserModel;
import main.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LevelWonPanel extends JPanel implements ActionListener {
    private JLabel titleLabel;
    private JButton nextLevelButton;
    private JButton mainMenuButton;

    CardLayout cardLayout;

    JFrame parentFrame;

    JPanel parentPanel;

    public LevelWonPanel(CardLayout cardLayout, JFrame parentFrame, JPanel parentPanel, UserModel user) {
        setPreferredSize(new Dimension(400, 400));
        setLayout(new GridBagLayout());
        this.cardLayout = cardLayout;
        this.parentPanel = parentPanel;
        this.parentFrame = parentFrame;

        titleLabel = new JLabel("Livello superato!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Create a GridBagConstraints to center components
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0; // Make components expand horizontally
        gbc.insets = new Insets(10, 10, 10, 10); // Add some padding

        // Add titleLabel at the top
        gbc.anchor = GridBagConstraints.PAGE_START;

        add(titleLabel, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        nextLevelButton = new JButton("Gioca il livello " + user.getLivelloRaggiunto().getLevelCode());
        nextLevelButton.addActionListener(this);

        mainMenuButton = new JButton("Torna al menu principale");
        mainMenuButton.addActionListener(this);

        buttonPanel.add(nextLevelButton);
        buttonPanel.add(mainMenuButton);

        // Add buttonPanel in the center
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        user.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("livelloRaggiunto".equals(evt.getPropertyName())) {
                    // Aggiorna il testo dello startButton quando cambia il livello raggiunto
                    nextLevelButton.setText("Gioca il livello " + user.getLivelloRaggiunto().getLevelCode());
                }
            }
        });

        parentFrame.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nextLevelButton) {
            new GameFrame(parentFrame, parentPanel, cardLayout);
            parentFrame.setVisible(false);
        } else if (e.getSource() == mainMenuButton) {
            parentPanel.remove(this);
            cardLayout.show(parentPanel, "IntroScreen");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("JBomberman");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 400);


                CardLayout cardLayout = new CardLayout();
                JPanel parentPanel = new JPanel(cardLayout);

                LevelWonPanel levelWonPanel = new LevelWonPanel(cardLayout, frame, parentPanel, UserModel.getInstance());

                parentPanel.add(levelWonPanel, "LevelWonPanel");

                frame.add(parentPanel);
                frame.setVisible(true);
            }
        });

    }

}



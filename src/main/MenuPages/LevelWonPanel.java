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

    public LevelWonPanel(CardLayout cardLayout, JFrame parentFrame, JPanel parentPanel) {
        setPreferredSize(new Dimension(400, 400));
        setLayout(new BorderLayout());
        this.cardLayout = cardLayout;
        this.parentPanel = parentPanel;
        this.parentFrame = parentFrame;

        UserModel user = UserModel.getInstance();

        titleLabel = new JLabel("Livello superato!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        nextLevelButton = new JButton("Gioca il livello " + user.getLivelloRaggiunto());
        nextLevelButton.addActionListener(this);
        buttonPanel.add(nextLevelButton);

        user.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("livelloRaggiunto".equals(evt.getPropertyName())) {
                    // Aggiorna il testo dello startButton quando cambia il livello raggiunto
                    nextLevelButton.setText("Gioca il livello " + user.getLivelloRaggiunto().getLevelCode());
                }
            }
        });

        mainMenuButton = new JButton("Torna al menu principale");
        mainMenuButton.addActionListener(this);
        buttonPanel.add(mainMenuButton);

        // Aggiunta di uno spazio vuoto in basso per centrare il contenuto
        add(Box.createVerticalStrut(100), BorderLayout.SOUTH);

        // Aggiunta di uno spazio vuoto a sinistra per centrare il contenuto
        add(Box.createHorizontalStrut(100), BorderLayout.WEST);

        add(buttonPanel, BorderLayout.CENTER);
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
                JFrame frame = new JFrame("Level Won Test");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 400);
                frame.setResizable(false);

                CardLayout cardLayout = new CardLayout();
                JPanel parentPanel = new JPanel(cardLayout);

                LevelWonPanel levelWonPanel = new LevelWonPanel(cardLayout, frame, parentPanel);

                parentPanel.add(levelWonPanel, "LevelWonPanel");

                frame.add(parentPanel);
                frame.setVisible(true);
            }
        });

    }

}



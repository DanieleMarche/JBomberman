package main.MenuPages;

import User.UserModel;
import main.GameFrame; // Assumi che LevelsPage sia una classe valida nel tuo progetto

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SuperBombermanMenuPanel extends JPanel {

    private CardLayout cardLayout;
    private JFrame parentFrame;

    public SuperBombermanMenuPanel(CardLayout cardLayout, JFrame parentFrame, JPanel parentPanel, UserModel usermodel) {
        this.parentFrame = parentFrame;
        setPreferredSize(new Dimension(500, 300));
        setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0; // Make components expand horizontally
        gbc.insets = new Insets(10, 10, 10, 10); // Add some padding

        // Add titleLabel at the top
        gbc.anchor = GridBagConstraints.PAGE_START;

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

// Add this line to center-align the title
        titlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("JBomberman");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titlePanel.add(titleLabel);

        JLabel subtitleLabel = new JLabel("Bomberman " + usermodel.getUsername() + ", sei pronto a salvare il mondo?");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        titlePanel.add(subtitleLabel);

        contentPanel.add(titlePanel, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout()); // Layout a flusso (allineamento orizzontale)

        JButton startButton = new JButton("Start level " + UserModel.getInstance().getLivelloRaggiunto().getLevelCode());
        JButton saveButton = new JButton("Salva progressi");
        JButton replayButton = new JButton("Rigioca livelli"); // Bottone "Rigioca livelli"
        JButton statisticsButton = new JButton("Statistiche");
        JButton optionsButton = new JButton("Options");
        JButton exitButton = new JButton("Exit");

        // Dichiarazione del JLabel per i dati salvati correttamente
        JLabel savedLabel = new JLabel("Dati salvati correttamente");
        savedLabel.setForeground(Color.BLACK); // Imposta il colore del testo a verde
        savedLabel.setVisible(false); // Imposta il JLabel inizialmente invisibile
        contentPanel.add(savedLabel, gbc);

        // ActionListener per il Timer che nasconde il JLabel dopo un ritardo
        ActionListener hideSavedLabel = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                savedLabel.setVisible(false);
            }
        };

        // Crea un Timer con un ritardo di 2 secondi (2000 millisecondi)
        Timer timer = new Timer(2000, hideSavedLabel);

        usermodel.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("username".equals(evt.getPropertyName())) {
                    // Aggiorna il sottotitolo quando cambia il nome utente
                    subtitleLabel.setText("Bomberman " + usermodel.getUsername() + ", sei pronto a salvare il mondo?");
                } else if ("livelloRaggiunto".equals(evt.getPropertyName())) {
                    // Aggiorna il testo dello startButton quando cambia il livello raggiunto
                    startButton.setText("Start level " + usermodel.getLivelloRaggiunto().getLevelCode());
                }
            }
        });

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Codice per avviare il gioco
                // Esempio: Avviare un nuovo JFrame per il gioco effettivo
                startButton.setFocusable(false);

                new GameFrame(parentFrame, parentPanel, cardLayout);
                parentFrame.setVisible(false);

                revalidate();
                repaint();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Codice per salvare i progressi dell'utente
                // Esempio: Chiama un metodo per salvare i progressi nel tuo UserController
                UserModel.getInstance().saveData(usermodel);

                // Mostra il JLabel "Dati salvati correttamente"
                savedLabel.setVisible(true);

                // Avvia il Timer per nascondere il JLabel dopo 2 secondi
                timer.restart();
            }
        });

        replayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Codice per gestire il click su "Rigioca livelli"
                cardLayout.show(parentPanel, "LevelsPage"); // Mostra la pagina LevelsPage
            }
        });

        statisticsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(parentPanel, "StatisticsPage");
            }
        });

        optionsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(parentPanel, "OptionsPage");
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Uscire dall'applicazione
            }
        });

        buttonPanel.add(startButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(replayButton); // Aggiunto il bottone "Rigioca livelli"
        buttonPanel.add(statisticsButton);
        buttonPanel.add(optionsButton);
        buttonPanel.add(exitButton);

        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        contentPanel.add(buttonPanel, gbc);

        // Aggiungi margini utilizzando EmptyBorder
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(contentPanel, BorderLayout.CENTER);
        parentFrame.pack();
    }

}



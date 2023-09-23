package main.MenuPages;

import User.UserModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Statistiche extends JPanel {

    private JLabel livelloRaggiuntoLabel;
    private JLabel maxScoreLiv1Label;
    private JLabel maxScoreLiv2Label;
    private JLabel livelliPersiLabel;
    private JLabel livelliVintiLabel;
    private JLabel totalScoreLabel;

    public Statistiche(CardLayout cardLayout, JPanel parentPanel, UserModel user) {
        // Crea un pannello per contenere tutto centrato
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS)); // Layout a colonna

        // Aggiungi il titolo "Statistiche"
        JLabel titleLabel = new JLabel("Statistiche");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centra il titolo
        centerPanel.add(titleLabel);

        // Imposta il layout del pannello a GridLayout con una colonna
        setLayout(new BorderLayout());
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centra il contenuto
        add(centerPanel, BorderLayout.CENTER);

        livelloRaggiuntoLabel = new JLabel("Livello Raggiunto: " + user.getLivelloRaggiunto().getLevelCode());
        maxScoreLiv1Label = new JLabel("Max score Livello 1: " + user.getMaxScoreLiv1());
        maxScoreLiv2Label = new JLabel("Max score Livello 2: " + user.getMaxScoreLiv2());
        livelliPersiLabel = new JLabel("Livelli Persi: " + user.getLivelliPersi());
        livelliVintiLabel = new JLabel("Livelli Vinti: " + user.getLivelliVinti());
        totalScoreLabel = new JLabel("Total Score: " + user.getTotalScore());

        // Aggiungi le etichette per visualizzare i dati delle statistiche
        centerPanel.add(createStatPanel(livelloRaggiuntoLabel));
        centerPanel.add(createStatPanel(maxScoreLiv1Label));
        centerPanel.add(createStatPanel(maxScoreLiv2Label));
        centerPanel.add(createStatPanel(livelliPersiLabel));
        centerPanel.add(createStatPanel(livelliVintiLabel));
        centerPanel.add(createStatPanel(totalScoreLabel));

        user.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("livelloRaggiunto".equals(evt.getPropertyName())) {
                    livelloRaggiuntoLabel.setText("Livello Raggiunto: " + user.getLivelloRaggiunto().getLevelCode());
                } else if ("maxScoreLiv1".equals(evt.getPropertyName())) {
                    maxScoreLiv1Label.setText("Max score Livello 1: " + user.getMaxScoreLiv1());
                } else if (("maxScoreLiv2".equals(evt.getPropertyName()))) {
                    maxScoreLiv2Label.setText("Max score Livello 2: " + user.getMaxScoreLiv2());
                } else if ("livelliPersi".equals(evt.getPropertyName())) {
                    livelliPersiLabel.setText("Livelli Persi: " + user.getLivelliPersi());
                } else if ("livelliVinti".equals(evt.getPropertyName())) {
                    livelliVintiLabel.setText("Livelli Vinti: " + user.getLivelliVinti());
                } else if ("totalScore".equals(evt.getPropertyName())) {
                    totalScoreLabel.setText("Total Score: " + user.getTotalScore());
                }
            }
        });

        // Aggiungi il bottone "Torna al menu principale"
        JButton backToMainMenuButton = new JButton("Torna al menu principale");
        backToMainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Tua logica per tornare al menu principale
                cardLayout.show(parentPanel, "IntroScreen");
            }
        });
        centerPanel.add(createStatPanel(backToMainMenuButton));

        // Centra il pannello delle statistiche nella finestra
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private JPanel createStatPanel(Component component) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(component);
        return panel;
    }
}







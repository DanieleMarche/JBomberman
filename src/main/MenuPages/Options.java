package main.MenuPages;


import User.UserModel;
import Utils.ImageUtils;
import main.JBomberMan;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import static Utils.ImageUtils.scaleImage;

public class Options extends JPanel {
    private JLabel titleLabel;
    private JLabel usernameLabel;
    private JTextField usernameField;
    private JLabel avatarLabel;
    private JRadioButton avatar1RadioButton;
    private JRadioButton avatar2RadioButton;
    private JRadioButton avatar3RadioButton;
    private JButton saveButton;
    private JButton backButton;
    private JButton newGameButton;

    private CardLayout cardLayout;
    private JFrame parentFrame;
    private JPanel parentPanel;

    public Options(CardLayout cardLayout, JFrame parentFrame, JPanel parentPanel) {
        setBorder(new EmptyBorder(10, 30, 10, 0));
        setLayout(new GridLayout(5, 1));

        this.cardLayout = cardLayout;
        this.parentFrame = parentFrame;
        this.parentPanel = parentPanel;

        titleLabel = new JLabel("Opzioni");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Centra il titolo
        add(titleLabel);

        usernameLabel = new JLabel("Cambia username:");
        usernameField = new JTextField(20);
        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);
        add(usernamePanel);

        BufferedImage whiteAvatar = ImageUtils.loadImage("res/bombermanSprites/white/front/bomberman_front_02.png");
        BufferedImage redAvatar = ImageUtils.loadImage("res/bombermanSprites/red/front/red-bomberman-front-2.png");
        BufferedImage blackAvatar = ImageUtils.loadImage("res/bombermanSprites/black/front/black-bomberman-front-2.png");

        whiteAvatar = scaleImage(whiteAvatar, 16 * 2, 24 * 2); // Scala l'immagine
        redAvatar = scaleImage(redAvatar, 16 * 2, 24 * 2);
        blackAvatar = scaleImage(blackAvatar, 16 * 2, 24 * 2);

        avatarLabel = new JLabel("Cambia colore avatar:");
        avatar1RadioButton = new JRadioButton(new ImageIcon(whiteAvatar));
        avatar2RadioButton = new JRadioButton(new ImageIcon(blackAvatar));
        avatar3RadioButton = new JRadioButton(new ImageIcon(redAvatar));

        ButtonGroup avatarGroup = new ButtonGroup();
        avatarGroup.add(avatar1RadioButton);
        avatarGroup.add(avatar2RadioButton);
        avatarGroup.add(avatar3RadioButton);
        JPanel avatarPanel = new JPanel(new GridLayout(1, 0)); // 1 riga, 0 colonne
        avatarPanel.add(avatarLabel);
        avatarPanel.add(avatar1RadioButton);
        avatarPanel.add(avatar2RadioButton);
        avatarPanel.add(avatar3RadioButton);
        add(avatarPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        saveButton = new JButton("Salva dati modificati");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!usernameField.getText().isEmpty()) {
                    UserModel.getInstance().setUsername(usernameField.getText());
                }

                UserModel.ColoreAvatar coloreAvatar = null;

                if (avatar1RadioButton.isSelected()) coloreAvatar = UserModel.ColoreAvatar.white;
                if (avatar2RadioButton.isSelected()) coloreAvatar = UserModel.ColoreAvatar.black;
                if (avatar3RadioButton.isSelected()) coloreAvatar = UserModel.ColoreAvatar.red;

                if(coloreAvatar != null) UserModel.getInstance().setAvatar(coloreAvatar);

                UserModel.getInstance().saveData(UserModel.getInstance());

                // Passa alla finestra sbmp dopo aver salvato l'utente
                cardLayout.show(parentPanel, "IntroScreen");
            }
        });
        buttonPanel.add(saveButton);

        backButton = new JButton("Torna al menu principale");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(parentPanel, "IntroScreen");
            }
        });
        buttonPanel.add(backButton);

        newGameButton = new JButton("Elimina tutti i dati e riavvia il gioco");

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                UserModel.getInstance().removeAllData();

                parentFrame.dispose();

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        JBomberMan.main(new String[]{});
                    }
                });

            }
        });
        buttonPanel.add(newGameButton);


        add(buttonPanel);
        parentFrame.pack();
    }
}

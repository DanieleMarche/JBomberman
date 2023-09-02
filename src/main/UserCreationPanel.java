package main;

import User.UserController;
import User.UserModel;
import Utils.ImageUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import static Utils.ImageUtils.scaleImage;

public class UserCreationPanel extends JPanel {
    private JLabel titleLabel;
    private JLabel usernameLabel;
    private JTextField usernameField;
    private JLabel avatarLabel;
    private JRadioButton avatar1RadioButton;
    private JRadioButton avatar2RadioButton;
    private JRadioButton avatar3RadioButton;
    private JLabel errorLabel;
    private JButton saveButton;

    public UserCreationPanel(CardLayout cardLayout, JFrame parentFrame, JPanel parentPanel) {
        setBorder(new EmptyBorder(10, 30, 10, 0));
        setLayout(new GridLayout(5, 1));

        titleLabel = new JLabel("Crea un utente");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Centra il titolo
        add(titleLabel);

        usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);
        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);
        add(usernamePanel);

        BufferedImage avatar1Image = ImageUtils.loadImage("res/bomberMan/white/front/bomberman_front_02.png");
        BufferedImage avatar2Image = ImageUtils.loadImage("res/bomberMan/red/front/red-bomberman-front-1.png");
        BufferedImage avatar3Image = ImageUtils.loadImage("res/bomberMan/black/front/black-bomberman-front-1.png");

        avatar1Image = scaleImage(avatar1Image, 16 * 2, 24 * 2); // Scala l'immagine
        avatar2Image = scaleImage(avatar2Image, 16 * 2, 24 * 2);
        avatar3Image = scaleImage(avatar3Image, 16 * 2, 24 * 2);

        avatarLabel = new JLabel("Colore avatar:");
        avatar1RadioButton = new JRadioButton(new ImageIcon(avatar1Image));
        avatar2RadioButton = new JRadioButton(new ImageIcon(avatar1Image));
        avatar3RadioButton = new JRadioButton(new ImageIcon(avatar1Image));

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

        errorLabel = new JLabel("Compilare tutti i campi!");
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);
        add(errorLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        saveButton = new JButton("Salva");
        saveButton.setPreferredSize(new Dimension(80, 30));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (usernameField.getText().isEmpty() ||
                        (!avatar1RadioButton.isSelected() &&
                                !avatar2RadioButton.isSelected() &&
                                !avatar3RadioButton.isSelected())) {
                    errorLabel.setVisible(true);
                } else {
                    errorLabel.setVisible(false);
                    UserModel.ColoreAvatar coloreAvatar = UserModel.ColoreAvatar.red;

                    if (avatar1RadioButton.isSelected()) coloreAvatar = UserModel.ColoreAvatar.red;
                    if (avatar2RadioButton.isSelected()) coloreAvatar = UserModel.ColoreAvatar.white;
                    if (avatar3RadioButton.isSelected()) coloreAvatar = UserModel.ColoreAvatar.black;

                    UserController.getInstance().addAndSaveNewUser(usernameField.getText(), coloreAvatar);
                    UserController.getInstance().loadUserData();

                    // Passa alla finestra sbmp dopo aver salvato l'utente
                    cardLayout.show(parentPanel, "IntroScreen");
                }
            }
        });
        buttonPanel.add(saveButton);
        add(buttonPanel);
        parentFrame.pack();
    }
}


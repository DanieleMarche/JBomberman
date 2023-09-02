package main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        titleLabel = new JLabel("Livello superato!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        nextLevelButton = new JButton("Gioca il prossimo livello");
        nextLevelButton.addActionListener(this);
        buttonPanel.add(nextLevelButton);

        mainMenuButton = new JButton("Torna al menu principale");
        mainMenuButton.addActionListener(this);
        buttonPanel.add(mainMenuButton);

        add(buttonPanel, BorderLayout.CENTER);
        parentFrame.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nextLevelButton) {
            // Aggiungi qui la logica per passare al prossimo livello
            System.out.println("Passa al prossimo livello");
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


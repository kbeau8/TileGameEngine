import profiles.PlayerProfile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameSelectionScreen extends JFrame {
    private PlayerProfile player;

    public GameSelectionScreen(PlayerProfile player) {
        this.player = player;
        setTitle("Select a Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        JLabel welcomeLabel = new JLabel("Welcome, " + player.getUsername(), SwingConstants.CENTER);
        add(welcomeLabel);

        JButton exampleButton = new JButton("Placeholder Game");

        exampleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                // new Game(player);
            }
        });

        add(exampleButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
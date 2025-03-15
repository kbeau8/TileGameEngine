import profiles.PlayerProfile;
import design.FontManager;

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
        setResizable(false);
        setLayout(new GridLayout(12, 3));

        JLabel welcomeLabel = new JLabel("Welcome, " + player.getUsername() + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(FontManager.getPixelFont(20f));
        add(welcomeLabel);

        JButton twenty48Button = new JButton("Play 2048");
        JButton puzzlesAndDragonsButton = new JButton("Play Puzzles & Dragons");
        twenty48Button.setFont(FontManager.getPixelFont(18f));
        puzzlesAndDragonsButton.setFont(FontManager.getPixelFont(18f));

        twenty48Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Twenty48Game(player);
            }
        });

        puzzlesAndDragonsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                // new PADGame();
            }
        });

        add(twenty48Button);
        add(puzzlesAndDragonsButton);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
import profiles.PlayerProfile;

import javax.swing.*;
import java.awt.*;

import input.Twenty48InputManager;

public class GamePanel extends JPanel {
    private Image backgroundImage;
    private Twenty48Game player1Game;
    private Twenty48Game player2Game;
    private Twenty48InputManager inputManager;

    public GamePanel(PlayerProfile player1, PlayerProfile player2, boolean isMultiplayer) {
        this.backgroundImage = new ImageIcon("assets/backgrounds/twenty48background.jpg").getImage();
        setLayout(null);

        player1Game = new Twenty48Game(player1, 1);
        player1Game.setBounds(50, 50, 440, 550);
        player1Game.setOpaque(false);
        add(player1Game);

        if (isMultiplayer) {
            player2Game = new Twenty48Game(player2, 2);
            player2Game.setBounds(550, 50, 440, 550);
            player2Game.setOpaque(false);
            add(player2Game);
            inputManager = new Twenty48InputManager(player1Game.grid, player2Game.grid);
        }
        else {
            inputManager = new Twenty48InputManager(player1Game.grid, null);
        }
        addKeyListener(inputManager);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    public void startGames() {
        player1Game.start();
        if (player2Game != null) {
            player2Game.start();
        }
    }
}
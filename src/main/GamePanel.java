import profiles.PlayerProfile;

import javax.swing.*;
import java.awt.*;
import input.InputManager;

public abstract class GamePanel extends JPanel {
    protected Image backgroundImage;
    protected Game player1Game;
    protected Game player2Game;
    protected InputManager inputManager;

    public GamePanel(PlayerProfile player1, PlayerProfile player2, boolean isMultiplayer, String backgroundImagePath) {
        setBackgroundImage(backgroundImagePath);
        setLayout(null);
    }

    protected void setBackgroundImage(String path) {
        this.backgroundImage = new ImageIcon(path).getImage();
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
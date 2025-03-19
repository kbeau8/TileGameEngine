import javax.swing.ImageIcon;
import input.Twenty48InputManager;
import profiles.PlayerProfile;

public class Twenty48GamePanel extends GamePanel{

    protected Twenty48Game player1Game;
    protected Twenty48Game player2Game;
    protected Twenty48InputManager inputManager;

    public Twenty48GamePanel(PlayerProfile player1, PlayerProfile player2, boolean isMultiplayer) {
        super(player1, player2, isMultiplayer, "assets/backgrounds/twenty48background.jpg");

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
    public void startGames() {
        player1Game.start();
        if (player2Game != null) {
            player2Game.start();
        }
    }
}

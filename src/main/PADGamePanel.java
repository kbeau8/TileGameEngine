import input.PADInputManager;
import profiles.PlayerProfile;

public class PADGamePanel extends GamePanel{
    protected PADGame player1Game;
    protected PADGame player2Game;
    protected PADInputManager inputManager;

    public PADGamePanel(PlayerProfile player1, PlayerProfile player2, boolean isMultiplayer) {
        super(player1, player2, isMultiplayer, "assets/backgrounds/padbackground.png");

        player1Game = new PADGame(player1, 1);
        player1Game.setBounds(50, 50, 800, 550);
        player1Game.setOpaque(false);
        add(player1Game);

        if (isMultiplayer) {
            player2Game = new PADGame(player2, 2);
            player2Game.setBounds(750, 50, 800, 550);
            player2Game.setOpaque(false);
            add(player2Game);
            inputManager = new PADInputManager(player1Game.tileMap, player2Game.tileMap);
        }
        else {
            inputManager = new PADInputManager(player1Game.tileMap, null);
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

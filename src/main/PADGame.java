import design.FontManager;
import logic.PADGameLogic;
import profiles.PlayerProfile;
import tiles.PADTile;
import tiles.PADTileMap;
import utils.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;

public class PADGame extends Game implements KeyListener {
    private PlayerProfile player;
    private PADTileMap tileMap;
    private PADGameLogic gameLogic = new PADGameLogic();
    private GameTimer timer = new GameTimer(300);

    private Image backgroundImage = new ImageIcon("assets/backgrounds/padbackground.png").getImage();

    private final int height = 5;
    private final int width = 6;
    private final int tileSize = 100;

    private Vector2D selected = new Vector2D();
    private boolean movingTile = false;

    public int score;

    public PADGame(PlayerProfile newPlayer) {
        super(newPlayer);
        this.player = newPlayer;
        this.tileMap = new PADTileMap(height, width);
        this.score = 0;

        start();
        addKeyListener(this);

        player.incrementGamesPlayed();
        player.getAllHighScores().computeIfAbsent("PAD", k -> score);

        JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (currentFrame == null) return;

        int newWidth = currentFrame.getWidth() + 400;
        int newHeight = currentFrame.getHeight() + 200;
        currentFrame.setSize(newWidth, newHeight);
        currentFrame.setLocationRelativeTo(null);
        currentFrame.setTitle("Puzzles and Dragons");
    }

    @Override
    public void update() {
        this.timer.update();
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(backgroundImage, 0, 0, null);

        // TODO: Text boxes
        g.setFont(FontManager.getPixelFont(24f));
        g.setColor(Color.WHITE);

        // Selected Tile Highlight
        g.setColor(movingTile ? Color.getHSBColor(142, 23, 100) : Color.LIGHT_GRAY);
        g.fillRect(selected.x * tileSize, selected.y * tileSize, tileSize, tileSize);

        // Tiles
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                int xCoord = x * tileSize;
                int yCoord = y * tileSize;

                PADTile tile = tileMap.tiles[y][x];

                g.drawImage(tile.getImage(), xCoord, yCoord, this);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!timer.isRunning) timer.startTimer();

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_ENTER) {
            if (movingTile) {
                HashSet<Vector2D> removals = gameLogic.getMatches(tileMap);
                tileMap.plop(removals);

                // TODO: Add points for removed tiles
            }
            movingTile = !movingTile;
        }

        if (movingTile) {
            // Swap tiles in direction of movement
            switch (code) {
                case KeyEvent.VK_UP:
                    if (selected.y == 0) break;
                    tileMap.swapTiles(selected.x, selected.y, selected.x, selected.y - 1);
                    selected.y--;
                    break;
                case KeyEvent.VK_DOWN:
                    if (selected.y == height - 1) break;
                    tileMap.swapTiles(selected.x, selected.y, selected.x, selected.y + 1);
                    selected.y++;
                    break;
                case KeyEvent.VK_LEFT:
                    if (selected.x == 0) break;
                    tileMap.swapTiles(selected.x, selected.y, selected.x - 1, selected.y);
                    selected.x--;
                    break;
                case KeyEvent.VK_RIGHT:
                    if (selected.x == width - 1) break;
                    tileMap.swapTiles(selected.x, selected.y, selected.x + 1, selected.y);
                    selected.x++;
                    break;
            }
        } else {
            // Move selection location
            switch (code) {
                case KeyEvent.VK_UP:
                    selected.y = Math.max(0, selected.y - 1);
                    break;
                case KeyEvent.VK_DOWN:
                    selected.y = Math.min(height - 1, selected.y + 1);
                    break;
                case KeyEvent.VK_LEFT:
                    selected.x = Math.max(0, selected.x - 1);
                    break;
                case KeyEvent.VK_RIGHT:
                    selected.x = Math.min(width - 1, selected.x + 1);
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}

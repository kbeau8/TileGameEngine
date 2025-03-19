import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import design.FontManager;
import logic.PADGameLogic;
import profiles.PlayerProfile;
import sounds.SoundManager;
import tiles.PADTile;
import tiles.PADTileMap;
import utils.Vector2D;

public class PADGame extends Game implements KeyListener {
    private PlayerProfile player;
    private PADTileMap tileMap;
    private PADGameLogic gameLogic = new PADGameLogic();
    private GameTimer timer = new GameTimer(30);
    private SoundManager soundManager = new SoundManager();

    private Image backgroundImage = new ImageIcon("assets/backgrounds/padframe.png").getImage();

    private final int height = 5;
    private final int width = 6;
    private final int tileSize = 100;

    private double health = 100;
    private long lastHealthUpdate = System.currentTimeMillis();
    private final double HEALTH_DECAY_PER_SECOND = 0.5;
    private final double MATCH_BONUS_PER_TILE = 1.0; 
    private final double NO_MATCH_PENALTY = 5.0;
    private JButton restartButton;

    private Vector2D selected = new Vector2D();
    private boolean movingTile = false;
    private boolean gameOver = false;

    public int score;
    public int tileMapYOffset = 25;

    public PADGame(PlayerProfile newPlayer) {
        super(newPlayer);
        this.player = newPlayer;
        this.tileMap = new PADTileMap(height, width);
        this.score = 0;

        start();
        addKeyListener(this);
        soundManager.startMusic("assets/music/PAD.wav");
        player.incrementGamesPlayed();
        player.getAllHighScores().computeIfAbsent("PAD", k -> score);

        JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (currentFrame == null) return;

        int newWidth = currentFrame.getWidth() - 184;
        int newHeight = currentFrame.getHeight() + 200;
        currentFrame.setSize(newWidth, newHeight);
        currentFrame.setLocationRelativeTo(null);
        currentFrame.setTitle("Puzzles and Dragons");
    }

    @Override
    public void update() {
        this.timer.update(); // Existing timer update

        long now = System.currentTimeMillis();
        double elapsedSeconds = (now - lastHealthUpdate) / 1000.0;
        health -= HEALTH_DECAY_PER_SECOND * elapsedSeconds;
        lastHealthUpdate = now;
        if (health < 0) health = 0;

        if ((health <= 0 || timer.getTimeLeft() <= 0 )&& !gameOver) {
            gameOver = true;
            timer.stopTimer();
            player.updateHighScore("PAD", score);
            
            restartButton = new JButton("Restart");
            restartButton.setBounds(tileSize * width / 2 - 50, tileSize * height / 2 + 60 - 25, 100, 50);
            restartButton.addActionListener(e -> restartGame());
            setLayout(null);
            add(restartButton);
            repaint();
        }
        if (gameOver) return;

    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(backgroundImage, 0, 0+tileMapYOffset, null);

        g.setFont(FontManager.getPixelFont(24f));
        g.setColor(Color.BLACK);

        g.drawString("Score: " + score, 20, 590 + tileMapYOffset);
        g.drawString("Timer: " + timer.getTimeLeft(), 20, 650 + tileMapYOffset);

        // Selected Tile Highlight
        g.setColor(movingTile ? Color.getHSBColor(142, 23, 100) : Color.LIGHT_GRAY);
        g.fillRect(selected.x * tileSize, selected.y * tileSize + tileMapYOffset, tileSize, tileSize);

        // Tiles
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                int xCoord = x * tileSize;
                int yCoord = y * tileSize + tileMapYOffset;

                PADTile tile = tileMap.tiles[y][x];

                g.drawImage(tile.getImage(), xCoord, yCoord, this);
            }
        }
        // Health Bar
        g.setColor(Color.GRAY); 
        int healthBarWidth = tileSize * width; 
        int healthBarHeight = 20; 
        g.fillRect(0, 5, healthBarWidth, healthBarHeight); 
        int currentHealthWidth = (int)(healthBarWidth * (health / 100.0)); 
        g.setColor(Color.GREEN);
        g.fillRect(0, 5, currentHealthWidth, healthBarHeight);
        g.setColor(Color.BLACK);
        g.drawRect(0, 5, healthBarWidth, healthBarHeight);
        g.drawString("Health: " + (int)health, 210, 20);

        // High Score
        g.drawString("High Score: " + player.getHighScore("PAD"), 20, 590 + 60);

        if (gameOver) {
            g.setColor(Color.BLACK);
            g.fillRect(0, getHeight() / 2 - 200, getWidth(), 100);
            g.setFont(FontManager.getPixelFont(48f));
            g.setColor(Color.RED);
            g.drawString("You Died", getWidth() / 2 - 100, getHeight() / 2 - 135);
        }      
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!timer.isRunning) timer.startTimer();

        int code = e.getKeyCode();
        HashSet<Vector2D> removals = gameLogic.getMatches(tileMap);
        if (code == KeyEvent.VK_ENTER) {
            if (movingTile) {
                tileMap.plop(removals);
                int multiplier = 1;
                if (removals.size() > 4) {
                    multiplier = removals.size() - 3;
                }
                score += removals.size() * multiplier;
            }
            if (removals.isEmpty()) { 
                health -= NO_MATCH_PENALTY;
            } else {
                health = Math.min(100, health + MATCH_BONUS_PER_TILE * removals.size());
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

    private void restartGame() {
        health = 100;
        score = 0;
        timer = new GameTimer(30);
        tileMap = new PADTileMap(height, width);
        gameOver = false;
        remove(restartButton);
        restartButton = null;
        start();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}

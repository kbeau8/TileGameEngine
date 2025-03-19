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

public class PADGame extends Game {
    private PlayerProfile player;
    public PADTileMap tileMap;
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

    private boolean gameOver = false;

    public int score;
    public int tileMapYOffset = 25;

    private int playerNum;

    public PADGame(PlayerProfile newPlayer, int playerNum) {
        super(newPlayer);
        this.player = newPlayer;
        this.tileMap = new PADTileMap(height, width);
        this.score = 0;
        this.playerNum = playerNum;

        soundManager.startMusic("assets/music/PAD.wav");
        player.incrementGamesPlayed();
        player.getAllHighScores().computeIfAbsent("PAD", k -> score);
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
        g.setColor(tileMap.movingTile ? Color.getHSBColor(142, 23, 100) : Color.LIGHT_GRAY);
        g.fillRect(tileMap.selected.x * tileSize, tileMap.selected.y * tileSize + tileMapYOffset, tileSize, tileSize);

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
    public void run() {
        if (playerNum == 1) {
//            this.showRules();
        }
        this.timer.startTimer();

        while (running) {
            update();
            repaint();
            try {
                // Stable 60 fps
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
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
}

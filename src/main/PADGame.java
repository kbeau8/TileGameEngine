import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import design.FontManager;
import logic.PADGameLogic;
import profiles.PlayerProfile;
import sounds.SoundManager;
import tiles.PADTile;
import tiles.PADTileMap;

public class PADGame extends Game {
    private PlayerProfile player;
    public PADTileMap tileMap;
    private PADGameLogic gameLogic = new PADGameLogic();
    private GameTimer timer = new GameTimer(180);
    private SoundManager soundManager = new SoundManager();

    private Image backgroundImage = new ImageIcon("assets/backgrounds/padframe.png").getImage();

    private final int height = 5;
    private final int width = 6;
    private final int tileSize = 100;

    private double health = 100;
    private long lastHealthUpdate = System.currentTimeMillis();
    private final double HEALTH_DECAY_PER_SECOND = 1.0;
    private final double MATCH_BONUS_PER_TILE = 1.0; 
    private final double NO_MATCH_PENALTY = 5.0;
    private JButton restartButton;

    private boolean gameOver = false;

    public int score;
    public int tileMapYOffset = 25;

    private int playerNum;

    private HashMap<String, String> rules;

    public PADGame(PlayerProfile newPlayer, int playerNum) {
        super(newPlayer);
        this.player = newPlayer;
        this.tileMap = new PADTileMap(height, width);
        this.score = 0;
        this.playerNum = playerNum;

        this.setRules();

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
        if (gameOver) {
            health = 0;
            return;

        }

    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(backgroundImage, 0, 0+tileMapYOffset, null);

        g.setFont(FontManager.getPixelFont(24f));
        g.setColor(Color.BLACK);

        

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
        Image heart = new ImageIcon("pad-assets/heart.png").getImage();
        int targetHeartWidth = 45;
        int targetHeartHeight = 45;
        int heartX = -7;
        int heartY = 16 - (targetHeartHeight / 2);
        


        g.setColor(Color.GRAY);
        int healthBarWidth = tileSize * width;
        int healthBarHeight = 25;
        g.fillRoundRect(0, 5, healthBarWidth, healthBarHeight, 10, 10);
        int currentHealthWidth = (int)(healthBarWidth * (health / 100.0));
        g.setColor(Color.GREEN);
        g.fillRoundRect(0, 5, currentHealthWidth, healthBarHeight, 10, 10);
        g.setColor(Color.BLACK);
        g.drawRoundRect(0, 5, healthBarWidth, healthBarHeight, 10, 10);
        g.drawString("Health: " + (int)health, 210, 25);

        g.drawImage(heart, heartX, heartY, targetHeartWidth, targetHeartHeight, null);

        // Text Panel
        g.setColor(Color.WHITE);
        g.fillRoundRect(180, 530, 350, 90,20,20);
        g.setColor(Color.BLACK);
        g.drawString("Score: " + score, 200, 560);
        g.drawString("Timer: " + timer.getTimeLeft(), 200, 580);
        g.drawString("High Score: " + player.getHighScore("PAD"), 200, 600);


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
            this.showRules();
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

    public void showRules() {
        JOptionPane.showMessageDialog(null,
                "Rules:" + "\n" + rules.get("Introduction") + "\n" + "Controls:" + "\n" + rules.get("Controls"));
    }

    public void setRules() {
        rules = new HashMap<String, String>();
        rules.put("Introduction",
                "Puzzles & Dragons (PAD) challenges you to match colored tiles on a grid to clear them and score points.\nYou have 180 seconds and a limited health bar. Matches of three or more tiles increase your score and restore some health, while non-matching moves and time decay reduce your health.\nThe game ends when time runs out or your health hits zero.");
        rules.put("Controls",
                "Use the WASD keys to move a cursor around the board.\nPress F to lock a tile, then use the WASD keys to swap it with adjacent tiles.\nPress F again to unlock the tile and finalize your move.\nIf you create matches of three or more, those tiles are removed and replaced with new ones, and you earn points.\nLarger matches grant higher scores and extra health, so plan your swaps carefully to maximize your points before the timer runs out!");
    }

    private void restartGame() {
        health = 100;
        score = 0;
        timer = new GameTimer(180);
        tileMap = new PADTileMap(height, width);
        gameOver = false;
        remove(restartButton);
        restartButton = null;
        start();
    }
}

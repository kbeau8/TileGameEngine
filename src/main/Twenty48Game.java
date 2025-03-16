import java.awt.*;
import java.awt.event.KeyListener;
import java.util.HashMap;

import javax.swing.*;

import java.awt.event.KeyEvent;

import entities.Player;
import sounds.SoundManager;
import tiles.Twenty48Tile;
import tiles.Twenty48TileMap;
import logic.Twenty48GameLogic;
import profiles.PlayerProfile;
import design.FontManager;

public class Twenty48Game extends Game implements KeyListener {
    private PlayerProfile player;
    private HashMap<String, String> rules;
    private Twenty48TileMap grid;
    static final String file_path = "../2048-assets";
    public static Image tileBoardImage = new javax.swing.ImageIcon("2048-assets-retro/tile-board.png").getImage();
    private Image backgroundImage = new ImageIcon("assets/backgrounds/twenty48background.jpg").getImage();
    public int score;
    private Twenty48GameLogic gameLogic = new Twenty48GameLogic();
    private GameTimer timer = new GameTimer(200);
    private SoundManager soundManager = new SoundManager();
    private int time_started;

    // settings for each game (in order): x position, y position, height/width of
    // the tile board
    private int game1Settings[] = { 6, 5, 440 };
    // private int game2Settings[] = { 600, 5, 440 };

    // todo: take in a game number to keep track of how many games there are and
    // positioning
    public Twenty48Game(PlayerProfile newPlayer) {
        super(newPlayer);
        this.player = newPlayer;
        this.grid = new Twenty48TileMap();
        soundManager.startMusic("assets/music/twenty48.wav");
        start();
        addKeyListener(this);
        this.score = 0;
        // do player stuff here when game is initialized
        player.incrementGamesPlayed();
        if (player.getAllHighScores().get("2048") == null) {
            player.getAllHighScores().put("2048", score);
        }

        // increases screen size, needed for when there are 2 players
        JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

        if (currentFrame != null) {
            int newWidth = currentFrame.getWidth() + 400; // Increase width by 100// Increase height by 100
            currentFrame.setSize(newWidth, currentFrame.getHeight() + 200);
            currentFrame.setLocationRelativeTo(null);
            currentFrame.setTitle("2048 Game");
        }

    }

    // Implement the keyPressed method
    @Override
    public void keyPressed(KeyEvent e) {
        // Start the timer when a key is pressed
        if (!timer.isRunning) {
            timer.startTimer();
            this.time_started = timer.getTimeLeft();
        }

        // takes key inputs and does an action for a specific key
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_RIGHT) {
            grid.swipeRight();
        }

        if (code == KeyEvent.VK_LEFT) {
            grid.swipeLeft();

        }

        if (code == KeyEvent.VK_UP) {
            grid.swipeUp();
        }

        if (code == KeyEvent.VK_DOWN) {
            grid.swipeDown();
        }

        // TODO: add key listeners for WASD for second player
    }

    // these methods are probably not needed but are here just in case
    // Implement the keyReleased method
    @Override
    public void keyReleased(KeyEvent e) {
    }

    // Implement the keyTyped method
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void update() {
        // Implement the update logic for the 2048 game
        this.gameLogic.updateScore(grid.score, player);
        this.timer.update();
        if(timer.isRunning && this.gameLogic.isGameWon(grid)){
            int score = timer.getTimeLeft() - this.time_started;
            JOptionPane.showMessageDialog(this, "You won! Your score was: " + score);
            this.running = false;
            this.player.updateHighScore("2048", score);
        }
        else if(timer.isRunning && this.gameLogic.isGameLost(grid)){
            JOptionPane.showMessageDialog(this, "You lost!");
            this.running = false;
        }
    }

    public void renderTiles(Graphics2D g, Twenty48TileMap map) {
        // draws all the tiles in the correct position
        // please DO NOT change the numbers on these, the positioning is finnicky
        int screenWidth = 430;
        int screenHeight = 430;
        int gridSize = 4;
        int gap = -1;

        int tileSizeX = (screenWidth - (gap * (gridSize - 1))) / gridSize;
        int tileSizeY = (screenHeight - (gap * (gridSize - 1))) / gridSize;

        int x = 0;
        int y = 0;

        // tile board 1
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                x = j * (tileSizeX + gap) + 4 + game1Settings[0];
                y = i * (tileSizeY + gap) + 8 + game1Settings[1];
                g.drawImage(map.tiles[i][j].image, x, y, this);
            }
        }

        // tile board 2
        // for (int i = 0; i < 4; i++) {
        // for (int j = 0; j < 4; j++) {
        // x = j * (tileSizeX + gap) + 3 + game2Settings[0];
        // y = i * (tileSizeY + gap) + 7 + game2Settings[1];
        // g.drawImage(map.tiles[i][j].image, x, y, this);
        // }
        // }
    }

    @Override
    public void render(Graphics2D g) {
        // custom font
        g.setFont(FontManager.getPixelFont(24f));
        g.setColor(Color.WHITE);
        // custom background
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        // draws tile board
        g.drawImage(tileBoardImage, game1Settings[0], game1Settings[1], game1Settings[2], game1Settings[2], this);
        // g.drawImage(tileBoardImage, game2Settings[0], game2Settings[1],
        // game2Settings[2], game2Settings[2], this);

        // draws tiles
        this.renderTiles(g, grid);

        // Draw text at a specific location (x, y)
        g.drawString("Player: " + player.getUsername(), game1Settings[0] + 10,
                game1Settings[1] + game1Settings[2] + 30);

        g.drawString("High Score: " + player.getHighScore("2048"), game1Settings[0] + 10,
                game1Settings[1] + game1Settings[2] + 60);

        g.drawString("Score: " + grid.score, game1Settings[0] + 10,
                game1Settings[1] + game1Settings[2] + 90);

        g.drawString("Timer: " + timer.getTimeLeft(), game1Settings[0] + 10,
                game1Settings[1] + game1Settings[2] + 120);

        // add stop buttons to indicate person solved puzzle and so timer will stop

        // g.drawString("Player: " + player.getUsername(), game2Settings[0] + 10,
        // game2Settings[1] + game2Settings[2] + 30);
    }

    @Override
    public void run() {
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render((Graphics2D) g);
    }

}
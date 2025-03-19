import java.awt.*;
import java.awt.event.KeyListener;
import java.util.HashMap;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
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
    private Twenty48GameLogic gameLogic = new Twenty48GameLogic();
    private Twenty48GameTimer timer = new Twenty48GameTimer(0);
    private SoundManager soundManager = new SoundManager();
    private JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

    // settings for each game (in order): x position, y position, height/width of
    // the tile board
    private int game1Settings[] = { 6, 5, 440 };
    // private int game2Settings[] = { 600, 5, 440 };

    public Twenty48Game(PlayerProfile newPlayer) {
        super(newPlayer);
        this.setRules();
        this.player = newPlayer;
        this.grid = new Twenty48TileMap();
        soundManager.startMusic("assets/music/twenty48.wav");
        start();
        addKeyListener(this);

        player.incrementGamesPlayed();
        if (player.getAllHighScores().get("2048") == null) {
            player.getAllHighScores().put("2048", 0);
        }

        // increases screen size, needed for when there are 2 players
        if (currentFrame != null) {
            int newWidth = currentFrame.getWidth() + 400; // Increase width by 100// Increase height by 100
            currentFrame.setSize(newWidth, currentFrame.getHeight() + 200);
            currentFrame.setLocationRelativeTo(null);
            currentFrame.setTitle("2048 Game");
        }

        this.showRules();

    }

    public void showRules() {
        JOptionPane.showMessageDialog(null,
                "Rules:" + "\n" + rules.get("Introduction") + "\n" + "Controls:" + "\n" + rules.get("Controls"));
    }

    public void setRules() {
        rules = new HashMap<String, String>();
        rules.put("Introduction",
                "2048's objective is to slide numbered tiles on a grid to combine them to create a tile with the number 2048."
                        + "\n"
                        + "The game is won when a tile with the number 2048 appears on the board. A second player can be added and the times can be compared to see who finished the game first.\nThe game is lost when the player has no legal moves left.");
        rules.put("Controls",
                "Use the arrow keys to move the tiles in the desired direction. The tiles will move in the direction of the arrow key until they hit the edge of the board or another tile."
                        + "\n"
                        + "When two tiles with the same number touch, they merge into one tile with the sum of the two tiles\nStop the timer only when you have won the game.");
    }

    // Implement the keyPressed method
    @Override
    public void keyPressed(KeyEvent e) {
        // Start the timer when a key is pressed
        if (!timer.isRunning) {
            timer.startTimer();
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
    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void update() {
        gameLogic.updateScore(grid.score, player);
        this.timer.update();
        if (timer.isRunning && this.gameLogic.isGameWon(grid)) {
            int time = timer.getTimeElapsed();
            repaint();
            JOptionPane.showMessageDialog(this,
                    player.getUsername() + " won! Score: " + gameLogic.getScore(grid) + " Time: " + time);
            this.running = false;
        } else if (timer.isRunning && this.gameLogic.isGameLost(grid)) {
            repaint();
            JOptionPane.showMessageDialog(this, player.getUsername() + " lost! Score: " + gameLogic.getScore(grid));
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
        // x = j * (tileSizeX + gap) + 4 + game2Settings[0];
        // y = i * (tileSizeY + gap) + 8 + game2Settings[1];
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

        // stop timer button
        JButton stopTimerButton = new JButton("Stop Timer");
        stopTimerButton.setBounds(250, 500, 120, 40);
        this.add(stopTimerButton);
        stopTimerButton.setBackground(Color.decode("#E74250"));
        stopTimerButton.setForeground(Color.WHITE);
        stopTimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soundManager.stopMusic();
                timer.stopTimer();
            }
        });

        // Draw text at a specific location (x, y)
        g.drawString("Player: " + player.getUsername(), game1Settings[0] + 10,
                game1Settings[1] + game1Settings[2] + 30);

        g.drawString("High Score: " + player.getHighScore("2048"), game1Settings[0] + 10,
                game1Settings[1] + game1Settings[2] + 60);

        g.drawString("Score: " + grid.score, game1Settings[0] + 10,
                game1Settings[1] + game1Settings[2] + 90);

        g.drawString("Timer: " + timer.getTimeElapsed(), game1Settings[0] + 10,
                game1Settings[1] + game1Settings[2] + 120);

        // player goes back to game selection
        JButton exitButton = new JButton("Back to Main");
        exitButton.setBounds(465, 100, 120, 40);
        this.add(exitButton);
        exitButton.setBackground(Color.decode("#E74250"));
        exitButton.setForeground(Color.WHITE);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                running = false;
                soundManager.stopMusic();
                currentFrame.dispose();
                new GameSelectionScreen(player);
            }
        });

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
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
    private PlayerProfile player1;
    private PlayerProfile player2;
    private HashMap<String, String> rules;
    private Twenty48TileMap grid1;
    private Twenty48TileMap grid2;
    static final String file_path = "../2048-assets";
    public static Image tileBoardImage = new javax.swing.ImageIcon("2048-assets-retro/tile-board.png").getImage();
    private Image backgroundImage = new ImageIcon("assets/backgrounds/twenty48background.jpg").getImage();
    private Twenty48GameLogic gameLogic = new Twenty48GameLogic();
    private Twenty48GameTimer timer1 = new Twenty48GameTimer(0);
    private Twenty48GameTimer timer2 = new Twenty48GameTimer(0);
    private SoundManager soundManager = new SoundManager();
    private JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

    // settings for each game (in order): x position, y position, height/width of
    // the tile board
    private int game1Settings[] = { 6, 5, 440 };
    private int game2Settings[] = { 600, 5, 440 };

    public Twenty48Game(PlayerProfile player1, PlayerProfile player2, boolean isMultiplayer) {
        super(player1);
        this.setRules();
        this.player1 = player1;
        this.player2 = player2;
        this.grid1 = new Twenty48TileMap();
        soundManager.startMusic("assets/music/twenty48.wav");
        addKeyListener(this);

        player1.incrementGamesPlayed();
        if (player1.getAllHighScores().get("2048") == null) {
            player1.getAllHighScores().put("2048", 0);
        }

        currentFrame.setSize(currentFrame.getWidth(), currentFrame.getHeight() + 200);

        if (player2 != null) {
            this.grid2 = new Twenty48TileMap();
            player2.incrementGamesPlayed();
            if (player2.getAllHighScores().get("2048") == null) {
                player2.getAllHighScores().put("2048", 0);
            }

            currentFrame.setSize(currentFrame.getWidth() + 300, currentFrame.getHeight());
            currentFrame.setLocationRelativeTo(null);
            currentFrame.setTitle("2048 Game");
        }

        currentFrame.setLocationRelativeTo(null);
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
    // todo: add multiplayer rules

    // Implement the keyPressed method
    @Override
    public void keyPressed(KeyEvent e) {
        // Start the timer when a key is pressed

        // takes key inputs and does an action for a specific key
        int code = e.getKeyCode();

        // player 1 inputs
        if (player1 != null) {

            if (!timer1.isRunning) {
                timer1.startTimer();
            }

            if (code == KeyEvent.VK_RIGHT) {
                grid1.swipeRight();
            }

            if (code == KeyEvent.VK_LEFT) {
                grid1.swipeLeft();

            }

            if (code == KeyEvent.VK_UP) {
                grid1.swipeUp();
            }

            if (code == KeyEvent.VK_DOWN) {
                grid1.swipeDown();
            }
        }

        // player 2 inputs
        if (player2 != null) {

            if (!timer2.isRunning) {
                timer2.startTimer();
            }

            if (code == KeyEvent.VK_D) {
                grid2.swipeRight();
            }

            if (code == KeyEvent.VK_A) {
                grid2.swipeLeft();

            }

            if (code == KeyEvent.VK_W) {
                grid2.swipeUp();
            }

            if (code == KeyEvent.VK_S) {
                grid2.swipeDown();
            }
        }

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
        gameLogic.updateScore(grid1.score, player1);
        if (player2 != null) {
            gameLogic.updateScore(grid2.score, player2);

            this.timer2.update();
            if (timer2.isRunning && this.gameLogic.isGameWon(grid2)) {
                int time2 = timer2.getTimeElapsed();
                repaint();
                JOptionPane.showMessageDialog(this,
                        player2.getUsername() + " won! Score: " + gameLogic.getScore(grid2) + " Time: " + time2);
                this.running = false;
            } else if (timer2.isRunning && this.gameLogic.isGameLost(grid2)) {
                repaint();
                JOptionPane.showMessageDialog(this,
                        player2.getUsername() + " lost! Score: " + gameLogic.getScore(grid2));
                this.running = false;
            }
        }
        this.timer1.update();
        if (timer1.isRunning && this.gameLogic.isGameWon(grid1)) {
            int time1 = timer1.getTimeElapsed();
            repaint();
            JOptionPane.showMessageDialog(this,
                    player1.getUsername() + " won! Score: " + gameLogic.getScore(grid1) + " Time: " + time1);
            this.running = false;
        } else if (timer1.isRunning && this.gameLogic.isGameLost(grid1)) {
            repaint();
            JOptionPane.showMessageDialog(this, player1.getUsername() + " lost! Score: " + gameLogic.getScore(grid1));
            this.running = false;
        }
    }

    public void renderTiles(Graphics2D g) {
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
                g.drawImage(grid1.tiles[i][j].image, x, y, this);
            }
        }

        // tile board 2 for second player
        if (player2 != null) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    x = j * (tileSizeX + gap) + 4 + game2Settings[0];
                    y = i * (tileSizeY + gap) + 8 + game2Settings[1];
                    g.drawImage(grid2.tiles[i][j].image, x, y, this);
                }
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        // custom font
        g.setFont(FontManager.getPixelFont(24f));
        g.setColor(Color.WHITE);
        // custom background
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        // draws tile board 1
        g.drawImage(tileBoardImage, game1Settings[0], game1Settings[1], game1Settings[2], game1Settings[2], this);

        // draws tile board 2
        if (player2 != null) {
            g.drawImage(tileBoardImage, game2Settings[0], game2Settings[1],
                    game2Settings[2], game2Settings[2], this);
        }

        // draws tiles
        this.renderTiles(g);

        // stop timer button
        JButton stopTimerButton = new JButton("Stop Timer");
        stopTimerButton.setBounds(250, 500, 120, 40);
        this.add(stopTimerButton);
        stopTimerButton.setBackground(Color.decode("#E74250"));
        stopTimerButton.setForeground(Color.WHITE);
        stopTimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer1.stopTimer();
                timer2.stopTimer();
                // todo: stop game running and display score??
            }
        });

        // player 1 text, draw text at a specific location (x, y)
        g.drawString("Player: " + player1.getUsername(), game1Settings[0] + 10,
                game1Settings[1] + game1Settings[2] + 30);

        g.drawString("High Score: " + player1.getHighScore("2048"), game1Settings[0] + 10,
                game1Settings[1] + game1Settings[2] + 60);

        g.drawString("Score: " + grid1.score, game1Settings[0] + 10,
                game1Settings[1] + game1Settings[2] + 90);

        g.drawString("Timer: " + timer1.getTimeElapsed(), game1Settings[0] + 10,
                game1Settings[1] + game1Settings[2] + 120);

        // player 2 text
        if (player2 != null) {
            g.drawString("Player: " + player2.getUsername(), game2Settings[0] + 10,
                    game2Settings[1] + game2Settings[2] + 30);

            g.drawString("High Score: " + player2.getHighScore("2048"), game2Settings[0] + 10,
                    game2Settings[1] + game2Settings[2] + 60);

            g.drawString("Score: " + grid2.score, game2Settings[0] + 10,
                    game2Settings[1] + game2Settings[2] + 90);

            g.drawString("Timer: " + timer2.getTimeElapsed(), game2Settings[0] + 10,
                    game2Settings[1] + game2Settings[2] + 120);
        }

        // player(s) goes back to game selection
        JButton exitButton = new JButton("Back to Main");
        exitButton.setBounds(465, 100, 120, 40);
        this.add(exitButton);
        exitButton.setBackground(Color.decode("#E74250"));
        exitButton.setForeground(Color.WHITE);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                running = false;
                currentFrame.dispose();
                new GameSelectionScreen(player1);
            }
        });

        // todo: add stop buttons to indicate person solved puzzle and so timer will
        // stop for both

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
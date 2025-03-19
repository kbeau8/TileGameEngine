import java.awt.*;
import java.util.HashMap;

import javax.swing.*;

import sounds.SoundManager;
import tiles.Twenty48TileMap;
import logic.Twenty48GameLogic;
import profiles.PlayerProfile;
import design.FontManager;

public class Twenty48Game extends Game {
    private PlayerProfile player;
    private HashMap<String, String> rules;
    public Twenty48TileMap grid;
    public static Image tileBoardImage = new javax.swing.ImageIcon("2048-assets-retro/tile-board.png").getImage();
    private Image backgroundImage = new ImageIcon("assets/backgrounds/twenty48background.jpg").getImage();
    private Twenty48GameLogic gameLogic = new Twenty48GameLogic();
    private Twenty48GameTimer timer = new Twenty48GameTimer(0);
    private SoundManager soundManager = new SoundManager();
    private JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
    private int playerNum;

    // settings for each game (in order): x position, y position, height/width of
    // the tile board
    private int gameSettings[] = { 6, 5, 440 };

    public Twenty48Game(PlayerProfile player, int playerNum) {
        super(player);
        this.setRules();
        this.player = player;
        this.playerNum = playerNum;
        this.grid = new Twenty48TileMap();

        if (playerNum == 1) {
            soundManager.startMusic("assets/music/twenty48.wav");
        }

        player.incrementGamesPlayed();
        if (player.getAllHighScores().get("2048") == null) {
            player.getAllHighScores().put("2048", 0);
        }
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

    public void renderTiles(Graphics2D g, int boardX, int boardY) {
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

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                x = j * (tileSizeX + gap) + 4 + boardX;
                y = i * (tileSizeY + gap) + 8 + boardY;
                g.drawImage(grid.tiles[i][j].image, x, y, tileSizeX, tileSizeY, this);
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        // custom font
        g.setFont(FontManager.getPixelFont(24f));
        g.setColor(Color.WHITE);
        if (playerNum == 1) {
            int timerX = getWidth() / 2;
            g.drawString("Timer: " + timer.getTimeElapsed(), timerX,gameSettings[2] + 30);
        }

        int boardX = (playerNum == 1) ? gameSettings[0] : getWidth() - gameSettings[2] - gameSettings[0];
        int boardY = gameSettings[1];

        // draws tiles
        g.drawImage(tileBoardImage, boardX, boardY, gameSettings[2], gameSettings[2], this);
        renderTiles(g, boardX, boardY);

        g.drawString("Player: " + player.getUsername(), boardX + 10, boardY + gameSettings[2] + 30);
        g.drawString("High Score: " + player.getHighScore("2048"), boardX + 10, boardY + gameSettings[2] + 60);
        g.drawString("Score: " + grid.score, boardX + 10, boardY + gameSettings[2] + 90);

    }


        // stop timer button
        // JButton stopTimerButton = new JButton("Stop Timer");
        // stopTimerButton.setBounds(250, 500, 120, 40);
        // this.add(stopTimerButton);
        // stopTimerButton.setBackground(Color.decode("#E74250"));
        // stopTimerButton.setForeground(Color.WHITE);
        // stopTimerButton.addActionListener(new ActionListener() {
        // @Override
        // public void actionPerformed(ActionEvent e) {
        // timer.stopTimer();
        // // todo: stop game running and display score??
        // }
        // });

        // player(s) goes back to game selection
        // JButton exitButton = new JButton("Back to Main");
        // exitButton.setBounds(465, 100, 120, 40);
        // this.add(exitButton);
        // exitButton.setBackground(Color.decode("#E74250"));
        // exitButton.setForeground(Color.WHITE);

        // exitButton.addActionListener(new ActionListener() {
        // @Override
        // public void actionPerformed(ActionEvent e) {
        // running = false;
        // currentFrame.dispose();
        // new GameSelectionScreen(player);
        // }
        // });


        // todo: add stop buttons to indicate person solved puzzle and so timer will
        // stop for both

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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render((Graphics2D) g);
    }
}
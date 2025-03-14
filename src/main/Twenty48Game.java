import java.awt.*;
import java.awt.event.KeyListener;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.awt.event.KeyEvent;

import tiles.Twenty48Tile;
import tiles.Twenty48TileMap;
import logic.Twenty48GameLogic;
import profiles.PlayerProfile;

public class Twenty48Game extends Game implements KeyListener {
    private PlayerProfile player;
    private HashMap<String, String> rules;
    private Twenty48TileMap grid;
    static final String file_path = "../2048-assets";
    public static Image tileBoardImage = new javax.swing.ImageIcon("2048-assets/tile-board.png").getImage();
    public int score;
    private Twenty48GameLogic gameLogic = new Twenty48GameLogic();
    // settings for each game (in order): x position, y position, height/width of
    // the tile board
    private int game1Settings[] = { 6, 5, 440 };
    // private int game2Settings[] = { 600, 5, 440 };

    // todo: take in a game number to keep track of how many games there are and
    // positioning
    public Twenty48Game(PlayerProfile newPlayer) {
        super();
        this.player = newPlayer;
        this.grid = new Twenty48TileMap();
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
            currentFrame.setSize(newWidth, currentFrame.getHeight());
        }

    }

    // Implement the keyPressed method
    // TODO: update scores on key presses
    @Override
    public void keyPressed(KeyEvent e) {
        // takes key inputs and does an action for a specific key
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_RIGHT) {
            System.out.println("RIGHT!");
            grid.swipeRight(); // not working at the moment
        }

        if (code == KeyEvent.VK_LEFT) {
            System.out.println("LEFT!");

        }

        if (code == KeyEvent.VK_UP) {
            System.out.println("UP!");
        }

        if (code == KeyEvent.VK_DOWN) {
            System.out.println("DOWN!");
        }

        // TODO: add key listeners for WASD
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
                x = j * (tileSizeX + gap) + 3 + game1Settings[0];
                y = i * (tileSizeY + gap) + 7 + game1Settings[1];
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
        // draws tile board
        g.drawImage(tileBoardImage, game1Settings[0], game1Settings[1], game1Settings[2], game1Settings[2], this);
        // g.drawImage(tileBoardImage, game2Settings[0], game2Settings[1],
        // game2Settings[2], game2Settings[2], this);

        // draws tiles
        this.renderTiles(g, grid);

        // game text: title, player, score, etc.
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 24));

        // Draw text at a specific location (x, y)
        g.drawString("2048 Game", game1Settings[0] + game1Settings[2] + 20, 50);

        g.drawString("Player: " + player.getUsername(), game1Settings[0] + 10,
                game1Settings[1] + game1Settings[2] + 30);

        g.drawString("Score: " + this.score, game1Settings[0] + 10,
                game1Settings[1] + game1Settings[2] + 60);

        // add timer text here when implemented
        g.drawString("Timer: ", game1Settings[0] + 10,
                game1Settings[1] + game1Settings[2] + 90);
        
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
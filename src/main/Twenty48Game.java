import java.awt.*;
import java.awt.event.KeyListener;
import java.util.HashMap;

import tiles.Twenty48Tile;
import tiles.Twenty48TileMap;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import profiles.PlayerProfile;

public class Twenty48Game extends Game implements KeyListener {
    private PlayerProfile player;
    private HashMap<String, String> rules;
    private Twenty48TileMap grid;
    static final String file_path = "../2048-assets";
    public static Image tileBoardImage = new javax.swing.ImageIcon("2048-assets/tile-board.png").getImage();

    public Twenty48Game(PlayerProfile player) {
        super();
        this.player = player;
        this.grid = new Twenty48TileMap();
        start();
        addKeyListener(this);

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

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                x = j * (tileSizeX + gap) + 9;
                y = i * (tileSizeY + gap) + 12;
                g.drawImage(map.tiles[i][j].image, x, y, this);
            }
        }
    }

    // Implement the keyPressed method
    @Override
    public void keyPressed(KeyEvent e) {
        // You can add custom logic here if needed
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_RIGHT) {
            System.out.println("RIGHT!");
            grid.swipeRight(); // not working at the moment
        }
    }

    // Implement the keyReleased method
    @Override
    public void keyReleased(KeyEvent e) {
        // You can add custom logic here if needed
    }

    // Implement the keyTyped method
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void update() {
        // Implement the update logic for the 2048 game
    }

    @Override
    public void render(Graphics2D g) {
        // Implement the render logic for the 2048 game

        g.drawImage(tileBoardImage, 6, 5, 440, 440, this);
        this.renderTiles(g, grid);

        System.out.println("rendering...");

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
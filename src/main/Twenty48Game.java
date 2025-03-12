import java.awt.*;
import java.util.HashMap;
import tiles.Twenty48TileMap;
import tiles.Twenty48Tile;

import profiles.PlayerProfile;

public class Twenty48Game extends Game {
    private PlayerProfile player;
    private HashMap<String, String> rules;
    private Twenty48TileMap grid;


    public Twenty48Game(PlayerProfile player) {
        super();
        this.player = player;
        this.grid = new Twenty48TileMap();
        start();
    }



    @Override
    public void update() {
        // Implement the update logic for the 2048 game
    }

    @Override
    public void render(Graphics2D g) {
        // Implement the render logic for the 2048 game
    }


}
package tiles;

import java.util.HashMap;
import java.awt.*;
import java.util.Random;

public class Twenty48TileMap extends TileMap {
    // private static final HashMap<Integer, Image> imageMap = new HashMap<Integer,
    // Image>();
    // can change these to private later if needed, need setters and getters if so
    public Twenty48Tile[][] tiles;
    public int score;

    public Twenty48TileMap() {
        super();
        this.height = 4;
        this.width = 4;
        this.score = 0;
        initializeBlankTiles();
    }

    private void initializeBlankTiles() {
        tiles = new Twenty48Tile[4][4];
        // makes the map all zeroes to start
        for (int i = 0; i < this.height; ++i) {
            for (int j = 0; j < this.width; ++j) {
                this.tiles[i][j] = new Twenty48Tile(0, i, j);
            }
        }

        // adds two starting tiles
        this.addTile();
        this.addTile();

    }

    public boolean isEmptySpace() {
        for (int i = 0; i < this.height; ++i) {
            for (int j = 0; j < this.width; ++j) {
                if (this.tiles[i][j].isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean is2048() {
        for (int i = 0; i < this.height; ++i) {
            for (int j = 0; j < this.width; ++j) {
                if (this.tiles[i][j].getValue() == 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    public void addTile() {
        // adds a tile with value 2 to
        if (isEmptySpace()) {
            int x = (int) (Math.random() * this.width);
            int y = (int) (Math.random() * this.height);
            while (!((Twenty48Tile) this.tiles[x][y]).isEmpty()) {
                x = (int) (Math.random() * this.width);
                y = (int) (Math.random() * this.height);
            }
            this.tiles[x][y] = new Twenty48Tile(2, x, y);
        }
    }

    public void combineTiles(Twenty48Tile tile1, Twenty48Tile tile2) {
        // tile1 stays and tile2 dissapears
        if (tile1.value == tile2.value) {
            int newValue = tile1.value * 2;
            tile1.updateValue(newValue);
            tile2.makeEmpty();
            this.score += newValue;
        }
    }

    public void swipeRight() {
        Twenty48Tile current;
        Twenty48Tile right;

        this.smooshRight();
        for (int row = 0; row < tiles.length; row++) { // Iterate over rows
            for (int col = 2; col >= 0; col--) { // Iterate over columns backwards, skip the first one
                current = tiles[row][col];
                right = tiles[row][col + 1];
                if (current.isEmpty()) {
                    continue;
                } else {
                    // combine same tiles and update score
                    if (right.isNotEmpty()) {
                        this.combineTiles(right, current);
                    } else {
                        continue;
                    }
                }
            }
        }

        this.smooshRight();
        this.addTile();

    }

    public void smooshRight() {
        Twenty48Tile current;
        Twenty48Tile right;

        // this loop works backwards, idk how to explain it 
        for (int row = 0; row < tiles.length; row++) { // Iterate over rows
            for (int col = 2; col >= 0; col--) { // Iterateover columns backwards, skip the first one
                current = tiles[row][col];
                right = tiles[row][col + 1];

                if (current.isEmpty()) {
                    continue;
                } else {
                    if (right.isEmpty()) {
                        right.updateValue(current.getValue());
                        current.makeEmpty();
                    }
                }

            }
        }

        // this loop works forward to get rid of more gaps
        for (int row = 0; row < tiles.length; row++) { // Iterate over rows
            for (int col = 0; col < 3; col++) { // Iterate over columns forwards, skip the last one
                current = tiles[row][col];
                right = tiles[row][col + 1];

                if (current.isEmpty()) {
                    continue;
                } else {
                    if (right.isEmpty()) {
                        right.updateValue(current.getValue());
                        current.makeEmpty();
                    }
                }

            }
        }

    }

    // public void swipeRight() {
    // // go up to down columns but right to left for rows
    // this.smooshRight();
    // for (int i = 0; i < this.height; ++i) {
    // for (int j = this.width - 1; j > 0; --j) {
    // // not checking last tile
    // if (tiles[i][j].isNotEmpty() && tiles[i][j - 1].isNotEmpty())
    // this.combineTiles(tiles[i][j], tiles[i][j - 1]);
    // }
    // }
    // this.smooshRight();
    // }

    // public void smooshRight() {
    // for (int i = 0; i >= this.height; ++i) {
    // for (int j = this.width - 1; j > 0; --j) {
    // if (tiles[i][j].isEmpty()) {
    // tiles[i][j] = tiles[i][j - 1];
    // tiles[i][j - 1].makeEmpty();
    // }
    // }
    // }

    // }

    // algo: smoosh right, swipe right, smoosh right?
}
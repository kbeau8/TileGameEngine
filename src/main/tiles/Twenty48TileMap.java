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

    public boolean combineTiles(Twenty48Tile tile1, Twenty48Tile tile2) {
        // tile1 stays and tile2 dissapears
        if (tile1.value == tile2.value) {
            int newValue = tile1.value * 2;
            tile1.updateValue(newValue);
            tile2.makeEmpty();
            this.score += newValue;
            return true;
        }
        return false;
    }

    // right direction

    public void swipeRight() {
        //can swipe right if empty space to the right of a tile or if two tiles can combine
        Twenty48Tile current;
        Twenty48Tile right;
        boolean combined_any_tiles = false;

        boolean smoosh_completed = this.smooshRight();
        for (int row = 0; row < tiles.length; row++) { // Iterate over rows
            for (int col = 2; col >= 0; col--) { // Iterate over columns backwards, skip the first one
                current = tiles[row][col];
                right = tiles[row][col + 1];
                if (current.isEmpty()) {
                    continue;
                } else {
                    // combine same tiles and update score
                    if (right.isNotEmpty()) {
                        if(this.combineTiles(right, current)){
                            combined_any_tiles = true;
                        }
                    } else {
                        continue;
                    }
                }
            }
        }

        this.smooshRight();
        if (smoosh_completed || combined_any_tiles) {
            this.addTile();
        }
    }

    public boolean smooshRight() {
        Twenty48Tile current;
        Twenty48Tile right;
        boolean smoosh_completed = false;

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
                        smoosh_completed = true;
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
                        smoosh_completed = true;
                    }
                }

            }
        }
        return smoosh_completed;
    }

    // left direction
    public void swipeLeft() {
        Twenty48Tile current;
        Twenty48Tile left;
        boolean combined_any_tiles = false;

        boolean smoosh_completed = this.smooshLeft();
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 1; col < 4; col++) {
                current = tiles[row][col];
                left = tiles[row][col - 1];
                if (current.isEmpty()) {
                    continue;
                } else {
                    // combine same tiles and update score
                    if (left.isNotEmpty()) {
                        if(this.combineTiles(left, current)) {
                            combined_any_tiles = true;
                        }
                    } else {
                        continue;
                    }
                }
            }
        }

        this.smooshLeft();
        if (smoosh_completed || combined_any_tiles) {
            this.addTile();
        }
    }

    public boolean smooshLeft() {
        Twenty48Tile current;
        Twenty48Tile left;
        boolean smoosh_completed = false;

        for (int row = 0; row < tiles.length; row++) {
            for (int col = 1; col < 4; col++) {
                current = tiles[row][col];
                left = tiles[row][col - 1];

                if (current.isEmpty()) {
                    continue;
                } else {
                    if (left.isEmpty()) {
                        left.updateValue(current.getValue());
                        current.makeEmpty();
                        smoosh_completed = true;
                    }
                }

            }
        }

        // this loop works forward to get rid of more gaps
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 3; col > 0; col--) {
                current = tiles[row][col];
                left = tiles[row][col - 1];

                if (current.isEmpty()) {
                    continue;
                } else {
                    if (left.isEmpty()) {
                        left.updateValue(current.getValue());
                        current.makeEmpty();
                        smoosh_completed = true;
                    }
                }

            }
        }
        return smoosh_completed;
    }

    // up direction
    public void swipeUp() {
        Twenty48Tile current;
        Twenty48Tile up;
        boolean combined_any_tiles = false;


        boolean smoosh_completed = this.smooshUp();
        for (int col = 0; col < tiles.length; col++) {
            for (int row = 1; row < 4; row++) {
                current = tiles[row][col];
                up = tiles[row - 1][col];
                if (current.isEmpty()) {
                    continue;
                } else {
                    // combine same tiles and update score
                    if (up.isNotEmpty()) {
                        if(this.combineTiles(up, current)) {
                            combined_any_tiles = true;
                        }
                    } else {
                        continue;
                    }
                }
            }
        }

        this.smooshUp();
        this.smooshLeft();
        if (smoosh_completed || combined_any_tiles) {
            this.addTile();
        }
    }

    public boolean smooshUp() {
        Twenty48Tile current;
        Twenty48Tile up;
        boolean smoosh_completed = false;

        for (int col = 0; col < tiles.length; col++) {
            for (int row = 1; row < 4; row++) {
                current = tiles[row][col];
                up = tiles[row - 1][col];

                if (current.isEmpty()) {
                    continue;
                } else {
                    if (up.isEmpty()) {
                        up.updateValue(current.getValue());
                        current.makeEmpty();
                        smoosh_completed = true;
                    }
                }

            }
        }

        for (int col = 0; col < tiles.length; col++) {
            for (int row = 3; row > 0; row--) {
                current = tiles[row][col];
                up = tiles[row - 1][col];

                if (current.isEmpty()) {
                    continue;
                } else {
                    if (up.isEmpty()) {
                        up.updateValue(current.getValue());
                        current.makeEmpty();
                        smoosh_completed = true;
                    }
                }

            }
        }
        return smoosh_completed;
    }

    // down direction
    public void swipeDown() {
        Twenty48Tile current;
        Twenty48Tile down;
        boolean combined_any_tiles = false;

        boolean smoosh_completed = this.smooshDown();
        for (int col = 0; col < tiles.length; col++) {
            for (int row = 2; row >= 0; row--) {
                current = tiles[row][col];
                down = tiles[row + 1][col];
                if (current.isEmpty()) {
                    continue;
                } else {
                    // combine same tiles and update score
                    if (down.isNotEmpty()) {
                        if(this.combineTiles(down, current)) {
                            combined_any_tiles = true;
                        }
                    } else {
                        continue;
                    }
                }
            }
        }

        this.smooshDown();
        if (smoosh_completed || combined_any_tiles) {
            this.addTile();
        }
    }

    public boolean smooshDown() {
        Twenty48Tile current;
        Twenty48Tile down;
        boolean smoosh_completed = false;

        for (int col = 0; col < 4; col++) {
            for (int row = 2; row >= 0; row--) {
                current = tiles[row][col];
                down = tiles[row + 1][col];

                if (current.isEmpty()) {
                    continue;
                } else {
                    if (down.isEmpty()) {
                        down.updateValue(current.getValue());
                        current.makeEmpty();
                        smoosh_completed = true;
                    }
                }

            }
        }

        for (int col = 0; col < tiles.length; col++) {
            for (int row = 1; row < 3; row++) {
                current = tiles[row][col];
                down = tiles[row + 1][col];

                if (current.isEmpty()) {
                    continue;
                } else {
                    if (down.isEmpty()) {
                        down.updateValue(current.getValue());
                        current.makeEmpty();
                        smoosh_completed = true;
                    }
                }

            }
        }
        return smoosh_completed;
    }    
}
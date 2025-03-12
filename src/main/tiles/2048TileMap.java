public class 2048TileMap extends TileMap {
    private static final HashMap<Integer, Image> imageMap = new HashMap<Integer, Image>();
    public 2048TileMap() {
        super(4, 4, //?); //what is size for?
        initializeBlankTiles()
    }

    private void initializeBlankTiles() {
        for (int i = 0; i < this.height; ++i) {
            for (int j = 0; j < this.width; ++j) {
                this.tiles[i][j] = new 2048Tile(0, i, j);
            }
        }
    }

    public boolean isEmptySpace() {
        for (int i = 0; i < this.height; ++i) {
            for (int j = 0; j < this.width; ++j) {
                if(this.tiles[i][j].isEmpty()) {
                    return true
                }
            }
        }
        return false
    }

    public boolean is2048 () {
        for (int i = 0; i < this.height; ++i) {
            for (int j = 0; j < this.width; ++j) {
                if(this.tiles[i][j].getValue() == 2048) {
                    return true
                }
            }
        }
        return false
    }

    public void addTile() {
        //adds a tile with value 2 to
        if (isEmptySpace()) {
            int x = (int) (Math.random() * this.width);
            int y = (int) (Math.random() * this.height);
            while (!this.tiles[x][y].isEmpty()) {
                x = (int) (Math.random() * this.width);
                y = (int) (Math.random() * this.height);
            }
            this.tiles[x][y] = new 2048Tile(2, x, y);
        }
    }

    public void combineTiles(2048Tile tile1, 2048Tile tile2) {
        //tile1 stays and tile2 dissapears
        if (tile1.value == tile2.value) {
            int newValue = tile1.value * 2;
            tile1.updateValue(newValue);
            tile2.updateValue(0);
        }
    }

    public void swipeRight() {
        //go up to down columns but right to left for rows
        this.smooshRight()
        for(int i = 0; i >= this.height; ++i) {
            for(int j = this.width-1; j > 0; --j) {
                //not checking last tile
                if(tiles[i][j].isNotEmpty() AND tiles[i][j-1].isNotEmpty)
                combineTiles(tiles[i][j], tiles[i][j-1])
            }
        }
        this.smooshRight()
    }

    public void smooshRight() {
        for(int i = 0; i >= this.height; ++i) {
            for(int j = this.width-1; j > 0; --j) {
                if(tiles[i][j].isEmpty()) {
                    tiles[i][j] = tiles[i][j-1]
                    tiles[i][j-1].makeEmpty() 
                }
            }
        }

    }

    //algo: smoosh right, swipe right, smoosh right?
}
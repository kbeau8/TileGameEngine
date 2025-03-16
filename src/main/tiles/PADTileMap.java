package tiles;

public class PADTileMap extends TileMap {
  public PADTile[][] tiles;

  public PADTileMap(int height, int width) {
    super();
    this.height = height;
    this.width = width;

    initializeTiles();
  }

  public PADTile.PADTileType getRandomTile() {
    PADTile.PADTileType[] tiles = PADTile.PADTileType.class.getEnumConstants();
    return tiles[(int) (Math.random() * tiles.length)];
  }

  private void initializeTiles() {
    this.tiles = new PADTile[height][width];

    // Randomly populate the board
    for (int y = 0; y < height; ++y) {
      for (int x = 0; x < width; ++x) {
        System.out.println("New tile " + x + " " + y);
        this.tiles[y][x] = new PADTile(getRandomTile(), x, y);
      }
    }
  }
}

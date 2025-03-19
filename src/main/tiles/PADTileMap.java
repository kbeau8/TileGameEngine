package tiles;

import utils.Vector2D;

import java.util.HashSet;

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
        this.tiles[y][x] = new PADTile(getRandomTile(), x, y);
      }
    }
  }

  public void swapTiles(int srcX, int srcY, int destX, int destY) {
    PADTile temp = tiles[srcY][srcX];
    tiles[srcY][srcX] = tiles[destY][destX];
    tiles[destY][destX] = temp;
  }

  public void plop(HashSet<Vector2D> removals) {
      // Remove matches found
      for (Vector2D v : removals) tiles[v.y][v.x] = null;

      // Shift down to fill empty space
      for (int x = 0; x < width; ++x) {
          int nullSpaces = 0;

          for (int y = height - 1; y >= 0; --y) {
              if (tiles[y][x] == null) nullSpaces++;
              else if (nullSpaces > 0) {
                  tiles[y + nullSpaces][x] = tiles[y][x];
                  tiles[y][x] = null;
              }
          }
      }

      // Fill empty spaces
      for (int y = 0; y < height; ++y) {
          for (int x = 0; x < width; ++x) {
              if (tiles[y][x] == null) tiles[y][x] = new PADTile(getRandomTile(), x, y);
          }
      }
  }
}

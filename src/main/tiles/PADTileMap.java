package tiles;

public class PADTileMap extends TileMap {
  public PADTile[][] tiles;
  public int score;

  public PADTileMap() {
    super();
    this.height = 5;
    this.width = 6;

    initializeTiles();
  }

  private void initializeTiles() {
    // randomly populate board
  }
}

package logic;

import tiles.PADTile;
import tiles.PADTileMap;
import utils.Vector2D;

import java.util.HashSet;

public class PADGameLogic extends GameLogic {
  @Override
  public void checkWinConditions() {
    // TODO
  }

  @Override
  public boolean isGameOver() {
    // TODO
    return false;
  }

  @Override
  public void updateScore() {
    // TODO
  }

  @Override
  public void getScore() {
    // TODO
  }

  public HashSet<Vector2D> getMatches(PADTileMap tileMap) {
      HashSet<Vector2D> matches = new HashSet<>();

      // Check for horizontal matches
      for (int y = 0; y < 5; ++y) {
          for (int x = 0; x < 4; ++x) {
              PADTile first = tileMap.tiles[y][x];
              PADTile second = tileMap.tiles[y][x + 1];
              PADTile third = tileMap.tiles[y][x + 2];

              if (first.type == second.type && second.type == third.type) {
                  matches.add(new Vector2D(x, y));
                  matches.add(new Vector2D(x + 1, y));
                  matches.add(new Vector2D(x + 2, y));
              }
          }
      }

      // Check for vertical matches
      for (int y = 0; y < 3; ++y) {
          for (int x = 0; x < 6; ++x) {
              PADTile first = tileMap.tiles[y][x];
              PADTile second = tileMap.tiles[y + 1][x];
              PADTile third = tileMap.tiles[y + 2][x];

              if (first.type == second.type && second.type == third.type) {
                  matches.add(new Vector2D(x, y));
                  matches.add(new Vector2D(x, y + 1));
                  matches.add(new Vector2D(x, y + 2));
              }
          }
      }

      return matches;
  }
}

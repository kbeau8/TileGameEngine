package input;

import logic.PADGameLogic;
import tiles.PADTileMap;
import tiles.Twenty48TileMap;
import utils.Vector2D;

import java.awt.event.KeyEvent;
import java.util.HashSet;

public class PADInputManager extends InputManager {
    private PADTileMap grid1, grid2;
    private PADGameLogic gameLogic;

    private final int height = 5;
    private final int width = 6;

    public PADInputManager(PADTileMap grid1, PADTileMap grid2) {
        super();
        this.grid1 = grid1;
        this.grid2 = grid2;
        this.gameLogic = new PADGameLogic();
    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // Player 1 Controls
        HashSet<Vector2D> removals1 = gameLogic.getMatches(grid1);
        if (code == KeyEvent.VK_F) {
            if (grid1.movingTile) {
                grid1.plop(removals1);
                int multiplier = 1;
                if (removals1.size() > 4) {
                    multiplier = removals1.size() - 3;
                }
//                score += removals.size() * multiplier;
            }
            if (removals1.isEmpty()) {
//                health -= NO_MATCH_PENALTY;
            } else {
//                health = Math.min(100, health + MATCH_BONUS_PER_TILE * removals.size());
            }
            grid1.movingTile = !grid1.movingTile;
        }

        // Player 2 Controls
        if (grid2 != null) {
            HashSet<Vector2D> removals2 = gameLogic.getMatches(grid2);
            if (code == KeyEvent.VK_SPACE) {
                if (grid2.movingTile) {
                    grid2.plop(removals2);
                    int multiplier = 1;
                    if (removals2.size() > 4) {
                        multiplier = removals2.size() - 3;
                    }
//                score += removals.size() * multiplier;
                }
                if (removals2.isEmpty()) {
//                health -= NO_MATCH_PENALTY;
                } else {
//                health = Math.min(100, health + MATCH_BONUS_PER_TILE * removals.size());
                }
                grid2.movingTile = !grid2.movingTile;
            }
        }

        // Player 1 Controls
        if (grid1.movingTile) {
            // Swap tiles in direction of movement
            switch (code) {
                case KeyEvent.VK_W:
                    if (grid1.selected.y == 0) break;
                    grid1.swapTiles(grid1.selected.x, grid1.selected.y, grid1.selected.x, grid1.selected.y - 1);
                    grid1.selected.y--;
                    break;
                case KeyEvent.VK_S:
                    if (grid1.selected.y == height - 1) break;
                    grid1.swapTiles(grid1.selected.x, grid1.selected.y, grid1.selected.x, grid1.selected.y + 1);
                    grid1.selected.y++;
                    break;
                case KeyEvent.VK_A:
                    if (grid1.selected.x == 0) break;
                    grid1.swapTiles(grid1.selected.x, grid1.selected.y, grid1.selected.x - 1, grid1.selected.y);
                    grid1.selected.x--;
                    break;
                case KeyEvent.VK_D:
                    if (grid1.selected.x == width - 1) break;
                    grid1.swapTiles(grid1.selected.x, grid1.selected.y, grid1.selected.x + 1, grid1.selected.y);
                    grid1.selected.x++;
                    break;
            }
        } else {
            // Move selection location
            switch (code) {
                case KeyEvent.VK_W:
                    grid1.selected.y = Math.max(0, grid1.selected.y - 1);
                    break;
                case KeyEvent.VK_S:
                    grid1.selected.y = Math.min(height - 1, grid1.selected.y + 1);
                    break;
                case KeyEvent.VK_A:
                    grid1.selected.x = Math.max(0, grid1.selected.x - 1);
                    break;
                case KeyEvent.VK_D:
                    grid1.selected.x = Math.min(width - 1, grid1.selected.x + 1);
                    break;
            }
        }

        // Player 2 Controls
        if (grid2 != null && grid2.movingTile) {
            // Swap tiles in direction of movement
            switch (code) {
                case KeyEvent.VK_UP:
                    if (grid2.selected.y == 0) break;
                    grid2.swapTiles(grid2.selected.x, grid2.selected.y, grid2.selected.x, grid2.selected.y - 1);
                    grid2.selected.y--;
                    break;
                case KeyEvent.VK_DOWN:
                    if (grid2.selected.y == height - 1) break;
                    grid2.swapTiles(grid2.selected.x, grid2.selected.y, grid2.selected.x, grid2.selected.y + 1);
                    grid2.selected.y++;
                    break;
                case KeyEvent.VK_LEFT:
                    if (grid2.selected.x == 0) break;
                    grid2.swapTiles(grid2.selected.x, grid2.selected.y, grid2.selected.x - 1, grid2.selected.y);
                    grid2.selected.x--;
                    break;
                case KeyEvent.VK_RIGHT:
                    if (grid2.selected.x == width - 1) break;
                    grid2.swapTiles(grid2.selected.x, grid2.selected.y, grid2.selected.x + 1, grid2.selected.y);
                    grid2.selected.x++;
                    break;
            }
        } else if (grid2 != null) {
            // Move selection location
            switch (code) {
                case KeyEvent.VK_UP:
                    grid2.selected.y = Math.max(0, grid2.selected.y - 1);
                    break;
                case KeyEvent.VK_DOWN:
                    grid2.selected.y = Math.min(height - 1, grid2.selected.y + 1);
                    break;
                case KeyEvent.VK_LEFT:
                    grid2.selected.x = Math.max(0, grid2.selected.x - 1);
                    break;
                case KeyEvent.VK_RIGHT:
                    grid2.selected.x = Math.min(width - 1, grid2.selected.x + 1);
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}

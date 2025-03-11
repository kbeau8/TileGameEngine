import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class PADGame extends Game implements KeyListener {
    private static final int row = 6;
    private static final int col = 6;
    private static final int TILE_SIZE = 50;
    private int[][] board;
    private int selectedRow;
    private int selectedCol;
    private int points;
    private Random random;
    private boolean tileLocked;

    public PADGame() {
        super();
        initBoard();
        addKeyListener(this);
    }

    private void initBoard() {
        board = new int[row][col];
        random = new Random();
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                board[r][c] = random.nextInt(3);
            }
        }
        selectedRow = 0;
        selectedCol = 0;
        points = 0;
        tileLocked = false;
    }

    @Override
    public void update() {
        
    }

    @Override
    public void render(Graphics2D g) {
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                int tile = board[r][c];
                Color col;
                if (tile == 0) {
                    col = Color.RED;
                } else if (tile == 1) {
                    col = Color.GREEN;
                } else if (tile == 2) {
                    col = Color.BLUE;
                } else {
                    col = Color.LIGHT_GRAY;
                }
                int x = c * TILE_SIZE;
                int y = r * TILE_SIZE;
                g.setColor(col);
                g.fillOval(x + 5, y + 5, TILE_SIZE - 10, TILE_SIZE - 10);
            }
        }
        g.setColor(Color.YELLOW);
        g.drawRect(selectedCol * TILE_SIZE, selectedRow * TILE_SIZE, TILE_SIZE - 1, TILE_SIZE - 1);
        g.setColor(Color.BLACK);
        g.drawString("Points: " + points, 10, 15);
        g.drawString("Locked: " + tileLocked, 10, 30);
    }

    private void checkMatches() {
        Set<BoardPos> removeSet = new HashSet<>();
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                int tile = board[r][c];
                if (tile < 0) continue;
                if (c + 2 < col && board[r][c + 1] == tile && board[r][c + 2] == tile) {
                    removeSet.add(new BoardPos(r, c));
                    removeSet.add(new BoardPos(r, c + 1));
                    removeSet.add(new BoardPos(r, c + 2));
                }
                if (r + 2 < row && board[r + 1][c] == tile && board[r + 2][c] == tile) {
                    removeSet.add(new BoardPos(r, c));
                    removeSet.add(new BoardPos(r + 1, c));
                    removeSet.add(new BoardPos(r + 2, c));
                }
                if (r + 2 < row && c + 2 < col && board[r + 1][c + 1] == tile && board[r + 2][c + 2] == tile) {
                    removeSet.add(new BoardPos(r, c));
                    removeSet.add(new BoardPos(r + 1, c + 1));
                    removeSet.add(new BoardPos(r + 2, c + 2));
                }
                if (r + 2 < row && c - 2 >= 0 && board[r + 1][c - 1] == tile && board[r + 2][c - 2] == tile) {
                    removeSet.add(new BoardPos(r, c));
                    removeSet.add(new BoardPos(r + 1, c - 1));
                    removeSet.add(new BoardPos(r + 2, c - 2));
                }
            }
        }
        if (!removeSet.isEmpty()) {
            for (BoardPos pos : removeSet) {
                board[pos.row][pos.col] = -1;
            }
            points += removeSet.size();
            for (BoardPos pos : removeSet) {
                board[pos.row][pos.col] = random.nextInt(3);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_ENTER) {
            tileLocked = !tileLocked;
            return;
        }
        if (tileLocked) {
            int newRow = selectedRow;
            int newCol = selectedCol;
            if (code == KeyEvent.VK_LEFT && selectedCol > 0) {
                newCol = selectedCol - 1;
                checkMatches();
            } else if (code == KeyEvent.VK_RIGHT && selectedCol < col - 1) {
                newCol = selectedCol + 1;
                checkMatches();
            } else if (code == KeyEvent.VK_UP && selectedRow > 0) {
                newRow = selectedRow - 1;
                checkMatches();
            } else if (code == KeyEvent.VK_DOWN && selectedRow < row - 1) {
                newRow = selectedRow + 1;
                checkMatches();
            }
            if (newRow != selectedRow || newCol != selectedCol) {
                int temp = board[newRow][newCol];
                board[newRow][newCol] = board[selectedRow][selectedCol];
                board[selectedRow][selectedCol] = temp;
                selectedRow = newRow;
                selectedCol = newCol;
            }
        } else {
            if (code == KeyEvent.VK_LEFT && selectedCol > 0) {
                selectedCol--;
                checkMatches();
            } else if (code == KeyEvent.VK_RIGHT && selectedCol < col - 1) {
                selectedCol++;
                checkMatches();
            } else if (code == KeyEvent.VK_UP && selectedRow > 0) {
                selectedRow--;
                checkMatches();
            } else if (code == KeyEvent.VK_DOWN && selectedRow < row - 1) {
                selectedRow++;
                checkMatches();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    private static class BoardPos {
        final int row;
        final int col;
        BoardPos(int row, int col) {
            this.row = row;
            this.col = col;
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof BoardPos)) return false;
            BoardPos bp = (BoardPos) o;
            return row == bp.row && col == bp.col;
        }
        @Override
        public int hashCode() {
            return 31 * row + col;
        }
    }
}

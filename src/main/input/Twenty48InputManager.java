package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

import tiles.Twenty48TileMap;

public class Twenty48InputManager extends InputManager {

    private Twenty48TileMap grid1, grid2;

    public Twenty48InputManager(Twenty48TileMap grid1, Twenty48TileMap grid2) {
        super();
        this.grid1 = grid1;
        this.grid2 = grid2;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        // takes key inputs and does an action for a specific key
        int code = e.getKeyCode();

        if (grid2 != null && grid1 != null) {

            if (code == KeyEvent.VK_D) {
                grid1.swipeRight();
            }

            if (code == KeyEvent.VK_A) {
                grid1.swipeLeft();

            }

            if (code == KeyEvent.VK_W) {
                grid1.swipeUp();
            }

            if (code == KeyEvent.VK_S) {
                grid1.swipeDown();
            }

            if (code == KeyEvent.VK_RIGHT) {
                grid2.swipeRight();
            }

            if (code == KeyEvent.VK_LEFT) {
                grid2.swipeLeft();

            }

            if (code == KeyEvent.VK_UP) {
                grid2.swipeUp();
            }

            if (code == KeyEvent.VK_DOWN) {
                grid2.swipeDown();
            }
        }

        // player 1 inputs
        else if (grid1 != null) {

            if (code == KeyEvent.VK_RIGHT) {
                grid1.swipeRight();
            }

            if (code == KeyEvent.VK_LEFT) {
                grid1.swipeLeft();

            }

            if (code == KeyEvent.VK_UP) {
                grid1.swipeUp();
            }

            if (code == KeyEvent.VK_DOWN) {
                grid1.swipeDown();
            }
        }
    }

    // these methods are probably not needed but are here just in case
    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}

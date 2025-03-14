package logic;

import tiles.Twenty48TileMap;
import profiles.PlayerProfile;

public class Twenty48GameLogic extends GameLogic {
    public void checkWinConditions(Twenty48TileMap tilemap) {
        if (tilemap.is2048()) {
            // do win condition
        }
    }

    public boolean isGameOver(Twenty48TileMap tilemap) {
        // no empty space on board AND no tiles to combine
        if (tilemap.isEmptySpace()) {
            return false;
        }
        return true;
    }

    public void updateScore(int score, PlayerProfile player) {
        // update player's high score if higher
        if (score > player.getHighScore("2048")) {
            player.updateHighScore("2048", score);
        }
        // update actual game score
    }

    // unsure about how to use this method - AT
    public int getScore(int score) {
        return score;
    }

    // UNUSED FUNCTIONS
    @Override
    public void checkWinConditions() {
    }

    public boolean isGameOver() {
        return false;
    }

    @Override
    public void updateScore() {
    }

    @Override
    public void getScore() {
    }

}
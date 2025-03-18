package logic;

import tiles.Twenty48TileMap;
import profiles.PlayerProfile;
import profiles.ProfileManager;

public class Twenty48GameLogic extends GameLogic {
    public void checkWinConditions(Twenty48TileMap tilemap) {
    }

    public boolean isGameWon(Twenty48TileMap tilemap) {
        if (tilemap.is2048()) {
            ProfileManager.saveProfiles();
            return true;
        }
        return false;
    }

    public boolean isGameLost(Twenty48TileMap tilemap) {
        // no empty space on board AND no tiles to combine
        if (tilemap.isEmptySpace()) {
            ProfileManager.saveProfiles();
            return false;
        }
        return true;
    }

    // todo: redo this with tilemap score
    public void updateScore(int score, PlayerProfile player) {
        // update player's high score if higher
        if (score > player.getHighScore("2048")) {
            player.updateHighScore("2048", score);
        }
    }

    // unsure about how to use this method and not sure if it's needed - AT
    public int getScore(Twenty48TileMap tilemap) {
        return tilemap.score;
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
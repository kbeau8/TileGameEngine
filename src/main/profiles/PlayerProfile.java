package profiles;

import java.io.Serializable;

public class PlayerProfile implements Serializable {
    private String username;
    private int highScore;
    private int gamesPlayed;

    public PlayerProfile(String username) {
        this.username = username;
        this.highScore = 0;
        this.gamesPlayed = 0;
    }

    public String getUsername() { return username; }
    public int getHighScore() { return highScore; }
    public int getGamesPlayed() { return gamesPlayed; }

    public void updateHighScore(int score) {
        if (score >= highScore) {
            highScore = score;
        }
    }

    public void incrementGamesPlayed() {
        gamesPlayed++;
    }
}


package profiles;

import java.util.Map;
import java.util.HashMap;

public class PlayerProfile {
    private String username;
    private String password;
    private Map<String, Integer> highScores;
    private int gamesPlayed;

    public PlayerProfile(String username, String password) {
        this.username = username;
        this.password = password;
        this.highScores = new HashMap<>();
        this.gamesPlayed = 0;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public int getGamesPlayed() { return gamesPlayed; }
    public Map<String, Integer> getAllHighScores() { return highScores; }
    public int getHighScore(String game) {
        if (highScores.containsKey(game)) {
            return highScores.get(game);
        }
        return 0;
    }


    public void updateHighScore(String game, int score) {
        if (!highScores.containsKey(game) || score >= highScores.get(game)) {
            highScores.put(game, score);
        }
    }

    public void incrementGamesPlayed() {
        gamesPlayed++;
    }

    public String formatText() {
        StringBuilder sb = new StringBuilder();

        // Format: username,password,gamesPlayed,Game:highScore(0 or more)
        sb.append(username).append(",").append(password).append(",").append(gamesPlayed);

        for (Map.Entry<String, Integer> highScore : highScores.entrySet()) {
            sb.append(",").append(highScore.getKey()).append(":").append(highScore.getValue());
        }
        return sb.toString();
    }

    public static PlayerProfile getProfile(String line) {
        String[] parts = line.split(",");
        if (parts.length < 3) return null;

        PlayerProfile profile = new PlayerProfile(parts[0], parts[1]);
        profile.gamesPlayed = Integer.parseInt(parts[2]);
        
        for (int i = 3; i < parts.length; i++) {
            String[] gameData = parts[i].split(":");
            if (gameData.length == 2) {
                profile.highScores.put(gameData[0], Integer.parseInt(gameData[1]));
            }
        }
        return profile;
    }
}
package profiles;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ProfileManager {
    private static final String FILE_PATH = "assets/data/profiles.txt";
    private static Map<String, PlayerProfile> profiles = new HashMap<>();

    public static void loadProfiles() {
        profiles.clear();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try {
                Files.createDirectories(Path.of(FILE_PATH).getParent());
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                PlayerProfile profile = PlayerProfile.getProfile(line);
                if (profile != null) {
                    profiles.put(profile.getUsername(), profile);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveProfiles() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (PlayerProfile profile : profiles.values()) {
                writer.write(profile.formatText());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PlayerProfile registerUser(String username, String password) {
        if (profiles.containsKey(username)) {
            return null;
        }
        PlayerProfile profile = new PlayerProfile(username, password);
        profiles.put(username, profile);
        saveProfiles();

        return profile;
    }

    public static PlayerProfile authenticateUser(String username, String password) {
        PlayerProfile profile = profiles.get(username);
        if (profile != null && profile.getPassword().equals(password)) {
            return profile;
        }
        return null;
    }
}
package profiles;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ProfileManager {
    private static final String FILE_PATH = "profiles.dat";
    private static Map<String, PlayerProfile> profiles = new HashMap<>();

    public static void loadProfiles() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            profiles = (Map<String, PlayerProfile>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            profiles = new HashMap<>();  // No profiles yet
        }
    }

    public static void saveProfiles() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(profiles);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PlayerProfile getOrCreateProfile(String username) {
        return profiles.computeIfAbsent(username, PlayerProfile::new);
    }
}

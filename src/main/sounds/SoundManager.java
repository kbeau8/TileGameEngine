package sounds;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundManager {
    private Clip clip;

    public void playSFX(String filePath) {
        try {
            File soundFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);

            // Convert to correct audio format because java sound dumb
            AudioFormat baseFormat = audioStream.getFormat();
            AudioFormat targetFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    44100,
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    44100,
                    false
            );

            AudioInputStream convertedStream = AudioSystem.getAudioInputStream(targetFormat, audioStream);
            Clip clip = AudioSystem.getClip();
            clip.open(convertedStream);
            clip.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void startMusic(String filePath) {
        stopMusic();

        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            // Convert to correct audio format because java sound dumb
            AudioFormat baseFormat = audioStream.getFormat();
            AudioFormat targetFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    44100,
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    44100,
                    false
            );

            AudioInputStream convertedStream = AudioSystem.getAudioInputStream(targetFormat, audioStream);
            clip = AudioSystem.getClip();
            clip.open(convertedStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
}

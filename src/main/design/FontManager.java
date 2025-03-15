package design;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FontManager {
    private static Font pixelFont;

    public static Font getPixelFont(float size) {
        if (pixelFont == null) {
            try {
                File fontFile = new File("assets/fonts/pixel-modern.ttf");
                pixelFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            } catch (IOException | FontFormatException e) {
                e.printStackTrace();
                pixelFont = new Font("Monospaced", Font.PLAIN, (int) size);
            }
        }
        return pixelFont.deriveFont(size);
    }
}

import java.awt.*;

public class Renderer {
    public static void drawTile(Graphics2D g, Image img, int x, int y, int size) {
        g.drawImage(img, x, y, size, size, null);
    }
}
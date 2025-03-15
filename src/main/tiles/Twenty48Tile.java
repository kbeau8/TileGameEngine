package tiles;

import java.util.HashMap;
import java.awt.*;

class Twenty48TileImages {
    static final HashMap<Integer, Image> imageMap = new HashMap<>();
    static final String file_path = "2048-assets-retro/";

    static {
        Image image = new javax.swing.ImageIcon(file_path + "tile-blank.png").getImage();
        imageMap.put(0, image);
        for (int i = 2; i <= 2048; i = i + i) {
            image = new javax.swing.ImageIcon(file_path + "tile-" + String.valueOf(i) + ".png").getImage();
            imageMap.put(i, image);
        }
    }
}

public class Twenty48Tile extends Tile {
    public int value;
    public static int tileSize = 100;
    public int x, y;
    public Image image;

    public Twenty48Tile(int value, int x, int y) {
        super();
        this.value = value;
        this.image = Twenty48TileImages.imageMap.get(value);
        this.x = x;
        this.y = y;
        this.size = tileSize;
    }

    public int getValue() {
        return this.value;
    }

    public boolean isEmpty() {
        if (this.value == 0) {
            return true;
        }
        return false;
    }

    public boolean isNotEmpty() {
        if (this.value == 0) {
            return false;
        }
        return true;
    }

    public void makeEmpty() {
        this.updateValue(0);
    }

    public void updateValue(int value) {
        this.value = value;
        this.image = Twenty48TileImages.imageMap.get(value);
    }
}
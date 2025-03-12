import java.utli.HashMap;

class Twenty48TileImages {
    static final HashMap<Integer, Image> imageMap = new HashMap<>();
    static final String file_path = "../2048-assets"

    static {
        imageMap.put(0, new Image(file_path + "blank.png").getImage());
        imageMap.put(2, new Image(file_path + "tile-2.png").getImage());
        imageMap.put(4, new Image(file_path + "tile-4.png").getImage());
        imageMap.put(8, new Image(file_path + "tile-8.png").getImage());
        imageMap.put(16, new Image(file_path + "tile-16.png").getImage());
        imageMap.put(32, new Image(file_path + "tile-32.png").getImage());
        imageMap.put(64, new Image(file_path + "tile-64.png").getImage());
        imageMap.put(128, new Image(file_path + "tile-128.png").getImage());
        imageMap.put(256, new Image(file_path + "tile-256.png").getImage());
        imageMap.put(512, new Image(file_path + "tile-512.png").getImage());
        imageMap.put(1024, new Image(file_path + "tile-1024.png").getImage());
        imageMap.put(2048, new Image(file_path + "tile-2048.png").getImage());
    }
}

public class Twenty48Tile extends Tile {
    public int value;
    public static size = 100;
    
    public Twenty48Tile(int value, int x, int y) {
        super()
        this.value = value
        this.image = Twenty48TileImages.imageMap.get(value)
        this.x = x
        this.y = y
        this.size = size
    }

    public int getValue() {
        return this.value
    }

    public boolean isEmpty() {
        if(this.value == 0) {
            return true
        }
        return false
    }

    public boolean isNotEmpty() {
        if(this.value == 0) {
            return false
        }
        return true
    }

    public void makeEmpty() {
        this.updateValue(0)
    }

    public void updateValue(int value) {
        this.value = value
        this.image = Twenty48TileImages.imageMap.get(value)
    }
}
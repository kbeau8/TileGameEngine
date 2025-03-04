import java.utli.HashMap;

class 2048TileImages {
    static final HashMap<Integer, Image> imageMap = new HashMap<>();

    static {
        //MAKE SURE THESE ARE ACTULALLY THE FILE NAMES
        this.imageMap.put(0, new Image("src/main/resources/0.png").getImage());
        this.imageMap.put(2, new Image("src/main/resources/2.png").getImage());
        this.imageMap.put(4, new Image("src/main/resources/4.png").getImage());
        this.imageMap.put(8,
        this.imageMap.put(16,
        this.imageMap.put(32,
        this.imageMap.put(64,
        this.imageMap.put(128,
        this.imageMap.put(256,
        this.imageMap.put(512,
        this.imageMap.put(1024,
        this.imageMap.put(2048,
    }
}

public class 2048Tile extends Tile {
    public int value;
    public static size = 100;
    
    public 2048Tile(int value, int x, int y) {
        super()
        this.value = value
        this.image = 2048TileImages.imageMap.get(value)
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

    public void updateValue(int value) {
        this.value = value
        this.image = 2048TileImages.imageMap.get(value)
    }
}
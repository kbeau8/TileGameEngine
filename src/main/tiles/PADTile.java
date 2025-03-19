package tiles;

import java.awt.Image;
import java.util.HashMap;

class PADTileImages {
    static final HashMap<PADTile.PADTileType, Image> imageMap = new HashMap<>();
    static final String ASSET_PATH = "pad-assets/";

    static {
        for (PADTile.PADTileType type : PADTile.PADTileType.values()) {
            Image image = new javax.swing.ImageIcon(ASSET_PATH + "tile-" + type.toString().toLowerCase() + ".png").getImage();
            imageMap.put(type, image);
        }
    }
}

public class PADTile extends Tile {
    public enum PADTileType {
        FIRE, WOOD, WATER, LIGHT, DARK
    }

    public PADTileType type;

    public PADTile(PADTileType type, int x, int y) {
        super();
        this.type = type;
        this.x = x;
        this.y = y;
        this.image = PADTileImages.imageMap.get(type);
    }

    public PADTileType getType() {
        return this.type;
    }

    public Image getImage() { return this.image; }

    public void setType(PADTileType type) {
        this.type = type;
        this.image = PADTileImages.imageMap.get(type);
    }
}
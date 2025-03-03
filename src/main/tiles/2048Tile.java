public class 2048Tile extends Tile {
    public int value;
    
    public 2048Tile() {
        //default constructor makes blank tiles
        //zero will be the value of a blank tile
        super();
        this.value = 0
        this.image = //image for blank tile
    }

    public 2048Tile(int value) {
        super()
        this.value = value
        this.image = //make a dictionary for tile images to assign the correct one quickly
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
}
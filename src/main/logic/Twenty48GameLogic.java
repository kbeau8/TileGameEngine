package logic;
import tiles.Twenty48TileMap;

public class Twenty48GameLogic extends GameLogic {
    public void checkWinConditions(Twenty48TileMap tilemap) {
        if(tilemap.is2048()) {
            //do win condition
        }
    }

    public boolean isGameOver(Twenty48TileMap tilemap) {
        //no empty space on board AND no tiles to combine
        if(tilemap.isEmptySpace()) {
            return false;
        }
        return true;
    }

    @Override
    public void updateScore() {
        // Implement the score update logic for the 2048 game
    }

    @Override
    public void getScore() {
        // Implement the score retrieval logic for the 2048 game
    }

    //UNUSED FUNCTIONS
    @Override 
    public void checkWinConditions() {}
    public boolean isGameOver() {
        return false;
    }

}
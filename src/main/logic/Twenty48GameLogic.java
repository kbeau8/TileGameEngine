public class Twenty48GameLogic extends GameLogic {
    @Override
    public void checkWinConditions(Twenty48TileMap tilemap) {
        if(tilemap.is2048()) {
            //do win condition
        }
    }

    @Override
    public boolean isGameOver(Twenty48TileMap tilemap) {
        //no empty space on board AND no tiles to combine
        if(tilemap.isEmptySpace() && )

    @Override
    public void updateScore() {
        // Implement the score update logic for the 2048 game
    }

    @Override
    public void getScore() {
        // Implement the score retrieval logic for the 2048 game
    }
}
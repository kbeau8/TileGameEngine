import java.awt.Graphics2D;

public abstract class GameState {
    public abstract void update();
    public abstract void render(Graphics2D g);
}
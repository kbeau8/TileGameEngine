import javax.swing.*;
import java.awt.*;

// Base engine, needs specific game to implement it
public abstract class Game extends JPanel implements Runnable {
    protected boolean running = false;
    private Thread gameThread;

    public Game() {
        // Keep set resolution otherwise dumb scaling things
        setSize(new Dimension(800, 600));
        setFocusable(true);
    }

    public void start() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void stop() {
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public abstract void update();
    public abstract void render(Graphics2D g);

    @Override
    public void run() {
        while (running) {
            update();
            repaint();
            try {
                // Stable 60 fps
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render((Graphics2D) g);
    }
}

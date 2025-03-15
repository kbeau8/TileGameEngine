public class GameTimer {
    private int timeLeft;
    public boolean isRunning;
    private long lastUpdate;

    public GameTimer(int startTime) {
        this.timeLeft = startTime;
        this.isRunning = false;
        this.lastUpdate = System.currentTimeMillis();
    }

    public void update() {
        if (isRunning && timeLeft > 0) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastUpdate >= 1000) {
                timeLeft--;
                lastUpdate = currentTime;
            }
        }
        else {
            isRunning = false;
        }
    }

    public void startTimer() {
        isRunning = true;
        lastUpdate = System.currentTimeMillis();
    }

    public void stopTimer() {
        isRunning = false;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void resetTimer(int startTime) {
        this.timeLeft = startTime;
        this.isRunning = false;
        this.lastUpdate = System.currentTimeMillis();
    }
}
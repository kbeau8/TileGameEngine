public class GameTimer {
    private int timeLeft;
    private boolean isRunning;

    public GameTimer(int startTime) {
        this.timeLeft = startTime;
        this.isRunning = false;
    }

    public void update() {
        if (isRunning && timeLeft > 0) {
            timeLeft--;
        }
        else {
            isRunning = false;
        }
    }

    public void startTimer() {
        isRunning = true;
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
    }
}
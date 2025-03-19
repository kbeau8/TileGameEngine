public class Twenty48GameTimer extends GameTimer {
    private long time_started;
    private int time_elapsed;

    public Twenty48GameTimer(int time) {
        super(time);

    }

    @Override 
    public void update() {
        if(isRunning) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastUpdate >= 1000) {
                time_elapsed++;
                lastUpdate = currentTime;
            }
        }
    }

    @Override
    public void startTimer() {
        this.time_started = (int) System.currentTimeMillis();
        this.time_elapsed = 0;
        this.isRunning = true;
    }

    public int getTimeElapsed() {
        return time_elapsed;
        //return (int) ((System.currentTimeMillis() - time_started)/1000);
    }
}
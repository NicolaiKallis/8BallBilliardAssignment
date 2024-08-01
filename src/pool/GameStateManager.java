package pool;

public class GameStateManager {

    public boolean isNewGameCalled = false;

    private long startTime;
    private double initialTime = 120.0; // [s]

    private static final GameStateManager instance = new GameStateManager();

    public static GameStateManager getInstance() {
        return instance;
    }

    public void NewGame(){
        this.startTime = System.currentTimeMillis();
    }

    public String TimerUpdate() {
        double currentTime = System.currentTimeMillis();
        double elapsedTime = (currentTime - this.startTime) / 1000.0;

        double remainingTime = initialTime - elapsedTime;

        if (remainingTime < 0) {
            remainingTime = 0;
        }

        long min = (long) (remainingTime / 60);
        long sec = (long) (remainingTime % 60);

        return String.format("Remaining time: %02d:%02d", min, sec);
    }

    public void increasePlayingTime() {initialTime += 10;}
}

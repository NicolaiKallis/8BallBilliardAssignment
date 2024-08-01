package pool;

public class GameStateManager {
    private static final GameStateManager instance = new GameStateManager();
    public static GameStateManager getInstance() {
        return instance;
    }

    public enum GameState {
        NEW_GAME("New Game"),
        READY_FOR_HIT("CueBall ready for Hit"),
        IN_MOTION("Balls in Motion"),
        GAME_OVER_WIN("Game Over - You win!"),
        GAME_OVER_LOSS("Game Over - You lose!");


        private final String stateMessage;
        GameState(String stateMessage){this.stateMessage = stateMessage; }

        public String getStateMessage() { return this.stateMessage; }
    }

    public GameState currentState = GameState.NEW_GAME;

    public boolean isNewGameCalled = false;
    private long startTime;

    private double initialTime = 120.0; // [s]

    public String NewGame(){
        this.startTime = System.currentTimeMillis();
        currentState = GameState.READY_FOR_HIT;

        return GameState.READY_FOR_HIT.getStateMessage();
    }

    public void setCurrentState(GameState newState){ currentState = newState; }
    public GameState getCurrentState() { return currentState; }

    private double getRemainingTime(){
        double currentTime = System.currentTimeMillis();
        double elapsedTime = (currentTime - this.startTime) / 1000.0;
        double remainingTime = initialTime - elapsedTime;

        return Math.max(0, remainingTime);
    }

    public String TimerUpdate() {
        double remainingTime = getRemainingTime();

        if (remainingTime < 0) {
            remainingTime = 0;
        }

        long min = (long) (remainingTime / 60);
        long sec = (long) (remainingTime % 60);

        return String.format("Remaining time: %02d:%02d", min, sec);
    }

    public void increasePlayingTime() {initialTime += 10;}

    public boolean checkWinningCondition() {
        if (getRemainingTime() > 0){
            setCurrentState(GameState.GAME_OVER_WIN);
            return true;
        }
        return false;
    }

    public boolean checkLosingCondition() {
        if(getRemainingTime() <= 0){
            setCurrentState(GameState.GAME_OVER_LOSS);
            //TODO: How to restart game after short delay?
            return true;
        }
        return false;
    }
}

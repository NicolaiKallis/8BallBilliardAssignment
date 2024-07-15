package pool;

import ch.aplu.jgamegrid.Actor;


public class Ball extends Actor {
    private final int ballNumber;
    private static final int BALL_SIZE = 20;
    private static final int[][] POCKETS = {
            {1,1}, {39,1}, {1,23}, {39,23},     // Corners
            {19, 1}, {19,23}                    // Middle pockets
    };
    private static final int POCKET_THRESHOLD = 1;
    private static final int MOVE_STEP = 1;

    public Ball(String assetPath, int ballNumber) {
        super(assetPath);
        this.ballNumber = ballNumber;
    }

    public int getBallNumber() {
        return ballNumber;
    }

    public void act() {
    }

    private boolean isInPocket() {
        int x = getX();
        int y = getY();

        for (int[] pocket : POCKETS) {
            int px = pocket[0];
            int py = pocket[1];

            if (Math.abs(x-px) <= POCKET_THRESHOLD && Math.abs(y-py) <= POCKET_THRESHOLD) {
                return true;
            }
        }
        return false;
    }
}

package pool;

import ch.aplu.jgamegrid.Actor;

import java.awt.event.KeyEvent;


public class Ball extends Actor {

    private static final int[][] POCKETS = {
            {1,1}, {39,1}, {1,23}, {39,23},     // Corners
            {19, 1}, {19,23}                    // Middle pockets
    };
    private static final int POCKET_THRESHOLD = 1;
    private static final int MOVE_STEP = 1;

    public Ball() {
        super("assets/kugel_white.gif");
    }

    public void act() {
        if (gameGrid.isKeyPressed(KeyEvent.VK_UP)) {
            setDirection(270);
            move(MOVE_STEP);
        }
        if (gameGrid.isKeyPressed(KeyEvent.VK_DOWN)) {
            setDirection(90);
            move(MOVE_STEP);
        }
        if (gameGrid.isKeyPressed(KeyEvent.VK_LEFT)) {
            setDirection(180);
            move(MOVE_STEP);
        }
        if (gameGrid.isKeyPressed(KeyEvent.VK_RIGHT)) {
            setDirection(0);
            move(MOVE_STEP);
        }

        if (isInPocket()) {
            hide();
        }
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

package pool;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;
import ch.aplu.jgamegrid.GGVector;

public class CueBall extends Ball {
    private static final String IMAGE_PATH = "assets/kugel_white.gif";
    private static final int BALL_SIZE = 20;

    public CueBall(int wallOffset) {
        super(IMAGE_PATH, 0);
    }
}

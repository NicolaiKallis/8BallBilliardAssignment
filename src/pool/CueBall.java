package pool;

public class CueBall extends Ball {
    private static final String IMAGE_PATH = "assets/kugel_white.gif";

    public CueBall(PoolTable poolTable) {
        super(IMAGE_PATH, 0, poolTable);
    }

    @Override
    protected void handlePocketCollision() {
        haltBall();
        setLocation(BallPositions.CUE_BALL_POSITION);
        LocationToPosition();
    }
}

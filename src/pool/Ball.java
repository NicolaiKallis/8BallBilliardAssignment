package pool;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.GGVector;
import ch.aplu.jgamegrid.Location;


public class Ball extends Actor {
    private final int ballNumber;
    private static final int BALL_SIZE = 20;
    private static final int[][] POCKETS = {
            {1,1}, {39,1}, {1,23}, {39,23},     // Corners
            {19, 1}, {19,23}                    // Middle pockets
    };
    private static final int POCKET_THRESHOLD = 1;

    private static int table_wall_offset = PoolTable.TABLE_WALL_OFFSET;

    private float dx, dy;
    final float FRICTION = 0.05f;

    private GGVector pos = new GGVector(0,0);
    private GGVector vel = new GGVector(0,0);

    public static final String[] ballAssets = {
            "assets/kugel_1.gif", "assets/kugel_2.gif", "assets/kugel_3.gif",
            "assets/kugel_4.gif", "assets/kugel_5.gif", "assets/kugel_6.gif",
            "assets/kugel_7.gif", "assets/kugel_8.gif", "assets/kugel_9.gif",
            "assets/kugel_10.gif", "assets/kugel_11.gif", "assets/kugel_12.gif",
            "assets/kugel_13.gif", "assets/kugel_14.gif", "assets/kugel_15.gif"
    };

    public Ball(String assetPath, int ballNumber) {
        super(assetPath);
        this.ballNumber = ballNumber;
    }

    @Override
    public void act() {
        LocationToPosition();
        if (!isInTableBed()) {
            keepInBed();
        }
    }

    public void LocationToPosition () {
        pos = new GGVector(getLocation().x, getLocation().y);
    }

    void hit(float force, float angle) {
        //System.out.println("angle " + angle);
        dx = (float) (Math.cos(angle) * force);
        dy = (float) (Math.sin(angle) * force);
        //System.out.println("dx " + dx);
        //System.out.println("dy " + dy);

        updateHitPosition(dx, dy);
    }

    void updateHitPosition(float dx, float dy) {
        while (Math.abs(dx) != 0 && Math.abs(dy) != 0) {
            float x = getX();
            float y = getY();

            x += dx;
            y += dy;

            if (dx > 0) dx -= FRICTION;
            else if (dx < 0) dx += FRICTION;

            if (dy > 0) dy -= FRICTION;
            else if (dy < 0) dy += FRICTION;

            if (Math.abs(dx) < FRICTION) dx = 0;
            if (Math.abs(dy) < FRICTION) dy = 0;

            int x_int = Math.round(x);
            int y_int = Math.round(y);

            System.out.println(x);
            System.out.println(y);

            setX(x_int);
            setY(y_int);
        }
    }



    public int getBallNumber() {
        return ballNumber;
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

    private boolean isInTableBed() {
        return (pos.x >= table_wall_offset          &&
                pos.y >= table_wall_offset          &&
                pos.x <= 800 - table_wall_offset    &&
                pos.y <= 438 - table_wall_offset);

    }

    private void keepInBed() {
        System.out.println("implementation needed.");
    }
}

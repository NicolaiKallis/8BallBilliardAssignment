package pool;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.GGVector;
import ch.aplu.jgamegrid.Location;

import java.time.Duration;
import java.time.Instant;


public class Ball extends Actor {
    private final int ballNumber;
    private static final int BALL_SIZE = 20;
    private static final int[][] POCKETS = {
            {1,1}, {39,1}, {1,23}, {39,23},     // Corners
            {19, 1}, {19,23}                    // Middle pockets
    };
    private static final int POCKET_THRESHOLD = 1;

    private static final int table_wall_offset = PoolTable.TABLE_WALL_OFFSET;

    private GGVector pos = new GGVector(0,0);
    private GGVector vel = new GGVector(0,0);

    private Instant AccessTime;

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
        AccessTime = Instant.now();
    }

    @Override
    public void act() {
        LocationToPosition();
        if (!isInTableBed()) {
            System.out.println("vel_X: " + vel.x);
            System.out.println("vel_Y: " + vel.y);
            keepInBed();
        }

        double dt = Duration.between(AccessTime , Instant.now()).toMillis() / 1000f;
        //System.out.println(dt);
        double dx = vel.x * dt;
        double dy = vel.y * dt;

        vel = vel.sub(vel.mult(PoolTable.FRICTION_CLOTH_BALL*dt));
        pos = pos.add(new GGVector(dx, dy));

        setLocation(new Location((int)pos.x, (int)pos.y));

        AccessTime = Instant.now();
    }

    public void LocationToPosition () {
        pos = new GGVector(getLocation().x, getLocation().y);
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
        final double dampingFactor = 0.9;

        if (pos.x <= table_wall_offset) {
            vel.x = -vel.x * dampingFactor;
            pos.x = table_wall_offset + 1;
        } else if (pos.x >= 800 - table_wall_offset) {
            vel.x = -vel.x * dampingFactor;
            pos.x = 800 - table_wall_offset - 1;
        }

        if (pos.y <= table_wall_offset) {
            vel.y = -vel.y * dampingFactor;
            pos.y = table_wall_offset + 1;
        } else if (pos.y >= 438 - table_wall_offset) {
            vel.y = -vel.y * dampingFactor;
            pos.y = 438 - table_wall_offset - 1;
        }
    }

    public void setterVel(GGVector velocity) {
        System.out.println(velocity);
        vel = velocity;
    }

    public GGVector getterVel() {
        return vel.clone();
    }
}

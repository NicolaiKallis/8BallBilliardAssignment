package pool;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.GGCircle;
import ch.aplu.jgamegrid.GGVector;
import ch.aplu.jgamegrid.Location;

import java.time.Duration;
import java.time.Instant;


public class Ball extends Actor {
    private final int ballNumber;
    private final PoolTable poolTable;
    public static final int BALL_RADIUS = 20;

    // physical properties sourced from https://billiards.colostate.edu/faq/physics/physical-properties/
    public static final double FRICTION_BALL_BALL = 0.055;
    private static final double BALL_VEL_STOP_CONDITION = 15;

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

    public Ball(String assetPath, int ballNumber, PoolTable poolTable) {
        super(assetPath);
        this.ballNumber = ballNumber;
        this.poolTable = poolTable;
        AccessTime = Instant.now();
    }

    @Override
    public void act() {
        LocationToPosition();
        if(!vel.isEqual(new GGVector(0,0))) {
            if (!isInTableBed()) {
                keepInBed();
            }
            else if (isInPocket()) {
                handlePocketCollision();
            }
            if (vel.magnitude() < BALL_VEL_STOP_CONDITION) {
                haltBall();
            }
        }

        double dt = Duration.between(AccessTime , Instant.now()).toMillis() / 1000f;

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

    private boolean isInPocket() {
        for (GGCircle pocket : PoolTable.pockets) {
            if (pocket.isIntersecting(pos)) {
                return true;
            }
        }
        return false;
    }

    protected void handlePocketCollision() {
        haltBall();
        removeSelf();
        poolTable.passivateBall(this);
    }

    protected void haltBall() {
        vel = new GGVector(0, 0);
    }

    public GGVector getterPos() {
        return pos.clone();
    }

    public GGVector getterVel() {
        return vel.clone();
    }

    public void setterVel(GGVector velocity) {
        vel = velocity;
    }


}

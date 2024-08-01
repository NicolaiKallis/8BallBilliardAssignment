package pool;

import java.util.ArrayList;
import java.util.List;

import ch.aplu.jgamegrid.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class PoolTable extends GameGrid implements MouseMotionListener,
        MouseListener, GGActorCollisionListener {

    private static final int TABLE_LENGTH               = 800;
    private static final int TABLE_WIDTH                = 438;
    public static final int TABLE_WALL_OFFSET           = 50;

    private static final int CELL_SIZE                  = 1;

    // phyiscal properties sourced from https://billiards.colostate.edu/faq/physics/physical-properties/
    public static final double FRICTION_CLOTH_BALL      = 0.2;
    private static final GGVector POCKET_TOP_LEFT       = new GGVector(25, 25);
    private static final GGVector POCKET_TOP_MIDDLE     = new GGVector(400, 25);
    private static final GGVector POCKET_TOP_RIGHT       = new GGVector(775,
            25);
    private static final GGVector POCKET_BOTTOM_LEFT    = new GGVector(25, 413);
    private static final GGVector POCKET_BOTTOM_MIDDLE  = new GGVector(400, 413);
    private static final GGVector POCKET_BOTTOM_RIGHT   = new GGVector(775,
            413);

    // assumed a ratio of 2:1 (pocket size: balls)
    // assumption made from: https://www.dimensions.com/element/billiards-pool-table-pockets
    // increased radius even further than 2:1 for UX
    // POCKET_RADIUS_MIDDLE is slightly too large but makes the UX way better.
    // If reduced to 30 a correct hit is difficult to achieve.
    private static final int POCKET_RADIUS_MIDDLE       = 35;
    private static final int POCKET_RADIUS_CORNERS      = 45;

    public static final ArrayList<GGCircle> pockets = new ArrayList<>();

    static {
            pockets.add(new GGCircle(POCKET_TOP_LEFT, POCKET_RADIUS_CORNERS));
            pockets.add(new GGCircle(POCKET_TOP_MIDDLE, POCKET_RADIUS_MIDDLE));
            pockets.add(new GGCircle(POCKET_TOP_RIGHT, POCKET_RADIUS_CORNERS));
            pockets.add(new GGCircle(POCKET_BOTTOM_LEFT, POCKET_RADIUS_CORNERS));
            pockets.add(new GGCircle(POCKET_BOTTOM_MIDDLE, POCKET_RADIUS_MIDDLE));
            pockets.add(new GGCircle(POCKET_BOTTOM_RIGHT, POCKET_RADIUS_CORNERS));
    }

    private static final double FACTOR_BALL_BALL_COLLISION =
            1 - Ball.FRICTION_BALL_BALL;

    private static final String TABLE_ASSET = "assets/pool_table.png";

    private CueStick cueStick;
    private CueBall cueBall;


    private final ArrayList<Actor> playingBalls = new ArrayList<>();
    public final ArrayList<Ball> activeBalls = new ArrayList<>();

    public PoolTable() {
        super(TABLE_LENGTH, TABLE_WIDTH, CELL_SIZE, null, TABLE_ASSET,false);
        setSimulationPeriod(20);
        addMouseMotionListener(this);
        addMouseListener(this);

        setBallsToStartPositions();
        setCueStickToStartPosition();

        show();
    }

    @Override
    public void act() {
        System.out.println("Printing velocities of all active balls:");
        for (int i = 0; i < activeBalls.size(); i++) {
            Ball ball = activeBalls.get(i);
            GGVector velocity = ball.getterVel();
            System.out.println("Ball " + i + " - Velocity: (" + velocity.x + ", " + velocity.y + ")");
            if (allBallStopped(activeBalls)) {
                System.out.println("STOP");
            }

            this.repaint();
        }
    }

    public void setBallsToStartPositions(){
        List<Integer> ballIndices = BallPositions.getRandomBallIndices();

        for (int i=0; i < BallPositions.TRIANGLE_POSITIONS.length; i++) {
            int BallIndex = ballIndices.get(i);
            Ball ball = new Ball(Ball.ballAssets[BallIndex - 1], BallIndex,
                    this);
            addActor(ball, BallPositions.TRIANGLE_POSITIONS[i]);
            ball.LocationToPosition();
            playingBalls.add(ball);
            activeBalls.add(ball);
            ball.addCollisionActors(playingBalls);
            ball.addActorCollisionListener(this);

            //ballNum = ball.getBallNumber();

        }

        cueBall = new CueBall(this);
        addActor(cueBall, BallPositions.CUE_BALL_POSITION);
        cueBall.LocationToPosition();
        activeBalls.add(cueBall);
        cueBall.addCollisionActors(playingBalls);
        cueBall.addActorCollisionListener(this);
    }

    public void setCueStickToStartPosition() {
        cueStick = new CueStick(cueBall, this);
        // fix this defintion as a constant somehow
        Location initialCueStickPosition = new Location(cueBall.getX() - CueStick.DISTANCE_FROM_CUE_BALL,
                cueBall.getY());
        addActor(cueStick, initialCueStickPosition);
    }

    @Override
    public int collide(Actor actor1, Actor actor2){
        if (actor1 instanceof Ball && actor2 instanceof Ball) {
            Ball ball1 = (Ball) actor1;
            Ball ball2 = (Ball) actor2;

            // vector pointing from actor2 to actor1
            GGVector distanceBalls =
                    ball1.getterPos().sub(ball2.getterPos());

            if (distanceBalls.magnitude2() == 0) {
                // Avoid division by zero
                return 15;
            }

            GGVector velA = ball1.getterVel();
            GGVector velB = ball2.getterVel();

            double dotProductA =
                    distanceBalls.dot(velA.sub(velB)) / distanceBalls.magnitude2();
            double dotProductB =
                    distanceBalls.dot(velB.sub(velA)) / distanceBalls.magnitude2();

            GGVector velAPost =
                    velA.sub(distanceBalls.mult(dotProductA * (FACTOR_BALL_BALL_COLLISION)));
            GGVector velBPost =
                    velB.sub(distanceBalls.mult(dotProductB * (FACTOR_BALL_BALL_COLLISION)));

            ball1.setterVel(velAPost);
            ball2.setterVel(velBPost);

            double absDistanceBalls = distanceBalls.magnitude();
            double overlap = Ball.BALL_SIZE - absDistanceBalls;

            // TODO: DEBUG
            if (overlap > 0) {
                GGVector collisionDirection = distanceBalls.mult(1 / absDistanceBalls);
                double forceMag = overlap * Ball.FRICTION_BALL_BALL;

                ball1.setterVel(ball1.getterVel().add(collisionDirection.mult(forceMag)));
                ball2.setterVel(ball2.getterVel().sub(collisionDirection.mult(forceMag)));
            }
        }
        return 15;
    }

    private boolean allBallStopped(ArrayList<Ball> activeBalls) {
        for (Ball ball : activeBalls) {
            GGVector vel = ball.getterVel();
            if (vel.x != 0 || vel.y != 0) {
                return false;
            }
        }
        return true;
    }

    public void passivateBall(Ball ball) {
        activeBalls.remove(ball);
    }








    @Override
    public void mouseMoved(MouseEvent e) {
        Point mousePos = e.getPoint();
        Location mouseLocation = toLocationInGrid(mousePos);

        Location cueBallLocation = cueBall.getLocation();
        Point cueBallPosition = new Point (cueBallLocation.x , cueBallLocation.y);
        double angle = Math.atan2(mouseLocation.getY() - cueBall.getY(), mouseLocation.getX() - cueBall.getX());
        cueStick.updatePosition(Math.toDegrees(angle));
        this.repaint();
    }

    //TODO: First 5 lines in helper method
    @Override
    public void mouseDragged(MouseEvent e) {
        Point mousePos = e.getPoint();
        Location mouseLocation = toLocationInGrid(mousePos);

        Location cueBallLocation = cueBall.getLocation();
        Point cueBallPosition = new Point (cueBallLocation.x , cueBallLocation.y);
        double angle = Math.atan2(mouseLocation.getY() - cueBall.getY(), mouseLocation.getX() - cueBall.getX());
        cueStick.pullBackCue(Math.toDegrees(angle));
        this.repaint();

    }

    //TODO: First 5 lines in helper method
    //TODO: Lock MouseDragged somehow --> Debug motion!
    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("Released");
        Point mousePos = e.getPoint();
        Location mouseLocation = toLocationInGrid(mousePos);

        double angle = Math.atan2(mouseLocation.getY() - cueBall.getY(), mouseLocation.getX() - cueBall.getX());

        cueStick.hitCueBall(angle);

        this.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Clicked");
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
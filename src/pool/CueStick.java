package pool;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.GameGrid;
import ch.aplu.jgamegrid.Location;

import java.awt.Point;

import java.util.Objects;

// Note: the arrow image was flipped 180° to implement the functionality of the cue stick more easily
public class CueStick extends Actor {
    private static final String IMAGE_PATH = "assets/arrow.png";
    private static final double INITIAL_ANGLE = 0d;
    public static final int DISTANCE_FROM_CUE_BALL = 20;
    private static final int MAX_DISTANCE_FROM_CUE_BALL = 90;
    private CueBall cueBall; //--> TODO: Has to connected to the actual cue ball
    private GameGrid gameGrid;

    public CueStick(CueBall cueBall, GameGrid gameGrid) {
        super(true, IMAGE_PATH); // Initialize without an image
        Objects.requireNonNull(cueBall);
        this.cueBall = cueBall;
        this.gameGrid = gameGrid;
        //updatePosition(INITIAL_ANGLE); // Initialize position

    }

    @Override
    public void act() {
        Point mousePosition = gameGrid.getMousePosition();
        System.out.println(mousePosition);
        if (mousePosition != null) {
            Location mouseLocation = gameGrid.toLocationInGrid(mousePosition);

            double dx = mouseLocation.x - cueBall.getX();
            double dy = mouseLocation.y - cueBall.getY();
            double angleInDegrees = Math.toDegrees(Math.atan2(dy, dx));

            //updatePosition(angleInDegrees);
        }
    }

    // TODO: implement correctly: Cue Stick should sit in the same direction as mouse
    // TODO: Naming convention should be improved
    public void updatePosition(double angle, Point cueBallPosition) {
        double correctedAngle = angle + 180;
        double radians = Math.toRadians(angle);

        int x = (int) (cueBall.getX() + DISTANCE_FROM_CUE_BALL * Math.cos(radians));
        int y = (int) (cueBall.getY() + DISTANCE_FROM_CUE_BALL * Math.sin(radians));

        setLocation(new Location(x,y));
        //System.out.println(angle);
        //System.out.println(correctedAngle);
        setDirection(correctedAngle);
        rotate(cueBallPosition, correctedAngle);
    }

    public void hitCueBall(double angle, Point cueBallPosition) {
        double correctedAngle = angle + 180;
        double radians = Math.toRadians(angle);

        int x = (int) (cueBall.getX() + MAX_DISTANCE_FROM_CUE_BALL * Math.cos(radians));
        int y = (int) (cueBall.getY() + MAX_DISTANCE_FROM_CUE_BALL * Math.sin(radians));

        setLocation(new Location(x,y));
        Point Goal = new Point(x,y);
        //System.out.println(angle);
        //System.out.println(correctedAngle);
        setDirection(correctedAngle);
        rotate(cueBallPosition, correctedAngle);
        //updatePosition(correctedAngle, Goal);
    }

}

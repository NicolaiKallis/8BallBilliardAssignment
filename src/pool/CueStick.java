package pool;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

import java.awt.Point;
import java.awt.MouseInfo;

import java.util.Objects;


public class CueStick extends Actor {
    private static final String IMAGE_PATH = "assets/arrow.png";
    private static final double INTIAL_ANGLE = 0d;
    private static final int DISTANCE_FROM_CUE_BALL = 50;
    private Ball cueBall; // what for? --> TODO: Has to connected to the actual cue ball


    public CueStick(Ball cueBall) {
        super(IMAGE_PATH); // Intialize without an image
        Objects.requireNonNull(cueBall);
        this.cueBall = cueBall;
        updatePosition(INTIAL_ANGLE); // Initialize position
    }

    public void act() {
        Point mousePosition = MouseInfo.getPointerInfo().getLocation();
        double angle = Math.atan2(mousePosition.y - cueBall.getY(), mousePosition.x - cueBall.getX());
        updatePosition(Math.toDegrees(angle)-90);
    }

    public void updatePosition(double angle) {
        double radians = Math.toRadians(angle);
        int x = (int) (cueBall.getX() + DISTANCE_FROM_CUE_BALL * Math.cos(radians));
        int y = (int) (cueBall.getY() + DISTANCE_FROM_CUE_BALL * Math.sin(radians));
        setLocation(new Location(x,y));
        setDirection(angle);
    }


}

package pool;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.GGVector;
import ch.aplu.jgamegrid.GameGrid;
import ch.aplu.jgamegrid.Location;

import java.util.Objects;

// Note: the arrow image was flipped 180° to implement the functionality of the cue stick more easily
public class CueStick extends Actor {
    private static final String IMAGE_PATH              = "assets/arrow.png";
    public static final int DISTANCE_FROM_CUE_BALL      = 20;
    private static final int MAX_DISTANCE_FROM_CUE_BALL = 90;
    // assumed value for good UX
    private static final float FORCE_CUESTICK_HIT       = 300f;

    private boolean visible;
    private boolean enabled;

    private final CueBall cueBall;

    public CueStick(CueBall cueBall, GameGrid gameGrid) {
        super(true, IMAGE_PATH); // Initialize without an image
        Objects.requireNonNull(cueBall);
        this.cueBall = cueBall;
        this.gameGrid = gameGrid;
    }

    @Override
    public void act() {
        if (!enabled) {
            return;
        }
    }

    private void updateCueStickPosition(double angle, int distance) {
        double radians = Math.toRadians(angle);

        int x = (int) (cueBall.getX() + distance * Math.cos(radians));
        int y = (int) (cueBall.getY() + distance * Math.sin(radians));

        setLocation(new Location(x,y));
        setDirection(angle+180);
    }

    public void updateCueStartPosition(double angle) {
        updateCueStickPosition(angle, DISTANCE_FROM_CUE_BALL);
    }

    public void pullBackCue(double angle) {
        updateCueStickPosition(angle, MAX_DISTANCE_FROM_CUE_BALL);
    }

    public void hitCueBall(double radians) {
        if (!enabled) {
            return;
        }

        GGVector initVel =
                new GGVector(FORCE_CUESTICK_HIT * Math.cos(radians+Math.PI),
                        FORCE_CUESTICK_HIT * Math.sin(radians+Math.PI));
        cueBall.setterVel(initVel);
    }

    public void setEnabled(boolean enabled){this.enabled = enabled;}
    public void setVisible(boolean visible){
        this.visible = visible;
        if (this.visible) {
            show();
        } else {
            hide();
        }
    }
}

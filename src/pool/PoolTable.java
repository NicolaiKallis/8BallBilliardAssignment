package pool;

import java.util.List;

import ch.aplu.jgamegrid.GameGrid;
import ch.aplu.jgamegrid.Location;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class PoolTable extends GameGrid implements MouseMotionListener, MouseListener {

    private static final int TABLE_LENGTH       = 800;
    private static final int TABLE_WIDTH        = 438;
    public static final int TABLE_WALL_OFFSET  = 40;

    private static final int CELL_SIZE          = 1;

    private static final String TABLE_ASSET = "assets/pool_table.png";

    private CueStick cueStick;
    private CueBall cueBall;

    public PoolTable() {
        super(TABLE_LENGTH, TABLE_WIDTH, CELL_SIZE, null, TABLE_ASSET, false);
        setSimulationPeriod(10);
        addMouseMotionListener(this);
        addMouseListener(this);

        setBallsToStartPositions();
        setCueStickToStartPosition();

        show();
    }

    @Override
    public void act() {
        this.repaint();
    }

    public void setBallsToStartPositions(){
        List<Integer> ballIndices = BallPositions.getRandomBallIndices();

        for (int i=0; i < BallPositions.TRIANGLE_POSITIONS.length; i++) {
            int BallIndex = ballIndices.get(i);
            Ball ball = new Ball(Ball.ballAssets[BallIndex - 1], BallIndex);
            addActor(ball, BallPositions.TRIANGLE_POSITIONS[i]);
        }

        cueBall = new CueBall(TABLE_WALL_OFFSET);
        addActor(cueBall, BallPositions.CUE_BALL_POSITION);
        cueBall.LocationToPosition();
    }

    public void setCueStickToStartPosition() {
        cueStick = new CueStick(cueBall, this);
        // fix this defintion as a constant somehow
        Location initialCueStickPosition = new Location(cueBall.getX() - CueStick.DISTANCE_FROM_CUE_BALL,
                cueBall.getY());
        addActor(cueStick, initialCueStickPosition);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Point mousePos = e.getPoint();
        Location mouseLocation = toLocationInGrid(mousePos);

        Location cueBallLocation = cueBall.getLocation();
        Point cueBallPosition = new Point (cueBallLocation.x , cueBallLocation.y);
        double angle = Math.atan2(mouseLocation.getY() - cueBall.getY(), mouseLocation.getX() - cueBall.getX());
        cueStick.updatePosition(Math.toDegrees(angle), cueBallPosition);
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
        cueStick.pullBack(Math.toDegrees(angle), cueBallPosition);
        this.repaint();

    }

    //TODO: First 5 lines in helper method
    //TODO: Lock MouseDragged somehow --> Debug motion!
    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("Released");
        Point mousePos = e.getPoint();
        Location mouseLocation = toLocationInGrid(mousePos);

        Location cueBallLocation = cueBall.getLocation();
        Point cueBallPosition = new Point (cueBallLocation.x , cueBallLocation.y);
        double angle = Math.atan2(mouseLocation.getY() - cueBall.getY(), mouseLocation.getX() - cueBall.getX());


        cueStick.hitCueBall(angle, cueBallPosition, cueBall);

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
package pool;

import ch.aplu.jgamegrid.GameGrid;
import ch.aplu.jgamegrid.Location;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

public class PoolGame extends GameGrid implements MouseMotionListener, MouseListener {

    private CueStick cueStick;
    private CueBall cueBall;


    public PoolGame() {
        super(800, 438, 1, null, "assets/pool_table.png", false);
        setSimulationPeriod(20);
        addMouseMotionListener(this);
        addMouseListener(this);

        cueBall = new CueBall();
        addActor(cueBall, BallPositions.CUE_BALL_POSITION);

        cueStick = new CueStick(cueBall, this);
        Location initialStickPosition = new Location (cueBall.getX() - CueStick.DISTANCE_FROM_CUE_BALL, cueBall.getY());
        addActor(cueStick, initialStickPosition);

        show();

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

    @Override
    public void mouseDragged(MouseEvent e) {
        Point mousePos = e.getPoint();
        Location mouseLocation = toLocationInGrid(mousePos);

        Location cueBallLocation = cueBall.getLocation();
        Point cueBallPosition = new Point (cueBallLocation.x , cueBallLocation.y);
        double angle = Math.atan2(mouseLocation.getY() - cueBall.getY(), mouseLocation.getX() - cueBall.getX());
        cueStick.hitCueBall(Math.toDegrees(angle), cueBallPosition);
        this.repaint();

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("Released");
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

    public static void main(String[] args) {
        PoolGame poolGame = new PoolGame();
        poolGame.show();

        String[] ballAssets = {
                "assets/kugel_1.gif", "assets/kugel_2.gif", "assets/kugel_3.gif",
                "assets/kugel_4.gif", "assets/kugel_5.gif", "assets/kugel_6.gif",
                "assets/kugel_7.gif", "assets/kugel_8.gif", "assets/kugel_9.gif",
                "assets/kugel_10.gif", "assets/kugel_11.gif", "assets/kugel_12.gif",
                "assets/kugel_13.gif", "assets/kugel_14.gif", "assets/kugel_15.gif"
        };

        List<Integer> ballIndices = BallPositions.getRandomBallIndices();

        for (int i=0; i < BallPositions.TRIANGLE_POSITIONS.length; i++) {
            int BallIndex = ballIndices.get(i);
            Ball ball = new Ball(ballAssets[BallIndex - 1], BallIndex);
            poolGame.addActor(ball, BallPositions.TRIANGLE_POSITIONS[i]);
        }

        new Location(0 , 0);
    }
}

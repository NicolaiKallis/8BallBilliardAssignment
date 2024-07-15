package pool;

import ch.aplu.jgamegrid.GGMouse;
import ch.aplu.jgamegrid.GameGrid;
import ch.aplu.jgamegrid.Location;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public abstract class PoolGame extends GameGrid implements MouseListener {

    private CueStick cueStick;
    private CueBall cueBall;

    public PoolGame() {
        super(800, 438, 1, null, "assets/pool_table.png", false);
        setSimulationPeriod(20);

        cueBall = new CueBall();
        addActor(cueBall, new Location(200, 219));

        cueStick = new CueStick(cueBall);

        Location initialStickPosition = new Location (cueBall.getX() + CueStick.DISTANCE_FROM_CUE_BALL, cueBall.getY());

        addActor(cueStick, initialStickPosition);



        show();
    }

    //@Override
    public boolean mouseEvent(GGMouse mouse) {
        Location mouseLocation = new Location(mouse.getX(), mouse.getY());
        if (mouse.getEvent() == GGMouse.lPress) {

        } else if (mouse.getEvent() == GGMouse.lDrag) {
            cueStick.mouseDragged();

        } else if (mouse.getEvent() == GGMouse.lRelease) {
            cueStick.mouseReleased();

        } else if (mouse.getEvent() == GGMouse.lClick) {
            cueStick.mousePressed();
        }

        return true;

    }

    public static void main(String[] args) {

        PoolGame poolGame = new PoolGame() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
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

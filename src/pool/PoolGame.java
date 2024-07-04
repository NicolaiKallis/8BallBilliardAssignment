package pool;

import ch.aplu.jgamegrid.GameGrid;
//import ch.aplu.jgamegrid.Location;

import java.util.List;

public class PoolGame {

    public static void main(String[] args) {
        GameGrid gameGrid = new GameGrid(800, 438, 1, null, false);
        gameGrid.hide();
        gameGrid.setBgImagePath("assets/pool_table.png");
        gameGrid.setTitle("8-Ball Pool Game");
        gameGrid.doRun();

        Ball cueBall = new Ball("assets/kugel_white.gif", 0);
        gameGrid.addActor(cueBall, BallPositions.CUE_BALL_POSITION);

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
            gameGrid.addActor(ball, BallPositions.TRIANGLE_POSITIONS[i]);
        }


        gameGrid.show();
    }
}

package pool;

import ch.aplu.jgamegrid.Location;

public class BallPositions {
    public static final Location CUE_BALL_POSITION = new Location(200, 219);

    public static final Location [] TRIANGLE_POSITIONS = {
            // first row
            new Location(600, 219),
            // second row
            new Location(620, 209), new Location(620, 229),
            //third row
            new Location(640, 199), new Location(640, 219), new Location(640, 239),
            // forth row
            new Location(660, 189), new Location(660, 209), new Location(660, 229), new Location(660, 249),
            // fifth row
            new Location(680, 179), new Location(680, 199), new Location(680, 219), new Location(680, 239), new Location(680, 259)
    };
}

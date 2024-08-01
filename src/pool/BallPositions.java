package pool;

import ch.aplu.jgamegrid.Location;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BallPositions {
    public static final Location CUE_BALL_POSITION = new Location(200, 219);

    public static final Location[] TRIANGLE_POSITIONS = {
            // first row
            new Location(580, 219),
            // second row
            new Location(600, 209), new Location(600, 229),
            //third row
            new Location(620, 199), new Location(620, 219), new Location(620, 239),
            // forth row
            new Location(640, 189), new Location(640, 209), new Location(640, 229), new Location(640, 249),
            // fifth row
            new Location(660, 179), new Location(660, 199), new Location(660, 219), new Location(660, 239), new Location(660, 259)
    };

    public static List<Integer> getRandomBallIndices() {
        List<Integer> solids = new ArrayList<>();
        List<Integer> stripes = new ArrayList<>();

        for (int i = 1; i <= 7; i++) {
            solids.add(i);
        }
        for (int i = 9; i <= 15; i++) {
            stripes.add(i);
        }

        // Shuffle the ordered lists
        Collections.shuffle(solids);
        Collections.shuffle(stripes);

        // Combine the two lists excluding the 8-ball
        List<Integer> ballIndices = new ArrayList<>(solids);
        ballIndices.addAll(stripes);

        // Add 8-ball in the correct position
        ballIndices.add(4, 8);

        // Ensures different colors for position 11 and 15
        if ((ballIndices.get(10) >= 1 && ballIndices.get(10) <= 7) == (ballIndices.get(13) >= 1 && ballIndices.get(13) <= 7)) {
            if (ballIndices.get(10) >= 1 && ballIndices.get(10) <= 7) {
                // Both are solids, replace one with a stripe
                for (int i = 0; i < 14; i++) {
                    if (ballIndices.get(i) >= 9 && ballIndices.get(i) <= 15) {
                        Collections.swap(ballIndices, 14, i);
                        break;
                    }
                }
            } else {
                // Both are stripes, replace one with a solid
                for (int i = 0; i < 14; i++) {
                    if (ballIndices.get(i) >= 1 && ballIndices.get(i) <= 7) {
                        Collections.swap(ballIndices, 14, i);
                        break;
                    }
                }

            }
        }
        return ballIndices;
    }
}

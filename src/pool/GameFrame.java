package pool;

import javax.swing.*;
import java.awt.*;

public class GameFrame {

    private static final int GAME_FRAME_LENGTH = 800;
    private static final int GAME_FRAME_HEIGHT = 500;

    private GameFrame() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static JFrame createGameFrame() {
        JFrame gameFrame = new JFrame("8Ball Pool");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(GAME_FRAME_LENGTH, GAME_FRAME_HEIGHT);
        return gameFrame;
    }

    public static JLabel createGameTimerLabel() {
        JLabel gameTimer = new JLabel("00:00");
        gameTimer.setHorizontalAlignment(JLabel.CENTER);
        gameTimer.setFont(new Font("Arial", Font.BOLD, 18));

        return gameTimer;
    }

    public static JLabel createBallCounterLabel() {
        JLabel ballCounterLabel = new JLabel("Balls left: 0");
        ballCounterLabel.setHorizontalAlignment(JLabel.LEFT);
        ballCounterLabel.setFont(new Font("Arial", Font.BOLD, 18));

        return ballCounterLabel;
    }
}

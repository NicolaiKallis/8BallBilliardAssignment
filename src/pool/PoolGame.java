package pool;

import javax.swing.*;
import java.awt.*;

public class PoolGame{

    // TODO: refactor if time allows
    public static void main(String[] args) {
        JFrame gameFrame = GameFrame.createGameFrame();
        JLabel gameTimer = GameFrame.createGameTimerLabel();
        JLabel ballCounter = GameFrame.createBallCounterLabel();

        JPanel southPanel = new JPanel(new BorderLayout());
        JPanel timerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel ballCounterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        PoolTable poolTable = new PoolTable(gameTimer, ballCounter);
        poolTable.doRun();

        timerPanel.add(gameTimer);
        ballCounterPanel.add(ballCounter);

        southPanel.add(timerPanel, BorderLayout.CENTER);
        southPanel.add(ballCounterPanel, BorderLayout.WEST);

        gameFrame.setLayout(new BorderLayout());
        gameFrame.add(southPanel, BorderLayout.SOUTH);
        gameFrame.add(poolTable, BorderLayout.CENTER);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
    }
}

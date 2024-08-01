package pool;

import javax.swing.*;
import java.awt.*;

public class PoolGame{

    public static void main(String[] args) {
        JFrame gameFrame = GameFrame.createGameFrame();
        JLabel gameTimer = GameFrame.createGameTimerLabel();
        JLabel gameState = GameFrame.createStatusLabel();
        JLabel ballCounter = GameFrame.createBallCounterLabel();

        JPanel southPanel = new JPanel(new BorderLayout());
        JPanel timerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel statePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel ballCounterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        PoolTable poolTable = new PoolTable(gameTimer, ballCounter, gameState);
        poolTable.doRun();

        timerPanel.add(gameTimer);
        statePanel.add(gameState);
        ballCounterPanel.add(ballCounter);

        southPanel.add(timerPanel, BorderLayout.EAST);
        southPanel.add(statePanel, BorderLayout.CENTER);
        southPanel.add(ballCounterPanel, BorderLayout.WEST);

        gameFrame.setLayout(new BorderLayout());
        gameFrame.add(southPanel, BorderLayout.SOUTH);
        gameFrame.add(poolTable, BorderLayout.CENTER);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
    }
}

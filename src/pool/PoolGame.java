package pool;

import ch.aplu.jgamegrid.GameGrid;
import ch.aplu.jgamegrid.Location;

import java.awt.*;

public class PoolGame {
    public static void main(String[] args) {
        GameGrid gameGrid = new GameGrid(40, 22, 20, java.awt.Color.red, false);
        gameGrid.setBgImagePath("assets/pool_table.png");
        gameGrid.setTitle("Pool Game");
        gameGrid.doRun();

        gameGrid.show();
    }
}

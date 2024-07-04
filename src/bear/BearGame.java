package bear;

import ch.aplu.jgamegrid.GameGrid;
import ch.aplu.jgamegrid.Location;

public class BearGame {

	public static void main(String[] args) {
		GameGrid gameGrid = new GameGrid(10, 10, 60, java.awt.Color.red, false);
		gameGrid.setTitle("Control the Bear with the arrow keys and try to eat the leafs");
		gameGrid.doRun();
		Bear bear = new Bear(Leaf.class);
		gameGrid.addActor(bear, new Location(0, 0));
		for (int i = 0; i < 10; i++){
			gameGrid.addActor(new Leaf(), gameGrid.getRandomEmptyLocation());
		}
		gameGrid.show();
	}

}
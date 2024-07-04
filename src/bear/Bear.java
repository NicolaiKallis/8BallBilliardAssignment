package bear;

import java.awt.event.KeyEvent;

import ch.aplu.jgamegrid.Actor;

public class Bear extends Actor {
	
	private Class<? extends Actor> foodClazz;

	public Bear(Class<? extends Actor> foodClazz) {
		super("sprites/bear.gif");
		this.foodClazz = foodClazz;
	}

	public void act() {
		if (gameGrid.isKeyPressed(KeyEvent.VK_UP) && getY() > 0) {
			setDirection(270);
			move();
		}
		if (gameGrid.isKeyPressed(KeyEvent.VK_DOWN) && getY() < 9) {
			setDirection(90);
			move();
		}
		if (gameGrid.isKeyPressed(KeyEvent.VK_LEFT) && getX() > 0) {
			setDirection(180);
			move();
		}
		if (gameGrid.isKeyPressed(KeyEvent.VK_RIGHT) && getX() < 9) {
			setDirection(0);
			move();
		}
		tryToEat();
	}

	void tryToEat() {
		Actor actor = gameGrid.getOneActorAt(getLocation(), foodClazz);
		if (actor != null)
			actor.hide();
	}
}
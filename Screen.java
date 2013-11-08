package dmeyers.engine;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import cs195n.Vec2i;

public abstract class Screen  {
	
	protected Application app;
	
	public Screen(Application a) {
		app = a;
	}


	protected void onTick(long nanosSincePreviousTick) {

	}

	
	protected void onDraw(Graphics2D g) {

	}

	
	protected void onKeyTyped(KeyEvent e) {

	}

	
	protected void onKeyPressed(KeyEvent e) {

	}

	
	protected void onKeyReleased(KeyEvent e) {

	}

	
	protected void onMouseClicked(MouseEvent e) {

	}

	
	protected void onMousePressed(MouseEvent e) {

	}

	
	protected void onMouseReleased(MouseEvent e) {

	}

	
	protected void onMouseDragged(MouseEvent e) {

	}

	
	protected void onMouseMoved(MouseEvent e) {

	}

	
	protected void onMouseWheelMoved(MouseWheelEvent e) {

	}

	
	protected void onResize(Vec2i newSize) {
		

	}

}

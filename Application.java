package dmeyers.engine;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import cs195n.*;

public class Application extends SwingFrontEnd {

	 public ArrayList<Screen> screens = new ArrayList<Screen>();
	
	 public int screenIndex = 0;

	 public Vec2i screenSize;
	 
	String title;

	public Vec2i oldSize;
	
	
	public Application(String title, boolean fullscreen) {
		super(title, fullscreen);
	}
	
	public Application(String title, boolean fullscreen, Vec2i windowSize) {
		super(title, fullscreen, windowSize);
		screenSize = windowSize;
	}

	public Application(String title, boolean fullscreen, Vec2i windowSize,
			int closeOp) {
		super(title, fullscreen, windowSize, closeOp);
		screenSize = windowSize;
	}
	
	public void switchTo(int index){
		screenIndex = index;
		onResize(screenSize);
	}
	
	public void set(int index, Screen s){
		if (index >= 0 && index < (screens.size())){
			screens.set(index, s);
		}
		else if (index >=0 && index == (screens.size())) {
			screens.add(s);
		}
		else {
			System.out.println("Setting failed. Index given is not valid.");
			return;
		}
		switchTo(index);
	}

	@Override
	protected void onTick(long nanosSincePreviousTick) {
		screens.get(screenIndex).onTick(nanosSincePreviousTick);

	}

	@Override
	protected void onDraw(Graphics2D g) {
		screens.get(screenIndex).onDraw(g);
	}

	@Override
	protected void onKeyTyped(KeyEvent e) {
		screens.get(screenIndex).onKeyTyped(e);

	}

	@Override
	protected void onKeyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.out.println("Esc was pressed.");
			super.doShutdown();
			return;
		} 
		else {
			screens.get(screenIndex).onKeyPressed(e);
		}
	}

	@Override
	protected void onKeyReleased(KeyEvent e) {
		screens.get(screenIndex).onKeyReleased(e);

	}

	@Override
	protected void onMouseClicked(MouseEvent e) {
		screens.get(screenIndex).onMouseClicked(e);
	}

	@Override
	protected void onMousePressed(MouseEvent e) {
		screens.get(screenIndex).onMousePressed(e);

	}

	@Override
	protected void onMouseReleased(MouseEvent e) {
		screens.get(screenIndex).onMouseReleased(e);

	}

	@Override
	protected void onMouseDragged(MouseEvent e) {
		screens.get(screenIndex).onMouseDragged(e);

	}

	@Override
	protected void onMouseMoved(MouseEvent e) {
		screens.get(screenIndex).onMouseMoved(e);

	}

	@Override
	protected void onMouseWheelMoved(MouseWheelEvent e) {
		screens.get(screenIndex).onMouseWheelMoved(e);

	}

	@Override
	protected void onResize(Vec2i newSize) {
		oldSize = screenSize;
		screenSize = newSize;

		for (int i = 0; i < screens.size(); i++){
			screens.get(i).onResize(newSize);
		}
	}


}

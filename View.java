package dmeyers.engine;

import java.awt.Graphics2D;

import cs195n.Vec2f;

public interface View {

	public void onDraw(Graphics2D g);
	public void onTick(long nanoSeconds);
	public void onMouseClicked(Vec2f returnVec, int i);
}

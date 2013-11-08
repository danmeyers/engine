package dmeyers.engine.UI;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import cs195n.*;

public class RectangleButton {

	String title;
	Vec2f scale;
	float dimx;
	float dimy;
	
	Vec2f pos;
	
	Color color;
	private Color textColor;
	
	public RectangleButton(Vec2f pos, Vec2f scale, String t, Color c) {
		title = t;
		this.pos = pos;
		this.scale = scale;
		
		color = c;
		
		
		dimx = scale.x;
		dimy = scale.y;
		
		textColor = Color.WHITE;
	}

	
	public boolean contains(MouseEvent e){
		
		double x = e.getPoint().getX();
		double y = e.getPoint().getY();
		if (x >= pos.x && x <= (pos.x + dimx) && y >= pos.y && y <= (pos.y + dimy)) return true;
		return false;
	}
	
	public void draw(Graphics2D g) {
		g.setColor(color);
		Rectangle2D.Float rec = new Rectangle2D.Float(pos.x, pos.y, dimx, dimy);
		g.draw(rec);
		g.fill(rec);
		g.setColor(textColor);
		
		g.setFont(new Font(g.getFont().getName(), g.getFont().getStyle(), (int) dimy));
		
		while (g.getFontMetrics().getStringBounds(title, g).getWidth() >= dimx){
			g.setFont(new Font(g.getFont().getFontName(),g.getFont().getStyle(),g.getFont().getSize() -1));
		}
		
		g.drawString(title, (pos.x +(dimx / 2) - ((float) g.getFontMetrics().getStringBounds(title, g).getWidth() / 2)), (pos.y + dimy- (dimy / 8)));
	}

	
	public Vec2f getScale() {
		return scale;
	}
	
	public Vec2f getPos(){
		return pos;
	}


	public void changeCoords(int i, int j) {
		pos = pos.plus(i, j);
	}
	
	public void changeTextColor(Color c){
		textColor = c;
	}
}

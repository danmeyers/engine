package dmeyers.engine.UI;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import cs195n.Vec2f;

public class UIElement {

	String title;
	Vec2f scale;
	float dimx;
	float dimy;
	
	Vec2f pos;
	
	
	public UIElement(Vec2f pos, Vec2f scale, String t) {
		title = t;
		this.pos = pos;
		this.scale = scale;
		
		dimx = scale.x;
		dimy = scale.y;
	}
	
	public void setTitle(String t){
		title = t;
	}

	
	public void setDim(Vec2f v){
		scale = v;
		dimx = v.x;
		dimy = v.y;
	}
	
	
	public void setPos(Vec2f v){
		pos = v;
	}
	
	public boolean contains(MouseEvent e){
		
		double x = e.getPoint().getX();
		double y = e.getPoint().getY();
		if (x >= pos.x && x <= (pos.x + dimx) && y >= pos.y && y <= (pos.y + dimy)) return true;
		return false;
	}
	
	public void draw(Graphics2D g) {
		Rectangle2D.Float rec = new Rectangle2D.Float(pos.x, pos.y, dimx, dimy);

		g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, (int) dimy));

		while (g.getFontMetrics().getStringBounds(title, g).getWidth() >= dimx - 5){
			g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, g.getFont().getSize() - 1));
			System.out.println(g.getFont().getSize());
		}
		
		g.setColor(Color.BLACK);
		g.fill(rec);
		g.setColor(Color.WHITE);

		g.draw(rec);
		g.drawString(title, pos.x + 5, (float) (pos.y + g.getFontMetrics().getAscent() - 5));
		
		g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, (int) dimy));
		g.drawString(title, (float) (pos.x - g.getFontMetrics().getStringBounds(title, g).getWidth()), pos.y + g.getFontMetrics().getAscent() -5);
	}


	public Vec2f getSecondVector() {
		return scale;
	}
	
	public Vec2f getFirstVector(){
		return pos;
	}

	public void setTitle(int gold) {
		setTitle(new Integer(gold).toString());
	}
}

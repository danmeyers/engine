package dmeyers.engine.geom;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;

import cs195n.Vec2f;

public class Cross {

	Vec2f pos;
	Vec2f direction;

	
	public Cross(Vec2f pos, Vec2f dir) {

		this.pos = pos;
		direction = dir;
		
	}

	
	public boolean contains(MouseEvent e){
		
		double x = e.getPoint().getX();
		double y = e.getPoint().getY();
		if (x >= pos.x && x <= (pos.x + direction.x) && y >= pos.y && y <= (pos.y + direction.y)) return true;
		return false;
	}
	
	public void draw(Graphics2D g) {

		Vec2f pos2 = new Vec2f(pos.x,direction.y);
		Vec2f direction2 = new Vec2f(direction.x,pos.y);
		
		Line2D.Float line1 = new Line2D.Float(pos.x,pos.y,direction.x,direction.y);
		Line2D.Float line2 = new Line2D.Float(pos2.x,pos2.y,direction2.x,direction2.y);
		
		g.draw(line1);
		g.draw(line2);
	}
}

package dmeyers.engine.UI;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.*;

import cs195n.Vec2f;

public class UILineElement {

	String title;
	String content;
	
	Vec2f scale;
	Vec2f pos;
	
	Vec2f innerPos;
	Vec2f innerScale;
	
	
	
	public UILineElement(Vec2f pos, Vec2f scale, Vec2f iP, Vec2f iS, String t, String c) {
		title = t;
		this.pos = pos;
		this.scale = scale;
		content = c;
		
		innerPos = pos.plus(iP);
		innerScale = iS;
	}
	
	public void setTitle(String t){
		title = t;
	}
	
	public void setContent(String c){
		content = c;
	}

	
	public void setDim(Vec2f v){
		scale = v;
	}
	
	
	public void setPos(Vec2f v){
		pos = v;
		innerPos = v.plus(innerScale.x, 0);
	}
	
	public boolean contains(MouseEvent e){
		
		double x = e.getPoint().getX();
		double y = e.getPoint().getY();
		if (x >= pos.x && x <= (pos.x + scale.x) && y >= pos.y && y <= (pos.y + scale.y)) return true;
		return false;
	}
	
	public void draw(Graphics2D g) {

		Rectangle2D.Float innerRec = new Rectangle2D.Float(innerPos.x,innerPos.y, innerScale.x, innerScale.y);
		
		
		g.setColor(Color.BLACK);
		g.fill(innerRec);
		
		g.setColor(Color.WHITE);
		g.draw(innerRec);
		
		g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, (int) scale.y));

		while (g.getFontMetrics().getStringBounds(title, g).getWidth() >= scale.x - 5){
			g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, g.getFont().getSize() - 1));
			System.out.println(g.getFont().getSize());
		}
		
		g.drawString(title, pos.x + 5, (float) (pos.y + g.getFontMetrics().getAscent() - 5));
		
		g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, (int) innerScale.y));
		
		while (g.getFontMetrics().getStringBounds(content, g).getWidth() >= innerScale.x - 5){
			g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, g.getFont().getSize() - 1));
			System.out.println(g.getFont().getSize());
		}
		
		g.drawString(content,  (innerPos.x + 2), innerPos.y + g.getFontMetrics().getAscent() -5);
	}


	public Vec2f getScale() {
		return scale;
	}
	
	public Vec2f getPos(){
		return pos;
	}

	public void setTitle(int num) {
		setTitle(new Integer(num).toString());
	}
	
	public void setContent(int num) {
		setContent(new Integer(num).toString());
	}
}

package dmeyers.engine.geom;

import java.awt.Color;
import java.util.ArrayList;

import cs195n.Vec2f;

public class Asterisk extends Compound {

	float scale;
	Vec2f posn;
	
	public Asterisk(Vec2f pos, float scale) {
		
		posn = pos;
		this.scale = scale;
		ArrayList<Vec2f> vert = new ArrayList<Vec2f>();
		vert.add(new Vec2f(5,0).smult(scale));
		vert.add(new Vec2f(5,14).smult(scale));
		vert.add(new Vec2f(9,14).smult(scale));
		vert.add(new Vec2f(9,0).smult(scale));
		
		
		ArrayList<Vec2f> horiz = new ArrayList<Vec2f>();
		horiz.add(new Vec2f(0,5).smult(scale));
		horiz.add(new Vec2f(0,9).smult(scale));
		horiz.add(new Vec2f(14,9).smult(scale));
		horiz.add(new Vec2f(14,5).smult(scale));
		
		
		ArrayList<Vec2f> diagdownright = new ArrayList<Vec2f>();
		diagdownright.add(new Vec2f(2,0).smult(scale));
		diagdownright.add(new Vec2f(0,2).smult(scale));
		diagdownright.add(new Vec2f(12,14).smult(scale));
		diagdownright.add(new Vec2f(14,12).smult(scale));

		
		ArrayList<Vec2f> diagdownleft = new ArrayList<Vec2f>();
		diagdownleft.add(new Vec2f(14,2).smult(scale));
		diagdownleft.add(new Vec2f(12,0).smult(scale));
		diagdownleft.add(new Vec2f(0,12).smult(scale));
		diagdownleft.add(new Vec2f(2,14).smult(scale));
		
		Polygon p1 = new Polygon(pos, vert, true, Color.RED);
		Polygon p2 = new Polygon(pos, horiz, true, Color.RED);
		Polygon p3 = new Polygon(pos, diagdownright, true, Color.RED);
		Polygon p4 = new Polygon(pos, diagdownleft, true, Color.RED);
		
		this.internalShapes.add(p1);
		this.internalShapes.add(p2);
		this.internalShapes.add(p3);
		this.internalShapes.add(p4);
		
	}

	public Asterisk(Vec2f pos, float scale, boolean fill, Color c) {
		this(pos,scale);
		color = c;
		this.fill = fill;
	}

	@Override
	public Vec2f getCenter(){
		return new Vec2f(7 * scale, 7 * scale).plus(posn);
	}

	@Override
	public float getArea() {
		return (14 * scale) * (14 * scale) / 2f;
	}

}

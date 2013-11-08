package dmeyers.engine.geom;

import java.awt.Color;
import java.util.ArrayList;

import cs195n.Vec2f;

public class Rhombus extends Polygon {

	public Rhombus(Vec2f pos, float scale, Color c) {
		super(pos, new ArrayList<Vec2f>(), true, c);

		
		points.add(new Vec2f(1,0).smult(scale).plus(pos));
		points.add(new Vec2f(0,1).smult(scale).plus(pos));
		points.add(new Vec2f(1,2).smult(scale).plus(pos));
		points.add(new Vec2f(2,1).smult(scale).plus(pos));

		super.redoVectors();
	}
	
	

}

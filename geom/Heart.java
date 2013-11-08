package dmeyers.engine.geom;

import java.awt.Color;
import java.util.ArrayList;

import cs195n.Vec2f;

public class Heart extends Compound {

	Vec2f posn;
	
	public Heart(Vec2f pos, float scale) {
	
		posn = pos;
		
		ArrayList<Vec2f> h1points = new ArrayList<Vec2f>();
		h1points.add(Formulas.makeHeart( Math.PI).smult(scale));
		h1points.add(Formulas.makeHeart( Math.PI / 10f * 7f).smult(scale));
		h1points.add(Formulas.makeHeart( Math.PI / 10f * 6f).smult(scale));
		h1points.add(Formulas.makeHeart( Math.PI / 10f * 5f).smult(scale));
		h1points.add(Formulas.makeHeart( Math.PI / 10f * 4f).smult(scale));
		h1points.add(Formulas.makeHeart( Math.PI / 10f * 3f).smult(scale));
		h1points.add(Formulas.makeHeart( Math.PI / 10f * 2f).smult(scale));
		h1points.add(Formulas.makeHeart( Math.PI / 10f).smult(scale));
		h1points.add(Formulas.makeHeart(0).smult(scale));

		ArrayList<Vec2f> h2points = new ArrayList<Vec2f>();
		h2points.add(Formulas.makeHeart(Math.PI * 2).smult(scale));
		h2points.add(Formulas.makeHeart( Math.PI + ( Math.PI / 10f * 9f)).smult(scale));
		h2points.add(Formulas.makeHeart( Math.PI + ( Math.PI / 10f * 8f)).smult(scale));
		h2points.add(Formulas.makeHeart( Math.PI + ( Math.PI / 10f * 7f)).smult(scale));
		h2points.add(Formulas.makeHeart( Math.PI + ( Math.PI / 10f * 6f)).smult(scale));
		h2points.add(Formulas.makeHeart( Math.PI + ( Math.PI / 10f * 5f)).smult(scale));
		h2points.add(Formulas.makeHeart( Math.PI + ( Math.PI / 10f * 4f)).smult(scale));
		h2points.add(Formulas.makeHeart( Math.PI).smult(scale));

		
		Polygon h1 = new Polygon(pos, h1points, true, Color.RED);
		Polygon h2 = new Polygon(pos, h2points, true, Color.RED);
		
		this.internalShapes.add(h1);
		this.internalShapes.add(h2);
		
	}

	public Heart(Vec2f pos, float scale, boolean fill, Color c) {
		this(pos,scale);
		color = c;
		this.fill = fill;
	}
	
	@Override
	public Vec2f getCenter(){
		return posn;
	}

}

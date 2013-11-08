package dmeyers.engine.geom;

import java.awt.Color;

import cs195n.Vec2f;

public class Plus extends Compound {

	Vec2f posn;
	Vec2f dim;
	public Plus(Vec2f pos, Vec2f dim) {
		posn = pos;
		this.dim = dim;
		
		AAB rec1 = new AAB(pos.plus(dim.x / 10 * 4,0), new Vec2f(dim.x / 10 * 2, dim.y), true, Color.BLACK);
		AAB rec2 = new AAB(pos.plus(0,dim.y / 10 * 4), new Vec2f(dim.x, dim.y / 10 * 2), true, Color.BLACK);
		
		this.internalShapes.add(rec1);
		this.internalShapes.add(rec2);
	}

	public Plus(Vec2f pos, Vec2f dim, boolean fill, Color c) {
		this(pos,dim);
		color = c;
		this.fill = fill;
	}

	@Override
	public Vec2f getCenter() {
		return posn.plus(dim.sdiv(2));
	}
}

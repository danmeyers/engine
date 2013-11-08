package dmeyers.engine.geom;

import java.awt.Color;

import cs195n.Vec2f;

public class PlusCore extends Compound {

	Vec2f posn;
	Vec2f dim;
	
	public PlusCore(Vec2f pos, Vec2f dim) {
		posn = pos;
		this.dim = dim;
		AAB rec1 = new AAB(pos.plus(dim.x / 10 * 4,0), new Vec2f(dim.x / 10 * 2, dim.y), true, color);
		AAB rec2 = new AAB(pos.plus(0,dim.y / 10 * 4), new Vec2f(dim.x, dim.y / 10 * 2), true, color);
		Circle core = new Circle(pos.plus(dim.x / 5,dim.y / 5),(dim.x / 1.7f), true, 1.0f, color);
				
		this.internalShapes.add(rec1);
		this.internalShapes.add(rec2);
		this.internalShapes.add(core);
	}

	public PlusCore(Vec2f pos, Vec2f dim, boolean fill, Color c) {
		this(pos,dim);
		color = c;
		this.fill = fill;
	}
	
	@Override
	public Vec2f getCenter(){
		return posn.plus(dim.sdiv(2));
	}

}

package dmeyers.engine.geom;

import cs195n.Vec2f;
import dmeyers.engine.Entity;

public class Collision {

	public final Entity other;
	public final Vec2f mtv;
	public final boolean isStatic;
	
	public Collision(Entity o, Vec2f mtv, boolean isStat) {
		other  = o;
		this.mtv = mtv;
		isStatic = isStat;
	}


}

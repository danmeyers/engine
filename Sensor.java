package dmeyers.engine;

import dmeyers.engine.geom.Collision;
import dmeyers.engine.geom.Shape;

abstract public class Sensor extends Entity {

	Entity trigger;
	
	public Sensor(Entity trigger, Shape s) {
		this.trigger = trigger;
		shape = s;
		posn = s.getPos();
	}

	
	@Override
	public boolean onCollide(Collision c){
		if (c.other.equals(trigger)) activate();
		return true;
	}
	
	public abstract void activate();
}

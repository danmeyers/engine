package dmeyers.engine;

import cs195n.Vec2f;

import dmeyers.engine.geom.Collision;

public abstract class StaticEntity extends Entity {

	
	@Override
	public void onTick(long nanos){}
	
	@Override
	public void applyForce(Vec2f f){}
	
	@Override
	public void applyImpulse(Vec2f p){}

	@Override
	public boolean onCollide(Collision c) {
		return c.other.collideWith(new Collision(this,c.mtv.smult(-1),true));
	}

	@Override
	public boolean collideWith(Collision c){
		return true;
	}
	
	@Override
	public void changeCoords(Vec2f mtv) {}

}

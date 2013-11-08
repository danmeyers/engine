package dmeyers.engine;

import java.awt.Graphics2D;
import java.util.Hashtable;

import cs195n.Vec2f;
import dmeyers.engine.geom.Collision;
import dmeyers.engine.geom.Shape;

public abstract class Entity {
	public Shape shape = null;
	public float mass;
	protected Vec2f velocity = new Vec2f(0,0);
	protected float restitution = 0;
	public Vec2f posn;
	protected Vec2f impulse = new Vec2f(0,0);
	protected Vec2f force = new Vec2f(0,0);
	
	
	
	public Hashtable<String, Output> outputs = new Hashtable<String, Output>();
	public Hashtable<String, Input> inputs = new Hashtable<String, Input>();

	
	public void onTick(long nanos){
		float seconds = nanos/1000000000f;
		velocity = velocity.plus(force.smult(seconds).sdiv(mass)).plus(impulse.sdiv(mass));
		posn = posn.plus(velocity.smult(seconds));
		force = new Vec2f(0,0);
		impulse = new Vec2f(0,0);
	}
	public void applyForce(Vec2f f){
		force = force.plus(f);
	}
	
	public void applyImpulse(Vec2f p){
		impulse = impulse.plus(p);
	}

	
	public abstract void onDraw(Graphics2D g);
	
	public boolean onCollide(Collision c){
		if (c.other != null && c !=null && c.mtv != null) return c.other.collideWith(new Collision(this,c.mtv.smult(-1),false));
		return false;
	}
	
	public boolean collideWith(Collision collision) {
		
		
		Vec2f velA = this.velocity.projectOnto(collision.mtv);
		Vec2f velB = collision.other.velocity.projectOnto(collision.mtv);

		float COR = (float) Math.sqrt(this.restitution * collision.other.restitution);

		if(collision.isStatic && !(collision.mtv.x == 0 && collision.mtv.y == 0)){
			Vec2f impulse = (velB.minus(velA)).smult(1 + COR).smult(mass);
//			applyImpulse(collision.mtv.smult(2000000).sdiv(mass));
			applyImpulse(impulse);
			changeCoords(collision.mtv);
			return true;
		}
		else if (!collision.isStatic && !(collision.mtv.x == 0 && collision.mtv.y == 0)){
			Vec2f impulse = velB.minus(velA).smult((mass * collision.other.mass * (1 + COR)) / (mass + collision.other.mass));
			
//			applyImpulse(collision.mtv.smult(5000000).sdiv(mass));
			applyImpulse(impulse);
			changeCoords(collision.mtv.sdiv(2));
			return true;
		}
		else return false;
	}
	public abstract void changeCoords(Vec2f mtv);
//	public abstract void initialize(World mWorld, Shape enteredShape,
//			Map<String, String> properties);

}
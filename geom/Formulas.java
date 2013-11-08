package dmeyers.engine.geom;

import cs195n.Vec2f;

public final class Formulas {

	protected static float midpointRaised(Vec2f a, Vec2f b){
		return ((b.x - a.x)* (b.x - a.x)) + ((b.y - a.y) * (b.y - a.y));
	}
	
	public static Vec2f normalizeVelocity(Vec2f v){
		double angle = Math.atan(v.y/v.x);
		double Yangle = Math.atan(v.x/v.y);
		
		if (v.x <= 0) angle += Math.PI;
		if (v.y <= 0) Yangle += Math.PI;

		return new Vec2f((float) Math.cos(angle),(float) Math.cos(Yangle));
		
	}
	
	public static Vec2f perpendicular (Vec2f v){
		float newx = -v.y;
		float newy = v.x;
		
		return new Vec2f(newx, newy);
	}

	public static Vec2f makeHeart (double d){
		float yVal = (float) - ((13 * Math.cos(d)) - (5 * Math.cos(2 * d)) - (2 * Math.cos(3 * d)) - Math.cos(4 * d));
		return new Vec2f((float) (16 * (Math.pow(Math.sin(d), 3))), yVal);
	}
}

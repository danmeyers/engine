package dmeyers.engine.geom;

import cs195n.Vec2f;

public class SeparatingAxis {

	Vec2f direction;
		
	
	public SeparatingAxis(Vec2f dir) {
		direction = dir.normalized();
	}
	
	public SeparatingAxis(Vec2f p1, Vec2f p2){
		direction = Formulas.normalizeVelocity((p2.minus(p1)));
	}

	
	public Range project(Circle c){
		Range r = new Range();
		float maxProj = c.getCenter().plus(direction.smult(c.radius)).dot(direction);
		float minProj = c.getCenter().plus(direction.smult(-c.radius)).dot(direction);

		r.setMinMax(minProj);
		r.setMinMax(maxProj);
		
		return r;
	}
	
	public Range project(AAB a){
		Range r  = new Range();
		float proj1 = a.pos.dot(direction);
		float proj2 = a.pos.plus(a.scale).dot(direction);
		float proj3 = a.pos.plus(a.scale.x, 0).dot(direction);
		float proj4 = a.pos.plus(0,a.scale.y).dot(direction);

		r.setMinMax(proj1);
		r.setMinMax(proj2);
		r.setMinMax(proj3);
		r.setMinMax(proj4);
		
		return r;
	}
	
	public Range project(Polygon p){
		Range r = new Range();
		for (Vec2f point : p.points){
			float proj = point.dot(direction);
			r.setMinMax(proj);
		}

		return r;
	}
	
	public Range project(Compound c){
		Range r = new Range();
		for (Shape s : c.internalShapes){
			Range r2 = s.projectOnto(this);
			r.setMinMax(r2.getMin());
			r.setMinMax(r2.getMax());
		}
		
		return r;
	}
	
	public Float rangeMTV(Range a, Range b){
		Float aRight = b.max - a.min;
		Float aLeft = a.max - b.min;
		
		if (aLeft < 0 || aRight < 0) return null;
		if (aRight < aLeft) return aRight;
		else return aLeft;
	}
}

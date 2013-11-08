package dmeyers.engine.geom;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;

import cs195n.Vec2f;
import dmeyers.engine.Entity;

public class Ray {
	
	Vec2f source;
	Vec2f direction;
	float timeLeft = 0.2f;
	float destX;
	float destY;
	Vec2f perpendicular;

	public Ray(Vec2f src, Vec2f dir) {
		source = src;
		direction = dir.normalized();
		
		destX = source.x + (direction.x * 500);
		destY = source.y + (direction.y * 500);
		
		perpendicular = new Vec2f(-direction.y,direction.x);
	}
	
	public void onDraw(Graphics2D g){
		//System.out.println("draw ray");
		g.setColor(Color.RED);
		
		Path2D.Float path = new Path2D.Float();
		path.moveTo(source.minus(perpendicular.smult(3)).x, source.minus(perpendicular.smult(3)).y);
		path.lineTo(source.plus(perpendicular.smult(3)).x, source.plus(perpendicular.smult(3)).y);
//		path.lineTo(destX + 10, destY + 10);
		path.lineTo(destX, destY);
		g.fill(path);	
	}
	
	public Vec2f raycastCircle(Circle c){
		if (c.contains(source)){
			Vec2f projection = c.getCenter().projectOntoLine(source, direction);
			float x = projection.dist(c.getCenter());
			return source.plus(direction.smult(projection.dist(source) + (float) Math.sqrt((c.radius * c.radius) - (x * x))));
		}
		else {
			Vec2f projection = c.getCenter().minus(source).projectOnto(direction).plus(source);
//			System.out.println(projection + ": " + c.getCenter());
			if (c.contains(projection) && checkSigns(projection)){
				float x = projection.dist(c.getCenter());
				return source.plus(direction.smult(projection.dist(source) - (float) Math.sqrt((c.radius * c.radius) - (x * x))));
			}
		}
		return null;
	}
	
	public Vec2f raycastPolygon(Polygon p) {
		
		float closestT = Float.POSITIVE_INFINITY;
		Vec2f closestTVec = null;
		int pSize = p.points.size();
		for(int i = 0; i<pSize; i++){
//			System.out.println(p + "-" + p.points.get(i));
			Vec2f p1 = p.points.get(i);
			Vec2f p2 = p.points.get((i + 1) % pSize);
			
			Vec2f segment = p2.minus(p1).normalized();
			Vec2f perpSeg = new Vec2f(-segment.y, segment.x);
			
//			System.out.println(p + " (" + p1 + ". " + p2 + "): " + p2.minus(source).cross(direction) * (p1.minus(source).cross(direction)));
			if (p2.minus(source).cross(direction) * (p1.minus(source).cross(direction)) < 0){
				float t = p2.minus(source).dot(perpSeg) / direction.dot(perpSeg);
				if (t < closestT && t >= 0) {
//					System.out.println(t + ":" + p1 + ":" + p2);
					closestT = t;
					closestTVec = source.plus(direction.smult(t));
				}
			}
		}
		
//		System.out.println(closestTVec);
		
		return closestTVec;
	}
	
	public Vec2f raycastAAB(AAB aab){
		//System.out.println(aab.pos);
		return raycastPolygon(aab.toPolygon());
	}
	
	
	public boolean checkSigns(Vec2f proj){
		Vec2f dif = proj.minus(source);
		return ((dif.x >= 0 && direction.x >= 0) || (dif.x < 0 && direction.x <0)) && ((dif.y >= 0 && direction.y >= 0) || (dif.y < 0 && direction.y <0));
	}

	public Entity collideRay(ArrayList<Entity> entities, ArrayList<Entity> es) {
		float minDistance = Float.POSITIVE_INFINITY;
		Entity minEntity = null;
		for (Entity e : entities){
			if (e.shape != null && !es.contains(e)){
//				System.out.println(e.shape);
				Vec2f intersect = e.shape.raycast(this);
//				if (intersect != null) System.out.println(intersect);
				if (intersect != null && intersect.dist2(source) < minDistance){
//					System.out.println(e.shape + " !" + intersect.dist2(source));
					minDistance = intersect.dist2(source);
					destX = intersect.x;
					destY = intersect.y;
					minEntity = e;
//					System.out.println(new Vec2f(destX,destY));
				}
			}
		}
		
		return minEntity;
	}
	
	public Vec2f getDirection() {
		return direction;
	}

	public boolean remove(long nanos) {
		if (timeLeft <= 0) return true;
		else {
			timeLeft -= nanos /1000000000f;
			return false;
		}
	}

}

package dmeyers.engine.geom;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;

import cs195n.Vec2f;

public class Polygon implements Shape {
	Vec2f pos;
	
	boolean fill = false;
	
	private Color color = Color.BLACK;
	
	ArrayList<Vec2f> points = new ArrayList<Vec2f>();

	ArrayList<Vec2f> vectors = new ArrayList<Vec2f>();
	
	ArrayList<SeparatingAxis> axes = new ArrayList<SeparatingAxis>();
	
	public Polygon(Vec2f pos, ArrayList<Vec2f> p, boolean f, Color c) {
		this.pos = pos;
		fill = f;
		color = c;
		int pSize = p.size();
		int i;
		for (Vec2f po : p){
			points.add(po.plus(pos));
		}
		for (i = 0; i < pSize - 1; i++){
			vectors.add(points.get(i + 1).minus(points.get(i)));
			axes.add(new SeparatingAxis(Formulas.perpendicular(vectors.get(i))));
//			axes.add(new SeparatingAxis(p.get(i+1),p.get(i)));
			//axes.add(new SeparatingAxis(p.get(i+1).minus(p.get(i).pmult(-1, 1)), p.get(i)));
			//point2x - 
		}
		if (!(points.isEmpty() || vectors.isEmpty())){
			vectors.add(points.get(0).minus(points.get(i)));
			axes.add(new SeparatingAxis(Formulas.perpendicular(vectors.get(i))));
		}
		//System.out.println(axes.size());
		
//		for (Vec2f v : vectors){
//			axes.add(new SeparatingAxis(new Vec2f(-v.y,v.x)));
//		}
//		System.out.println(Arrays.deepToString(points.toArray()));

	}

	@Override
	public boolean collides(Shape s) {
		return s.collidesPoly(this);
	}
	
	public void redoVectors(){
		int pSize = points.size();

		int i = 0;
		for (i = 0; i < pSize - 1; i++){
			vectors.add(points.get(i + 1).minus(points.get(i)));
			axes.add(new SeparatingAxis(Formulas.perpendicular(vectors.get(i))));
		}
		if (!(points.isEmpty() || vectors.isEmpty())){
			vectors.add(points.get(0).minus(points.get(i)));
			axes.add(new SeparatingAxis(Formulas.perpendicular(vectors.get(i))));
		}

	}
	@Override
	public boolean collidesPoint(Vec2f v) {
	
		int i;

		for (i = 0; i < points.size() - 1; i++){
			if(v.minus(points.get(i)).cross(points.get(i + 1).minus(points.get(i))) < 0) {

				return false;
			}
		}
		if(v.minus(points.get(i)).cross(points.get(0).minus(points.get(i))) < 0) return false;

		return true;
	}
	
	@Override
	public boolean collidesCircle(Circle c) {
		Vec2f shortest = new Vec2f(0,0);
		float shortestFloat = Float.POSITIVE_INFINITY;
		for (Vec2f p : points){
			float distance = p.dist2(c.getCenter()); //changed to vec2f dist function
			if (distance <= shortestFloat) {
				shortest = p;
				shortestFloat = distance;
			}
		}

		SeparatingAxis circleAxis = new SeparatingAxis(c.getCenter(),shortest);

		ArrayList<SeparatingAxis> allAxes = new ArrayList<SeparatingAxis>();
		allAxes.addAll(this.axes);
		allAxes.add(circleAxis);
		
		for (SeparatingAxis sA : allAxes){
			Range circleRange = sA.project(c);
			Range polyRange = sA.project(this);
			if (!circleRange.overlap(polyRange)) return false;
		}
		
		return true;
	}

	@Override
	public boolean collidesAAB(AAB aab) {
		
		ArrayList<SeparatingAxis> allAxes = new ArrayList<SeparatingAxis>();
		allAxes.addAll(this.axes);

		SeparatingAxis left = new SeparatingAxis(new Vec2f(-1,0));
		SeparatingAxis top = new SeparatingAxis(new Vec2f(0,1));
		
		allAxes.add(left);
		allAxes.add(top);

		for (SeparatingAxis sA : allAxes){
			Range recRange = sA.project(aab);
			Range polyRange = sA.project(this);
			if (!recRange.overlap(polyRange)) {
				return false;
			}
		}

		return true;
	
	}


	@Override
	public boolean collidesPoly(Polygon polygon) {
		ArrayList<SeparatingAxis> allAxes = new ArrayList<SeparatingAxis>();
		allAxes.addAll(this.axes);
		allAxes.addAll(polygon.axes);
		
		for (SeparatingAxis sA : allAxes){
			Range poly1Range = sA.project(polygon);
			Range poly2Range = sA.project(this);
			if (!poly1Range.overlap(poly2Range)) return false;
		}
		
		return true;

	}

	@Override
	public boolean collidesComp(Compound compShapes) {
		for (Shape s : compShapes.internalShapes){
			if(s.collidesPoly(this)) return true;
		}
		
		return false;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(color);
		Path2D.Float path = new Path2D.Float();
		for (int i = 0; i < points.size(); i++){
			if (i == 0) path.moveTo(points.get(i).x, points.get(i).y);
			else {
				path.lineTo(points.get(i).x, points.get(i).y);
			}
		}

		g.fill(path);
	}

	@Override
	public Vec2f changeCoords(Vec2f v) {
		if (v != null) {
				
			for (int i = 0; i < points.size(); i++){
				points.set(i, points.get(i).plus(v));
			}
			
			pos = pos.plus(v);
		}
		return v;
	}

	@Override
	public void changeColor(Color color) {
		this.color = color;
	}

	@Override
	public void setCoords(Vec2f v) {
		Vec2f diff = v.minus(pos);
		changeCoords(diff);
	}

	public Range projectOnto(SeparatingAxis sA){
		return sA.project(this);
	}
	
	@Override
	public Vec2f getCenter() {
		Vec2f total = new Vec2f(0,0);
		int numshapes = 0;
	
		for (Vec2f p : points){
			numshapes++;
			total = total.plus(p);
		}
		
		return total.sdiv(numshapes);
	}

	@Override
	public float getArea() {
		int pSize = points.size();
		
		float acc = 0;
		
		for (int i = 0; i < pSize; i++){
			Vec2f p1 = points.get(i);
			Vec2f p2 = points.get((i+1) % pSize);	
			
			acc += (p1.x * p2.y) - (p1.y * p2.x);
		}
		
		return Math.abs(acc / 2f);
	}
	
	@Override
	public Vec2f polyMTV(Polygon s) {
		Float minMag = Float.POSITIVE_INFINITY;
		Vec2f mtv = null;
		ArrayList<SeparatingAxis> allAxes = new ArrayList<SeparatingAxis>();
		allAxes.addAll(axes);
		for (SeparatingAxis sA : s.axes){
			allAxes.add(new SeparatingAxis(sA.direction.smult(-1)));
		}
		for (SeparatingAxis axis : allAxes) {
			Float MTV1d = axis.rangeMTV(this.projectOnto(axis), s.projectOnto(axis));
			
			if (MTV1d == null) return null;
			if (Math.abs(MTV1d) < minMag) {
				minMag = Math.abs(MTV1d);
				mtv = axis.direction.smult(MTV1d);
			}
			else if (Math.abs(MTV1d) == minMag && (s.getCenter().x >= getCenter().x || ((s.getCenter().y <= getCenter().y) && (s.getCenter().x >= getCenter().x)))) {
				//System.out.println(s.getCenter() + "::" + this.getCenter());
				minMag = Math.abs(MTV1d);
				mtv = axis.direction.smult(MTV1d);
			}
		}
		return mtv.smult(-1);
	}

	@Override
	public Vec2f circleMTV(Circle c) {
		Float minMag = Float.POSITIVE_INFINITY;
		Vec2f mtv = null;
		
		Vec2f shortest = new Vec2f(0,0);
		float shortestFloat = Float.POSITIVE_INFINITY;
		for (Vec2f p : points){
			float distance = p.dist2(c.getCenter()); //changed to vec2f dist function
			if (distance <= shortestFloat) {
				shortest = p;
				shortestFloat = distance;
			}
		}

		SeparatingAxis circleAxis = new SeparatingAxis(c.getCenter(),shortest);
		circleAxis.direction = circleAxis.direction.smult(-1);
	
		ArrayList<SeparatingAxis> allAxes = new ArrayList<SeparatingAxis>();
		allAxes.addAll(this.axes);
		allAxes.add(circleAxis);
		
		for (SeparatingAxis axis : allAxes) {
			Float MTV1d = axis.rangeMTV(this.projectOnto(axis), c.projectOnto(axis));
			
			if (MTV1d == null) return null;
			if (Math.abs(MTV1d) < minMag) {
				minMag = Math.abs(MTV1d);
				mtv = axis.direction.smult(Math.abs(MTV1d));
			}
		}
		//System.out.println(mtv);
		return mtv.smult(-1);
	}

	@Override
	public Vec2f AABMTV(AAB aab) {
		Float minMag = Float.POSITIVE_INFINITY;
		Vec2f mtv = null;

		ArrayList<SeparatingAxis> allAxes = new ArrayList<SeparatingAxis>();
		allAxes.addAll(this.axes);
		if (this.pos.y <= aab.getCenter().y) allAxes.add(new SeparatingAxis(new Vec2f(0,1)));
		else allAxes.add(new SeparatingAxis(new Vec2f(0,-1)));
		if (this.pos.x <= aab.getCenter().x) allAxes.add(new SeparatingAxis(new Vec2f(1,0)));
		else allAxes.add(new SeparatingAxis(new Vec2f(-1,0)));
		
		for (SeparatingAxis axis : allAxes) {
			Float MTV1d = axis.rangeMTV(this.projectOnto(axis), aab.projectOnto(axis));
			
			if (MTV1d == null) return null;
			if (Math.abs(MTV1d) < minMag) {
				minMag = Math.abs(MTV1d);
				mtv = axis.direction.smult(MTV1d).sdiv(-1);
			}
		}

		return mtv;

	}

	@Override
	public Vec2f MTV(Shape s) {
		Vec2f mtv = s.polyMTV(this);
		if (mtv != null) return mtv.smult(-1);
		return mtv;
	}

	@Override
	public Vec2f getPos() {
		return pos;
	}
	
	@Override
	public Vec2f raycast(Ray r) {
		return r.raycastPolygon(this);
	}

	@Override
	public Vec2f getMax() {
		Vec2f max = new Vec2f(0,0);
		for (Vec2f p : points){
			if (p.y >= max.y) max = p;
		}
		
		return max;
	}

}

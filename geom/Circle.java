package dmeyers.engine.geom;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import cs195n.Vec2f;

public class Circle implements Shape{

	float radius;
	float diameter;
	Vec2f pos;
	
	boolean fill = false;
	
	float opacity = 1.0f;
	private Color color = Color.BLACK;
	
	public Circle(Vec2f pos, float d, boolean f) {
		fill =f;
		this.pos = pos;
		diameter = d;
		radius = d / 2;
	}

	
	public Circle(Vec2f pos, float d, boolean f, float o, Color c) {
		this(pos,d,f);
		opacity = o;
		color = c;
	}
	

	public Vec2f getCenter() {
		return new Vec2f(pos.x + radius,pos.y + radius);
	}

	public boolean contains(MouseEvent e){
		
		double x = e.getPoint().getX();
		double y = e.getPoint().getY();
		if (x >= pos.x && x <= (pos.x + diameter) && y >= pos.y && y <= (pos.y + diameter)) return true;
		return false;
	}
	
	public void draw(Graphics2D g) {
		g.setColor(color);
		
		Ellipse2D.Float circle = new Ellipse2D.Float(pos.x, pos.y, diameter, diameter);
		Composite r = g.getComposite();
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		if(fill) g.fill(circle);

		g.draw(circle);
		
		g.setComposite(r);

	}


	public boolean contains(Vec2f gameCoords) {
		double x = gameCoords.x;
		double y = gameCoords.y;
		if (x >= pos.x && x <= (pos.x + diameter) && y >= pos.y && y <= (pos.y + diameter)) return true;
		return false;
	}
	
	/* Starts Collide With */
	
	@Override
	public boolean collides(Shape s){
		return s.collidesCircle(this);
	}


	@Override
	public boolean collidesCircle(Circle c) {
		return Formulas.midpointRaised(c.getCenter(), getCenter()) <= ((radius + c.radius) * (radius + c.radius));
	}


	@Override
	public boolean collidesAAB(AAB aab) {
		float clampX = getCenter().x;
		float clampY = getCenter().y;
		
		if (clampX >= (aab.pos.x + aab.scale.x)) clampX = aab.pos.x + aab.scale.x;
		else if (clampX <= aab.pos.x) clampX = aab.pos.x;
		
		if (clampY >= (aab.pos.y + aab.scale.y)) clampY = aab.pos.y + aab.scale.y;
		else if (clampY <= aab.pos.y) clampY = aab.pos.y;
		
		return collidesPoint(new Vec2f(clampX,clampY));
		}


	@Override
	public boolean collidesComp(Compound comp) {
		return comp.collidesCircle(this);
	}


	@Override
	public boolean collidesPoint(Vec2f v) {
		return Formulas.midpointRaised(v, getCenter()) <= (radius * radius);
	}
	

	@Override
	public boolean collidesPoly(Polygon polygon) {
		return polygon.collidesCircle(this);
	}



	@Override
	public Vec2f changeCoords(Vec2f v) {
		if (v != null) pos = pos.plus(v);
		return v;
	}
	
	
	@Override
	public void changeColor(Color color){
		this.color  = color;
	}


	public void setCoords(Vec2f position) {
		pos = position;
	}


	public float getRadius() {
		return radius;
	}

	public Range projectOnto(SeparatingAxis sA){
		return sA.project(this);
	}


	@Override
	public float getArea() {
		return (float) (Math.PI * radius * radius);
	}


	@Override
	public Vec2f polyMTV(Polygon p) {
		return p.circleMTV(this).smult(-1);
	}


	@Override
	public Vec2f circleMTV(Circle c) {
		float dist = getCenter().dist(c.getCenter());
		float radii = radius + c.radius;

		float diff = dist - radii;
		
		if (diff > 0) return null;
		
		Vec2f nAxis = c.getCenter().minus(getCenter()).normalized();

		return nAxis.smult(diff);
	}


	@Override
	public Vec2f AABMTV(AAB aab) {
		Vec2f center = getCenter();
		if (aab.contains(center)) {

			Float minMag = Float.POSITIVE_INFINITY;
			Vec2f mtv = null;

			ArrayList<SeparatingAxis> allAxes = new ArrayList<SeparatingAxis>();
			allAxes.add(new SeparatingAxis(new Vec2f(0,-1)));
			allAxes.add(new SeparatingAxis(new Vec2f(-1,0)));
			
			for (SeparatingAxis axis : allAxes) {
				Float MTV1d = axis.rangeMTV(this.projectOnto(axis), aab.projectOnto(axis));
				
				if (MTV1d == null) return null;
				if (Math.abs(MTV1d) < minMag) {
					minMag = Math.abs(MTV1d);
					mtv = axis.direction.smult(MTV1d);
				}
			}
			return mtv;
		}
			
			
		float clampX = getCenter().x;
		float clampY = getCenter().y;
		
		if (clampX >= (aab.pos.x + aab.scale.x)) clampX = aab.pos.x + aab.scale.x;
		else if (clampX <= aab.pos.x) clampX = aab.pos.x;
		
		if (clampY >= (aab.pos.y + aab.scale.y)) clampY = aab.pos.y + aab.scale.y;
		else if (clampY <= aab.pos.y) clampY = aab.pos.y;
		
		float diff = radius - getCenter().dist(new Vec2f(clampX,clampY));
		
		if (diff < 0) return null;
		
		return getCenter().minus(new Vec2f(clampX,clampY)).normalized().smult(diff);
		
		
	}


	@Override
	public Vec2f MTV(Shape s) {
		return s.circleMTV(this).smult(-1);
	}


	@Override
	public Vec2f getPos() {
		return pos;
	}
	
	@Override
	public Vec2f raycast(Ray r) {
		return r.raycastCircle(this);
	}


	@Override
	public Vec2f getMax() {
		return pos.plus(diameter,diameter);
	}


}

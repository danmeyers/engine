package dmeyers.engine.geom;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import cs195n.Vec2f;

public class AAB implements Shape {

	Vec2f scale;
	
	Vec2f pos;

	boolean fill;

	private Color color = Color.BLACK;
	
	ArrayList<SeparatingAxis> allAxes = new ArrayList<SeparatingAxis>();
	
	
	public AAB(Vec2f pos, Vec2f scale) {
		this.pos = pos;
		this.scale = scale;
		allAxes.add(new SeparatingAxis(new Vec2f(1,0)));
		allAxes.add(new SeparatingAxis(new Vec2f(0,1)));
	}

	
	public AAB(Vec2f pos, Vec2f scale, boolean fill, Color c) {
		this(pos,scale);
		this.fill = fill;
		color = c;
	}


	public boolean contains(MouseEvent e){
		double x = e.getPoint().getX();
		double y = e.getPoint().getY();
		if (x >= pos.x && x <= (pos.x + scale.x) && y >= pos.y && y <= (pos.y + scale.y)) return true;
		return false;
	}
	
	public void draw(Graphics2D g) {
		g.setColor(color);
		Rectangle2D.Float rec = new Rectangle2D.Float(pos.x, pos.y, scale.x, scale.y);
		g.draw(rec);
		if (fill) g.fill(rec);
	}


	public Vec2f getScale() {
		return scale;
	}
	
	public Vec2f getPos(){
		return pos;
	}


	public boolean contains(Vec2f gameCoords) {
		double x = gameCoords.x;
		double y = gameCoords.y;
		if (x >= pos.x && x <= (pos.x + scale.x) && y >= pos.y && y <= (pos.y + scale.y)) return true;
		return false;	
	}


	@Override
	public boolean collides(Shape s) {
		return s.collidesAAB(this);
	}



	@Override
	public boolean collidesCircle(Circle c) {
		float clampX = c.getCenter().x;
		float clampY = c.getCenter().y;
		
		if (clampX >= (pos.x + scale.x)) clampX = pos.x + scale.x;
		else if (clampX <= pos.x) clampX = pos.x;
		
		if (clampY >= (pos.y + scale.y)) clampY = pos.y + scale.y;
		else if (clampY <= pos.y) clampY = pos.y;
		
		return c.collidesPoint(new Vec2f(clampX,clampY));
	}


	@Override
	public boolean collidesAAB(AAB aab) {
		return (pos.x <= (aab.pos.x + aab.scale.x)) && (pos.x + scale.x >= (aab.pos.x)) && (pos.y <= (aab.pos.y + aab.scale.y)) && (pos.y + scale.y >= (aab.pos.y));
	}


	@Override
	public boolean collidesComp(Compound comp) {
		return comp.collidesAAB(this);
	}


	@Override
	public boolean collidesPoint(Vec2f v) {
		return (v.x >= pos.x && v.x <= (pos.x + scale.x) && v.y >= pos.y && v.y <= (pos.y + scale.y));
	}
	
	@Override
	public boolean collidesPoly(Polygon polygon) {
		return polygon.collidesAAB(this);
	}
	
	
	@Override
	public Vec2f changeCoords(Vec2f v) {
		if (v != null) pos = pos.plus(v);
		return v;
	}
	
	@Override
	public void changeColor(Color color){
		this.color = color;
	}


	@Override
	public void setCoords(Vec2f v) {
		pos = v;
		
	}
	
	public Range projectOnto(SeparatingAxis sA){
		return sA.project(this);
	}

	@Override
	public Vec2f getCenter() {
		return pos.plus(scale.sdiv(2));
	}


	@Override
	public float getArea() {
		return (scale.x - pos.x) * (scale.y - pos.y);
	}


	@Override
	public Vec2f MTV(Shape s) {
		return s.AABMTV(this).smult(-1);
	}


	@Override
	public Vec2f polyMTV(Polygon p) {
		return p.AABMTV(this).smult(-1);
	}


	@Override
	public Vec2f circleMTV(Circle c) {
		return c.AABMTV(this).smult(-1);
	}


	@Override
	public Vec2f AABMTV(AAB aab) {
		Float minMag = Float.POSITIVE_INFINITY;
		Vec2f mtv = null;

		ArrayList<SeparatingAxis> allAxes = new ArrayList<SeparatingAxis>();
		if (this.getCenter().y <= aab.getCenter().y) allAxes.add(new SeparatingAxis(new Vec2f(0,-1)));
		else allAxes.add(new SeparatingAxis(new Vec2f(0,1)));
		if (this.getCenter().x <= aab.getCenter().x) allAxes.add(new SeparatingAxis(new Vec2f(-1,0)));
		else allAxes.add(new SeparatingAxis(new Vec2f(1,0)));

		for (SeparatingAxis axis : allAxes) {
			Float MTV1d = axis.rangeMTV(aab.projectOnto(axis), this.projectOnto(axis));

			if (MTV1d == null) return null;
			if (Math.abs(MTV1d) < minMag) {
				minMag = Math.abs(MTV1d);
				mtv = axis.direction.smult(MTV1d);
			}
		}
		return mtv;

	}


	public Polygon toPolygon() {
		ArrayList<Vec2f> points = new ArrayList<Vec2f>();
		points.add(pos);
		points.add(pos.plus(0,scale.y));
		points.add(pos.plus(scale));
		points.add(pos.plus(scale.x,0));
		
		return new Polygon(new Vec2f(0,0), points, fill, color);
	}


	@Override
	public Vec2f raycast(Ray r) {
		return r.raycastAAB(this);
	}


	@Override
	public Vec2f getMax() {
		return pos.plus(scale);
	}



}

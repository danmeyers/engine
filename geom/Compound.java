package dmeyers.engine.geom;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import cs195n.Vec2f;

public class Compound implements Shape {

	ArrayList<Shape> internalShapes = new ArrayList<Shape>();
	 Color color = Color.BLACK;
	 boolean fill;
	
	public Compound() {
 	}
	
	public Compound(ArrayList<Shape> shapes) {
		internalShapes = shapes;
		fill = true;
 	}

	
	public Compound(Color c) {
		this();
		color = c;
 	}
	
	@Override
	public void draw(Graphics2D g){
		for (Shape s : internalShapes){
			s.changeColor(color);
			s.draw(g);
		}
	}
	
	@Override
	public boolean collides(Shape s) {
		return s.collidesComp(this);
	}

	@Override
	public boolean collidesCircle(Circle c) {
		for (Shape s : internalShapes){
			if (c.collides(s)) return true;
		}
		return false;
	}

	@Override
	public boolean collidesAAB(AAB aab) {
		for (Shape s : internalShapes){
			if (aab.collides(s)) return true;
		}
		return false;
	}

	@Override
	public boolean collidesComp(Compound compShapes) {
		for (Shape s : internalShapes){
			for (Shape s2 : compShapes.internalShapes){
				if (s.collides(s2)) return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean collidesPoint(Vec2f v){
		for (Shape s : internalShapes){
			if (s.collidesPoint(v)) return true;
		}
	return false;
	}
	
	@Override
	public boolean collidesPoly(Polygon p){
		return p.collidesComp(this);
	}
	
	@Override
	public Vec2f changeCoords(Vec2f v){
		for (Shape s : internalShapes){
			s.changeCoords(v);
		}
		return v;
	}

	@Override
	public void changeColor(Color color){
		this.color = color;
	}
	
	@Override
	public void setCoords(Vec2f v){
		
	}
	public Range projectOnto(SeparatingAxis sA){
		return sA.project(this);
	}
	@Override
	public float getArea(){
		float acc = 0;
		for (Shape s : internalShapes){
			acc += s.getArea();
		}
		return acc;
	}


	@Override
	public Vec2f polyMTV(Polygon s) {
		return null;
	}

	@Override
	public Vec2f circleMTV(Circle s) {
		return null;
	}

	@Override
	public Vec2f AABMTV(AAB s) {
		return null;
	}
	@Override
	public Vec2f MTV(Shape s) {
		return null;
	}
	
	@Override
	public Vec2f getPos(){
		return null;
	}
	
	@Override
	public Vec2f raycast(Ray r){
		return null;
	}

	@Override
	public Vec2f getCenter() {
		return null;
	}

	@Override
	public Vec2f getMax() {
		Vec2f max = new Vec2f(0,0);
		for (Shape s : internalShapes){
			if (s.getMax().y > max.y) max = s.getMax();
		}
		
		return max;
	}

}

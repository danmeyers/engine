package dmeyers.engine.geom;

import java.awt.Color;
import java.awt.Graphics2D;

import cs195n.Vec2f;

public interface Shape {
	public boolean collides(Shape s);
	public boolean collidesCircle(Circle c);
	public boolean collidesAAB(AAB aab);
	public boolean collidesPoint(Vec2f v);
	public boolean collidesPoly(Polygon polygon);
	boolean collidesComp(Compound compShapes);
	public void draw(Graphics2D g);
	Vec2f changeCoords(Vec2f v);
	public void changeColor(Color color);
	void setCoords(Vec2f v);
	public Range projectOnto(SeparatingAxis separatingAxis);
	public Vec2f getCenter();
	public float getArea();
	public Vec2f MTV(Shape s);
	public Vec2f polyMTV(Polygon s);
	public Vec2f circleMTV(Circle s);
	public Vec2f AABMTV(AAB s);
	public Vec2f getPos();
	public Vec2f raycast(Ray r);
	public Vec2f getMax();

}

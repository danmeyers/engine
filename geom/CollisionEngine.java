package dmeyers.engine.geom;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import cs195n.Vec2f;

import dmeyers.engine.Application;
import dmeyers.engine.Screen;

public class CollisionEngine {

	public CollisionEngine() {
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Application a = new Application("Collision Engine", false);
		
		a.screens.add(new CollisionEngineScreen(a));
		
		a.startup();
		
//		Vec2f aa = new Vec2f(1,3);
//		Vec2f b = new Vec2f(4,4);
//		Vec2f c = new Vec2f(2,2);
	//	System.out.println(c.projectOnto(b.minus(aa)));
	//	System.out.println(c.projectOntoLine(b, aa));
		//System.out.println(c.projectOnto(b.minus(aa)).equals(c.projectOntoLine(aa, b)));
	}
	
	public static class CollisionEngineScreen extends Screen {

		boolean mtvOn = true;
		
		ArrayList<Shape> shapes = new ArrayList<Shape>();
		private Shape currentShape = null;
		
		public CollisionEngineScreen(Application a) {
			super(a);
			
			Plus p = new Plus(new Vec2f(0,0), new Vec2f(100,100),true,Color.black);
			AAB rec = new AAB(new Vec2f(500,400), new Vec2f(60,30), true, null);
			Circle circ = new Circle(new Vec2f(300,200), 75, true, 1.0f, Color.BLACK);
			
			Plus p1 = new Plus(new Vec2f(200,0), new Vec2f(100,100),true,Color.black);
			AAB rec1 = new AAB(new Vec2f(700,400), new Vec2f(60,30), true, null);
			Circle circ1 = new Circle(new Vec2f(500,200), 20, true, 1.0f, Color.BLACK);
			
			
			ArrayList<Vec2f> points = new ArrayList<Vec2f>();
			points.add(new Vec2f(10,10));
			points.add(new Vec2f(30,40));
			points.add(new Vec2f(60,30));
			Polygon poly = new Polygon(new Vec2f(500,400), points, true, Color.BLACK);
			ArrayList<Vec2f> points2 = new ArrayList<Vec2f>();
			points2.add( new Vec2f(40,40));
			points2.add( new Vec2f(110,30));
			points2.add(new Vec2f(10,15));
			Polygon poly2 = new Polygon(new Vec2f(600,400), points2, true, Color.BLACK);

			Rhombus heart1 = new Rhombus(new Vec2f(300,300), 100f, Color.RED);
			Rhombus heart2 = new Rhombus(new Vec2f(340,340), 40f, Color.RED);
			
			Heart h1 = new Heart(new Vec2f(600,30), 1f);
//			shapes.add(p);
//			shapes.add(rec);
//			shapes.add(circ1);
//			shapes.add(p1);
//			shapes.add(rec1);
//			shapes.add(circ);
			shapes.add(poly);
			shapes.add(poly2);
			shapes.add(heart1);
			shapes.add(heart2);
			System.out.println(poly.pos + " :" + poly.getCenter());
//			System.out.println(rec.projectOnto(new SeparatingAxis(new Vec2f(0,-1))).getMin());
//			rec.changeCoords(new Vec2f(10,10));
//			System.out.println(rec.projectOnto(new SeparatingAxis(new Vec2f(0,-1))).getMin());

		}
		
		@Override
		protected void onTick(long nanosSincePreviousTick) {
			ArrayList<Shape> noFly = new ArrayList<Shape>();
			for (Shape s : shapes){
				for (Shape s1 : shapes){
					if (!s.equals(s1)  && !noFly.contains(s) && s.collides(s1)){
						s.changeColor(Color.RED);
						if (mtvOn && !(s instanceof Compound) && !(s1 instanceof Compound)) {
							//s.changeCoords(s.MTV(s1));
							//System.out.println(s.MTV(s1));
							s.changeCoords(s.MTV(s1));
							noFly.add(s1);
						}
						break;
					}
					else s.changeColor(Color.BLACK);
				}
			}
		}

		@Override
		protected void onDraw(Graphics2D g) {
			for (Shape s : shapes){
				s.draw(g);
			}
		}
		
		Vec2f latestVec = null;
		@Override
		protected void onMousePressed(MouseEvent e) {
			for (Shape s : shapes){
				if (s.collidesPoint(new Vec2f(e.getPoint().x,e.getPoint().y))) {
					currentShape  = s;
					latestVec = new Vec2f(e.getPoint().x,e.getPoint().y);
					return;
				}
			}
			currentShape = null;
			
		}

		@Override
		protected void onMouseReleased(MouseEvent e) {
			currentShape = null;
		}


		@Override
		protected void onMouseDragged(MouseEvent e) {

			if (currentShape != null){

				currentShape.changeCoords(new Vec2f(e.getPoint().x,e.getPoint().y).minus(latestVec));
				latestVec = new Vec2f(e.getPoint().x,e.getPoint().y);
			}
		}
		
		
	}

}

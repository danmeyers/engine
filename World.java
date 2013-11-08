package dmeyers.engine;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import cs195n.LevelData;
import cs195n.Vec2f;
import cs195n.Vec2i;
import dmeyers.engine.geom.Collision;

public abstract class World {
	
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	public ArrayList<Entity> toAdd = new ArrayList<Entity>();

	protected Map<String, Class<? extends Entity>> classMap = new Hashtable<String,Class<? extends Entity>>();
	public Map<String, Entity> entityMap = new Hashtable<String,Entity>();

	
	public World() {
	}
			
	public void onTick(long nanosSincePreviousTick) {
		for (int i = entities.size() - 1; i >= 0;i--){
			entities.get(i).onTick(nanosSincePreviousTick);
		}
		
		
		//Collide all
		
		ArrayList<Entity> blackList = new ArrayList<Entity>();
		for (Entity e : entities){
			for (Entity e2: entities){
				if (!e.equals(e2) && !blackList.contains(e2)) {
				//	System.out.println(e + ":" + e.shape);
					if (e.shape != null && e2.shape !=null && e.shape.collides(e2.shape)){
						
						Vec2f mtv = e.shape.MTV(e2.shape);
						
						e.onCollide(new Collision(e2, mtv, false));
						e2.onCollide(new Collision(e, mtv.smult(-1), false));
					}
				}
			}
			blackList.add(e);
		}
		
		entities.addAll(toAdd);
		toAdd.clear();
	}

	
	public void onDraw(Graphics2D g) {
		for (Entity e : entities){
			e.onDraw(g);
		}
	}

	
	protected abstract void fillWorld(LevelData LD) throws InstantiationException, IllegalAccessException;

	
	protected void onKeyTyped(KeyEvent e) {

	}

	
	protected void onKeyPressed(KeyEvent e) {

	}

	
	protected void onKeyReleased(KeyEvent e) {

	}

	
	protected void onMouseClicked(MouseEvent e) {

	}

	
	protected void onMousePressed(MouseEvent e) {

	}

	
	protected void onMouseReleased(MouseEvent e) {

	}

	
	protected void onMouseDragged(MouseEvent e) {

	}

	
	public void onMouseMoved(MouseEvent e) {

	}

	
	protected void onResize(Vec2i newSize) {
		

	}

}


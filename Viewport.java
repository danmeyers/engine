package dmeyers.engine;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;

import cs195n.Vec2f;
import cs195n.Vec2i;

public class Viewport {

	float scrollSpeed = 100;
	Vec2i screenCoords;
	Vec2i screenDim;
	Vec2f gameCoords;
	float scale;
	
	Vec2f gameDims;
	View view;
		
	public Viewport(Vec2i screenCoords, Vec2i screenDim, Vec2f gameCoords, Vec2f gameDims, float scale, View view) {
		this.screenCoords = screenCoords;
		this.screenDim = screenDim;
		this.gameCoords = gameCoords;
		this.scale = scale;
		this.gameDims = gameDims;
		
		this.view = view;
	}
	
	public java.awt.Rectangle getBounds(){
		return new java.awt.Rectangle(screenCoords.x, screenCoords.y,screenDim.x,screenDim.y);
	}
	
	public Viewport cloneWithNewView(View v){
		return new Viewport(screenCoords,screenDim,gameCoords,gameDims,scale,v);
	}
	
	public Viewport reset(Vec2f gameCoords, Float s, View v){
		return new Viewport(screenCoords,screenDim,gameCoords,gameDims,s,v);
	}
	
	public void onDraw(Graphics2D g){
		java.awt.Rectangle r = g.getClip().getBounds();
	
		AffineTransform at = g.getTransform();
		Shape clip = g.getClip();
		
		g.setColor(Color.WHITE);
		g.setClip(screenCoords.x, screenCoords.y,screenDim.x,screenDim.y);

		g.fillRect(r.x, r.y, r.width, r.height);

		g.translate(-gameCoords.x, -gameCoords.y);
		g.translate(screenCoords.x, screenCoords.y);
		g.scale(scale, scale);

		if (view != null) view.onDraw(g);
		
		g.setTransform(at);
		g.setClip(clip);
		
	}
	
	public void onTick(){
		changeGameCoords(keepMovingX, keepMovingY);
	}
	
	public void setViewPortDim(Vec2i screenCoords, Vec2i screenDim){
		this.screenCoords = screenCoords;
		this.screenDim = screenDim;
	}
	
	public void setGameCoords(Vec2f gameCoords){
		this.gameCoords = gameCoords;
	}
	
	
	public void changeGameCoords(float x, float y){
		this.gameCoords = this.gameCoords.plus(new Vec2f(x,y));
		//System.out.println(gameCoords);
	}
	
	public void setScale(float tScale, int boardWidth, int boardHeight){
		
	
	float xDif = (gameDims.x * scale) - (gameDims.x * tScale);
	float yDif = (gameDims.y * scale) - (gameDims.y * tScale);
	
	Vec2f newLoc = getGameCoordinates(this.screenDim.plus(this.screenCoords).sdiv(2));
	changeGameCoords(-xDif /(boardWidth / newLoc.x),-yDif /(boardHeight / newLoc.y));
	scale = tScale;

	}
	public void setScale2(float tScale){
				scale = tScale;

	}
	public float getScale(){	
		return scale;
	}

	public void onKeyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) changeGameCoords(-scrollSpeed,0);
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT) changeGameCoords(scrollSpeed,0);
		else if (e.getKeyCode() == KeyEvent.VK_UP) changeGameCoords(0,-scrollSpeed);
		else if (e.getKeyCode() == KeyEvent.VK_DOWN) changeGameCoords(0,scrollSpeed);

	}

	public Vec2f getGameCoordinates(Vec2i vec2i){
		
		Vec2f returnVec = new Vec2f(vec2i.x,vec2i.y);
		returnVec = returnVec.minus(screenCoords.x,screenCoords.y);
		returnVec = returnVec.plus(gameCoords.x,gameCoords.y);
		returnVec = returnVec.sdiv(scale);

		return returnVec;
	}
	public Vec2f getGameCoordinates(Vec2f vec2f){
		
		Vec2f returnVec = new Vec2f(vec2f.x,vec2f.y);
		returnVec = returnVec.minus(screenCoords.x,screenCoords.y);
		returnVec = returnVec.plus(gameCoords.x,gameCoords.y);
		returnVec = returnVec.sdiv(scale);

		return returnVec;
	}
	
	public Vec2f getScreenCoordinates(Vec2f vec2f){
		
		Vec2f returnVec = new Vec2f(vec2f.x,vec2f.y);
		returnVec = returnVec.smult(scale);
		returnVec = returnVec.minus(gameCoords.x,gameCoords.y);
		returnVec = returnVec.plus(screenCoords.x,screenCoords.y);

		return returnVec;
		
		
	}


	
	public void onMouseClicked(MouseEvent e) {

		Point p = e.getPoint();
		Vec2f returnVec = new Vec2f(p.x,p.y);

		
		
		returnVec = returnVec.minus(screenCoords.x,screenCoords.y);
		returnVec = returnVec.plus(gameCoords.x,gameCoords.y);
		returnVec = returnVec.sdiv(scale);

		
		if (view != null) view.onMouseClicked(returnVec, e.getButton());
	}

	public void drawFloating(Shape thisSelected) {
		// TODO Auto-generated method stub
		
	}
	int keepMovingX = 0;
	int keepMovingY = 0;
	
	public void onMouseMoved(MouseEvent e) {
		if (e.getX() >= screenCoords.x && e.getX() <= (screenCoords.x + (screenDim.x / 10))) {
			keepMovingX = -5;
			keepMovingY = 0;
		}
		else if (e.getX() <= screenCoords.x + screenDim.x && e.getX() >= (screenCoords.x + (screenDim.x / 10 * 9))) {
			keepMovingX = 5;
			keepMovingY = 0;
		}
		else if (e.getY() <= screenCoords.y + screenDim.y && e.getY() >= (screenCoords.y + (screenDim.y / 10 * 9))) {
			keepMovingX = 0;
			keepMovingY = 5;
		}
		else if (e.getX() >= screenCoords.y && e.getY() <= (screenCoords.y + (screenDim.y / 10))) {
			keepMovingX = 0;
			keepMovingY = -5;
		}
		else {
			keepMovingX = 0;
			keepMovingY = 0;
		}
	}
	



	Vec2i oldPoint = null;
	public void onMouseDragged(MouseEvent e) {
		if (oldPoint == null) oldPoint = new Vec2i(e.getX(),e.getY());
		else {
			Vec2i change = (new Vec2i(e.getX(),e.getY())).minus(oldPoint).smult(-1);
			changeGameCoords(change.x,change.y);
			oldPoint = new Vec2i(e.getX(),e.getY());
		}
	}
	
	public void onMouseReleased(MouseEvent e){
		oldPoint = null;
	}

	public void followInGame(Vec2f posn, Vec2f posn2) {
		Vec2f screenCoord = getScreenCoordinates(posn);
		Vec2f screenCoord2 = getScreenCoordinates(posn2);
		if (screenCoord.x >= screenCoords.x && screenCoord.y >= screenCoords.y && screenCoord2.x <= screenDim.x && screenCoord2.y <= screenDim.y) return;
		float scrnX = screenCoord.x - screenCoords.x;
		float scrnXFar = screenCoord2.x - screenDim.x;
		
		float scrnY = screenCoord.y - screenCoords.y;
		float scrnYFar = screenCoord2.y - screenDim.y;
		
		float totalX = 0;
		float totalY = 0;
		
		if (scrnX <=0) totalX = scrnX;
		else if (scrnXFar >=0) totalX = scrnXFar;
		
		if (scrnY <=0) totalY = scrnY;
		else if (scrnYFar >=0) totalY = scrnYFar;

						
		if (!(totalX == 0 && totalY ==0)) changeGameCoords(totalX,totalY);
	}

}

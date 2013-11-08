package dmeyers.engine.UI;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import cs195n.Vec2f;
import cs195n.Vec2i;

public class SpriteManager {

	public BufferedImage sprite;
	Vec2i sheetDim;
	int padding;
	Vec2i eachDim;
	
	public SpriteManager(BufferedImage sprite, Vec2i indDim, int padding) {
		this.sprite = sprite;
		this.sheetDim = new Vec2i(sprite.getWidth(),sprite.getHeight());
		this.eachDim = indDim;
		this.padding = padding;
	}
	
	public SpriteManager(BufferedImage sprite){
		this.sprite = sprite;
		this.sheetDim = new Vec2i(sprite.getWidth(),sprite.getHeight());
	}
	
	
	public void drawSprite(Graphics2D g, Vec2i sourceCoords, Vec2f destinationLocation, Vec2f destinationDim){
		if (eachDim == null) return;
		Vec2i sLocation = sourceCoords.pmult(eachDim.plus(padding, padding)).plus(padding, padding);

		Vec2i sourceDim = sLocation.plus(eachDim);
		Vec2f destDim = destinationLocation.plus(destinationDim);		

		g.drawImage(sprite, (int) destinationLocation.x, (int) destinationLocation.y, (int) destDim.x, (int) destDim.y, sLocation.x, sLocation.y, sourceDim.x, sourceDim.y, null);
	}
	
	public void drawSpriteBare(Graphics2D g, Vec2i sourcePos, Vec2i sourcePos2, Vec2i destPos, Vec2i destPos2){
		g.drawImage(sprite, destPos.x, destPos.y, destPos2.x, destPos2.y, sourcePos.x, sourcePos.y, sourcePos2.x, sourcePos2.y, null);
	}
	

}

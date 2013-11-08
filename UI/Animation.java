package dmeyers.engine.UI;

import java.awt.Graphics2D;

import cs195n.Vec2f;
import cs195n.Vec2i;

public class Animation {
	
	Vec2i[] frames;
	int currentFrame;
	float secondsPerFrame;
	float secondsLeft;
	public SpriteManager spriteManager;

	public Animation(SpriteManager sm, Vec2i[] f, float spf) {
		frames = f;
		secondsPerFrame = spf;
		secondsLeft = spf;
		this.spriteManager = sm;
	}
	
	
	public void onTick(float seconds){
		secondsLeft -= seconds;
		if (secondsLeft <= 0){
			changeFrame();
			secondsLeft = secondsPerFrame;
		}
	}


	private void changeFrame() {
		currentFrame = (currentFrame + 1) % frames.length;
	}
	
	public Vec2i getCurrentFrame() {
		return frames[currentFrame];
	}
	
	public void drawCurrentFrame(Graphics2D g, Vec2f destinationLocation, Vec2f destinationDim) {
		spriteManager.drawSprite(g, frames[currentFrame], destinationLocation, destinationDim);
	}

}

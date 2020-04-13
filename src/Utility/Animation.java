package Utility;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation {
	private ArrayList<BufferedImage> frames;
	private long nanosecPerFrame;
	private int currentFrame = 0;
	private long timer = 0;
	
	public Animation(ArrayList<BufferedImage> frames, double secondsPerFrame) {
		this.frames = frames;
		nanosecPerFrame = Math.round(secondsPerFrame*Math.pow(10, 9));
	}
	
	public BufferedImage getFrame(long delta) {
		timer += delta;
		if (timer >= nanosecPerFrame) {
			currentFrame++;
			timer -= nanosecPerFrame;
		}
		if (currentFrame >= frames.size()) {
			currentFrame = 0;
		}
		return frames.get(currentFrame);
	}
}

package Enemies;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Utility.Animation;
import Utility.Utility;

public class WalkingEnemy extends Enemy {
	public static final int DEFAULT_BOUNTY = 30;

	public WalkingEnemy(ArrayList<Point> path) {
		super(path);
		bounty = DEFAULT_BOUNTY;
	}
	
	@Override
	public Animation getAnimation() {
		return new Animation(Utility.getTiles("an.png", 32, 32), .5f);
	}

}

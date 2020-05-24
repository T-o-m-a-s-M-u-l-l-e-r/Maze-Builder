package Enemies;

import java.awt.Point;
import java.util.ArrayList;

import Utility.Animation;
import Utility.Assets;

public class WalkingEnemy extends Enemy {
	public static final int DEFAULT_BOUNTY = 30;

	public WalkingEnemy(ArrayList<Point> path) {
		super(path);
		bounty = DEFAULT_BOUNTY;
	}
	
	@Override
	public Animation getAnimation() {
		return Assets.animation_walkingEnemy;
	}

}

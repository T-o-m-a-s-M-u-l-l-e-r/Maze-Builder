package Enemies;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Utility.Utility;

public class FlyingEnemy extends Enemy {

	public FlyingEnemy(ArrayList<Point> path) {
		super(path);
	}
	
	@Override
	public ArrayList<BufferedImage> getFrames() {
		return Utility.getTiles("tileSet.png", 32, 32);
	}

}

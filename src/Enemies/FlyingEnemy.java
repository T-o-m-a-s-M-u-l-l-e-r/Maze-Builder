package Enemies;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import Utility.Animation;
import Utility.Utility;

public class FlyingEnemy extends Enemy {
	public static final int DEFAULT_BOUNTY = 45;
	private double angle;

	public FlyingEnemy(ArrayList<Point> path) {
		super(path);
		angle = getAngle(path.get(0), path.get(path.size() - 1));
		ArrayList<Point> newPath = new ArrayList<Point>();
		newPath.add(path.get(path.size() - 1));
		this.path = newPath;
		bounty = DEFAULT_BOUNTY;
	}

	public double getAngle(Point spawnPoint, Point endPoint) {
		double a = spawnPoint.x - endPoint.x;
		double b = spawnPoint.y - endPoint.y;
		return Math.toDegrees(Math.atan(a / b));
	}

	public Animation getAnimation() {
		return new Animation(Utility.getTiles("ani.png", 47, 39), .15f);
	}

	@Override
	public void paint(Graphics2D g2) {
		AffineTransform trans = new AffineTransform();
		trans.rotate(Math.toRadians(-angle), getCenterX(), getCenterY());
		g2.setTransform(trans);
		g2.drawImage(texture, (int) x, (int) y, collisionBox.width, collisionBox.height, null);
		trans.rotate(Math.toRadians(angle), getCenterX(), getCenterY());
		g2.setTransform(trans);
		bar.paint(g2);
	}

}

package Enemies;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Components.GamePanel;
import Utility.Animation;
import Utility.Utility;

public class Enemy {
	public static final int DEFAULT_BOUNTY = 30;
	public static final int DEFAULT_WIDTH = 16;
	public static final int DEFAULT_HEIGHT = 16;
	public static final int DEFAULT_HEALTH = 50;
	public static final double DEFAULT_SPEED = 1.5;
	protected int bounty = DEFAULT_BOUNTY;
	protected Rectangle collisionBox;
	protected double health = DEFAULT_HEALTH;
	protected double maxHealth = DEFAULT_HEALTH;
	protected HealthBar bar;
	protected ArrayList<Point> path;
	protected double x, y;
	protected int pathIndex = 0;
	protected double speed = DEFAULT_SPEED;
	protected boolean alive = true;
	protected Animation animation;
	protected BufferedImage texture;

	public Enemy(ArrayList<Point> path) {
		Point spawnPoint = path.get(0);
		this.x = spawnPoint.x-DEFAULT_WIDTH/2;
		this.y = spawnPoint.y-DEFAULT_HEIGHT/2;
		collisionBox = new Rectangle((int)x, (int)y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		bar = new HealthBar(this);
		this.path = path;
		
		ArrayList<BufferedImage> frames = getFrames();
		texture = frames.get(0);
		animation = new Animation(frames, .15);
	}
	
	public ArrayList<BufferedImage> getFrames() {
		return Utility.getTiles("an.png", 32, 32);
	}
	
	public void paint(Graphics2D g2) {
		g2.drawImage(texture, (int)x, (int)y, collisionBox.width, collisionBox.height, null);
		bar.paint(g2);
	}
	
	public int getBounty() {
		return bounty;
	}
	
	public void setPath(ArrayList<Point> path) {
		this.path = path;
	}

	public double getHealth() {
		return health;
	}

	public double getMaxHealth() {
		return maxHealth;
	}

	public int getX() {
		return collisionBox.x;
	}
	
	public int getY() {
		return collisionBox.y;
	}
	
	public double getCenterX() {
		return x+collisionBox.width/2;
	}
	
	public double getCenterY() {
		return y+collisionBox.height/2;
	}

	public Rectangle getCollisionBox() {
		return collisionBox;
	}
	
	public void dealDamage(int damage) {
		health -= damage;
		if (health <= 0 && alive) {
			GamePanel.deleteEnemy(this, false);
			alive = false;
		}
	}
	
	public void tick(long delta) {
		Point pathPoint = path.get(pathIndex);
		if (moveToPosition(pathPoint)) {
			pathIndex++;
			if (pathIndex == path.size()) {
				GamePanel.deleteEnemy(this, true);
			}
		}
		collisionBox.setLocation((int)x, (int)y);
		texture = animation.getFrame(delta);
	}
	
	public boolean moveToPosition(double toX, double toY) {
		double alpha = Math.atan(Math.abs(getCenterY()-toY)/Math.abs(getCenterX()-toX));
		double xChange = Math.cos(alpha)*speed;
		double yChange = Math.sin(alpha)*speed;
		
		if (getCenterX() > toX) {
			xChange = -xChange;
		}
		
		if (getCenterY() > toY) {
			yChange = -yChange;
		}
		
		if ((int)getCenterX() == (int)toX) {
			xChange = 0;
		}
		
		if ((int)getCenterY() == (int)toY) {
			yChange = 0;
		}
		
		if(Math.abs(getCenterY()-toY) <= speed) {
			y = toY-collisionBox.height/2;
		}
		
		if(Math.abs(getCenterX()-toX) <= speed) {
			x = toX-collisionBox.width/2;
		}
		
		if ((int)getCenterY() == (int)toY && (int)getCenterX() == (int)toX) {
			return true;
		}
		
		x += xChange;
		y += yChange;
		return false;
	}
	
	public boolean moveToPosition(Point point) {
		return moveToPosition(point.x, point.y);
	}
	
}

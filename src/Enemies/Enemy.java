package Enemies;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Components.GamePanel;
import Utility.Animation;

public class Enemy {
	public static final int DEFAULT_BOUNTY = 15;
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
	protected BufferedImage currentFrame;

	public Enemy(ArrayList<Point> path) {
		Point spawnPoint = path.get(0);
		this.x = spawnPoint.x-DEFAULT_WIDTH/2;
		this.y = spawnPoint.y-DEFAULT_HEIGHT/2;
		collisionBox = new Rectangle((int)x, (int)y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		bar = new HealthBar(this);
		this.path = path;
		
		animation = getAnimation();
		currentFrame = animation.getFrame(0);
	}

	public Animation getAnimation() {
		return null;
	}
	
	public void paint(Graphics2D g2) {
		g2.drawImage(currentFrame, (int)x, (int)y, collisionBox.width, collisionBox.height, null);
		bar.paint(g2);
	}
	
	public int getBounty() {
		return bounty;
	}
	
	public void increaseBounty(int bonus) {
		bounty += bonus;
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
		currentFrame = animation.getFrame(delta);
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
	
	public class HealthBar {
		private Enemy enemy;
		private double height = 5;
		private double width = 25;
		private int yOffset = 5;
		
		public HealthBar(Enemy enemy) {
			this.enemy = enemy;
		}
		
		public void paint(Graphics2D g2) {
			g2.setColor(Color.black);
			g2.fillRect((int)(enemy.getCenterX()-width/2), (int)(enemy.getCenterY()-enemy.getCollisionBox().height/2-yOffset-height/2), (int)width, (int)height);
			double percWidth = width*(enemy.getHealth()/enemy.getMaxHealth());
			g2.setColor(Color.red);
			g2.fillRect((int)(enemy.getCenterX()-width/2), (int)(enemy.getCenterY()-enemy.getCollisionBox().height/2-yOffset-height/2), (int)percWidth, (int)height);
		}
		
	}

	
}

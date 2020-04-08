package Enemies;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import Components.GamePanel;

public class Enemy {
	public static final int BOUNTY = 30;
	private Rectangle collisionBox;
	private double health;
	private double maxHealth;
	private double x, y;
	private int width, height;
	private HealthBar bar;
	private ArrayList<Point> path;
	private int pathIndex = 0;
	private double speed = 1.5;
	private boolean alive = true;

	public Enemy(ArrayList<Point> path) {
		width = height = 16;
		Point spawnPoint = path.get(0);
		this.x = spawnPoint.x-width/2;
		this.y = spawnPoint.y-height/2;
		collisionBox = new Rectangle((int)x, (int)y, width, height);
		maxHealth = health = 50;
		bar = new HealthBar(this);
		this.path = path;
	}
	
	public void paint(Graphics2D g2) {
		g2.setColor(Color.black);
		g2.fillRect((int)x, (int)y, collisionBox.width, collisionBox.height);
		bar.paint(g2);
	}
	
	public double getHealth() {
		return health;
	}

	public double getMaxHealth() {
		return maxHealth;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getX() {
		return collisionBox.x;
	}
	
	public int getY() {
		return collisionBox.y;
	}
	
	public double getCenterX() {
		return x+width/2;
	}
	
	public double getCenterY() {
		return y+height/2;
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
	
	public void tick() {
		Point pathPoint = path.get(pathIndex);
		if (moveToPosition(pathPoint)) {
			pathIndex++;
			if (pathIndex == path.size()) {
				GamePanel.deleteEnemy(this, true);
			}
		}
		collisionBox.setLocation((int)x, (int)y);
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
			y = toY-height/2;
		}
		
		if(Math.abs(getCenterX()-toX) <= speed) {
			x = toX-width/2;
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

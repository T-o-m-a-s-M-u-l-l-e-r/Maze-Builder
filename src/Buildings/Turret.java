package Buildings;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Timer;
import java.util.TimerTask;

import Enemies.Enemy;

public class Turret extends Building {
	public int radius = 80;
	private int cooldown = 1000;
	private boolean canShoot = true;
	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	private ArrayList<Enemy> enemies;

	public Turret(int x, int y, ArrayList<Enemy> enemies) {
		super(x, y);
		this.enemies = enemies;
	}
	
	@Override
	public BuildType getBuildingType() {
		return BuildType.Turret;
	}

	@Override
	public void paint(Graphics2D g2) {
		super.paint(g2);
		try {
			for (Projectile projectile : projectiles) {
				projectile.paint(g2);
			}
		} catch (ConcurrentModificationException e) {

		}
	}

	public void tick() {
		shoot();
		try {
			for (Projectile projectile : projectiles) {
				projectile.tick();
			}
		} catch (ConcurrentModificationException e) {
			
		}
	}

	public void shoot() {
		Enemy target = null;
		double lowestDistance = -1;

		for (Enemy enemy : enemies) {
			double a = Math.abs(getCenter().x - enemy.getCenterX());
			double b = Math.abs(getCenter().y - enemy.getCenterY());
			double distance = Math.pow(Math.pow(a, 2) + Math.pow(b, 2), 1 / 2.0);
			if (distance <= radius && (distance < lowestDistance || lowestDistance < 0)) {
				lowestDistance = distance;
				target = enemy;
			}
		}

		try {
			if (target != null && canShoot) {
				projectiles.add(new Projectile(centerBox.x + SIZE / 2, centerBox.y + SIZE / 2, target));
				canShoot = false;
				new Timer().schedule(new TimerTask() {

					@Override
					public void run() {
						canShoot = true;
					}
				}, cooldown);
			}
		} catch (ConcurrentModificationException e) {

		} catch (IllegalStateException e) {

		}
	}

	public class Projectile {
		private Enemy enemy;
		private double x, y;
		private double speed = 2.5;
		private Rectangle collisionBox;
		public static final int SIZE = 16;
		private int damage = 25;

		public Projectile(double x, double y, Enemy enemy) {
			this.enemy = enemy;
			this.x = x - SIZE / 2;
			this.y = y - SIZE / 2;
			collisionBox = new Rectangle((int) x, (int) y, SIZE, SIZE);
		}

		public void tick() {
			double alpha = Math.atan(Math.abs(y - enemy.getY()) / Math.abs(x - enemy.getX()));
			double xChange = Math.cos(alpha) * speed;
			double yChange = Math.sin(alpha) * speed;

			if (x > enemy.getX()) {
				xChange = -xChange;
			}

			if (y > enemy.getY()) {
				yChange = -yChange;
			}

			x += xChange;
			y += yChange;

			collisionBox.setLocation((int) x, (int) y);

			if (checkEnemyCollision()) {
				projectiles.remove(this);
				enemy.dealDamage(damage);
			}

		}

		public boolean checkEnemyCollision() {
			return enemy.getCollisionBox().intersects(collisionBox);
		}

		public void paint(Graphics2D g2) {
			g2.setColor(Color.red);
			g2.fillOval((int) x, (int) y, SIZE, SIZE);
		}

	}
}

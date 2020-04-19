package Buildings;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Timer;
import java.util.TimerTask;

import Enemies.Enemy;
import Utility.Assets;

public class Turret extends Building {
	public static final int RANGE = 105;
	private int cooldown = 1000;
	private boolean canShoot = true;
	private Rectangle centerBox;
	public static ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	private ArrayList<Enemy> enemies;

	public Turret(int x, int y, ArrayList<Enemy> enemies) {
		super(x, y);
		this.enemies = enemies;
		centerBox = collisionBox.get(0);
		range = RANGE;
	}
	
	@Override
	public BuildType getBuildingType() {
		return BuildType.Turret;
	}
	
	public Point getCenter() {
		return new Point(centerBox.x+SIZE/2, centerBox.y+SIZE/2);
	}

	@Override
	public void paint(Graphics2D g2) {
		for (Rectangle rectangle : collisionBox) {
			g2.drawImage(Assets.turretTile, rectangle.x, rectangle.y, rectangle.width, rectangle.height, null);
		}
		try {
			for (Projectile projectile : projectiles) {
				projectile.paint(g2);
			}
		} catch (ConcurrentModificationException e) {

		}
	}

	public void tick(long delta) {
		shoot();
	}

	public void shoot() {
		Enemy target = null;
		double lowestDistance = -1;

		for (Enemy enemy : enemies) {
			double a = Math.abs(getCenter().x - enemy.getCenterX());
			double b = Math.abs(getCenter().y - enemy.getCenterY());
			double distance = Math.pow(Math.pow(a, 2) + Math.pow(b, 2), 1 / 2.0);
			if (distance <= range && (distance < lowestDistance || lowestDistance < 0)) {
				lowestDistance = distance;
				target = enemy;
			}
		}

		try {
			if (target != null && canShoot) {
				projectiles.add(new Projectile(centerBox.x, centerBox.y, target));
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
		private double speed = 6.5;
		private Rectangle collisionBox;
		public static final int SIZE = 16;
		private int damage = 25;
		private BufferedImage texture = Assets.projectileTile;

		public Projectile(double x, double y, Enemy enemy) {
			this.enemy = enemy;
			this.x = x;
			this.y = y;
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
			g2.drawImage(texture, (int)x, (int)y, null);
		}

	}
}

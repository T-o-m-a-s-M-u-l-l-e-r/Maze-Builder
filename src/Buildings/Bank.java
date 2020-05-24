package Buildings;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import Enemies.Enemy;
import Enums.BuildType;
import Utility.Assets;

public class Bank extends Building {
	public static final int RANGE = 150;
	public static final int BOUNTY_BONUS = 35;
	private ArrayList<Enemy> enemies;
	private ArrayList<Enemy> affected;

	public Bank(int x, int y, ArrayList<Enemy> enemies) {
		super(x, y);
		range = RANGE;
		Rectangle rect = collisionBox.get(0);
		center = new Point(rect.x, rect.y);
		this.enemies = enemies;
		affected = new ArrayList<Enemy>();
	}

	@Override
	public void tick(long delta) {
		setBounties();
	}

	public void setBounties() {

		for (Enemy enemy : enemies) {
			double a = Math.abs(getCenter().x - enemy.getCenterX());
			double b = Math.abs(getCenter().y - enemy.getCenterY());
			double distance = Math.pow(Math.pow(a, 2) + Math.pow(b, 2), 1 / 2.0);

			if (distance <= range && !affected.contains(enemy)) {
				enemy.increaseBounty(BOUNTY_BONUS);
				affected.add(enemy);
			} else if (distance > range && affected.contains(enemy)) {
				enemy.increaseBounty(-BOUNTY_BONUS);
				affected.remove(enemy);
			}

		}

	}

	@Override
	public BuildType getBuildingType() {
		return BuildType.Bank;
	}

	public void paint(Graphics2D g2) {
		Rectangle rectangle = getCollisionBox().get(0);
		g2.drawImage(Assets.tile_bank2, rectangle.x, rectangle.y, rectangle.width, rectangle.height, null);
		rectangle = getCollisionBox().get(1);
		g2.drawImage(Assets.tile_bank1, rectangle.x, rectangle.y, rectangle.width, rectangle.height, null);
		rectangle = getCollisionBox().get(2);
		g2.drawImage(Assets.tile_bank3, rectangle.x, rectangle.y, rectangle.width, rectangle.height, null);
	}

}

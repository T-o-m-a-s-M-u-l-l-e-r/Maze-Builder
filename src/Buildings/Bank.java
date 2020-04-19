package Buildings;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import Utility.Assets;

public class Bank extends Building {
	public static final int RANGE = 275;

	public Bank(int x, int y) {
		super(x, y);
		range = RANGE;
		Rectangle rect = collisionBox.get(0);
		center = new Point(rect.x, rect.y);
	}
	
	@Override
	public BuildType getBuildingType() {
		return BuildType.Bank;
	}
	
	public void paint(Graphics2D g2) {
		for (Rectangle rectangle : collisionBox) {
			g2.drawImage(Assets.wallTile, rectangle.x, rectangle.y, rectangle.width, rectangle.height, null);
		}
	}

}

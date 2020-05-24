package Buildings;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import Enums.BuildType;
import Utility.Assets;

public class Wall extends Building {

	public Wall(int x, int y) {
		super(x, y);
	}
	
	@Override
	public BuildType getBuildingType() {
		return BuildType.Wall;
	}
	
	public void paint(Graphics2D g2) {
		for (Rectangle rectangle : collisionBox) {
			g2.drawImage(Assets.tile_wall, rectangle.x, rectangle.y, rectangle.width, rectangle.height, null);
		}
	}

}

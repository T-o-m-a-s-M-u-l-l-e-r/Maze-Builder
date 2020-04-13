package Buildings;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Building {
	public static final int SIZE = 32;
	protected ArrayList<Rectangle> collisionBox = new ArrayList<Rectangle>();

	public Building(int x, int y) {
		collisionBox.add(new Rectangle(x, y, SIZE, SIZE));
		collisionBox.addAll(BuildType.getStructure(getBuildingType()).getCollisionBox(x, y));
	}
	
	public ArrayList<Rectangle> getCollisionBox() {
		return collisionBox;
	}
	
	public boolean containsPoint(int x, int y) {
		Point point = new Point(x, y);
		
		for (Rectangle rectangle : collisionBox) {
			if (rectangle.contains(point)) {
				return true;
			}
		}
		
		return false;
	}
	
	public BuildType getBuildingType() {
		return null;
	}

	public void tick(long delta) {
	}
	
	//getCenter

	public void paint(Graphics2D g2) {
		g2.setColor(Color.blue);
		for (Rectangle rectangle : collisionBox) {
			g2.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		}
	}

}

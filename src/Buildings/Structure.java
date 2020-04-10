package Buildings;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Structure {
	private Point[] points;

	public Structure(Point... points) {
		this.points = points;
	}
	
	public ArrayList<Rectangle> getCollisionBox(int x, int y) {
		ArrayList<Rectangle> collisionBox = new ArrayList<Rectangle>();
		for(int i = 0; i < points.length; i++) {
			collisionBox.add(new Rectangle(x+(points[i].x*Building.SIZE), y+(points[i].y*Building.SIZE), Building.SIZE, Building.SIZE));
		}
		return collisionBox;
	}
	
	public Point[] getPoints() {
		return points;
	}
}

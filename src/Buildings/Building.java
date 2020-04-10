package Buildings;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Building {
	public static final int SIZE = 32;
	protected Rectangle centerBox;
	protected ArrayList<Rectangle> drawingBox = new ArrayList<Rectangle>();

	public Building(int x, int y) {
		centerBox = new Rectangle(x, y, SIZE, SIZE);
		drawingBox = BuildType.getStructure(getBuildingType()).getCollisionBox(x, y);
	}

	public ArrayList<Rectangle> getCollisionBox() {
		return drawingBox;
	}
	
	public BuildType getBuildingType() {
		return null;
	}

	public void tick() {
	}

	public Point getCenter() {
		return new Point(centerBox.x + SIZE / 2, centerBox.y + SIZE / 2);
	}

	public void paint(Graphics2D g2) {
		g2.setColor(Color.blue);
		g2.fillRect(centerBox.x, centerBox.y, SIZE, SIZE);
		for (Rectangle rectangle : drawingBox) {
			g2.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		}
	}

}

package Buildings;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Enemies.Enemy;

public enum BuildType {
	Turret, Wall, Bank;

	public static Structure getStructure(BuildType type) {
		switch(type) {
		case Bank: return new Structure(new Point(-1, 0), new Point(0, -1));
			default: return new Structure();
		}
	}
	
	public static Building getInstance(BuildType type, int x, int y, ArrayList<Enemy> enemies) {
		switch (type) {
		case Bank: return new Bank(x, y);
		case Wall: return new Wall(x, y);
		case Turret: return new Turret(x, y, enemies);
		default: return null;
		}
	}
	
	public static BuildType getType(int keyCode) {
		switch (keyCode) {
		case KeyEvent.VK_1: return BuildType.Turret;
		case KeyEvent.VK_2: return BuildType.Wall;
		case KeyEvent.VK_3: return BuildType.Bank;
			default: return null;
		}
	}
}

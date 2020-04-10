package Buildings;

import java.awt.Point;

public enum BuildType {
	Turret, Wall;

	public static Structure getStructure(BuildType type) {
		switch(type) {
		case Wall: return new Structure();
		case Turret: return new Structure(new Point(1, 0));
			default: return new Structure();
		}
	}
}

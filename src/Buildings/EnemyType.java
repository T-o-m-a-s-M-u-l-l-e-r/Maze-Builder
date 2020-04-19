package Buildings;

import java.awt.Point;
import java.util.ArrayList;

import Enemies.Enemy;
import Enemies.FlyingEnemy;

public enum EnemyType {
	Default, Flying;
	
	public static EnemyType getType(char a) {
		switch (a) {
		case 'A': return EnemyType.Default;
		case 'B': return EnemyType.Flying;
			default: return null;
		}
	}
	
	public static Enemy getEnemy(EnemyType type, ArrayList<Point> path) {
		switch (type) {
		case Default: return new Enemy(path);
		case Flying: return new FlyingEnemy(path);
			default: return null;
		}
	}
}

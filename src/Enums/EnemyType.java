package Enums;

import java.awt.Point;
import java.util.ArrayList;

import Enemies.Enemy;
import Enemies.FlyingEnemy;
import Enemies.WalkingEnemy;
import Utility.Animation;
import Utility.Assets;
import Utility.Utility;

public enum EnemyType {
	Walking, Flying;
	
	public static EnemyType getType(char a) {
		switch (a) {
		case 'A': return EnemyType.Walking;
		case 'B': return EnemyType.Flying;
			default: return null;
		}
	}
	
	public static Enemy getEnemy(EnemyType type, ArrayList<Point> path) {
		switch (type) {
		case Walking: return new WalkingEnemy(path);
		case Flying: return new FlyingEnemy(path);
			default: return null;
		}
	}
}

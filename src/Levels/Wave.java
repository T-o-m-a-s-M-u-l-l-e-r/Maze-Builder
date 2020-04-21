package Levels;

import java.awt.Point;
import java.util.ArrayList;

import Enemies.Enemy;
import Enemies.WalkingEnemy;

public class Wave {
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
	public Wave(ArrayList<Enemy> enemies) {
		this.enemies = enemies;
	}
	
	public Enemy getNextEnemy() {
		Enemy enemy = null;
		try {
		enemy = enemies.get(0);
		enemies.remove(0);
		} catch (IndexOutOfBoundsException e) {
		}
		return enemy;
	}
	
	public void setPath(ArrayList<Point> path) {
		for (Enemy enemy : enemies) {
			if (enemy instanceof WalkingEnemy) {
			enemy.setPath(path);
			}
		}
	}
	
	public boolean isEmpty() {
		return enemies.isEmpty();
	}

}

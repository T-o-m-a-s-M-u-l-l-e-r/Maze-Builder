package Levels;

import java.awt.Point;
import java.util.ArrayList;

import Enemies.Enemy;

public class Wave {
	public static final char ENEMY_SYMBOL = 'a';
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
	public Wave(String input, ArrayList<Point> path) {
		String string = input.toLowerCase().replaceAll(" ", "").trim();
		for (int i = 0; i < string.length(); i++) {
			switch (string.charAt(i)) {
			case ENEMY_SYMBOL: enemies.add(new Enemy(path)); break;
			default: enemies.add(new Enemy(path)); break;
			}
		}
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
			enemy.setPath(path);
		}
	}
	
	public boolean isEmpty() {
		return enemies.isEmpty();
	}

}

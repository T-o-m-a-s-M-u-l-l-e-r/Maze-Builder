package Levels;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Timer;
import java.util.TimerTask;

import Buildings.Building;
import Buildings.Structure;
import Components.GamePanel;
import Components.Launcher;
import Enemies.Enemy;

public class Level {
	public static final long SPAWN_ENEMY_CD = 1000;
	public static final long ENEMY_WAVE_CD = 5000;
	public static final char PATH_SYMBOL = '1';
	public static final char PATH_END_SYMBOL = 'X';
	public int playerHealth, playerMoney;
	private char[][] tileMap = new char[Launcher.FRAME_WIDTH / GamePanel.TILE_WIDTH][Launcher.FRAME_HEIGHT
			/ GamePanel.TILE_HEIGHT];
	private int[][] buildingMap = new int[Launcher.FRAME_WIDTH/Building.SIZE][Launcher.FRAME_HEIGHT/Building.SIZE];
	private int levelNumber;
	private int bounty = 0;
	private Wave currentWave;
	private boolean waveOngoing = false;
	private ArrayList<Point> path = new ArrayList<Point>();
	private ArrayList<Rectangle> pathCollision = new ArrayList<Rectangle>();
	private ArrayList<Wave> waves = new ArrayList<Wave>();
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Building> buildings = new ArrayList<Building>();

	public Level(File levels, int levelNumber) {
		this.levelNumber = levelNumber;
		playerHealth = 500;
		playerMoney = 500;
		try {
			readFile(levels);
		} catch (Exception e) {
		}
	}

	public void test() {
//		for (int y = 0; y < tileMap[0].length; y++) {
//			for (int x = 0; x < tileMap.length; x++) {
//				System.out.print(tileMap[x][y]);
//			}
//			System.out.println();
//		}
		for (int y = 0; y < buildingMap[0].length; y++) {
			for (int x = 0; x < buildingMap.length; x++) {
				System.out.print(buildingMap[x][y]);
			}
			System.out.println();
		}
		
		System.out.println();
	}

	public boolean nextWave() {
		waveOngoing = true;
		currentWave = waves.get(0);
		System.out.println("Wave");

		new Timer().scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				Enemy enemy = currentWave.getNextEnemy();
				if (enemy != null) {
					enemies.add(enemy);
				}
				if (currentWave.isEmpty()) {
					waves.remove(currentWave);
					cancel();
				}
			}
		}, 0, SPAWN_ENEMY_CD);

		return true;
	}

	public void build(MouseEvent e, Structure structure) {
		int cost = 50;
		
		int mapX = e.getX()/Building.SIZE;
		int mapY = e.getY()/Building.SIZE;

		if (!checkBuildingCollision(mapX, mapY, structure.getPoints()) && !waveOngoing) {
			if (!spendMoney(cost)) {
				buildings.add(new Building(mapX*Building.SIZE, mapY*Building.SIZE, enemies));
				
				buildingMap[mapX][mapY] = 1;
				for (Point point : structure.getPoints()) {
					buildingMap[mapX+point.x][mapY+point.y] = 1;
				}
			}
		}
	}

	public void deleteEnemy(Enemy e, boolean reachedEnd) {
		enemies.remove(e);

		if (reachedEnd) {
			if (damagePlayer(10)) {
				GamePanel.gameOver();
			}
		} else {
			bounty += Enemy.BOUNTY;
		}

		if (enemies.isEmpty() && currentWave.isEmpty()) {
			waveOngoing = false;
		}

		if (!waveOngoing) {
			playerMoney += bounty;
			bounty = 0;

			if (waves.isEmpty()) {
				GamePanel.nextLevel();
			} else {
				GamePanel.startWaveCooldown();
			}
		}
	}

	public void paint(Graphics2D g2) {
		for (int x = 0; x < tileMap.length; x++) {
			for (int y = 0; y < tileMap[0].length; y++) {
				switch (tileMap[x][y]) {
				case '0':
					g2.setColor(Color.green);
					break;
				case PATH_SYMBOL:
					g2.setColor(Color.white);
					break;
				}
				g2.fillRect(x * GamePanel.TILE_WIDTH, y * GamePanel.TILE_HEIGHT, GamePanel.TILE_WIDTH,
						GamePanel.TILE_HEIGHT);
			}
		}

		try {
			for (Enemy enemy : enemies) {
				enemy.paint(g2);
			}
		} catch (ConcurrentModificationException e) {

		}

		try {
			for (Building building : buildings) {
				building.paint(g2);
			}
		} catch (ConcurrentModificationException e) {

		}
	}

	public boolean checkBuildingCollision(int mapX, int mapY, Point[] pointStructure) {
		if (buildingMap[mapX][mapY] != 0) {
			return true;
		}
		
		for (int i = 0; i < pointStructure.length; i++) {
			if (buildingMap[mapX+pointStructure[i].x][mapY+pointStructure[i].y] != 0) {
				return true;
			}
		}

		return false;
	}

	public void tick() {
		try {
			for (Building building : buildings) {
				building.tick();
			}

		} catch (ConcurrentModificationException e) {

		}

		try {
			for (Enemy enemy : enemies) {
				enemy.tick();
			}
		} catch (ConcurrentModificationException e) {

		}
	}

	public void readFile(File file) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;
		int count = 0;
		int y = 0;
		while (y < Launcher.FRAME_HEIGHT / GamePanel.TILE_HEIGHT) {
			line = reader.readLine();

			if (line.isBlank()) {
				count++;
			} else if (count == levelNumber) {
				for (int x = 0; x < line.length(); x++) {
					tileMap[x][y] = line.charAt(x);
				}
				y++;
			}

		}

		path.add(new Point(getPathStart().x * GamePanel.TILE_WIDTH + GamePanel.TILE_WIDTH / 2,
				getPathStart().y * GamePanel.TILE_HEIGHT + GamePanel.TILE_HEIGHT / 2));

		Point end = getPathEnd();
		Point currentPoint = getPathStart();
		Point lastVisited = null;
		tileMap[getPathEnd().x][getPathEnd().y] = PATH_SYMBOL;

		while (!currentPoint.equals(end)) {

			for (int i = 0; i < 4; i++) {
				Point newPoint = null;

				switch (i) {
				case 0:
					newPoint = new Point(currentPoint.x, currentPoint.y + 1);
					break;
				case 1:
					newPoint = new Point(currentPoint.x, currentPoint.y - 1);
					break;
				case 2:
					newPoint = new Point(currentPoint.x + 1, currentPoint.y);
					break;
				case 3:
					newPoint = new Point(currentPoint.x - 1, currentPoint.y);
					break;
				}

				try {
					if (tileMap[newPoint.x][newPoint.y] == PATH_SYMBOL && !newPoint.equals(lastVisited)) {
						lastVisited = currentPoint;
						currentPoint = newPoint;
						break;
					}
				} catch (ArrayIndexOutOfBoundsException e) {

				}

			}

			if (isPathPoint(currentPoint.x, currentPoint.y)) {
				path.add(new Point(currentPoint.x * GamePanel.TILE_WIDTH + GamePanel.TILE_WIDTH / 2,
						currentPoint.y * GamePanel.TILE_HEIGHT + GamePanel.TILE_HEIGHT / 2));
			}
		}

		for (int a = 0; a < tileMap.length; a++) {
			for (int b = 0; b < tileMap[0].length; b++) {
				if (tileMap[a][b] == PATH_SYMBOL) {
					pathCollision.add(new Rectangle(a * GamePanel.TILE_WIDTH, b * GamePanel.TILE_HEIGHT,
							GamePanel.TILE_WIDTH, GamePanel.TILE_HEIGHT));
				}
			}
		}

		while ((line = reader.readLine()) != null && count == levelNumber) {
			if (line.isBlank()) {
				count++;
			} else {
				waves.add(new Wave(line, path));
			}
		}
		reader.close();
	}

	public Point getPathStart() {

		for (int y = 0; y < tileMap[0].length; y++) {
			for (int x = 0; x < tileMap.length; x++) {
				if (tileMap[x][y] == PATH_SYMBOL && (x == 0 || y == 0 || x == tileMap.length - 1 || y == tileMap[0].length - 1)) {
					return new Point(x, y);
				}
			}
		}

		return null;
	}

	public Point getPathEnd() {

		for (int y = 0; y < tileMap[0].length; y++) {
			for (int x = 0; x < tileMap.length; x++) {
				if (tileMap[x][y] == PATH_END_SYMBOL) {
					return new Point(x, y);
				}
			}
		}

		return null;
	}

	public boolean isPathPoint(int x, int y) {

		if (tileMap[x][y] != PATH_SYMBOL) {
			return false;
		}

		if (tileMap[x][y] == PATH_END_SYMBOL) {
			return true;
		}

		try {
			if (tileMap[x - 1][y] != PATH_SYMBOL && tileMap[x][y - 1] != PATH_SYMBOL) {
				return true;
			}

			if (tileMap[x - 1][y] != PATH_SYMBOL && tileMap[x][y + 1] != PATH_SYMBOL) {
				return true;
			}

			if (tileMap[x + 1][y] != PATH_SYMBOL && tileMap[x][y - 1] != PATH_SYMBOL) {
				return true;
			}

			if (tileMap[x + 1][y] != PATH_SYMBOL && tileMap[x][y + 1] != PATH_SYMBOL) {
				return true;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return true;
		}

		return false;
	}

	public ArrayList<Rectangle> getPathCollision() {
		return new ArrayList<Rectangle>(pathCollision);
	}

	public ArrayList<Point> getPath() {
		return new ArrayList<Point>(path);
	}

	public boolean damagePlayer(int damage) {
		playerHealth -= damage;
		if (playerHealth <= 0) {
			return true;
		} else {
			System.out.println(playerHealth);
			return false;
		}
	}

	public boolean spendMoney(int cost) {
		if (playerMoney - cost < 0) {
			return true;
		} else {
			playerMoney -= cost;
			return false;
		}
	}

}

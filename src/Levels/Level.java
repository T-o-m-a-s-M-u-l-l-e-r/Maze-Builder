package Levels;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Timer;
import java.util.TimerTask;

import Buildings.BuildType;
import Buildings.Building;
import Buildings.Structure;
import Buildings.Turret;
import Buildings.Wall;
import Components.GamePanel;
import Components.Launcher;
import Enemies.Enemy;
import Utility.Assets;
import Utility.Utility;

public class Level {
	public static final long SPAWN_ENEMY_CD = 1000;
	public static final long ENEMY_WAVE_CD = 10000;
	public int playerHealth, playerMoney;
	private char[][] tileMap = new char[Launcher.FRAME_WIDTH / GamePanel.TILE_WIDTH][Launcher.FRAME_HEIGHT
			/ GamePanel.TILE_HEIGHT];
	private int[][] buildingMap = new int[Launcher.FRAME_WIDTH / Building.SIZE][Launcher.FRAME_HEIGHT / Building.SIZE];
	private int levelNumber;
	private int bounty = 0;
	private Wave currentWave;
	private boolean waveOngoing = false;
	private Pathfinder pathfinder;
	private ArrayList<Point> path = new ArrayList<Point>();
	private ArrayList<Wave> waves = new ArrayList<Wave>();
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Building> buildings = new ArrayList<Building>();

	public Level(File levels, int levelNumber) {
		this.levelNumber = levelNumber;
		playerHealth = 500;
		playerMoney = 5000;
		try {
			readFile(levels);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void test() {
		System.out.println(buildings.size());

		for (int y = 0; y < buildingMap[0].length; y++) {
			for (int x = 0; x < buildingMap.length; x++) {
				System.out.print(buildingMap[x][y]);
			}
			System.out.println();
		}
	}

	public boolean nextWave() {
		waveOngoing = true;
		currentWave = waves.get(0);

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

	public void setPath(ArrayList<Point> path) {
		for (Wave wave : waves) {
			wave.setPath(path);
		}
	}

	public void build(MouseEvent e, BuildType type) {
		int cost = 50;

		int mapX = e.getX() / Building.SIZE;
		int mapY = e.getY() / Building.SIZE;

		Building building = null;
		switch (type) {
		case Turret:
			building = new Turret(mapX * Building.SIZE, mapY * Building.SIZE, enemies);
			break;
		case Wall:
			building = new Wall(mapX * Building.SIZE, mapY * Building.SIZE);
			break;
		}

		Structure structure = BuildType.getStructure(type);
		if (!checkBuildingCollision(mapX, mapY, structure.getPoints()) && !waveOngoing) {
			int[][] tempArray = Utility.copyArray(buildingMap);
			tempArray[mapX][mapY] = 1;
			for (Point point : structure.getPoints()) {
				tempArray[mapX + point.x][mapY + point.y] = 1;
			}

			if (adaptPath(tempArray)) {
				if (!spendMoney(cost)) {
					buildings.add(building);
					buildingMap = Utility.copyArray(tempArray);
				}
			}
		}
	}

	public void remove(MouseEvent e) {
		if (!waveOngoing) {
			Building buildingToRemove = null;

			for (Building building : buildings) {
				if (building.containsPoint(e.getX(), e.getY())) {
					buildingToRemove = building;
					break;
				}
			}

			try {
				for (Rectangle rectangle : buildingToRemove.getCollisionBox()) {
					buildingMap[rectangle.x / Building.SIZE][rectangle.y / Building.SIZE] = 0;
				}
				buildings.remove(buildingToRemove);
				adaptPath(buildingMap);
			} catch (NullPointerException a) {

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

	public boolean adaptPath(int[][] buildingMap) {
		ArrayList<Point> testPath = pathfinder.getPath(buildingMap);

		if (testPath.size() <= 1) {
			return false;
		}

		path = testPath;
		for (Wave wave : waves) {
			wave.setPath(path);
		}
		return true;
	}

	public void paint(Graphics2D g2) {
		for (int x = 0; x < tileMap.length; x++) {
			for (int y = 0; y < tileMap[0].length; y++) {
				BufferedImage texture = null;
				switch (tileMap[x][y]) {
				case '0': texture = Assets.plantTile_1; break;
				case '1': texture = Assets.plantTile_2; break;
				case '2': texture = Assets.plantTile_3; break;
				case '3': texture = Assets.grassTile; break;		
				}
				
				g2.drawImage(texture, x * GamePanel.TILE_WIDTH, y * GamePanel.TILE_HEIGHT, GamePanel.TILE_WIDTH,
						GamePanel.TILE_HEIGHT, null);
			}
		}

		try {
			paintPath(g2);
		} catch (Exception e) {

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

	public void paintPath(Graphics2D g2) {
		for (Point point : path) {
			
			if (path.indexOf(point) == path.size()-1) {
				g2.drawImage(Assets.pathEndTile, (point.x/Building.SIZE)*Building.SIZE, (point.y/Building.SIZE)*Building.SIZE, Building.SIZE, Building.SIZE, null);
				break;
			}
			
			g2.drawImage(Assets.pathTile, (point.x/Building.SIZE)*Building.SIZE, (point.y/Building.SIZE)*Building.SIZE, Building.SIZE, Building.SIZE, null);
			
			Point nextPoint = path.get(path.indexOf(point)+1);
			
			if (point.y == nextPoint.y) {
				
				int x1 = point.x/Building.SIZE;
				int x2 = nextPoint.x/Building.SIZE;
				
				for (int x = Math.min(x1, x2)+1; x < Math.max(x1, x2); x++) {
					g2.drawImage(Assets.pathTile, x*Building.SIZE, (point.y/Building.SIZE)*Building.SIZE, Building.SIZE, Building.SIZE, null);
				}
				
			} else {
				
				int y1 = point.y/Building.SIZE;
				int y2 = nextPoint.y/Building.SIZE;
				
				for (int y = Math.min(y1, y2)+1; y < Math.max(y1, y2); y++) {
					g2.drawImage(Assets.pathTile, (point.x/Building.SIZE)*Building.SIZE, y*Building.SIZE, Building.SIZE, Building.SIZE, null);
				}
				
			}
		}
	}

	public boolean checkBuildingCollision(int mapX, int mapY, Point[] pointStructure) {
		try {
			if (buildingMap[mapX][mapY] != 0) {
				return true;
			}

			for (int i = 0; i < pointStructure.length; i++) {
				if (buildingMap[mapX + pointStructure[i].x][mapY + pointStructure[i].y] != 0) {
					return true;
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return true;
		}

		return false;
	}

	public void tick(long delta) {
		try {
			for (Building building : buildings) {
				building.tick(delta);
			}

		} catch (ConcurrentModificationException e) {

		}

		try {
			for (Enemy enemy : enemies) {
				enemy.tick(delta);
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

		String[] firstLine = reader.readLine().split("-");
		Point startPoint = new Point(Integer.valueOf(firstLine[0]), Integer.valueOf(firstLine[1]));

		String[] secondLine = reader.readLine().split("-");
		Point endPoint = new Point(Integer.valueOf(secondLine[0]), Integer.valueOf(secondLine[1]));

		pathfinder = new Pathfinder(startPoint, endPoint);
		path = pathfinder.getPath(buildingMap);

		while ((line = reader.readLine()) != null && count == levelNumber) {
			if (line.isBlank()) {
				count++;
			} else {
				waves.add(new Wave(line, path));
			}
		}
		reader.close();
	}

	public boolean damagePlayer(int damage) {
		playerHealth -= damage;
		if (playerHealth <= 0) {
			return true;
		} else {
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

package Components;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import Buildings.Building;
import Enemies.Enemy;
import Enums.BuildType;
import Enums.ClickType;
import Levels.Level;

public class GamePanel extends JPanel implements MouseListener, KeyListener, MouseMotionListener {
	private BuildType buildSelection = null;
	private ClickType clickSelection = null;
	public static final int TILE_WIDTH = 32;
	public static final int TILE_HEIGHT = 32;
	private int mouseX, mouseY;
	private static File levels;
	private static Level currentLevel;
	private static int levelNumber = 0;

	public GamePanel() {
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(new Dimension(512, 512));

		levels = new File("Levels");
		nextLevel();
	}

	public static void nextLevel() {
		currentLevel = new Level(levels, levelNumber);
		levelNumber++;
		startWaveCooldown();
	}

	public static void main(String[] args) {
		new Launcher();
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		currentLevel.paint(g2);

		showBuildingPreview(g2);
		g2.setColor(Color.black);
		g2.drawString(String.valueOf(currentLevel.playerMoney), 5, 20);
		g2.drawString("1 - Turret \n 2 - Wall \n 3 - Bank \n 4 - Remove", 8, 52);
	}
	
	public void showBuildingPreview(Graphics2D g2) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		
		if (buildSelection != null) {
			int x = ((mouseX)/Building.SIZE)*Building.SIZE;
			int y = ((mouseY)/Building.SIZE)*Building.SIZE;
			
			Building building = BuildType.getInstance(buildSelection, x, y, null);
			int range = building.getRange();
			Point center = building.getCenter();
			g2.setColor(Color.DARK_GRAY);
			g2.fillOval(center.x-range/2, center.y-range/2, range, range);
			building.paint(g2);
		}
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
	}

	public static void startWaveCooldown() {
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				currentLevel.nextWave();
			}
		}, Level.ENEMY_WAVE_CD);
	}

	public static void deleteEnemy(Enemy e, boolean reachedEnd) {
		currentLevel.deleteEnemy(e, reachedEnd);
	}

	public static void gameOver() {
		System.out.println("Game Over");
	}

	public void tick(long delta) {
		currentLevel.tick(delta);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getModifiersEx() == InputEvent.BUTTON1_DOWN_MASK && clickSelection != null) {
			
			switch (clickSelection) {
			case Build: currentLevel.build(e, buildSelection); break;
			case Remove: currentLevel.remove(e); break;
			}
			buildSelection = null;
			clickSelection = null;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		buildSelection = BuildType.getType(e.getKeyCode());
		clickSelection = ClickType.getType(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

}

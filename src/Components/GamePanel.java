package Components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
import Levels.Level;

public class GamePanel extends JPanel implements MouseListener, KeyListener, MouseMotionListener {
	public static final int TILE_WIDTH = 32;
	public static final int TILE_HEIGHT = 32;
	private boolean playerIsBuilding = false;
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

		g2.setColor(Color.cyan);
		if (playerIsBuilding) {
			g2.fillRect(mouseX - Building.SIZE / 2, mouseY - Building.SIZE / 2, Building.SIZE, Building.SIZE);
		}
		g2.setColor(Color.black);
		g2.drawString(String.valueOf(currentLevel.playerMoney), 20, 20);
	}

	public static void startWaveCooldown() {
		System.out.println("cooldown");
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

	public void tick() {
		currentLevel.tick();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (playerIsBuilding && e.getModifiersEx() == InputEvent.BUTTON1_DOWN_MASK) {
			playerIsBuilding = false;
			currentLevel.build(e);
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
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			playerIsBuilding = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_A) {
			playerIsBuilding = true;
		}
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

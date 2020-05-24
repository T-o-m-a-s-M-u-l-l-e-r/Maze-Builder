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

import javax.swing.GroupLayout;
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
	private static Level currentLevel;
	private static int levelNumber = 0;

	public GamePanel() {
		initLayout();
		addMouseListener(this);
		addMouseMotionListener(this);
		requestFocusInWindow();
		nextLevel();
	}

	public void initLayout() {
		setBackground(new Color(204, 0, 51));
		setPreferredSize(new Dimension(512, 512));

		GroupLayout gamePanelLayout = new GroupLayout(this);
		setLayout(gamePanelLayout);
		gamePanelLayout.setHorizontalGroup(
				gamePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 512, Short.MAX_VALUE));
		gamePanelLayout.setVerticalGroup(
				gamePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 512, Short.MAX_VALUE));
	}

	public static void nextLevel() {
		currentLevel = new Level(levelNumber);
		levelNumber++;
	}
	
	public static Level getCurrentLevel() {
		return currentLevel;
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		currentLevel.paint(g2);

		showBuildingPreview(g2);
	}

	public void showBuildingPreview(Graphics2D g2) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));

		if (buildSelection != null) {
			int x = ((mouseX) / Building.SIZE) * Building.SIZE;
			int y = ((mouseY) / Building.SIZE) * Building.SIZE;

			Building building = BuildType.getInstance(buildSelection, x, y, null);
			int range = building.getRange();
			Point center = building.getCenter();
			g2.setColor(Color.DARK_GRAY);
			g2.fillOval(center.x - range, center.y - range, range*2, range*2);
			building.paint(g2);
		}

		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
	}

	public static void deleteEnemy(Enemy e, boolean reachedEnd) {
		currentLevel.deleteEnemy(e, reachedEnd);
	}
	
	public int getHealth() {
		return currentLevel.playerHealth;
	}
	
	public int getMoney() {
		return currentLevel.playerMoney;
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
			case Build:
				currentLevel.build(e, buildSelection);
				break;
			case Remove:
				currentLevel.remove(e);
				break;
			}
			buildSelection = null;
			clickSelection = null;
		}
		
		if (e.getModifiersEx() == InputEvent.BUTTON3_DOWN_MASK) {
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
	
	public void setBuildSelection(BuildType buildSelection) {
		this.buildSelection = buildSelection;
	}
	
	public void setClickSelection(ClickType clickSelection) {
		this.clickSelection = clickSelection;
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

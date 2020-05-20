package Components;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Launcher extends JFrame implements KeyListener {
	private static Launcher launcher;
	private UserInterfacePanel interfacePanel;
	private GamePanel gamePanel;
	public static final int FRAME_WIDTH = 512;
	public static final int FRAME_HEIGHT = 512;
	public static final int FPS = 60;

	private Launcher() {
		super("Game Game");
		initComponents();
		requestFocus();

		long lastTime = System.nanoTime();
		double timePerFrame = 1000000000 / FPS;
		long delta = 0;

		while (true) {
			long now = System.nanoTime();
			delta += now - lastTime;
			lastTime = now;
			while (delta >= timePerFrame) {
				gamePanel.tick(delta);
				interfacePanel.tick(delta);
				delta -= timePerFrame;
			}
			
			repaint();
		}
	}

	private void initComponents() {
		gamePanel = new GamePanel();
		interfacePanel = new UserInterfacePanel(gamePanel);

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(new Dimension(0, 0));
		addKeyListener(this);
		getContentPane().setLayout(new FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

		getContentPane().add(gamePanel);
		getContentPane().add(interfacePanel);

		setVisible(true);
		pack();
	}
	
	public static void gameOver(boolean won) {
		
	}

	public static void main(String args[]) {
		launcher = new Launcher();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		gamePanel.keyTyped(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		gamePanel.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		gamePanel.keyReleased(e);
	}

}
package Components;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import Utility.Assets;

public class Launcher extends JFrame implements KeyListener {
	private static Launcher launcher;
	private UserInterfacePanel interfacePanel;
	private GamePanel gamePanel;
	public static final int FRAME_WIDTH = 512;
	public static final int FRAME_HEIGHT = 512;
	public static final int FPS = 60;

	private Launcher() {
		super("Maze Builder");
		ArrayList<BufferedImage> icons = new ArrayList<BufferedImage>();
		icons.add(Assets.icon_game_128);
		icons.add(Assets.icon_game_64);
		icons.add(Assets.icon_game_32);
		icons.add(Assets.icon_game_16);
		setIconImages(icons);
		initComponents();
		requestFocus();
		setLocationRelativeTo(null);
		new CustomDialog("Tip of the day", 320, 75, null,
				"<html><b>Tip: </b>Try to block the enemies' path by placing walls and turrets in their way. They'll find a way around them!",
				null).setVisible(true);

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

	static class CustomDialog extends JDialog {
		public CustomDialog(String title, int width, int height, ImageIcon icon, String text, Font font) {
			super(launcher);
			setTitle(title);
			setModal(true);
			setResizable(false);
			setSize(new Dimension(width, height));
			setAutoRequestFocus(true);
			setLocationRelativeTo(null);

			JLabel label = new JLabel(icon);
			label.setHorizontalTextPosition(JLabel.CENTER);
			label.setVerticalTextPosition(JLabel.CENTER);
			label.setFont(font);
			label.setText(text);

			getContentPane().add(label);
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
		CustomDialog gameOverDialog;
		if (won) {
			Font font = new Font("Bodoni MT Black", 1, 24);
			gameOverDialog = new CustomDialog("Congratulations!", 270, 200, Assets.gif_win, "You have won!", font);
		} else {
			gameOverDialog = new CustomDialog("Game Over", 270, 180, Assets.gif_loss, "", null);
		}
		gameOverDialog.setVisible(true);

		System.exit(0);
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
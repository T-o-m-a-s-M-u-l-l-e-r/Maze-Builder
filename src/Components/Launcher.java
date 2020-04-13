package Components;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class Launcher extends JFrame implements KeyListener{
	public static final int FRAME_WIDTH = 512;
	public static final int FRAME_HEIGHT = 512;
	public static final int FPS = 60;
	private GamePanel panel;

	public Launcher() {
		super("ye");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		addKeyListener(this);
		
		panel = new GamePanel();
		add(panel);
		pack();
		
	      long lastTime = System.nanoTime();
	      double timePerFrame = 1000000000 / FPS;
	      long delta = 0;
	      
	      while(true) {
	       long now = System.nanoTime();
	       delta += now - lastTime;
	       lastTime = now;
	       while(delta >= timePerFrame) {
	        panel.tick(delta);
	        delta -= timePerFrame;
	       }
	       repaint();
	      }
	}

	@Override
	public void keyTyped(KeyEvent e) {
		panel.keyTyped(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		panel.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		panel.keyReleased(e);
	}
}

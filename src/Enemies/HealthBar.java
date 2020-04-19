package Enemies;
import java.awt.Color;
import java.awt.Graphics2D;

public class HealthBar {
	private Enemy enemy;
	private double height = 5;
	private double width = 25;
	private int yOffset = 5;
	
	public HealthBar(Enemy enemy) {
		this.enemy = enemy;
	}
	
	public void paint(Graphics2D g2) {
		g2.setColor(Color.black);
		g2.fillRect((int)(enemy.getCenterX()-width/2), (int)(enemy.getCenterY()-enemy.getCollisionBox().height/2-yOffset-height/2), (int)width, (int)height);
		double percWidth = width*(enemy.getHealth()/enemy.getMaxHealth());
		g2.setColor(Color.red);
		g2.fillRect((int)(enemy.getCenterX()-width/2), (int)(enemy.getCenterY()-enemy.getCollisionBox().height/2-yOffset-height/2), (int)percWidth, (int)height);
	}
	
}

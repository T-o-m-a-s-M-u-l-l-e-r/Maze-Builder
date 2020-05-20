package Utility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Components.GamePanel;

public class Assets {
	private static final ArrayList<BufferedImage> tileSet = Utility.getTiles("tileSet.png", GamePanel.TILE_WIDTH, GamePanel.TILE_HEIGHT);
	public static final BufferedImage plantTile_1 = tileSet.get(0);
	public static final BufferedImage plantTile_2 = tileSet.get(1);
	public static final BufferedImage plantTile_3 = tileSet.get(2);
	public static final BufferedImage grassTile = tileSet.get(3);
	public static final BufferedImage pathTile = tileSet.get(4);
	public static final BufferedImage pathEndTile = tileSet.get(5);
	public static final BufferedImage wallTile = tileSet.get(6);
	public static final BufferedImage turretTile = tileSet.get(7);
	public static final BufferedImage projectileTile = tileSet.get(8);
	public static final BufferedImage pathStartTile = tileSet.get(9);
	public static final BufferedImage bankTile_1 = tileSet.get(10);
	public static final BufferedImage bankTile_2 = tileSet.get(11);
	public static final BufferedImage bankTile_3 = tileSet.get(12);
}

package Utility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Components.GamePanel;

public class Assets {
	private static final ArrayList<BufferedImage> grassTileSet = Utility.getTiles("grassTileSet.png", GamePanel.TILE_WIDTH, GamePanel.TILE_HEIGHT);
	public static final BufferedImage plantTile_1 = grassTileSet.get(0);
	public static final BufferedImage plantTile_2 = grassTileSet.get(1);
	public static final BufferedImage plantTile_3 = grassTileSet.get(2);
	public static final BufferedImage grassTile = grassTileSet.get(3);
	public static final BufferedImage pathTile = grassTileSet.get(4);
	public static final BufferedImage pathEndTile = grassTileSet.get(5);
}

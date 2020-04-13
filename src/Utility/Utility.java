package Utility;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Utility {

	public static int[][] copyArray(int[][] input) {
		int[][] copy = new int[input.length][input[0].length];

		for (int y = 0; y < input[0].length; y++) {
			for (int x = 0; x < input.length; x++) {
				copy[x][y] = input[x][y];
			}
		}

		return copy;
	}
	
	public static ArrayList<BufferedImage> getTiles(BufferedImage tileSet, int tileWidth, int tileHeight) {
		ArrayList<BufferedImage> tiles = new ArrayList<BufferedImage>();
		for(int y = 0; y < tileSet.getHeight()/tileHeight; y++) {
			for (int x = 0; x < tileSet.getWidth()/tileWidth; x++) {
				tiles.add(getTile(tileSet, tileWidth, tileHeight, x, y));
			}
		}
		return tiles;
	}
	
	private static BufferedImage getTile(BufferedImage tileSet, int tileWidth, int tileHeight, int imageX, int imageY) {
		BufferedImage tile = new BufferedImage(tileWidth, tileHeight, tileSet.getType());
		for (int y = 0; y < tileHeight; y++) {
			for (int x = 0; x < tileWidth; x++) {
				tile.setRGB(x, y, tileSet.getRGB((imageX*tileWidth)+x, (imageY*tileHeight)+y));
			}
		}
		return tile;
	}
	
}

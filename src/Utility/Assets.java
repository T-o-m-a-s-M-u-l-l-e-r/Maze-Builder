package Utility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import Components.GamePanel;

public class Assets {
	public static final File levels = getFile("Levels");
	public static final ArrayList<BufferedImage> tileset_button = getTiles("tileset_button.png", 94, 46);
	private static final ArrayList<BufferedImage> tileset_texture = getTiles("tileset_texture.png", GamePanel.TILE_WIDTH,
			GamePanel.TILE_HEIGHT);
	public static final BufferedImage tile_grass1_1 = tileset_texture.get(0);
	public static final BufferedImage tile_grass1_2 = tileset_texture.get(1);
	public static final BufferedImage tile_grass1_3 = tileset_texture.get(2);
	public static final BufferedImage tile_grass1_4 = tileset_texture.get(3);
	public static final BufferedImage tile_path = tileset_texture.get(4);
	public static final BufferedImage tile_path_end = tileset_texture.get(5);
	public static final BufferedImage tile_wall = tileset_texture.get(6);
	public static final BufferedImage tile_turret = tileset_texture.get(7);
	public static final BufferedImage tile_turret_projectile = tileset_texture.get(8);
	public static final BufferedImage tile_bank1 = tileset_texture.get(10);
	public static final BufferedImage tile_bank2 = tileset_texture.get(11);
	public static final BufferedImage tile_bank3 = tileset_texture.get(12);
	public static final BufferedImage tile_grass2_1 = tileset_texture.get(15);
	public static final BufferedImage tile_grass2_2 = tileset_texture.get(14);
	public static final BufferedImage tile_grass2_3 = tileset_texture.get(13);
	public static final BufferedImage icon_heart = getImage(getFile("icon_heart.png"));
	public static final BufferedImage icon_money = getImage(getFile("icon_money.png"));
	public static final BufferedImage icon_game_128 = getImage(getFile("icon_game_128.png"));
	public static final BufferedImage icon_game_64 = getImage(getFile("icon_game_64.png"));
	public static final BufferedImage icon_game_32 = getImage(getFile("icon_game_32.png"));
	public static final BufferedImage icon_game_16 = getImage(getFile("icon_game_16.png"));
	public static final BufferedImage background_UIpanel = getImage(getFile("background.png"));
	public static final ImageIcon gif_loss = getImageIcon("gif_loss.gif");
	public static final ImageIcon gif_win = getImageIcon("gif_win.gif");
	public static final Animation animation_walkingEnemy = new Animation(getTiles("animation_walkingEnemy.png", 32, 32), .5f);
	public static final Animation animation_flyingEnemy = new Animation(getTiles("animation_flyingEnemy.png", 47, 39), .15f);

	public static File getFile(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			throw new RuntimeException("Assets failed to load a file");
		}
		return file;
	}
	
	public static ImageIcon getImageIcon(String filePath) {
		File file = getFile(filePath);
		return new ImageIcon(filePath);
	}
	
	public static BufferedImage getImage(File file) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			throw new RuntimeException("Assets failed to read a file");
		}
		return image;
	}

	public static ArrayList<BufferedImage> getTiles(String tilesetPath, int tileWidth, int tileHeight) {
		BufferedImage tileSet = null;
		tileSet = getImage(getFile(tilesetPath));

		ArrayList<BufferedImage> tiles = new ArrayList<BufferedImage>();
		for (int y = 0; y < tileSet.getHeight() / tileHeight; y++) {
			for (int x = 0; x < tileSet.getWidth() / tileWidth; x++) {
				tiles.add(getTile(tileSet, tileWidth, tileHeight, x, y));
			}
		}
		return tiles;
	}

	private static BufferedImage getTile(BufferedImage tileset, int tileWidth, int tileHeight, int imageX, int imageY) {
		BufferedImage tile = new BufferedImage(tileWidth, tileHeight, tileset.getType());
		for (int y = 0; y < tileHeight; y++) {
			for (int x = 0; x < tileWidth; x++) {
				tile.setRGB(x, y, tileset.getRGB((imageX * tileWidth) + x, (imageY * tileHeight) + y));
			}
		}
		return tile;
	}

}

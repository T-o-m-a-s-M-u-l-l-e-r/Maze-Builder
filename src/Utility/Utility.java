package Utility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

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
	
}

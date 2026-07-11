package Utility;

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

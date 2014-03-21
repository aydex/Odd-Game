package utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;
import java.util.Scanner;

public class RandomOdd {
	
	public static int getRandomInt(int a, int b) {
		Random rand = new Random();
		int n = rand.nextInt((b-a)+1)+a;
		return n;
	}
	
	public static double getRandomDouble(double a, double b) {
		Random rand = new Random();
		double d = rand.nextDouble()*(b-a)+a;
		return d;
	}
	
	public static String getRandomNameFromFile(String filename) throws FileNotFoundException {
		Scanner inFile = new Scanner(new FileReader(filename));
		Random rand = new Random();
		String result = null;
		int n = 0;
		while (inFile.hasNext()) {
			n++;
			String line = inFile.nextLine();
			if (rand.nextInt(n) == 0) {
				result = line;
			}
		}
		inFile.close();
		return result;
	}
	
}

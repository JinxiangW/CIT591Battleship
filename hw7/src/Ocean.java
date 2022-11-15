

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Ocean {
	private Ship[][] ships = new Ship[10][10];
	private int shotsFired;
	private int hitCount;
	private int shipsSunk;
	
	public Ocean() {
		EmptySea empty = new EmptySea();
		for (Ship[] i : ships) {
			Arrays.fill(i, empty);
		}
		this.shotsFired = 0;
		this.hitCount = 0;
		this.shipsSunk = 0;
	}
	
	void placeAllShipsRandomly() {
		Random rd = new Random();
		battleshipRd(rd);
		cruiserRd(rd);
		destroyerRd(rd);
		submarineRd(rd);
	}
	
	void battleshipRd(Random rd) {
		boolean isVerticle = rd.nextBoolean();
		Battleship battleship = new Battleship();
		if (!isVerticle)
		{
			int m = rd.nextInt(10);
			int n = rd.nextInt(7);
			for (int i = 0; i < 4; ++i)
			{
				ships[m][n + i] = battleship;
			}
		} else 
		{
			int m = rd.nextInt(7);
			int n = rd.nextInt(10);
			for (int i = 0; i < 4; ++i)
			{
				ships[m + i][n] = battleship;
			}
		}

	}
	
	boolean isAvailable(int m, int n, int length, boolean isVertical) {
		EmptySea empty = new EmptySea();
		if (isVertical)
		{
			for (int i = 0; i < length; ++i) 
			{
				for (int j = 0; j < 3; ++j)
				{
					if (ships[m - 1 + i][n - 1 + j] != empty) return false;
				}
			} 
		} else 
		{
			for (int i = 0; i < 3; ++i) 
			{
				for (int j = 0; j < length; ++j)
				{
					if (ships[m - 1 + i][n - 1 + j] != empty) return false;
				}
			}
		}
		return true;
	}
	
	void cruiserRd(Random rd) {
		boolean isVerticle = rd.nextBoolean();
		Cruiser cruiser = new Cruiser();
		if (!isVerticle)
		{
			int m = rd.nextInt(10);
			int n = rd.nextInt(7);
			while (!isAvailable(m, n, 3, isVerticle))
			{
				m = rd.nextInt(10);
				n = rd.nextInt(7);
			}
			for (int i = 0; i < 3; ++i)
			{
				ships[m][n + i] = cruiser;
			}
		} else
		{
			int m = rd.nextInt(7);
			int n = rd.nextInt(10);
			while (!isAvailable(m, n, 3, isVerticle))
			{
				m = rd.nextInt(7);
				n = rd.nextInt(10);
			}
			for (int i = 0; i < 3; ++i)
			{
				ships[m + 1][n] = cruiser;
			}
		}
	}
	
	void destroyerRd(Random rd) {
		boolean isVerticle = rd.nextBoolean();
		Destroyer destroyer = new Destroyer();
		if (!isVerticle)
		{
			int m = rd.nextInt(10);
			int n = rd.nextInt(7);
			while (!isAvailable(m, n, 3, isVerticle))
			{
				m = rd.nextInt(10);
				n = rd.nextInt(7);
			}
			for (int i = 0; i < 3; ++i)
			{
				ships[m][n + i] = destroyer;
			}
		} else
		{
			int m = rd.nextInt(7);
			int n = rd.nextInt(10);
			while (!isAvailable(m, n, 3, isVerticle))
			{
				m = rd.nextInt(7);
				n = rd.nextInt(10);
			}
			for (int i = 0; i < 3; ++i)
			{
				ships[m + 1][n] = destroyer;
			}
		}
	}
	
	void submarineRd(Random rd) {
		boolean isVerticle = rd.nextBoolean();
		Submarine submarine = new Submarine();
		if (!isVerticle)
		{
			int m = rd.nextInt(10);
			int n = rd.nextInt(7);
			while (!isAvailable(m, n, 3, isVerticle))
			{
				m = rd.nextInt(10);
				n = rd.nextInt(7);
			}
			for (int i = 0; i < 3; ++i)
			{
				ships[m][n + i] = submarine;
			}
		} else
		{
			int m = rd.nextInt(7);
			int n = rd.nextInt(10);
			while (!isAvailable(m, n, 3, isVerticle))
			{
				m = rd.nextInt(7);
				n = rd.nextInt(10);
			}
			for (int i = 0; i < 3; ++i)
			{
				ships[m + 1][n] = submarine;
			}
		}
	}
	
}

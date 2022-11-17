

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Ocean {
	private static int NUM_BATTLESHIP = 1;
	private static int NUM_CRUISER = 2;
	private static int NUM_DESTROYER = 3;
	private static int NUM_SUBMARINE = 4;
	private Ship[][] ships = new Ship[10][10];
	private String[][] table = new String[10][10];
	private int shotsFired;
	private int hitCount;
	private int shipsSunk;
	
	public Ocean() {
		for (int i = 0; i < 10; ++i) {
			for (int j = 0; j < 10; ++j) {
				EmptySea empty = new EmptySea();
				empty.setBowRow(i);
				empty.setBowColumn(j);
				this.ships[i][j] = empty;
			}
		}
		
		for (String[] i : table)
		{
			Arrays.fill(i, ".");
		}
		
		this.shotsFired = 0;
		this.hitCount = 0;
		this.shipsSunk = 0;
	}
	
	void placeAllShipsRandomly() {
		Random rd = new Random();
		for(int i = 0; i < Ocean.NUM_BATTLESHIP; ++i) battleshipRd(rd);
		for(int i = 0; i < Ocean.NUM_CRUISER; ++i) cruiserRd(rd);
		for(int i = 0; i < Ocean.NUM_DESTROYER; ++i) destroyerRd(rd);
		for(int i = 0; i < Ocean.NUM_SUBMARINE; ++i) submarineRd(rd);	
	}
	
	boolean isOccupied(int row, int column) {
		EmptySea empty = new EmptySea();
		if (ships[row][column] != empty) return true;
		return false;
	}
	
	boolean shootAt(int row, int column) {
		Ship ship = ships[row][column];
		ship.shootAt(row, column);
		if (!ship.getShipType().equals("empty") && !ship.isSunk())
		{
			this.table[row][column] = "x";
			return true;
		}
			
		//additional shot should be placed at this location
		return false;
	}
	
	int getShotsFired() {
		return this.shotsFired;
	}
	
	int getHitCount() {
		return this.getHitCount();
	}
	
	int getShipsSunk() {
		return this.shipsSunk;
	}
	
	boolean isGameOver() {
		if (this.shipsSunk == 10) return true;
		return false;
	}
	
	Ship[][] getShipArray(){
		return this.ships;
	}
	
	void setShipArray(Ship[][] ships) {
		this.ships = ships;
	}
	
	void print() {
		for (int i = 0; i < 10; ++i) System.out.print(" " + i);
		System.out.print("\n");
		for (int i = 0; i < 10; ++i)
		{
			System.out.print(i);
			for (int j = 0; j < 10; ++j)
			{
				if (this.ships[i][j].isSunk()) 
				{
					System.out.print(this.ships[i][j].toString() + " ");
					continue;
				}
				System.out.print(table[i][j] + " ");
			}
			System.out.print("\n");
		}
	}
	
	void printWithShips() {
		for (int i = 0; i < 10; ++i) System.out.print(" " + i);
		System.out.print("\n");
		for (int i = 0; i < 10; ++i)
		{
			System.out.print(i);
			for (int j = 0; j < 10; ++j)
			{
				boolean isEmpty = this.ships[i][j].getShipType().equals("empty");
				System.out.print((isEmpty? this.ships[i][j].toString() : " ") + " ");
			}
			System.out.print("\n");
		}
	}
	void battleshipRd(Random rd) {
		boolean isVerticle = rd.nextBoolean();
		Battleship battleship = new Battleship();
		int m, n;
		if (!isVerticle)
		{
			m = rd.nextInt(10);
			n = rd.nextInt(11 - battleship.getLength());		
		} else 
		{
			m = rd.nextInt(11 - battleship.getLength());
			n = rd.nextInt(10);
		}
		battleship.setBowRow(m);
		battleship.setBowColumn(n);
		battleship.placeShipAt(m, n, !isVerticle, this);
	}
	
	boolean isAvailable(int m, int n, int length, boolean isVertical) {
		EmptySea empty = new EmptySea();
		if (isVertical)
		{
			for (int i = 0; i < length + 2; ++i) 
			{
				for (int j = 0; j < 3; ++j)
				{
					if (m - 1 + i < 0 || m - 1 + i >= 10 || n - 1 + j < 0 || n - 1 + j >= 10) continue;
					if (this.ships[m - 1 + i][n - 1 + j].getShipType() != "empty") return false;
				}
			} 
		} else 
		{
			for (int i = 0; i < 3; ++i) 
			{
				for (int j = 0; j < length + 2; ++j)
				{
					if (m - 1 + i < 0 || m - 1 + i >= 10 || n - 1 + j < 0 || n - 1 + j >= 10) continue;
//					String temp = this.ships[m - 1 + i][n - 1 + j].getShipType();
					if (this.ships[m - 1 + i][n - 1 + j].getShipType() != "empty") return false;
				}
			}
		}
		return true;
	}
	
	void cruiserRd(Random rd) {
		boolean isVerticle = rd.nextBoolean();
		Cruiser cruiser = new Cruiser();
		int m, n;
		if (!isVerticle)
		{
			m = rd.nextInt(10);
			n = rd.nextInt(11 - cruiser.getLength());
			while (!isAvailable(m, n, cruiser.getLength(), isVerticle))
			{
				m = rd.nextInt(10);
				n = rd.nextInt(11 - cruiser.getLength());
			}
		} else
		{
			m = rd.nextInt(11 - cruiser.getLength());
			n = rd.nextInt(10);
			while (!isAvailable(m, n, cruiser.getLength(), isVerticle))
			{
				m = rd.nextInt(11 - cruiser.getLength());
				n = rd.nextInt(10);
			}
		}
		cruiser.setBowRow(m);
		cruiser.setBowColumn(n);
//		System.out.println(m + " " + n);
		cruiser.placeShipAt(m, n, !isVerticle, this);
	}
	
	void destroyerRd(Random rd) {
		boolean isVerticle = rd.nextBoolean();
		Destroyer destroyer = new Destroyer();
		int m, n;
		if (!isVerticle)
		{
			m = rd.nextInt(10);
			n = rd.nextInt(11 - destroyer.getLength());
			while (!isAvailable(m, n, destroyer.getLength(), isVerticle))
			{
				m = rd.nextInt(10);
				n = rd.nextInt(11 - destroyer.getLength());
			}
		} else
		{
			m = rd.nextInt(11 - destroyer.getLength());
			n = rd.nextInt(10);
			while (!isAvailable(m, n, destroyer.getLength(), isVerticle))
			{
				m = rd.nextInt(11 - destroyer.getLength());
				n = rd.nextInt(10);
			}
		}
		destroyer.setBowRow(m);
		destroyer.setBowColumn(n);
		destroyer.placeShipAt(m, n, !isVerticle, this);
	}
	
	void submarineRd(Random rd) {
		boolean isVerticle = rd.nextBoolean();
		Submarine submarine = new Submarine();
		int m, n;
		if (!isVerticle)
		{
			m = rd.nextInt(10);
			n = rd.nextInt(11 - submarine.getLength());
			while (!isAvailable(m, n, submarine.getLength(), isVerticle))
			{
				m = rd.nextInt(10);
				n = rd.nextInt(11 - submarine.getLength());
			}
		} else
		{
			m = rd.nextInt(11 - submarine.getLength());
			n = rd.nextInt(10);
			while (!isAvailable(m, n, submarine.getLength(), isVerticle))
			{
				m = rd.nextInt(11 - submarine.getLength());
				n = rd.nextInt(10);
			}
		}
		submarine.setBowRow(m);
		submarine.setBowColumn(n);
		submarine.placeShipAt(m, n, !isVerticle, this);
	}
	
	
}

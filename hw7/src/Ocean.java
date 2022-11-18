

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
		++this.shotsFired;
		Ship ship = ships[row][column];
		
//		System.out.println(ship.getShipType() + " " + ship.isSunk());
		if (!ship.getShipType().equals("empty") && !ship.isSunk())
		{
			++this.hitCount;
			this.table[row][column] = "x";
			ship.shootAt(row, column);
			return true;
		}
			
		//additional shot should be placed at this location
		return false;
	}
	
	int getShotsFired() {
		return this.shotsFired;
	}
	
	int getHitCount() {
		return this.hitCount;
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
				System.out.print(this.ships[i][j].toString() + " ");
			}
			System.out.print("\n");
		}
	}
	void battleshipRd(Random rd) {
		boolean horizontal = rd.nextBoolean();
		Battleship battleship = new Battleship();
		int m, n;
		if (horizontal)
		{
			m = rd.nextInt(10);
			n = rd.nextInt(11 - battleship.getLength()) + battleship.getLength() - 1;		
		} else 
		{
			m = rd.nextInt(11 - battleship.getLength()) + battleship.getLength() - 1;
			n = rd.nextInt(10);
		}
		battleship.placeShipAt(m, n, horizontal, this);
	}
	
	boolean isAvailable(int m, int n, int length, boolean horizontal) {
		if (horizontal)
		{
			for (int i = 0; i < length + 2; ++i)
			{
				for (int j = 0; j < 3; ++j)
				{
					int row = m + 1 - i, col = n  + 1 - j;
					if (row < 0 || row >= 10 || col < 0 || col >= 10) continue;
					if (!this.ships[row][col].getShipType().equals("empty")) return false;
				}
			}
		} else
		{
			for (int i = 0; i < 3; ++i)
			{
				for (int j = 0; j < length + 2; ++j)
				{
					int row = m + 1 - i, col = n  + 1 - j;
					if (row < 0 || row >= 10 || col < 0 || col >= 10) continue;
					if (!this.ships[row][col].getShipType().equals("empty")) return false;
				}
			}
		}
		return true;
	}
	
	void cruiserRd(Random rd) {
		boolean horizontal = rd.nextBoolean();
		Cruiser cruiser = new Cruiser();
		int m, n;
		if (horizontal)
		{
			m = rd.nextInt(10);
			n = rd.nextInt(11 - cruiser.getLength()) + cruiser.getLength() - 1;
			while (!isAvailable(m, n, cruiser.getLength(), horizontal))
			{
				m = rd.nextInt(10);
				n = rd.nextInt(11 - cruiser.getLength()) + cruiser.getLength() - 1;
			}
		} else
		{
			m = rd.nextInt(11 - cruiser.getLength()) + cruiser.getLength() - 1;
			n = rd.nextInt(10);
			while (!isAvailable(m, n, cruiser.getLength(), horizontal))
			{
				m = rd.nextInt(11 - cruiser.getLength()) + cruiser.getLength() - 1;
				n = rd.nextInt(10);
			}
		}
//		System.out.println(m + " " + n);
		cruiser.placeShipAt(m, n, horizontal, this);
	}
	
	void destroyerRd(Random rd) {
		boolean horizontal = rd.nextBoolean();
		Destroyer destroyer = new Destroyer();
		int m, n;
		if (horizontal)
		{
			m = rd.nextInt(10);
			n = rd.nextInt(11 - destroyer.getLength()) + destroyer.getLength() - 1;
			while (!isAvailable(m, n, destroyer.getLength(), horizontal))
			{
				m = rd.nextInt(10);
				n = rd.nextInt(11 - destroyer.getLength()) + destroyer.getLength() - 1;
			}
		} else
		{
			m = rd.nextInt(11 - destroyer.getLength()) + destroyer.getLength() - 1;
			n = rd.nextInt(10);
			while (!isAvailable(m, n, destroyer.getLength(), horizontal))
			{
				m = rd.nextInt(11 - destroyer.getLength()) + destroyer.getLength() - 1;
				n = rd.nextInt(10);
			}
		}
		destroyer.placeShipAt(m, n, horizontal, this);
	}
	
	void submarineRd(Random rd) {
		boolean horizontal = rd.nextBoolean();
		Submarine submarine = new Submarine();
		int m, n;
		if (horizontal)
		{
			m = rd.nextInt(10);
			n = rd.nextInt(11 - submarine.getLength()) + submarine.getLength() - 1;
			while (!isAvailable(m, n, submarine.getLength(), horizontal))
			{
				m = rd.nextInt(10);
				n = rd.nextInt(11 - submarine.getLength()) + submarine.getLength() - 1;
			}
		} else
		{
			m = rd.nextInt(11 - submarine.getLength()) + submarine.getLength() - 1;
			n = rd.nextInt(10);
			while (!isAvailable(m, n, submarine.getLength(), horizontal))
			{
				m = rd.nextInt(11 - submarine.getLength()) + submarine.getLength() - 1;
				n = rd.nextInt(10);
			}
		}
		submarine.placeShipAt(m, n, horizontal, this);
	}
	
	
}

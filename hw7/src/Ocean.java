

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
	
	/**
	 * initialize the ocean. Set the 2d ship object array to represent the allocation
	 * of ships. The ships array is initialized with emptySea object. Set utility variables
	 * to 0
	 */
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
	
	/**
	 * place ships on the board. The number of ships that are placed is based
	 * on its type. Different randomization method is wrote for each type of ship
	 * respectively
	 */
	void placeAllShipsRandomly() {
		Random rd = new Random();
		for(int i = 0; i < Ocean.NUM_BATTLESHIP; ++i) battleshipRd(rd);
		for(int i = 0; i < Ocean.NUM_CRUISER; ++i) cruiserRd(rd);
		for(int i = 0; i < Ocean.NUM_DESTROYER; ++i) destroyerRd(rd);
		for(int i = 0; i < Ocean.NUM_SUBMARINE; ++i) submarineRd(rd);	
	}
	
	/**
	 * check if the given location is occupied
	 * @param row
	 * @param column
	 * @return
	 */
	boolean isOccupied(int row, int column) {
		EmptySea empty = new EmptySea();
		if (ships[row][column] != empty) return true;
		return false;
	}
	
	/**
	 * shoot the given location, set mark respectively on the game board table for 
	 * print method
	 * @param row
	 * @param column
	 * @return
	 */
	boolean shootAt(int row, int column) {
		++this.shotsFired;
		Ship ship = ships[row][column];
		
		if (!ship.getShipType().equals("empty") && !ship.isSunk())
		{
			++this.hitCount;
			this.table[row][column] = "x";
			ship.shootAt(row, column);
			return true;
		} else if (!ship.getShipType().equals("empty") && ship.isSunk())
		{
			this.shipsSunk += ship.sunkCnt;
			ship.setSunkCnt();
		} else 
		{
			ship.setSunkCnt();
			ship.shootAt(row, column);
		}
			
		//additional shot should be placed at this location
		return false;
	}
	
	/**
	 * return shots fired
	 * @return
	 */
	int getShotsFired() {
		return this.shotsFired;
	}
	
	/**
	 * return hit count
	 * @return
	 */
	int getHitCount() {
		return this.hitCount;
	}
	
	/**
	 * return the number of ship that has sunk
	 * @return
	 */
	int getShipsSunk() {
		return this.shipsSunk;
	}
	
	/**
	 * check if the game is over by checking the total number of hit that has been
	 * dealt to ship objects that are not emptysea
	 * @return
	 */
	boolean isGameOver() {
		if (this.hitCount == Ocean.NUM_BATTLESHIP * 4
				+ Ocean.NUM_CRUISER * 3
				+ Ocean.NUM_DESTROYER * 2
				+ Ocean.NUM_SUBMARINE) return true;
		return false;
	}
	
	/**
	 * return ship array
	 * @return
	 */
	Ship[][] getShipArray(){
		return this.ships;
	}
	
	/**
	 * set ship array
	 * @param ships
	 */
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
	
	/**
	 * randomly placing a battle ship
	 * @param rd
	 */
	private void battleshipRd(Random rd) {
		// randomly choosing an orientation
		boolean horizontal = rd.nextBoolean();
		Battleship battleship = new Battleship();
		int m, n;
		// if the orientation is horizontal, set row between 0 - 9 and column between 3 - 9. 
		// if vertical, set row between 3 - 9 and column between 0 - 9
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
	
	/**
	 * check if the given location is valid for placing a ship
	 * @param m
	 * @param n
	 * @param length
	 * @param horizontal
	 * @return
	 */
	boolean isAvailable(int m, int n, int length, boolean horizontal) {
		//return false if any ship is too close 
//		this.printWithShips();
		if (horizontal)
		{
			for (int i = 0; i < length + 2; ++i)
			{
				for (int j = 0; j < 3; ++j)
				{
					int row = m + 1 - j, col = n  + 1 - i;
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
					int row = m + 1 - j, col = n  + 1 - i;
					if (row < 0 || row >= 10 || col < 0 || col >= 10) continue;
					if (!this.ships[row][col].getShipType().equals("empty")) return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * randomly placing a cruiser
	 * @param rd
	 */
	private void cruiserRd(Random rd) {
		// randomly choosing an orientation
		boolean horizontal = rd.nextBoolean();
		Cruiser cruiser = new Cruiser();
		int m, n;
		// if the orientation is horizontal, set row between 0 - 9 and column between 3 - 9. 
		// if vertical, set row between 3 - 9 and column between 0 - 9
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
	
	/**
	 * randomly placing a destroyer
	 * @param rd
	 */
	private void destroyerRd(Random rd) {
		// randomly choosing an orientation
		boolean horizontal = rd.nextBoolean();
		Destroyer destroyer = new Destroyer();
		int m, n;
		// if the orientation is horizontal, set row between 0 - 9 and column between 3 - 9. 
		// if vertical, set row between 3 - 9 and column between 0 - 9
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
	
	/**
	 * 
	 * @param rd
	 */
	private void submarineRd(Random rd) {
		// randomly choosing an orientation
		boolean horizontal = rd.nextBoolean();
		Submarine submarine = new Submarine();
		int m, n;
		// if the orientation is horizontal, set row between 0 - 9 and column between 3 - 9. 
		// if vertical, set row between 3 - 9 and column between 0 - 9
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

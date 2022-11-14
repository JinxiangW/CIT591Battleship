

import java.util.Arrays;

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
		
	}
}

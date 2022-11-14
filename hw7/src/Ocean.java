

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
		int[] btlIndex = battleShipRd(rd);
		
	}
	
	int[] battleShipRd(Random rd) {
		int m = rd.nextInt(10), n = rd.nextInt(10);
		while (m + 3 > 9 && m - 3 < 0 && n + 3 > 9 && n - 3 < 0 )
		{
			m = rd.nextInt(10);
			n = rd.nextInt(10);
		}
		ArrayList<Integer> dir = new ArrayList<Integer>();
		
		dir.add((m + 3 > 9)? -1 : m + 3);
		dir.add((m - 3 < 0)? -1 : m - 3);
		dir.add((n + 3 > 9)? -1 : n + 3);
		dir.add((n - 3 < 0)? -1 : n - 3);
		
		int dirRd = rd.nextInt(4);
		while (dir.get(dirRd) == -1) dirRd = rd.nextInt(4);
		int mEnd = m, nEnd = n;
		
		if (dirRd == 0 || dirRd == 1) 
		{
			mEnd = dir.get(dirRd);
		}else if (dirRd == 2 || dirRd == 3)
		{
			nEnd = dir.get(dirRd);
		}
		
		int[] res = {m, mEnd, n, nEnd};
		return res;
	}
	
	
}

import java.util.Arrays;

public abstract class Ship {
	private int bowRow;
	private int bowColumn;
	private int length = 0;
	private boolean horizontal;
	private boolean[] hit;
	public int sunkCnt = 1;
	public Ship(int length) {
		this.length = length;
		this.hit = new boolean[length];
		Arrays.fill(hit, false);
	}
	
	/**
	 * return row number
	 * @return
	 */
	public int getBowRow() {
		return bowRow;
	}

	/**
	 * set row number
	 * @param bowRow
	 */
	public void setBowRow(int bowRow) {
		this.bowRow = bowRow;
	}

	/**
	 * return column number
	 * @return
	 */
	public int getBowColumn() {
		return bowColumn;
	}

	/**
	 * set column number
	 * @param bowColumn
	 */
	public void setBowColumn(int bowColumn) {
		this.bowColumn = bowColumn;
	}

	/**
	 * return orientation, true for horizontal, false for vertical
	 * @return
	 */
	public boolean isHorizontal() {
		return horizontal;
	}

	/**
	 * set orientation
	 * @param horizontal
	 */
	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}

	/**
	 *  return length 
	 * @return
	 */
	public int getLength() {
		return length;
	}

	/**
	 * return hit array
	 * @return
	 */
	public boolean[] getHit() {
		return hit;
	}
	
	/**
	 * abstract method for returning ship type respectively
	 * @return
	 */
	public abstract String getShipType();
	
	/**
	 * check if the given row, column, and orientation is valid for placing a ship
	 * @param row
	 * @param column
	 * @param horizontal
	 * @param ocean
	 * @return
	 */
	boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean) {
		
		return ocean.isAvailable(row, column, this.length, !horizontal);
	}
	
	/**
	 * place a ship at the given location with given orentation
	 * @param row
	 * @param column
	 * @param horizontal
	 * @param ocean
	 */
	void placeShipAt(int row, int column, boolean horizontal, Ocean ocean){
		//here might be some problem, the ocean ships might not get updated.
		this.setBowRow(row);
		this.setBowColumn(column);
		this.setHorizontal(horizontal);
//		System.out.println(this.getShipType() + " is placed at " + this.bowRow + " " + this.bowColumn + " " + horizontal);
		Ship ships[][] = ocean.getShipArray();
		for (int i = 0; i < this.length; ++i)
		{
			if (horizontal)
			{
//				System.out.println(this.getShipType() + " is placed at: " + row + " " + (column+i));
				ships[row][column - i] = this;
			} else
			{
//				System.out.println(this.getShipType() + " is placed at: " + (row + i) + " " + column);
				ships[row - i][column] = this;
			}
			
		}

		
		ocean.setShipArray(ships);
	}
	
	/**
	 * shoot a given location, if the ship got hit, return true and update the hit array
	 * @param row
	 * @param column
	 * @return
	 */
	boolean shootAt(int row, int column) {
		if (this.isSunk()) return false;
		boolean ifCntain = false;
		for (int i = 0; i < this.length; ++i)
		{
			if (horizontal && (this.bowRow == row && this.bowColumn - i == column)) ifCntain = true;
			else if (!horizontal && (this.bowRow - i == row && this.bowColumn == column)) ifCntain = true;
		}
		if (!ifCntain) return false;
		int index = this.bowRow - row + this.bowColumn - column;
		this.hit[index] = true;
		return true;
	}
	
	void setSunkCnt() {
		this.sunkCnt = 0;
	}
	/**
	 * check the hit array, if all elements in the array is true, return true
	 * @return
	 */
	boolean isSunk() {
		for (boolean i : this.hit)
		{
			if (!i) return false;
		}
		return true;
	}
	
	/**
	 * a char representation of ship on the game board
	 */
	@Override
	public String toString() {
		return isSunk()? "s" : "x";
	}
}

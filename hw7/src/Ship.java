import java.util.Arrays;

public abstract class Ship {
	private int bowRow;
	private int bowColumn;
	private int length;
	private boolean horizontal;
	private boolean[] hit;
	
	public Ship(int length) {
		this.length = length;
		Arrays.fill(hit, false);
	}

	public int getBowRow() {
		return bowRow;
	}

	public void setBowRow(int bowRow) {
		this.bowRow = bowRow;
	}

	public int getBowColumn() {
		return bowColumn;
	}

	public void setBowColumn(int bowColumn) {
		this.bowColumn = bowColumn;
	}

	public boolean isHorizontal() {
		return horizontal;
	}

	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}

	public int getLength() {
		return length;
	}

	public boolean[] getHit() {
		return hit;
	}
	
	public abstract String getShipType();
	
	boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean) {
		
		return ocean.isAvailable(row, column, this.length, !horizontal);
	}
	
	void placeShipAt(int row, int column, boolean horizontal, Ocean ocean){
		//here might be some problem, the ocean ships might not get updated.
		Ship ships[][] = ocean.getShipArray();
		for (int i = 0; i < this.length; ++i)
		{
			if (horizontal)
			{
				ships[row][column + i] = this;
			} else
			{
				ships[row + i][column] = this;
			}
		}
		ocean.setShipArray(ships);
	}
	
	boolean shootAt(int row, int column) {
		
		return true;
	}
	
	boolean isSunk() {
		for (boolean i : this.hit)
		{
			if (!i) return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return isSunk()? "s" : "x";
	}
}

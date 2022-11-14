

public abstract class Ship {
	private int bowRow;
	private int bowColumn;
	private int length;
	private boolean horizontal;
	private boolean[] hit;
	
	public Ship(int length) {
		this.length = length;
		
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
		
		return true;
	}
	
	void placeShipAt(int row, int column, boolean horizontal, Ocean ocean){
		
	}
	
	boolean shootAt(int row, int column) {
		
		return true;
	}
	
	boolean isSunk() {
		return true;
	}
	
	@Override
	public String toString() {
		return isSunk()? "s" : "x";
	}
}

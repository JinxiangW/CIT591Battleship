

public class Battleship extends Ship{
	private static final String TYPE = "battleship";
	private static final int LENGTH = 4; 
	public Battleship() {
		super(Battleship.LENGTH);
		
	}

	@Override
	public String getShipType() {		
		return Battleship.TYPE;
	}
	
}

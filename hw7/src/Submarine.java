

public class Submarine extends Ship{
	private static final String TYPE = "Submarine";
	private static final int LENGTH = 1; 
	
	public Submarine() {
		super(Submarine.LENGTH);
	}

	@Override
	public String getShipType() {		
		return Submarine.TYPE;
	}
	
	
}

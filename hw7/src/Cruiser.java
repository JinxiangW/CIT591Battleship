

public class Cruiser extends Ship{
	private static final String TYPE = "cruiser";
	private static final int LENGTH = 3; 
	
	public Cruiser() {
		super(Cruiser.LENGTH);
	}

	@Override
	public String getShipType() {
		return Cruiser.TYPE;
	}
	
	
}

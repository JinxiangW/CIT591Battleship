

public class Destroyer extends Ship{
	private static final String TYPE = "destroyer";
	private static final int LENGTH = 2;
	
	public Destroyer() {
		super(Destroyer.LENGTH);
	}

	@Override
	public String getShipType() {		
		return Destroyer.TYPE;
	}
	
	
}

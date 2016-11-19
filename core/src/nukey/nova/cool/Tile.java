package nukey.nova.cool;

public class Tile {
	private int type;
	private Unit unit;
	Tile (int property, Unit unit) {
		type=property;
		this.unit=unit;
	}
	
	Tile (int property) {
		type=property;
	}
	
	public void setUnit(Unit unit){
		this.unit=unit;
	}
	
	public void removeUnit() {
		unit=null;
	}
	
	public Unit getUnit() {
		return unit;
	}
}

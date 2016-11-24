package nukey.nova.cool;

public class Tile {
	private int type;
	private Unit unit;
	
	Tile (int property) {
		type=property;
	}
	
	public int getType() {
		return type;
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

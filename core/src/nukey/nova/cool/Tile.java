package nukey.nova.cool;

public class Tile {
	private int type;
	private Unit unit;
	private int posx, posy;
	
	public int getPosx() {
		return posx;
	}

	public int getPosy() {
		return posy;
	}

	Tile (int property, int posx, int posy) {
		type=property;
		this.posx = posx;
		this.posy = posy;
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

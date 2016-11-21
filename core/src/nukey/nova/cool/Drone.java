package nukey.nova.cool;

public class Drone extends Unit {
	public Drone(int xpos, int ypos, int owner) {
		this.setXpos(xpos);
		this.setYpos(ypos);
		this.setHP(5);
		this.setAttack(1);
		this.setRange(3);
		this.setOwner(owner);
		this.setSpeed(3);
		this.setSprite(0);
	}
}

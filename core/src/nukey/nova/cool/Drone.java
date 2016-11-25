package nukey.nova.cool;

import nukey.nova.cool.Cool.Player;

public class Drone extends Unit {
	public Drone(int xpos, int ypos, Player owner) {
		this.setXpos(xpos);
		this.setYpos(ypos);
		this.setHP(10);
		this.setMaxHP(10);
		this.setAttack(2);
		this.setRange(3);
		this.setOwner(owner);
		this.setSpeed(3);
		this.setID(1);
		this.setActions(0);
		this.setMaxActions(2);
	}
}

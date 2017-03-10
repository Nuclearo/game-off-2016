package nukey.nova.cool;

import java.util.ArrayList;
import java.util.Arrays;

import nukey.nova.cool.Cool.Player;
import nukey.nova.cool.Unit.Action;

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
		this.abilities = new ArrayList<Action>(Arrays.asList(new Action[]{Action.MOVE, Action.ATTACK}));
	}
}

package nukey.nova.cool;

import java.util.ArrayList;
import java.util.Arrays;
import nukey.nova.cool.Cool.Player;

public class Hacker extends Unit {
	private int bandwidth,maxBandwidth,hackRange;
	public Hacker(int xpos, int ypos, Player owner) {
		this.setXpos(xpos);
		this.setYpos(ypos);
		this.setHP(5);
		this.setMaxHP(5);
		this.setAttack(0);
		this.setRange(5);
		this.setOwner(owner);
		this.setSpeed(5);
		this.setID(0);
		this.setActions(2);
		this.setMaxActions(2);
		this.setBandwidth(2);
		this.setMaxBandwidth(2);
		this.setHackRange(7);
		this.abilities = new ArrayList<Action>(Arrays.asList(new Action[]{Action.MOVE, Action.HACK}));
	}
	public int getBandwidth() {
		return bandwidth;
	}
	public void setBandwidth(int bandwidth) {
		this.bandwidth = bandwidth;
	}
	public int getMaxBandwidth() {
		return maxBandwidth;
	}
	public void setMaxBandwidth(int maxBandwidth) {
		this.maxBandwidth = maxBandwidth;
	}
	public int getHackRange() {
		return hackRange;
	}
	public void setHackRange(int hackRange) {
		this.hackRange = hackRange;
	}
	@Override
	public void doAction(Action action, Tile target, Cool game) {
		if(action!=Action.HACK){
			game.setAvailableBandwidth(game.getAvailableBandwidth()+action.bandwidthCost); //hackers don't need bandwidth to move
		}
		super.doAction(action, target, game);
	}

	
}

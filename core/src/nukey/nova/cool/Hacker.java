package nukey.nova.cool;

import java.util.ArrayList;
import java.util.Arrays;
import nukey.nova.cool.Cool.Player;
import nukey.nova.cool.Unit.Action;

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
		this.setSpeed(7);
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
	public boolean canPerform(Action action){
		if(action == Action.HACK) {
			return getOwner().getAvailableBandwidth()>=action.bandwidthCost;
		} else {
			return abilities.contains(action);
		}
	}
	
	@Override
	public void queueCommand(Action action, Tile target, Cool game) {
		if(abilities.contains(action) && action!=Action.HACK){
			//hackers only use bandwidth to hack
			getOwner().setAvailableBandwidth(getOwner().getAvailableBandwidth()+action.bandwidthCost);
		}
		super.queueCommand(action, target, game);
	}

	
}

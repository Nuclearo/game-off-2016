package nukey.nova.cool;

import nukey.nova.cool.Unit.Action;

public class Command {
	public Action action;
	public Tile target;
	
	Command(Action action, Tile target){
		this.action = action;
		this.target = target;
	}

	public int bandwidthCost() {
		return action.bandwidthCost;
	}
}

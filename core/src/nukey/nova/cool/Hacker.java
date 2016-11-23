package nukey.nova.cool;

import nukey.nova.cool.Cool.Player;

public class Hacker extends Unit {
	private int bandwidth,maxBandwidth;
	public Hacker(int xpos, int ypos, Player owner) {
		this.setXpos(xpos);
		this.setYpos(ypos);
		this.setHP(5);
		this.setMaxHP(5);
		this.setAttack(1);
		this.setRange(5);
		this.setOwner(owner);
		this.setSpeed(5);
		this.setSprite(0);
		this.setActions(0);
		this.setMaxActions(2);
		this.setBandwidth(0);
		this.setMaxBandwidth(2);
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
}

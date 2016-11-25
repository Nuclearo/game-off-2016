package nukey.nova.cool;

import nukey.nova.cool.Cool.Player;

public abstract class Unit {
	private int xpos,ypos,HP,maxHP,attack,range,speed,ID,actions,maxActions;
	private Player owner;
	
	public int getID() {
		return ID;
	}
	
	public int getXpos() {
		return xpos;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}

	public int getActions() {
		return actions;
	}

	public void setActions(int actions) {
		this.actions = actions;
	}

	public int getMaxActions() {
		return maxActions;
	}

	public void setMaxActions(int maxActions) {
		this.maxActions = maxActions;
	}

	public int getHP() {
		return HP;
	}

	public void setHP(int hP) {
		HP = hP;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void setXpos(int xpos) {
		this.xpos = xpos;
	}

	public void setYpos(int ypos) {
		this.ypos = ypos;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public int getYpos() {
		return ypos;
	}


	public void act() {
		
	}
}


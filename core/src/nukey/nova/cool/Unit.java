package nukey.nova.cool;

import java.util.ArrayList;

import com.sun.jmx.remote.internal.ArrayQueue;

import nukey.nova.cool.Cool.Player;

public abstract class Unit {
	
	public enum Action{
		MOVE(2),
		ATTACK(2),
		HACK(10);
		
		public int bandwidthCost;
		public int actionCost;
		Action(int bandwidthCost){
			this.bandwidthCost = bandwidthCost;
		}
	}
	
	private int xpos,ypos,HP,maxHP,attack,range,speed,ID,time,maxActions;
	private Player owner;
	protected ArrayList<Action> abilities;
	protected ArrayQueue<Command> commandQueue;
	
	public Unit() {
		commandQueue = new ArrayQueue<Command>(8);
	}
	
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
		return time;
	}

	public void setActions(int actions) {
		this.time = actions;
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

	public void setHP(int HP) {
		this.HP = HP;
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
	
	public boolean canPerform(Action action){
		System.out.println("have " + owner.getAvailableBandwidth() +"B, need " + action.bandwidthCost);
		return abilities.contains(action) && (owner.getAvailableBandwidth()>=action.bandwidthCost);
	}
	
	public void doAction(Action action, Tile target, Cool game) {
		System.out.println(this.getClass().getName()+" gonna "+action.name()+" and hast "+this.getOwner().getAvailableBandwidth()+"B left\n");
		switch(action){
		case HACK:
			if(target.getUnit()==null){
				System.err.println("Can't hack the ground, m8\n");
				return;
			} else {
				target.getUnit().setOwner(owner);
			}
			break;
		case ATTACK:
			if(target.getUnit()==null){
				System.err.println("Can't attack the ground, m8\n");
				return;
			} else {
				Unit enemy = target.getUnit();
				enemy.setHP(enemy.getHP()-this.getAttack());
				if (enemy.getHP()<=0) {
					game.getUnitManager().getUnits().remove(enemy);
					target.setUnit(null);;
				}
			}
			break;
		case MOVE:
			if(target.getUnit()!=null){
				System.err.println("there's something in the tile!");
				return;
			}
			game.getWorld().getTile(getXpos(),getYpos()).setUnit(null);
			setXpos(target.getPosx());
			setYpos(target.getPosy());
			target.setUnit(this);
			break;
		default:
			System.err.println("What the fuck are you doing here?!");
		}
		
		owner.setAvailableBandwidth(owner.getAvailableBandwidth()-action.bandwidthCost);
		System.out.println("Now have " + owner.getAvailableBandwidth() + "B remaining\n");
	}
	
	public void queueCommand(Action action, Tile target, Cool game) {
		if(!canPerform(action)){
			return;
		}
		commandQueue.add(new Command(action, target));
	}
	
	public void continueQueue(Cool game){
		for(Command command:commandQueue) {
			
		}
	}

}


package nukey.nova.cool;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import com.badlogic.gdx.utils.Queue;

import nukey.nova.cool.Cool.Player;

public abstract class Unit {
	
	public enum Action{
		MOVE(2,1),
		ATTACK(2,2),
		HACK(10,0);
		
		public int bandwidthCost;
		public int actionCost;
		Action(int bandwidthCost, int actionCost){
			this.bandwidthCost = bandwidthCost;
			this.actionCost = actionCost;
		}
	}
	
	private int xpos,ypos,HP,maxHP,attack,range,speed,ID,time,maxActions;
	private Player owner;
	protected ArrayList<Action> abilities;
	protected Queue<Command> commandQueue;
	
	public Unit() {
		commandQueue = new Queue<Command>(8);
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

	public boolean canPerform(Action action){
		return abilities.contains(action) && (owner.getAvailableBandwidth()>=action.bandwidthCost);
	}
	
	public void doAction(Action action, Tile target, Cool game) {
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
		time -= action.actionCost;
		owner.setAvailableBandwidth(owner.getAvailableBandwidth()-action.bandwidthCost);
	}
	
	public void sendCommand(Command command, Cool game) {
		if(!canReceive(command)) {
			return;
		}
		if(hasTime(command)) {
			doAction(command.action, command.target, game);
		} else {
			commandQueue.addLast(command);
		}
	}
	
	private boolean hasTime(Command command) {
		return time>=command.action.actionCost;
	}

	private boolean canReceive(Command command) {
		return canPerform(command.action) &&
				getOwner().getAvailableBandwidth()>=command.bandwidthCost();
	}

	public void continueQueue(Cool game){
		try { //queue has no empty method.. really? :\
			while(hasTime(commandQueue.first())) {
				Command command  =  commandQueue.removeFirst();
				doAction(command.action, command.target, game);
			}
		} catch (NoSuchElementException e) {
			return;
		}
	}

}


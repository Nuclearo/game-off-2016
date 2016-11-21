package nukey.nova.cool;

public abstract class Unit {
	private int xpos,ypos,HP,attack,range,owner,speed,sprite;
	
	public int getSprite() {
		return sprite;
	}
	
	public int getXpos() {
		return xpos;
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

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
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

	public void setSprite(int sprite) {
		this.sprite = sprite;
	}

	public int getYpos() {
		return ypos;
	}


	public void act() {
		
	}
}


/*


wife try to make the Unit class more substantial, maybe create a couple subclasses like Hacker and Drone.. 

and make Cool.nextTurn() actually do something of value, like process all the units or whatever..

don't be afraid to move stuff into subclasses if Cool becomes cluttered...


*/
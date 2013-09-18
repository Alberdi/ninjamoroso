package org.quegrande.ninjamoroso.model;

public class Moroso extends DynamicEntity {
	
	private boolean scareable = false;
	private boolean gotScared = false;
	private boolean watcher = false;

	public Moroso(float posX, float posY, Game game) {
		super(posX, posY, game);
	}

	public boolean isScareable() {
		return scareable;
	}

	public void setScareable(boolean scareable) {
		this.scareable = scareable;
	}

	public void getScared(Ninja scarer) {
		if(this.isScareable())
			this.runaway(scarer);
	}

	public boolean fight(DynamicEntity rival) {
		int range = 15;
		if(Math.abs(rival.getPosX()-this.getPosX()) < range &&
				Math.abs(rival.getPosY()-this.getPosY()) < range) {
			return true;
		}
		return false;
	}
	
	public void runaway(DynamicEntity rival) {
		setGotScared(true);
		//float rundistance = 400;
		float runfactor = 2;
		//float targetX = rival.getPosX() > this.getPosX() ? this.getPosX()-rundistance : this.getPosX()+rundistance;
		//float targetY = rival.getPosY() > this.getPosY() ? this.getPosY()-rundistance : this.getPosY()+rundistance;
		this.setSpeed(this.getSpeed()*runfactor);
		//if(targetX >= 32*game.getMap().getWidth()) targetX = 0;
		//else if(targetX <= 0) targetX = 0;
		//if(targetY >= 32*game.getMap().getHeight()) targetY = 0;
		//else if(targetY<= 0) targetY = 0;
		this.pathfinding(19, 19);
		this.prepareToDissapear(20, 19);
	}

	public void surrender() {
		//TODO: something
	}

	public boolean gotScared() {
		return gotScared;
	}

	public void setGotScared(boolean gotScared) {
		this.gotScared = gotScared;
	}

	public boolean isWatcher() {
		return watcher;
	}

	public void setWatcher(boolean watcher) {
		this.watcher = watcher;
	}

}

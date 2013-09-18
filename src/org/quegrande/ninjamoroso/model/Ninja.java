package org.quegrande.ninjamoroso.model;

public class Ninja extends DynamicEntity {
	
	private boolean hidden;
	private boolean canShoot = false;
	private int shurikenTimer = 0;

	public Ninja(float posX, float posY, Game game) {
		super(posX, posY, game);
	}

	public boolean scare() {
		if(!isHidden()) return false;
		hidden = false;
		int range = 25;
		for(DynamicEntity p : game.getDynamicEntities()) {
			if(p.getClass() == Moroso.class) {
				Moroso m = (Moroso) p;
				if(Math.abs(m.getPosX()-this.getPosX()) < range &&
						Math.abs(m.getPosY()-this.getPosY()) < range)
					m.getScared(this);
			}
		}
		return true;
	}
	
	public boolean hide() {
		hidden = !isBeingSeenByMorosos();
		return isHidden();
	}
	
	public Shuriken shoot(float targetX, float targetY) {
		if(!canShoot()) return null;
		shurikenTimer = 100;
		Shuriken s = new Shuriken(getPosX(), getPosY(), game);
		s.goTo(targetX, targetY); //TODO: not exactly like this, must continue until collision
		//s.pathfinding((int)targetX/32, (int)targetY/32);
		//s.setStopped(true);
		game.addDynamicEntity(s);
		return s;
	}
	
	public boolean isBeingSeenByMorosos() {
		for(DynamicEntity m : game.getDynamicEntities()) {
			if(!m.getClass().equals(Moroso.class)) continue;
			if (!((Moroso)m).isWatcher()) continue;
			if(m.hasLineOfSightTo(getPosX(), getPosY())) return true;
		}
		return false;
	}
	
	public void updatePos() {
		if(shurikenTimer >=0) shurikenTimer--;
		float tmpPosX = getPosX();
		float tmpPosY = getPosY();
		int tmpFacing = getFacing();
		setStopped(true);
		if(getPosX() != getTargetX()) {
			if(getTargetX() > getPosX()) {
				tmpPosX = getPosX()+getSpeed()>getTargetX() ? getTargetX() : getPosX()+getSpeed();
				tmpFacing = 1;
			} else {
				tmpPosX = getPosX()-getSpeed()<getTargetX() ? getTargetX() : getPosX()-getSpeed();
				tmpFacing = 3;
			}
		}
		else if(getPosY() != getTargetY()) {
			if(getTargetY() > getPosY()) {
				tmpPosY = getPosY()+getSpeed()>getTargetY() ? getTargetY() : getPosY()+getSpeed();
				tmpFacing = 2;
			} else {
				tmpPosY = getPosY()-getSpeed()<getTargetY() ? getTargetY() : getPosY()-getSpeed();
				tmpFacing = 0;
			}
		}
		if(game.getMap().isPixelPassable(tmpPosY+32, tmpPosX+15)) {
			setPosX(tmpPosX);
			setPosY(tmpPosY);
			setFacing(tmpFacing);
			setStopped(false);
			hidden = false;
		}
	}

	public boolean isHidden() {
		return hidden;
	}
	
	public void reveal() {
		hidden = false;
	}

	public boolean canShoot() {
		return canShoot && shurikenTimer <= 0;
	}

	public void setCanShoot(boolean canShoot) {
		this.canShoot = canShoot;
	}
}

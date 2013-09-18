package org.quegrande.ninjamoroso.model;

public class Shuriken extends DynamicEntity {

	public Shuriken(float posX, float posY, Game game) {
		super(posX, posY, 4, game);
	}

	@Override
	public void updatePos() {
		float tmpPosX = getPosX();
		float tmpPosY = getPosY();
		setStopped(true);
		if(getPosX() != getTargetX()) {
			if(getTargetX() > getPosX()) {
				tmpPosX = getPosX()+getSpeed()>getTargetX() ? getTargetX() : getPosX()+getSpeed();
			} else {
				tmpPosX = getPosX()-getSpeed()<getTargetX() ? getTargetX() : getPosX()-getSpeed();
			}
		}
		if(getPosY() != getTargetY()) {
			if(getTargetY() > getPosY()) {
				tmpPosY = getPosY()+getSpeed()>getTargetY() ? getTargetY() : getPosY()+getSpeed();
			} else {
				tmpPosY = getPosY()-getSpeed()<getTargetY() ? getTargetY() : getPosY()-getSpeed();
			}
		}
		if(game.getMap().isPixelPassable(tmpPosY+15, tmpPosX+15)) {
			setStopped(getPosX() == tmpPosX && getPosY() == tmpPosY);
			setPosX(tmpPosX);
			setPosY(tmpPosY);
		}
		if(isStopped()) {
			remove();
		}
	}
	
	public void remove() {
		game.getDynamicEntities().remove(this);
	}
	
	
}

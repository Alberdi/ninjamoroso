package org.quegrande.ninjamoroso.view;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.quegrande.ninjamoroso.model.Game;
import org.quegrande.ninjamoroso.model.Ninja;
import org.quegrande.ninjamoroso.model.Shuriken;

public class SlickNinja extends SlickDynamicEntity {

	private final static String imgSprite = "ninja2";

	private Ninja modNinja;
	private Sound scareSound;
	private Animation[] animHide = new Animation[4];
	
	public SlickNinja(Game game) throws SlickException {
		super(imgSprite, 15);
		SpriteSheet sheet = new SpriteSheet("res/images/" +imgSprite+".png",32,32);
		
		for (int dir=0; dir < 4; dir++){
			Animation tmp = new Animation();
			tmp.setAutoUpdate(true);
			for (int frame=0;frame<2;frame++) {
				tmp.addFrame(sheet.getSprite(frame+15, dir), 300);
			}
			animHide[dir] = tmp;
		}
		
		modNinja = new Ninja(360,180,game);
		scareSound = new Sound("res/sounds/boo.ogg");
	}
	
	public void move(int direction){
		switch (direction) {
			case 0: // up
				modNinja.goTo(modNinja.getPosX(), 0); break;
			case 2: // down
				modNinja.goTo(modNinja.getPosX(), 608); break;
			case 1: // right
				modNinja.goTo(608, modNinja.getPosY()); break;
			case 3: // left
				modNinja.goTo(0, modNinja.getPosY()); break;
		}
	}

	public Ninja getModel() {
		return modNinja;
	}
	
	public void stop() {
		modNinja.stop();
	}
	
	public SlickShuriken shoot(float x, float y) throws SlickException {
		modNinja.reveal();
		Shuriken s = modNinja.shoot(x, y);
		if(s == null) return null;
		return new SlickShuriken(s);
	}
	
	public void update(GameContainer container, int delta){
		if (!modNinja.isHidden()){
			setAnimation(getAnimationDirs()[getModel().getFacing()]);
			modNinja.updatePos();
		}	
	}

	public void hide() {
		if (modNinja.isHidden()) modNinja.reveal();
		else if (modNinja.hide()) {
			setAnimation(animHide[modNinja.getFacing()]);	
		}
	}

	public void scare() {
		if(modNinja.isHidden()) {
			scareSound.play();
			modNinja.scare();
		}
	}
}

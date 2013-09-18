package org.quegrande.ninjamoroso.view;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.quegrande.ninjamoroso.model.DynamicEntity;
import org.quegrande.ninjamoroso.model.Shuriken;

public class SlickShuriken extends SlickDynamicEntity {
	
	private Shuriken modShuriken;
	private Image image;
	
	public SlickShuriken(Shuriken s) throws SlickException {
		super("shuriken",0);
		modShuriken = s;
		image = new Image("res/images/shuriken.png");
	}
	
	public void render(GameContainer container, Graphics g){
		g.drawImage(image, modShuriken.getPosX(), modShuriken.getPosY());
	}
	
	public void update(GameContainer container, int delta, SlickMain main) {
		if(!modShuriken.isStopped()) {
			image.rotate(20.0f);
			modShuriken.updatePos();
		} else main.removeDynamicEntity(this);
	}

	@Override
	public DynamicEntity getModel() {
		return modShuriken;
	}

}

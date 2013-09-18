package org.quegrande.ninjamoroso.view;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.quegrande.ninjamoroso.model.DynamicEntity;

public abstract class SlickDynamicEntity {
	
	private Animation animation;
	private Animation[] animationDirs = new Animation[4];
	private SlickSpeechBalloon ssb;
	
	public SlickDynamicEntity(String sprite, int frames) throws SlickException {
		SpriteSheet sheet = new SpriteSheet("res/images/" +sprite+".png",32,32);
		setAnimation(new Animation());
		
		for (int dir=0; dir < 4; dir++){
			Animation tmp = new Animation();
			tmp.setAutoUpdate(true);
			for (int frame=0;frame<frames;frame++) {
				if (sheet.getHeight() == 32){
				tmp.addFrame(sheet.getSprite(frame,0), 150);
				tmp.getImage(frame).rotate(90.0f * dir);
				}else {
					tmp.addFrame(sheet.getSprite(frame, dir), 150);
				}
			}
			getAnimationDirs()[dir] = tmp;
		}
		setAnimation(getAnimationDirs()[0]);
	}
	
	public void render(GameContainer container, Graphics g){
		g.drawAnimation(getAnimation(), getModel().getPosX(), getModel().getPosY());
		if(ssb != null) ssb.render(container, g);
	}
	
	public void update(GameContainer container, int delta, SlickMain main) {
		setAnimation(getAnimationDirs()[getModel().getFacing()]);
		getModel().updatePos();
	}
	
	public abstract DynamicEntity getModel();

	public Animation getAnimation() {
		return animation;
	}

	protected void setAnimation(Animation animation) {
		this.animation = animation;
	}

	public Animation[] getAnimationDirs() {
		return animationDirs;
	}
	
	
	public void talk(String text) throws SlickException {
		if (!text.equalsIgnoreCase("")){
			getModel().talk(text);
			ssb = new SlickSpeechBalloon(getModel().getBalloon(), Color.white);
		} else
			ssb = null;
	}
	
	public void talk(String text, int width, int heigth) throws SlickException {
		if (!text.equalsIgnoreCase("")){
			getModel().talk(text);
			ssb = new SlickSpeechBalloon(getModel().getBalloon(), Color.white, width, heigth);
		} else
			ssb = null;
	}

}

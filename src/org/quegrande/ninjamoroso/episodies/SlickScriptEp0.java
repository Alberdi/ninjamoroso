package org.quegrande.ninjamoroso.episodies;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.quegrande.ninjamoroso.view.SlickMain;

public class SlickScriptEp0 extends SlickScript  {
	
	private SlickMain main;
	private Image splash;
	private int progress = 0;

	public SlickScriptEp0 (SlickMain main) throws SlickException {
		this.main = main;
	}

	@Override
	public void init() throws SlickException {
		splash = new Image("/res/images/start.png");
		main.setSplash(splash);
	}

	@Override
	public void update() throws SlickException {
		progress++;
		
	}

	@Override
	public boolean ended() throws SlickException {
		if (progress >= 200) {
			main.setSplash(null);
			return true;
		}
		return false;
	}
}

package org.quegrande.ninjamoroso.episodies;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.quegrande.ninjamoroso.view.SlickMain;

public class SlickScriptEp99 extends SlickScript  {
	
	private SlickMain main;
	private Image splash;

	public SlickScriptEp99 (SlickMain main) throws SlickException {
		this.main = main;
	}

	@Override
	public void init() throws SlickException {
		splash = new Image("/res/images/end.png");
		main.setSplash(splash);
	}

	@Override
	public void update() throws SlickException {	}

	@Override
	public boolean ended() throws SlickException { return false; }
}

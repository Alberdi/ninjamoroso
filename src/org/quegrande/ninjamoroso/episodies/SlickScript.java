package org.quegrande.ninjamoroso.episodies;

import org.newdawn.slick.SlickException;

public abstract class SlickScript {
	
	public abstract void init() throws SlickException;
	public abstract void update() throws SlickException;
	public abstract boolean ended() throws SlickException;

}

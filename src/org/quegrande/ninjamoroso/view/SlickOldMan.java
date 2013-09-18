package org.quegrande.ninjamoroso.view;

import org.newdawn.slick.SlickException;
import org.quegrande.ninjamoroso.model.DynamicEntity;
import org.quegrande.ninjamoroso.model.Game;
import org.quegrande.ninjamoroso.model.Moroso;

public class SlickOldMan extends SlickDynamicEntity  {
	
	private Moroso modMoroso;
	
	public SlickOldMan(Game game) throws SlickException {
		super("oldman", 3);
		modMoroso = new Moroso(0,0,game);
	}

	@Override
	public DynamicEntity getModel() {
		return modMoroso;
	}

}

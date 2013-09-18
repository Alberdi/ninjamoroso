package org.quegrande.ninjamoroso.view;

import java.util.List;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.quegrande.ninjamoroso.model.DynamicEntity;
import org.quegrande.ninjamoroso.model.Game;
import org.quegrande.ninjamoroso.model.Moroso;

public class SlickMoroso extends SlickDynamicEntity {
	
	private Moroso modMoroso;
	private Sound scaredSound;
	private boolean gotScaredSoundPlayed = false;
	
	
	public SlickMoroso(Game game, String name) throws SlickException {
		super(name, 3);
		Random r = new Random();
		List<Integer[]> doors = game.getMap().getDoors();
		Integer[] i = doors.get(r.nextInt(doors.size()));
		modMoroso = new Moroso(i[1]*32,i[0]*32,game);
		scaredSound = new Sound("res/sounds/aah.ogg");
	}

	public Moroso getModMoroso() {
		return modMoroso;
	}

	@Override
	public DynamicEntity getModel() {
		return modMoroso;
	}
	
	public void update(GameContainer container, int delta, SlickMain main) {
		super.update(container, delta, main);
		if(!gotScaredSoundPlayed  && modMoroso.gotScared()) {
			scaredSound.play();
			gotScaredSoundPlayed = true;
		}
	}


}

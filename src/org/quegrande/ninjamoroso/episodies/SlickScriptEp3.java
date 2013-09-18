package org.quegrande.ninjamoroso.episodies;

import org.newdawn.slick.SlickException;
import org.quegrande.ninjamoroso.view.SlickMain;
import org.quegrande.ninjamoroso.view.SlickMoroso;
import org.quegrande.ninjamoroso.view.SlickNinja;
import org.quegrande.ninjamoroso.view.SlickOldMan;

public class SlickScriptEp3 extends SlickScript {

	private SlickMain main;
	private SlickNinja ninja;
	private SlickOldMan oldMan;
	
	private int progress = 0;
	private int timer2 = 0;
	private int creeping = 0;
	private SlickMoroso moroso1;

	public SlickScriptEp3(SlickMain main) throws SlickException {
		this.main = main;
		this.ninja = main.getNinja();
		this.oldMan = main.getOldMan();
	}

	@Override
	public void init() throws SlickException {
		//oldMan.getModel().setPosX(19*32);
		//oldMan.getModel().setPosY(19*32);
		oldMan.getModel().setStopped(true);
		oldMan.getModel().pathfinding(12,13);
		
		moroso1 = new SlickMoroso(main.getGame(), "moroso_1");
		moroso1.getModMoroso().setWatcher(true);
		main.addDynamicEntity(moroso1);
		
		moroso1.getModel().setPosX(6*32);
		moroso1.getModel().setPosY(18*32);
		moroso1.getModel().setStopped(true);
		moroso1.getModel().pathfinding(2,19);
	}

	@Override
	public void update() throws SlickException {
		// OldMan greeting.
		if(progress == 0 && oldMan.getModel().getPath().isEmpty() && oldMan.getModel().isAtTarget()) {
			oldMan.getModel().setFacing(2);
			oldMan.talk("Tengo un trabajo para ti.");
			progress++;
		}
		
		// Moroso1 path.
		if(moroso1.getModel().getPath().isEmpty() && moroso1.getModel().isAtTarget()) {
			if(moroso1.getModel().getPosX() == 19*32 && moroso1.getModel().getPosY() == 2*32)
				moroso1.getModel().pathfinding(5,5);
			else if(moroso1.getModel().getPosX() == 5*32 && moroso1.getModel().getPosY() == 5*32)
				moroso1.getModel().pathfinding(18,18);
			else if(moroso1.getModel().getPosX() == 18*32 && moroso1.getModel().getPosY() == 18*32)
				moroso1.getModel().pathfinding(2,19);
		}
		
		// Quest giving.
		if(progress == 1 &&
				ninja.getModel().hasLineOfSightTo(oldMan.getModel().getPosX(), oldMan.getModel().getPosY())) {
			ninja.getModel().stop();
			if(timer2 == 0) timer2  = 350; 
			if(--timer2 > 0) {
				oldMan.talk("Creo que cumples todos los requisitos para ser un verdadero Ninja del Moroso.");
			} else {
				oldMan.talk("Para probarlo, sigue de cerca a esa chica que pasea hasta que prometa devolverme mi dinero, lo de asustarla no funcionara dos veces seguidas.");
				progress++;
				timer2 = 350;
			}
		}
		
		// Let's go creeping.
		if(progress == 2) {
			if(--timer2 <= 0) oldMan.talk("");
			if(ninja.getModel().hasLineOfSightTo(moroso1.getModel().getPosX(), moroso1.getModel().getPosY())) {
				if(creeping++ >= 1000) {
					progress++;
					moroso1.getModMoroso().setScareable(true);
					moroso1.getModMoroso().getScared(ninja.getModel());
					moroso1.talk("Vale, vale, pagare!");
					oldMan.talk("Jajaja!");
				}
			} else if(creeping-- <= 0) creeping = 0;
		}
	}

	@Override
	public boolean ended() throws SlickException {
		if(progress >= 3) progress++;
		if(progress >= 600) {
			oldMan.talk("");
			moroso1.talk("");
			main.removeDynamicEntity(moroso1);
		}
		return progress >= 600;
	}
	
}

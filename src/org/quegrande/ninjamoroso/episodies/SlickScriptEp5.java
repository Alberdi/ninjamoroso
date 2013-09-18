package org.quegrande.ninjamoroso.episodies;

import java.util.*;

import org.newdawn.slick.SlickException;
import org.quegrande.ninjamoroso.view.SlickMain;
import org.quegrande.ninjamoroso.view.SlickMoroso;
import org.quegrande.ninjamoroso.view.SlickNinja;
import org.quegrande.ninjamoroso.view.SlickOldMan;

public class SlickScriptEp5 extends SlickScript {
	
	private SlickMain main;
	private SlickNinja ninja;
	private SlickOldMan oldMan;
	private List<SlickMoroso> clones;
	private SlickMoroso moroso;
	
	private int progress = 0;

	public SlickScriptEp5(SlickMain main) throws SlickException {
		this.main = main;
		this.ninja = main.getNinja();
		this.oldMan = main.getOldMan();
		clones = new ArrayList<SlickMoroso>();
	}

	@Override
	public void init() throws SlickException {
		moroso = new SlickMoroso(main.getGame(), "moroso_4");
		clones.add(moroso);
		for(int i=0; i<24; i++) {
			SlickMoroso c = new SlickMoroso(main.getGame(), "moroso_2");
			clones.add(c);
		}
		for(SlickMoroso m : clones) {
			main.addDynamicEntity(m);
			m.getModMoroso().setWatcher(true);
			m.getModel().setPosX(16*32);
			m.getModel().setPosY(12*32);
			m.getModel().setStopped(true);
			m.getModel().pathfinding(12,16);
		}
		oldMan.getModel().setStopped(true);
		oldMan.getModel().pathfinding(12,13);
	}

	private int timer2 = 0;
	
	@Override
	public void update() throws SlickException {
		// OldMan greeting.
		if(progress == 0 && oldMan.getModel().getPath().isEmpty() && oldMan.getModel().isAtTarget()) {
			oldMan.getModel().setFacing(2);
			oldMan.talk("Los clones nos han invadido.");
			progress++;
		}
		
		// Quest giving.
		if(progress == 1 &&
				ninja.getModel().hasLineOfSightTo(oldMan.getModel().getPosX(), oldMan.getModel().getPosY())) {
			ninja.getModel().stop();
			if(timer2 == 0) timer2  = 350; 
			if(--timer2 > 0) {
				oldMan.talk("Debe de haber un 3x2 en la tienda de clones, porque esto no es normal.");
			} else {
				oldMan.talk("Intenta espantar al jefe para desfacer la gravedad de este entuerto.");
				for(SlickMoroso m : clones) m.getModMoroso().setScareable(true);
				progress++;
				timer2 = 350;
			}
		}
		
		// Clones go to random doors.
		if(progress <= 2)
			for(SlickMoroso m : clones)
				if(m.getModel().getPath().isEmpty() && m.getModel().isAtTarget())
					m.getModel().goToRandomDoor();
					
		// OldMan should shut up soon.
		if(progress == 2)
			if(--timer2 <= 0) { oldMan.talk(""); timer2 = 0; }

		// Try to scare the leader.
		if(progress == 2 && moroso.getModMoroso().gotScared()) {

			progress++;
			clones.remove(moroso);
			moroso.getModel().unDissapear();
			for(SlickMoroso m : clones)
				m.getModMoroso().getScared(ninja.getModel());
			moroso.getModel().stop();
			moroso.getModel().pathfinding(1, 19);
		}

		// Leader confesses.
		if(progress == 3 &&
				moroso.getModel().getPath().isEmpty() && moroso.getModel().isAtTarget()) {
			if(timer2 == 0) timer2  = 350; 
			if(--timer2 > 0) {
				moroso.talk("Vaya susto!");
			} else {
				moroso.talk("Y ademas estos clones del Carrefour me dejaron solo.");
				progress++;
				timer2 = 350;
			}
		}
		
		// Thank you very much, as always.
		if(progress == 4) {
			if(--timer2 <= 0) {
				moroso.talk("");
				moroso.getModMoroso().getScared(ninja.getModel());
				oldMan.talk("Jajaja, buen trabajo!");
				progress++;
			}
		}
	}

	@Override
	public boolean ended() throws SlickException {
		if(progress >= 5) progress++;
		if(progress >= 600) {
			oldMan.talk("");
			main.removeDynamicEntity(moroso);
			for(SlickMoroso m : clones) main.removeDynamicEntity(m);
		}
		return progress >= 600;
	}

}

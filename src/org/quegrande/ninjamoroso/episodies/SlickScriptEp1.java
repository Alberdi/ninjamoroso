package org.quegrande.ninjamoroso.episodies;

import org.newdawn.slick.SlickException;
import org.quegrande.ninjamoroso.view.SlickMain;
import org.quegrande.ninjamoroso.view.SlickMoroso;
import org.quegrande.ninjamoroso.view.SlickNinja;
import org.quegrande.ninjamoroso.view.SlickOldMan;

// Episode 1: Scaring tutorial.
public class SlickScriptEp1 extends SlickScript  {
	
	private SlickMain main;
	private SlickNinja ninja;
	private SlickOldMan oldMan;
	private SlickMoroso moroso1;

	public SlickScriptEp1(SlickMain main) throws SlickException {
		this.main = main;
		this.ninja = main.getNinja();
		this.oldMan = main.getOldMan();
	}
	
	public void init() throws SlickException {
		moroso1 = new SlickMoroso(main.getGame(), "moroso_1");
		moroso1.getModMoroso().setWatcher(true);
		main.addDynamicEntity(moroso1);
		
		ninja.getModel().setPosX(2*32);
		ninja.getModel().setPosY(2*32);
		ninja.getModel().setTargetX(2*32);
		ninja.getModel().setTargetY(4*32);
		
		oldMan.getModel().setPosX(19*32);
		oldMan.getModel().setPosY(19*32);
		oldMan.getModel().setStopped(true);
		oldMan.getModel().pathfinding(12,13);
		
		moroso1.getModel().setPosX(6*32);
		moroso1.getModel().setPosY(18*32);
		moroso1.getModel().setStopped(true);
		moroso1.getModel().pathfinding(3, 2);
	}

	private int timer, timer2 = 0;
	private int progress = 0;
	public void update() throws SlickException {
		// OldMan greeting.
		if(progress == 0 && oldMan.getModel().getPath().isEmpty() && oldMan.getModel().isAtTarget()) {
			oldMan.getModel().setFacing(2);
			oldMan.talk("Hola, amigo.");
		}
		
		// Moroso1 path.
		if(moroso1.getModel().getPath().isEmpty() && moroso1.getModel().isAtTarget()) {			
			if(moroso1.getModel().getPosX() == 2*32 && moroso1.getModel().getPosY() == 3*32) {
				moroso1.getModel().pathfinding(2, 2);
				timer = 0;
			}
			else if(moroso1.getModel().getPosX() == 2*32 && moroso1.getModel().getPosY() == 2*32
					&& timer++ >= 400)
				moroso1.getModel().pathfinding(18, 6);
			else if(moroso1.getModel().getPosX() == 6*32 && moroso1.getModel().getPosY() == 18*32) {
				moroso1.getModel().pathfinding(17, 6);
				timer = 0;
			}
			else if(moroso1.getModel().getPosX() == 6*32 && moroso1.getModel().getPosY() == 17*32
					&& timer++ >= 400)
				moroso1.getModel().pathfinding(3, 2);
		}
		
		// Invisible tutorial.
		if(progress == 0 &&
				ninja.getModel().hasLineOfSightTo(oldMan.getModel().getPosX(), oldMan.getModel().getPosY())) {
			ninja.getModel().stop();
			if(timer2 == 0) timer2 = 350; 
			if(--timer2 > 0) {
				oldMan.talk("Tengo una tarea para ti. Hay una chica que me la ha jugado varias veces, " +
					"podrias darle un susto de mi parte?");
			} else {
				oldMan.talk("Solo tienes que ponerte invisible pulsando " +
						"espacio sin que ella te vea. Prueba ahora.");
				progress++;
			}
			moroso1.getModMoroso().setScareable(true);
		}
		
		// Scare tutorial.
		if(progress == 1 && ninja.getModel().isHidden() &&
				ninja.getModel().hasLineOfSightTo(oldMan.getModel().getPosX(), oldMan.getModel().getPosY())) {
			progress++;
			oldMan.talk("Estupendo! Ahora escondete por donde vaya a pasar y, cuando este cerca, presiona" +
					" la tecla E para asustarla.");
			moroso1.getModMoroso().setScareable(true);
		}

		// She runs away.
		if(progress == 2 && moroso1.getModMoroso().gotScared()) {
			progress++;
			ninja.talk("Has recogido una bolsa que se le ha caido al suelo.");
			ninja.getModel().setCanShoot(true);
			oldMan.talk("");
		}
		
		// Congratulations.
		if(progress == 3 && ninja.getModel().hasLineOfSightTo(oldMan.getModel().getPosX(), oldMan.getModel().getPosY())) {
			progress++;
			ninja.stop();
			ninja.talk("");
			oldMan.talk("Muchas gracias, caballero. La gravedad del asunto requeria esas medidas.");
		}
	}
	
	public boolean ended() throws SlickException {
		if(progress >= 4) progress++;
		if(progress >= 600) {
			oldMan.talk("");
			main.removeDynamicEntity(moroso1);
		}
		return progress >= 600;
	}
	
}

package org.quegrande.ninjamoroso.episodies;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import org.quegrande.ninjamoroso.model.DynamicEntity;
import org.quegrande.ninjamoroso.model.Shuriken;
import org.quegrande.ninjamoroso.view.SlickMain;
import org.quegrande.ninjamoroso.view.SlickNinja;
import org.quegrande.ninjamoroso.view.SlickOldMan;

// Episode 2: Shooting tutorial.
public class SlickScriptEp2 extends SlickScript {
	
	private SlickMain main;
	private SlickNinja ninja;
	private SlickOldMan oldMan;
	
	private int progress = 0;

	public SlickScriptEp2(SlickMain main) throws SlickException {
		this.main = main;
		this.ninja = main.getNinja();
		this.oldMan = main.getOldMan();
	}

	@Override
	public void init() throws SlickException {
		ninja.getModel().setCanShoot(true);
//		oldMan.getModel().setPosX(19*32);
//		oldMan.getModel().setPosY(19*32);
		oldMan.getModel().setStopped(true);
		oldMan.getModel().pathfinding(15,19);
	}

	private int timer2 = 0;
	@Override
	public void update() throws SlickException {
		// OldMan greeting.
		if(progress == 0 && oldMan.getModel().getPath().isEmpty() && oldMan.getModel().isAtTarget()) {
			oldMan.getModel().setFacing(2);
			oldMan.talk("Te quiero mostrar el truco del almendruco.");
			progress++;
		}
		
		// Setup shuriken tutorial.
		if(progress == 1 &&
				ninja.getModel().hasLineOfSightTo(oldMan.getModel().getPosX(), oldMan.getModel().getPosY())) {
			ninja.getModel().stop();
			if(timer2 == 0) timer2 = 350; 
			if(--timer2 > 0) {
				oldMan.talk("Veo que has conseguido una bolsa de shurikens de esa mujer. Sabes que son algo especiales?");
			} else {
				oldMan.talk("Intenta dispararle al barril de abajo desde esta zona pulsando el boton del raton.");
				progress++;
			}
		}
		
		// Angle shoot tutorial.
		if(progress == 2) {
			if((int)ninja.getModel().getPosX()/32 <=16 || (int)ninja.getModel().getPosY()/32 >=16)
				oldMan.talk("Ponte justo a mi izquierda.");
			else {
				ninja.getModel().stop();
				oldMan.talk("Intenta dispararle al barril desde esta zona pulsando el boton del raton.");
				for(DynamicEntity de : main.getGame().getDynamicEntities()) {
					if(!de.getClass().equals(Shuriken.class)) continue;
					if((int)de.getPosX()/32 == 0 &&
							((int)de.getPosY()/32 == 19) || (int)de.getPosY()/32 == 18) {
						progress++;
						TiledMap aux = main.getSlickMap().getTiledMap();
						aux.setTileId(0, 18, 7, aux.getTileId(0, 18, 3));
						oldMan.talk("Viste que efecto mas chulo? No es sencillo, pero una vez dominado es la leche.");
						break;
					}
				}
			}
		}

		
	}

	@Override
	public boolean ended() throws SlickException {
		if(progress >= 3) progress++;
		if(progress >= 600) oldMan.talk("");
		return progress >= 600;
	}

}

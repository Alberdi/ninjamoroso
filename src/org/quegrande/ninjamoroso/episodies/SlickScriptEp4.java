package org.quegrande.ninjamoroso.episodies;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.SlickException;
import org.quegrande.ninjamoroso.view.SlickDynamicEntity;
import org.quegrande.ninjamoroso.view.SlickMain;
import org.quegrande.ninjamoroso.view.SlickMoroso;
import org.quegrande.ninjamoroso.view.SlickNinja;
import org.quegrande.ninjamoroso.view.SlickOldMan;

public class SlickScriptEp4 extends SlickScript {
		
		private SlickMain main;
		private SlickNinja ninja;
		private SlickOldMan oldMan;
		private SlickMoroso morosa;
		
		private int timer2 = 0;
		
		private boolean[] checkTalk = new boolean[5];
		private int progress = 0;
		
		private List<SlickDynamicEntity> neighbors;
		private List<SlickMoroso> atHome;

		public SlickScriptEp4(SlickMain main) throws SlickException {
			this.main = main;
			this.ninja = main.getNinja();
			this.oldMan = main.getOldMan();
		}

		@Override
		public void init() throws SlickException {
			ninja.getModel().setCanShoot(true);
			oldMan.getModel().setStopped(true);
			oldMan.getModel().pathfinding(15,19);
			morosa = new SlickMoroso(main.getGame(), "moroso_1");
			//Mover a la chica hasta una casa
			morosa.getModel().setPosX(9*32);
			morosa.getModel().setPosY(2*32);
			morosa.getModel().stop();
			morosa.getModel().pathfinding(2, 9);
			neighbors = new ArrayList<SlickDynamicEntity>();
			neighbors.add(new SlickMoroso(main.getGame(), "moroso_2"));
			neighbors.add(new SlickMoroso(main.getGame(), "moroso_3"));
			neighbors.add(new SlickMoroso(main.getGame(), "moroso_5"));
			neighbors.add(new SlickMoroso(main.getGame(), "moroso_6"));
			neighbors.add(new SlickMoroso(main.getGame(), "moroso_4"));
			for (SlickDynamicEntity sde : neighbors) {
				sde.getModel().setPosX(2*32);
				sde.getModel().setPosY(10*32);
				sde.getModel().stop();
				sde.getModel().pathfinding(10, 2);
				main.addDynamicEntity(sde);
			}
			atHome = new ArrayList<SlickMoroso>();
			
		}
		
		public void update() throws SlickException {
			// OldMan greeting.
			if(progress == 0 && oldMan.getModel().getPath().isEmpty() && oldMan.getModel().isAtTarget()) {
				oldMan.getModel().setFacing(2);
				oldMan.talk("Vai sendo hora de ir para a casa");
				progress++;
			}
			
			// Despedida
			if(progress == 1 &&
			  ninja.getModel().hasLineOfSightTo(oldMan.getModel().getPosX(), oldMan.getModel().getPosY())) {
				if(timer2 == 0) timer2 = 350; 
				if(--timer2 > 0) {
					oldMan.talk("Gracias, me has ayudado mucho. Espero que nos volvamos a ver pronto. Toma mi telefono");
					ninja.getModel().stop();
				} else {
					progress++;
					oldMan.talk("");
					ninja.talk("Has anotado un numero en tu agenda");
					oldMan.getModel().pathfinding(11, 2);
				}
			}
			
			// el viejo va a la taberna
			if(progress == 2){
				if(oldMan.getModel().getPosX() == 2*32 && oldMan.getModel().getPosY() == 11*32) {
					oldMan.getModel().pathfinding(10, 2);
					ninja.talk("");
					progress++;
				}
			}
			
			// Salen los vecinos
			if (progress == 3 &&
					ninja.getModel().hasLineOfSightTo(oldMan.getModel().getPosX(), oldMan.getModel().getPosY())){
				if(timer2 == 0) timer2 = 50; 
				if(--timer2 > 0) {} 
				else {
					progress++;
					for(SlickDynamicEntity sde : neighbors) {
						sde.getModel().setStopped(false);
						sde.getModel().pathfinding(10, 2);
					}
					ninja.getModel().stop();
					ninja.getModel().pathfinding(19, 1);
				} 
			}
			
			if (progress == 4){
				ninja.getModel().setStopped(false);
				if(timer2 == 0) {timer2 = 250; }
				timer2--;
				if(timer2 > 200) {neighbors.get(0).getModel().pathfinding(18, 2); }
				if((200 > timer2) && (timer2 > 150)) {neighbors.get(1).getModel().pathfinding(17, 2); }
				if((150 > timer2) && (timer2 > 100)) {neighbors.get(2).getModel().pathfinding(16, 2); }
				if((100 > timer2) && (timer2 > 50)) {neighbors.get(3).getModel().pathfinding(15, 2); }
				if((50 > timer2) && (timer2 > 0)) {neighbors.get(4).getModel().pathfinding(14, 2); progress++;timer2=0;}
			}
			
			if (progress == 5){
				timer2--;
				if(timer2 <= 0) {timer2 = 250; }
				if(timer2 > 200) {neighbors.get(0).talk("Dios", 64, 32); checkTalk[0] = true; }
				if((200 > timer2) && (timer2 > 150)) {neighbors.get(1).talk("que", 64, 32); checkTalk[1] = true;}
				if((150 > timer2) && (timer2 > 100)) {neighbors.get(2).talk("miedo", 64, 32); checkTalk[2] = true;}
				if((100 > timer2) && (timer2 > 50)) {neighbors.get(3).talk("he", 64, 32); checkTalk[3] = true;}
				if((50 > timer2) && (timer2 > 0)) {neighbors.get(4).talk("pasado", 64, 32); checkTalk[4] = true;}
						
				if (checkTalk[0] == true && checkTalk[1] == true 
						&& checkTalk[2] == true && checkTalk[3] == true && checkTalk[4] == true){
					progress++;
				}
			}
			
			// El viejo se va para arriba
			if (progress == 6) {
				if(timer2 == 0) timer2 = 20; 
				if(--timer2 > 0) {oldMan.getModel().pathfinding(11, 2);} 
				else {
					for (SlickDynamicEntity sde : neighbors) { sde.talk(""); }
					oldMan.getModel().pathfinding(1, 19);
					progress++;
				}
			}
			
			// El viejo esta arriba
			if (progress == 7 &&
					oldMan.getModel().getPosX() == 19*32 && oldMan.getModel().getPosY() == 1*32) {
				oldMan.talk("... Que miedo ...");
				if(ninja.getModel().hasLineOfSightTo(oldMan.getModel().getPosX(), oldMan.getModel().getPosY())){
					if(timer2 == 0) timer2 = 350; 
					if(--timer2 > 0) { oldMan.talk("Esa chica ha intentado pegarme"); ninja.stop(); } 
					else { oldMan.talk("Localizala por mi debe de estar en alguna casa"); progress++; }
				}
			}
			
			// Envia a los vecinos a casa.....
			if (progress == 8) {
				for(SlickDynamicEntity sde : neighbors){
					sde.getModel().pathfinding(11, 2);
				}
				neighbors.get(4).getModel().pathfinding(12, 16);
				progress++;
			}
			
			if (progress >= 9) {
				for(SlickDynamicEntity sde : neighbors) {
					if(atHome.contains(sde)) continue;
					if(sde.getModel().getPath().isEmpty() && sde.getModel().isAtTarget()) {
						atHome.add((SlickMoroso)sde);
						sde.getModel().stop();
						sde.getModel().pathfinding((int)(sde.getModel().getPosY()/32)-1,
								(int)(sde.getModel().getPosX()/32));
					}
				}
			}
			
			if (progress == 9) { 
				//if(--timer2 == 0) ninja.talk("");
				if ((int)ninja.getModel().getPosX()/32 == 9 && (int)ninja.getModel().getPosY()/32 == 3){
					ninja.talk("Has encontrado a la muchacha");
					progress++;
				}
				else if (((int)ninja.getModel().getPosX()/32 == 10 && (int)ninja.getModel().getPosY()/32 == 18) ||
					((int)ninja.getModel().getPosX()/32 == 6 && (int)ninja.getModel().getPosY()/32 == 18) ||
					((int)ninja.getModel().getPosX()/32 == 2 && (int)ninja.getModel().getPosY()/32 == 11) ||
					((int)ninja.getModel().getPosX()/32 == 2 && (int)ninja.getModel().getPosY()/32 == 3)) {
					oldMan.talk("Cuando la encuentres avisame");
					ninja.talk("Parece que no hay nadie");
				} else {
					ninja.talk("");
				}
			}
			
			if (progress == 10) {
				if(ninja.getModel().hasLineOfSightTo(oldMan.getModel().getPosX(), oldMan.getModel().getPosY())){
					oldMan.talk("Muchas gracias, hare que reciba su merecido");
					ninja.talk("");
					ninja.stop();
					progress++;
				}
			}
		}

		@Override
		public boolean ended() throws SlickException {
			if(progress >= 11) progress++;
			if(progress >= 600) {
				oldMan.talk("");
				main.removeDynamicEntity(morosa);
				for(SlickDynamicEntity m : neighbors) main.removeDynamicEntity(m);
			}
			return progress >= 600;
		}

}

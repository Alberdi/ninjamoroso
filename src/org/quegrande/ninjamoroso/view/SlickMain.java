package org.quegrande.ninjamoroso.view;

import java.util.*;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.quegrande.ninjamoroso.episodies.*;
import org.quegrande.ninjamoroso.model.Game;
import org.quegrande.ninjamoroso.view.SlickMap;
 
public class SlickMain extends BasicGame {
	
	private SlickNinja ninja;
	private SlickOldMan oldMan;
	private List<SlickDynamicEntity> dynamicEntities;
	private SlickMap map;
	private Game game;
	private Music theme;
	private Image splashImage;
	
	private List<SlickScript> slickScripts;
	private SlickScript curScript;
	
	public SlickMain() {
		super("El Ninja del Moroso");
	}
	
	public void init(GameContainer container) throws SlickException {
		container.setShowFPS(false);
		container.setVSync(true);
		map = new SlickMap("res/tiles/city_20x20.tmx");
		game = new Game(map.getMap());
		ninja = new SlickNinja(game);
		oldMan = new SlickOldMan(game);
		dynamicEntities = new ArrayList<SlickDynamicEntity>();
		dynamicEntities.add(oldMan);

		splashImage = null;
		theme = new Music("res/sounds/maintheme.ogg");
		theme.loop();

		slickScripts = new ArrayList<SlickScript>();
		slickScripts.add(new SlickScriptEp0(this));
		slickScripts.add(new SlickScriptEp1(this));
		slickScripts.add(new SlickScriptEp2(this));
		slickScripts.add(new SlickScriptEp3(this));
		slickScripts.add(new SlickScriptEp4(this));
		slickScripts.add(new SlickScriptEp5(this));
		
		slickScripts.add(new SlickScriptEp99(this));
		curScript = slickScripts.remove(0);
		curScript.init();
	}
	
	public void update(GameContainer container, int delta) throws SlickException {
		Input in = container.getInput();
		if (in.isKeyDown(Input.KEY_ESCAPE)) {container.exit();}
		if (in.isKeyPressed(Input.KEY_P)) { container.setPaused(!container.isPaused()); }
		if(container.isPaused()) return;

		if(curScript.ended() && !slickScripts.isEmpty()) {
			curScript = slickScripts.remove(0);
			curScript.init();
		}
		curScript.update();
		
		if (in.isKeyDown(Input.KEY_A)) {ninja.move(3);}
		if (in.isKeyDown(Input.KEY_D)) {ninja.move(1);}
		if (in.isKeyDown(Input.KEY_W)) {ninja.move(0);}
		if (in.isKeyDown(Input.KEY_S)) {ninja.move(2);}
		if (in.isKeyPressed(Input.KEY_SPACE)) {ninja.stop(); ninja.hide();}
		if (in.isKeyPressed(Input.KEY_E)) {ninja.stop(); ninja.scare();}
		
		if(in.isKeyPressed(Input.KEY_M)) {
			if(theme.playing()) theme.stop();
			else theme.loop();
		}

		if(in.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			SlickShuriken shuriken = ninja.shoot(in.getMouseX(), in.getMouseY());
			if(shuriken != null) this.addDynamicEntity(shuriken);
		}
		
		ninja.update(container, delta);
		List<SlickDynamicEntity> aux = new ArrayList<SlickDynamicEntity>(dynamicEntities);
		for(SlickDynamicEntity de : aux) de.update(container, delta, this);
	}
	
/*	private void createRandomPeople() throws SlickException {
		if(Math.random() < 0.01) {
			SlickMoroso m = new SlickMoroso(game);
			game.addDynamicEntity(m.getModel());
			m.getModel().goToRandomDoor();
			morosos.add(m);
		}
	}*/

	public void render(GameContainer container, Graphics g)  {
		if (splashImage == null) {
			map.render(container, g);
			ninja.render(container, g);
			for(SlickDynamicEntity de : dynamicEntities) de.render(container, g);
			map.render(container, g, 0);
			map.render(container, g, 1);
		} else
			splashImage.draw(0, 0);
	}

	public static void main(String[] argv) throws SlickException {
		AppGameContainer container = 
			new AppGameContainer(new SlickMain(), 640, 640, false);
		container.start();
	}

	public SlickNinja getNinja() {
		return ninja;
	}

	public Game getGame() {
		return game;
	}
	
	public SlickMap getSlickMap(){
		return map;
	}
	
	public void addDynamicEntity(SlickDynamicEntity de) {
		dynamicEntities.add(de);
		game.getDynamicEntities().add(de.getModel());
	}

	public void removeDynamicEntity(SlickDynamicEntity de) {
		dynamicEntities.remove(de);
		game.getDynamicEntities().remove(de.getModel());
	}

	public SlickOldMan getOldMan() {
		return oldMan;
	}
	
	public void setSplash(Image i){
		splashImage = i;
	}
}

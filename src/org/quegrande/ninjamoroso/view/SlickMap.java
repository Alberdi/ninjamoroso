package org.quegrande.ninjamoroso.view;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import org.quegrande.ninjamoroso.model.Map;

public class SlickMap {
	
	private TiledMap tiledMap;
	private Map map;
	
	public SlickMap(String tiledPath) throws SlickException {
		tiledMap = new TiledMap(tiledPath);
		map = new Map(20,20);
		for(int group=0; group<2; group++) { 
			int objects = tiledMap.getObjectCount(group);
			for(int k=0; k<objects; k++) {
				int x = tiledMap.getObjectX(group,k)/32;
				int y = tiledMap.getObjectY(group,k)/32;
				int w = tiledMap.getObjectWidth(group,k)/32;
				int h = tiledMap.getObjectHeight(group,k)/32;
				for(int i=x; i<x+w; i++)
					for(int j=y; j<y+h; j++) {
						if(group==0)
							map.setImpassable(j,i);
						else {
							map.setPassable(j,i);
							map.addDoor(j,i);
						}
					}
			}
		}
	}

	public void render(GameContainer container, Graphics g, int layer) {
		tiledMap.render(0, 0, layer);
	}

	public void render(GameContainer container, Graphics g) {
		tiledMap.render(0, 0);
	}
	
	public Map getMap() {
		return map;
	}
	
	public TiledMap getTiledMap() {
		return tiledMap;
	}

}

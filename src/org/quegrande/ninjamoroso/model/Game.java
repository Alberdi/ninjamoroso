package org.quegrande.ninjamoroso.model;

import java.util.*;

public class Game {
	
	private List<DynamicEntity> dynamicEntities;
	private Map map;
	
	public Game(Map map) {
		this.map = map;
		dynamicEntities = new ArrayList<DynamicEntity>();
	}

	public List<DynamicEntity> getDynamicEntities() {
		return dynamicEntities;
	}
	
	public void addDynamicEntity(DynamicEntity e) {
		dynamicEntities.add(e);
	}
	
	public void removeDynamicEntity(DynamicEntity e) {
		dynamicEntities.remove(e);
	}

	public Map getMap() {
		return map;
	}

}

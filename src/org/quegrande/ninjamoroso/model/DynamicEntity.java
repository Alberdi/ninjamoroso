package org.quegrande.ninjamoroso.model;

import java.util.*;

public abstract class DynamicEntity {
	
	private float posX;
	private float posY;
	private float targetX;
	private float targetY;
	private float speed;
	private int facing;
	
	private SpeechBalloon sb;
	
	protected Game game;
	private boolean stopped;
	private List<Integer> path = new ArrayList<Integer>();
	private int dissapearX = -1;
	private int dissapearY;
	private boolean dissapeared = false;
	
	public DynamicEntity(float posX, float posY, float speed, Game game) {
		this.posX = posX;
		this.posY = posY;
		this.speed = speed;
		this.game = game;
	}
	
	public DynamicEntity(float posX, float posY, Game game) {
		this.posX = posX;
		this.posY = posY;
		this.speed = 2;
		this.game = game;
	}
	
	public void goTo(float targetX, float targetY) {
		this.setTargetX(targetX);
		this.setTargetY(targetY);
	}
	
	public boolean isAtTarget() {
		return posX == getTargetX() && posY == getTargetY();
	}
	
	public void updatePos() {
		if((isStopped() || isAtTarget()))
			if(!path.isEmpty()) {
				int n = path.remove(path.size()-1);
				targetX = (n%20)*32;
				targetY = (n/20)*32;
			} else if(dissapearX != -1)  {
				if(targetX == dissapearX) setDissapeared(true);
				targetX = dissapearX;
				targetY = dissapearY;
			}
		
		float tmpPosX = getPosX();
		float tmpPosY = getPosY();
		int tmpFacing = facing;
		setStopped(true);
		if(getPosX() != getTargetX()) {
			if(getTargetX() > getPosX()) {
				tmpPosX = getPosX()+getSpeed()>getTargetX() ? getTargetX() : getPosX()+getSpeed();
				tmpFacing = 1;
			} else {
				tmpPosX = getPosX()-getSpeed()<getTargetX() ? getTargetX() : getPosX()-getSpeed();
				tmpFacing = 3;
			}
		}
		else if(getPosY() != getTargetY()) {
			if(getTargetY() > getPosY()) {
				tmpPosY = getPosY()+getSpeed()>getTargetY() ? getTargetY() : getPosY()+getSpeed();
				tmpFacing = 2;
			} else {
				tmpPosY = getPosY()-getSpeed()<getTargetY() ? getTargetY() : getPosY()-getSpeed();
				tmpFacing = 0;
			}
		}
		//if(game.getMap().isPixelPassable(tmpPosX+15, tmpPosY+15)) {
			setPosX(tmpPosX);
			setPosY(tmpPosY);
			setFacing(tmpFacing);
			setStopped(false);
		//}
	}
	
	public void pathfinding(int targetX, int targetY) {
		Map map = game.getMap();
		int w = map.getWidth();
		int h = map.getHeight();
		int[] dist = new int[w*h];
		int[] prev = new int[w*h];
		
		// Step 1: initialize graph
		for(int i=0; i<w; i++)
			for(int j=0; j<h; j++) {
				dist[i*w+j] = 1000;
				prev[i*w+j] = -1;
			}
		dist[((int) posY/32)*w + ((int) posX/32)] = 0;
		
		// Step 2: relax edges repeatedly
		for(int k=0; k<w*h; k++)
			for(int i=0; i<w; i++)
				for(int j=0; j<h; j++) {
					int u = i*w+j;
					int v = (i+1)*w+j;
					if(i+1<w && map.isPassable(i+1,j) && dist[u]+1<dist[v]) {
						dist[v] = dist[u]+1;
						prev[v] = u;
					}
					v = (i-1)*w+j;
					if(i-1>=0 && map.isPassable(i-1,j) && dist[u]+1<dist[v]) {
						dist[v] = dist[u]+1;
						prev[v] = u;
					}
					v = i*w+j+1;
					if(j+1<h && map.isPassable(i,j+1) && dist[u]+1<dist[v]) {
						dist[v] = dist[u]+1;
						prev[v] = u;
					}
					v = i*w+j-1;
					if(j-1>=0 && map.isPassable(i,j-1) && dist[u]+1<dist[v]) {
						dist[v] = dist[u]+1;
						prev[v] = u;
					}					
				}
		
		// Step 3: check for negative-weight cycles.
		// No need.
		
		// Reconstruct path.
		path = new ArrayList<Integer>();
		int cur = targetX*w+targetY;
		path.add(cur);
		int p;
		while((p = prev[cur]) != -1) {
			path.add(p);
			cur = p;
		}
	}

	public int getFacing() {
		return facing;
	}

	public void setFacing(int facing) {
		this.facing = facing;
	}

	public float getPosX() {
		return posX;
	}

	public float getPosY() {
		return posY;
	}

	public float getSpeed() {
		return speed;
	}

	public void setPosX(float posX) {
		this.posX = posX;
	}

	public void setPosY(float posY) {
		this.posY = posY;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public void stop(){
		setStopped(true);
		setTargetX(posX);
		setTargetY(posY);
	}

	public boolean isStopped() {
		return stopped;
	}
	
	public void setBalloon(SpeechBalloon spBall) {
		sb = spBall;
	}
	
	public SpeechBalloon getBalloon(){
		return sb;
	}
	
	public SpeechBalloon talk(String s){
		sb = new SpeechBalloon(s, this);
		return sb;
	}

	//TODO: This is not exactly a LoS algorithm
	public boolean hasLineOfSightTo(float x, float y) {
		int range = 80;
		return Math.abs(x-this.getPosX()) < range &&
					Math.abs(y-this.getPosY()) < range;
	}

	public float getTargetX() {
		return targetX;
	}

	public void setTargetX(float targetX) {
		this.targetX = targetX;
	}

	public void setStopped(boolean stopped) {
		this.stopped = stopped;
	}

	public float getTargetY() {
		return targetY;
	}

	public void setTargetY(float targetY) {
		this.targetY = targetY;
	}
	
	public Game getGame() {
		return game;
	}

	public void goToRandomDoor() {
		Random r = new Random();
		List<Integer[]> doors = game.getMap().getDoors();
		Integer[] i = doors.get(r.nextInt(doors.size()));
		pathfinding(i[0], i[1]);
		setStopped(true);
	}
	
	protected void prepareToDissapear(int x, int y) {
		this.dissapearX = x*32;
		this.dissapearY = y*32;
	}
	
	public void unDissapear() {
		this.dissapearX = -1;
	}

	public boolean isDissapeared() {
		return dissapeared;
	}
	
	private void setDissapeared(boolean b) {
		dissapeared = b;
	}

	public List<Integer> getPath() {
		return this.path;
	}
}

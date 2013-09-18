package org.quegrande.ninjamoroso.model;

import java.util.*;

public class Map {
	
	private int width;
	private int height;
	private boolean[][] impassable;
	private List<Integer[]> doors;

	public Map(int width, int height) {
		this.width = width;
		this.height = height;
		impassable = new boolean[width][height];
		doors = new ArrayList<Integer[]>();
	}
	
	public void addDoor(int x, int y) {
		Integer[] pos = new Integer[2];
		pos[0] = x;
		pos[1] = y;
		doors.add(pos);
	}
	
	public List<Integer[]> getDoors() {
		return doors;
	}
	
	public void setImpassable(int x, int y) {
		impassable[x][y] = true;
	}

	public void setPassable(int x, int y) {
		impassable[x][y] = false;		
	}
	
	public boolean isImpassable(int x, int y) {
		if(x>=width || x<0 || y>=height || y<0) return true;
		return impassable[x][y];
	}
	
	public boolean isPassable(int x, int y) {
		return !isImpassable(x, y);
	}
	
	public boolean isPixelImpassable(float x, float y) {
		if(x>=width*32 || x<0 || y>=height*32 || y<0) return true;
		return impassable[(int) (x/32)][(int) (y/32)];
	}
	
	public boolean isPixelPassable(float x, float y) {
		return !isPixelImpassable(x, y);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
}

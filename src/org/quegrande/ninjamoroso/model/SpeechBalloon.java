package org.quegrande.ninjamoroso.model;

public class SpeechBalloon {
	
	private String text; 
	private DynamicEntity owner;
	
	public SpeechBalloon(String t, DynamicEntity de){
		text = t;
		owner = de;
	}
	
	public String getText(){
		return text;
	}
	
	public DynamicEntity getOwner(){
		return owner;
	}
}

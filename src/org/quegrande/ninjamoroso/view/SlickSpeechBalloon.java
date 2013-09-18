package org.quegrande.ninjamoroso.view;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.SpriteSheetFont;
import org.newdawn.slick.geom.Rectangle;
import org.quegrande.ninjamoroso.model.DynamicEntity;
import org.quegrande.ninjamoroso.model.Ninja;
import org.quegrande.ninjamoroso.model.SpeechBalloon;

public class SlickSpeechBalloon {

	private SpeechBalloon sb;
	private SpriteSheetFont ssf;
	private SpriteSheet fontSprite;
	private Color textColor;
	private Color balloonColor = Color.blue;
	private Rectangle balloon;
	private int maxCharLine = 30;
	
	public SlickSpeechBalloon(SpeechBalloon speachBalloon, Color c) throws SlickException{
		sb = speachBalloon;
		fontSprite = new SpriteSheet("res/images/font.png",8,12);
		ssf = new SpriteSheetFont(fontSprite, ' ');
		textColor = c;
		balloon = new Rectangle(320,608, maxCharLine*8,0);
	}
	
	public SlickSpeechBalloon(SpeechBalloon speachBalloon, Color c, int width, int heigth) throws SlickException {
		sb = speachBalloon;
		fontSprite = new SpriteSheet("res/images/font.png",8,12);
		ssf = new SpriteSheetFont(fontSprite, ' ');
		textColor = c;
		balloon = new Rectangle(320, 608, width,heigth);
	}

	public void render(GameContainer container, Graphics g){
		DynamicEntity de = sb.getOwner();
		balloon.setHeight(((sb.getText().length()/maxCharLine)+1)*16);
		
		if (!de.getClass().equals(Ninja.class)){
			balloon.setX(de.getPosX()-balloon.getWidth());
			balloon.setY(de.getPosY()-balloon.getHeight());
		}
		
		g.setLineWidth(2);
		g.drawRoundRect(balloon.getX(),
				balloon.getY(),
				balloon.getWidth(),
				balloon.getHeight(),
                5);
		g.setColor(balloonColor);
		g.fill(balloon);
		int length = 0;
		for(int i=0; i<5;i++) {
			if (i*maxCharLine < sb.getText().length()){
				length = ( sb.getText().length() < (i+1)*maxCharLine )?sb.getText().length():(i+1)*maxCharLine;
				String tmp = sb.getText().substring(i*maxCharLine, length);
				ssf.drawString(balloon.getX()+1, balloon.getY()+(16*i), tmp, textColor);
			}
		}
	}
	
	public void update(GameContainer container, int delta){
		
	}
}

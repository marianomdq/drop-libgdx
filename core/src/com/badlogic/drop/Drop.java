package com.badlogic.drop;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Drop extends Game {
	
	public SpriteBatch batch;
	public BitmapFont font;
	
	@Override
	public void create() {
		
		// Instance spriteBatch
		batch = new SpriteBatch();
		
		// Instance BitmapFont
		font = new BitmapFont();
		
		//Set the game's first screen to render
		this.setScreen(new MainMenuScreen(this));
	}
		
	@Override
	public void render() {
		super.render(); //important!!
	}

	@Override
	public void dispose() {
	    batch.dispose();
	    font.dispose();
	}
		
	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}

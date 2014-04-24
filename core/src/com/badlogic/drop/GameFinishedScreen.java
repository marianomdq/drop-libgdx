package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class GameFinishedScreen implements Screen {
	
	final Drop game;
	
	OrthographicCamera camera;
	
	public GameFinishedScreen(final Drop game) {
		this.game = game;
		
		// Instance camera and configure the size
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
	}
	

	@Override
	public void render(float delta) {
		// Clear the screen
		Gdx.gl.glClearColor(0.1f, 0.2f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Update the camera
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		
		// Draw game over message
		game.batch.begin();
		game.font.draw(game.batch, "You have gathered enough water!! Now you can irrigate your crops! ", 300, 200);
		game.font.draw(game.batch, "Tap anywhere to exit", 300, 150);
		game.batch.end();
		
		if (Gdx.input.isTouched()) {
			if (Gdx.input.justTouched()) {
				dispose();
				Gdx.app.exit();
			}
		}
		
		
		

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}

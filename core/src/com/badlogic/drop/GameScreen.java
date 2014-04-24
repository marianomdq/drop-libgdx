package com.badlogic.drop;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {

	final Drop game;
	
	OrthographicCamera camera;
	Rectangle bucket;
	Vector3 touchPos;
	Array<Rectangle> raindrops;
	long lastDropTime;
//	private State state = State.RUN;
	int dropsGathered;
	
	
	//Assets
	Texture dropImage, bucketImage;
	Sound dropSound;
	Music rainMusic;
	
	public GameScreen(final Drop game) {
		this.game = game;
		

		// Load the images from assets, 64x64
		dropImage = new Texture(Gdx.files.internal("droplet.png"));
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));
		
		// Load the sound and music from assets
		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
		rainMusic.setLooping(true);
		
//		// Start the playback of the background music
//		rainMusic.play();
		
		// Instance camera and configure the size
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
	
		// Instance and configure the rectangle
		bucket = new Rectangle();
		bucket.x = 800 / 2 - 64 / 2;
		bucket.y = 20;
		bucket.width = 64;
		bucket.height = 64;
		
		// Instance of raindrops array and first spawned raindrop
		raindrops = new Array<Rectangle>();
		spawnRaindrop();
	
	}
	
	private void spawnRaindrop() {
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, 800-64);
		raindrop.y = 480;
		raindrop.width = 64;
		raindrop.height = 64;
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
	}
	
	@Override
	public void render(float delta) {
		// Set the color for clearing the screen and clear it
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Update the camera to update its matrices
		camera.update();
		
		// Render the bucket
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		game.font.draw(game.batch, "Drops Collected: " + dropsGathered, 0, 480);
		game.batch.draw(bucketImage, bucket.x, bucket.y);
		for (Rectangle raindrop : raindrops)
			game.batch.draw(dropImage, raindrop.x, raindrop.y);
		game.batch.end();
		
		// Making the bucket move via touch
		// Takes the touch coords and set the bucket coords
		if (Gdx.input.isTouched()) {
			touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.x = touchPos.x - 64 / 2;			
		}
		
		// Making the bucket move via keyboard
		if (Gdx.input.isKeyPressed(Keys.LEFT))
			bucket.x -= 200 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Keys.RIGHT))
			bucket.x += 200 * Gdx.graphics.getDeltaTime();
		
		// Restrict the bucket within the screen
		if (bucket.x < 0)
			bucket.x = 0;
		if (bucket.x > 800 - 64)
			bucket.x = 800 - 64;
		
		// Adding the raindrops
		if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
			spawnRaindrop();
		
		// Making the raindrops move
		Iterator<Rectangle> iter = raindrops.iterator();
		while (iter.hasNext()) {
			Rectangle raindrop = iter.next();
			raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
			// Raindrop leaves screen
			if (raindrop.y + 64 < 0)
				iter.remove();
			// Raindrop hits the bucket
			if(raindrop.overlaps(bucket)) {
				dropsGathered++;
		         dropSound.play();
		         iter.remove();
			}
		}
		
		// Ending game condition
		if (dropsGathered >= 20) {
			game.setScreen(new GameFinishedScreen(game));
			dispose();
		}

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// Start the playback of the music
		rainMusic.play();
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
		dropImage.dispose();
	    bucketImage.dispose();
	    dropSound.dispose();
	    rainMusic.dispose();

	}

}

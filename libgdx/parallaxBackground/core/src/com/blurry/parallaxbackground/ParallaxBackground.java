package com.blurry.parallaxbackground;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.blurry.parallaxbackground.screens.GameScreen;

public class ParallaxBackground extends Game {
	//game aspect ratio
	public static final int V_WIDTH = 640;
	public static final int V_HEIGHT = 360;

	//sprite
	public SpriteBatch batch;

	@Override
	public void create() {
		batch = new SpriteBatch();
		this.setScreen(new GameScreen(this));
	}
}

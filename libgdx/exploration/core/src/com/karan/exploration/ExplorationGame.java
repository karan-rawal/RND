package com.karan.exploration;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.karan.exploration.screens.GameScreen;

public class ExplorationGame extends Game {

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

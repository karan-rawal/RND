package com.blurry.parallaxbackground.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.blurry.parallaxbackground.ParallaxBackground;

/**
 * Created by karan on 6/1/18.
 */

public class GameScreen extends ScreenAdapter {
  private ParallaxBackground game;

  public GameScreen(ParallaxBackground parallaxBackground) {
    this.game = parallaxBackground;
  }

}

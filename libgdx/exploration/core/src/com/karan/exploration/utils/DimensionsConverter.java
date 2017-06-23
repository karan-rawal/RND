package com.karan.exploration.utils;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by karan on 23/6/17.
 */

public class DimensionsConverter {
    public static Sprite updateSpriteWithBodyDimens(Sprite sprite, Body body){
        Vector2 position = body.getPosition();

        // Center body is center sprite here
        float hw = sprite.getWidth() / 2.0f;
        float hh = sprite.getHeight() / 2.0f;
        float a = body.getAngle() * MathUtils.radiansToDegrees;
        float x = position.x - hw;
        float y = position.y - hh;

        sprite.setPosition(x, y);
        sprite.setRotation(a);

        return sprite;
    }

    public static Vector2 getPositionForBodyFromSprite(Sprite sprite){
        float x = sprite.getX() + sprite.getWidth()/2;
        float y = sprite.getY() + sprite.getHeight()/2;

        return new Vector2(x, y);
    }

    public static Vector2 getWidthAndHeightForBodyFromSprite(Sprite sprite){
        float x = sprite.getWidth()/2;
        float y = sprite.getHeight()/2;

        return new Vector2(x, y);
    }
}

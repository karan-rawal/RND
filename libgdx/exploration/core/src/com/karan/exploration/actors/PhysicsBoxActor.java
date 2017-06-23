package com.karan.exploration.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by karan on 23/6/17.
 */

public class PhysicsBoxActor extends Actor{

    private Body body;

    public PhysicsBoxActor(float x, float y, float width, float height, World world, BodyDef.BodyType bodyType){
        setOrigin(width/2, height/2);
        setBounds(x - width/2, y - height/2, width, height);
        setDebug(false);
        createBody(world, bodyType);
    }


    public void createBody(World world, BodyDef.BodyType bodyType) {
        this.body = world.createBody(createBodyDef(bodyType));
        createFixture();
        this.body.setUserData(this);
    }

    private BodyDef createBodyDef(BodyDef.BodyType bodyType){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(getX() + getWidth()/2, getY() + getHeight()/2);
        return bodyDef;
    }

    private Fixture createFixture(){
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth()/2, getHeight()/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;
        fixtureDef.density = 10;

        Fixture fixture = body.createFixture(fixtureDef);
        return fixture;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Vector2 position = body.getPosition();
        float width = getWidth();
        float height = getHeight();
        float cx = position.x - width/2;
        float cy = position.y - height/2;
        setBounds(cx, cy, width, height);
        setRotation(body.getAngle() * MathUtils.radiansToDegrees);
    }

    public Body getBody(){
        return body;
    }

}

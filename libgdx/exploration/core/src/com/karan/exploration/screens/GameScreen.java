package com.karan.exploration.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.karan.exploration.ExplorationGame;
import com.karan.exploration.actors.PhysicsBoxActor;

import box2dLight.ConeLight;
import box2dLight.DirectionalLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;

/**
 * Created by karan on 23/6/17.
 */

public class GameScreen extends ScreenAdapter {
    private ExplorationGame explorationGame;

    //camera stuff
    private OrthographicCamera camera;
    private Viewport viewport;

    private Stage stage;

    private World world;
    private Box2DDebugRenderer debugRenderer;

    private PhysicsBoxActor player;
    private PhysicsBoxActor ground;
    private PhysicsBoxActor play;

    private RayHandler rayHandler;

    public GameScreen(ExplorationGame explorationGame) {
        this.explorationGame = explorationGame;
        setUpCamera();
        setUpWorld();
        setUpStage();
        setUpInputListener();
        setUpLights();
        Gdx.input.setInputProcessor(stage);
    }

    private void setUpLights() {
        rayHandler = new RayHandler(world);
//        PointLight pointLight = new PointLight(rayHandler, 200, Color.BLUE, 100, 100, 100);
//        pointLight.attachToBody(player.getBody());
        ConeLight coneLight = new ConeLight(rayHandler, 12, Color.RED, 400, 0, 0, 0, 10);
        coneLight.attachToBody(player.getBody());
        player.getBody().setUserData(coneLight);

//        DirectionalLight directionalLight = new DirectionalLight(rayHandler,4000, Color.GOLD, -45);
//        directionalLight.setSoft(false);
    }

    private void setUpInputListener() {
        stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Vector2 velocity = player.getBody().getLinearVelocity();
                player.getBody().setLinearVelocity(velocity.x, 100);
                ConeLight coneLight = (ConeLight) player.getBody().getUserData();
                coneLight.setActive(! coneLight.isActive());
                return true;
            }

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                Vector2 velocity = player.getBody().getLinearVelocity();
                if (keycode == Input.Keys.LEFT) {
                    player.getBody().setLinearVelocity(-100, velocity.y);
                } else if (keycode == Input.Keys.RIGHT) {
                    player.getBody().setLinearVelocity(100, velocity.y);
                }
                return true;
            }

            @Override
            public boolean keyTyped(InputEvent event, char character) {
                return super.keyTyped(event, character);
            }
        });
    }

    private void setUpWorld() {
        Vector2 gravity = new Vector2(0, -98f);
        world = new World(gravity, false);
        debugRenderer = new Box2DDebugRenderer();
    }

    private void setUpStage() {
        stage = new Stage(this.viewport, explorationGame.batch);

        player = new PhysicsBoxActor(ExplorationGame.V_WIDTH / 2, ExplorationGame.V_HEIGHT / 2, 20, 20, world, BodyDef.BodyType.DynamicBody);
        stage.addActor(player);
        ground = new PhysicsBoxActor(ExplorationGame.V_WIDTH / 2, 20, ExplorationGame.V_WIDTH - 50, 20, world, BodyDef.BodyType.StaticBody);
        stage.addActor(ground);
        play = new PhysicsBoxActor(ExplorationGame.V_WIDTH / 2, ExplorationGame.V_HEIGHT / 2, 20, 20, world, BodyDef.BodyType.DynamicBody);
        stage.addActor(play);
    }

    private void setUpCamera() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(ExplorationGame.V_WIDTH, ExplorationGame.V_HEIGHT, camera);
        viewport.apply(true);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderLights();
        renderWorld(delta);
        renderStage(delta);

        camera.update();
    }

    private void renderLights() {
        //rayHandler.setAmbientLight(0.2f, 0, 0 ,1);
        rayHandler.setCombinedMatrix(camera);
        rayHandler.updateAndRender();
    }

    private void renderWorld(float delta) {
        world.step(delta, 6, 6);
        //debugRenderer.render(world, camera.combined);
    }

    private void renderStage(float delta) {
        stage.getBatch().setProjectionMatrix(camera.combined);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        // fix box2dlights viewport
        int gutterW = viewport.getLeftGutterWidth();
        int gutterH = viewport.getTopGutterHeight();
        int rhWidth = width - (2 * gutterW);
        int rhHeight = height - (2 * gutterH);
        rayHandler.useCustomViewport(gutterW, gutterH, rhWidth, rhHeight);
    }

    @Override
    public void dispose() {
        rayHandler.dispose();
        world.dispose();
        stage.dispose();
    }
}

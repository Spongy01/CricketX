package com.cricketx.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cricketx.CricketX;

import java.io.IOException;


public class MenuScreen implements Screen {

    private CricketX parent;
    OrthographicCamera camera;
    Stage stage;
    Skin skin;
    int width = 1920/2;
    int height = 1080/2;
    boolean isCorrected= false;
    public static boolean didGameOvercall=false;
    Texture background;
    Viewport viewport;
    public MenuScreen(CricketX cx){
        parent = cx;
        camera = new OrthographicCamera();
        viewport = new FitViewport(1920/2,1080/2,camera);
        parent.skin = parent.loader.manager.get(parent.loader.skin);
        skin = parent.skin;

    }
    @Override
    public void show() {
        stage = new Stage(viewport);

        stage.clear();
        Gdx.input.setInputProcessor(stage);

        TextButton newGame = new TextButton("New Game", skin);
        TextButton settings = new TextButton("Settings",skin);
        TextButton exit = new TextButton("Exit",skin);

        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        table.add(newGame).fillX().uniformX();
        table.row().pad(10,0,10,0);
        table.add(settings).fillX().uniformX();
        table.row();
        table.add(exit).fillX().uniformX();
        if (didGameOvercall && !isCorrected){
            isCorrected = true;
            width/=2;
            height/=2;
        }
        stage.addActor(table);

        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    parent.setData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Gdx.app.exit();
            }
        });

        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                HUD.life =5;
                HUD.Score =0;
                parent.changeScreen(CricketX.GAME);
            }
        });

        settings.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SettingScreen.fromMenu = true;
                SettingScreen.fromPause = false;
                parent.changeScreen(CricketX.SETTING);
            }
        });
        //parent.batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        background = new Texture("blur Background2.png");
        parent.batch.begin();
        parent.batch.draw(background,0,0,width,height);
        parent.batch.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        background.dispose();
    }
}

package com.cricketx.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.cricketx.CricketX;

public class GameOver implements Screen {

    CricketX parent;
    HUD hud;
    Stage stage;
    Skin skin;
    Texture background;
    private int score;
    public GameOver(CricketX p){
        parent = p;
        hud = MainScreen.getHud();
        stage  = new Stage(new ScreenViewport());
        skin = parent.loader.manager.get(parent.loader.skin);
        score =0;
        background = new Texture("blur background.png");
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        score = hud.Score;
        Table table =  new Table();
        table.setFillParent(true);
        table.setDebug(true);
        stage.addActor(table);
        TextButton gameover = new TextButton("GameOver",skin,"unclickable");
        table.add(gameover).pad(10,10,10,10).colspan(2);
        TextButton scoreButton = new TextButton("Score: "+score,skin,"unclickable-small");
        int playerHighscore = parent.userData.getUserScore(parent.currentPlayer);
        if(score>playerHighscore){
            parent.userData.setUserScore(parent.currentPlayer,score);
        }
        TextButton highScoreButton = new TextButton("HighScore: "+parent.userData.getUserScore(parent.currentPlayer),skin,"unclickable-small");
        TextButton endgame =  new TextButton("Exit",skin,"small");
        table.row().pad(10,0,10,0);
        table.add(scoreButton);
        table.add(highScoreButton);
        table.row();
        table.add(endgame).colspan(2);

        endgame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                MenuScreen.didGameOvercall = true;
                parent.changeScreen(CricketX.MENU);
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        parent.batch.begin();
        parent.batch.draw(background,0,0,1920/4,1080/4);
        parent.batch.end();
        stage.act();
        stage.draw();


    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height,true);
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

    }
}

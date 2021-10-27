package com.cricketx.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cricketx.CricketX;


public class HUD {
    public Stage stage;

    private Viewport viewport;
    ImageButton pause;
    public static int Score;
    private CricketX parent;
    Label score_label;
    Label lives_label;
    public static int life;
    String formatedScore;
    public HUD(SpriteBatch batch, final CricketX p){
        viewport = new FitViewport(1920f/4, 1080f/4, new OrthographicCamera());
        stage = new Stage(viewport,batch);
        Score = 0;
        formatedScore = Integer.toString(Score);
        pause = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("pause_icon.png")))));
        life = 5;
        Table pausemenu= new Table();
        pausemenu.top().left();
        pausemenu.setFillParent(true);
        pausemenu.setDebug(true);
        pausemenu.add(pause).pad(5,5,5,5);
        pausemenu.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                parent.changeScreen(CricketX.PAUSE);
                System.out.println("clicked");
            }
        });


        Table score=  new Table();
        score.bottom().left();
        score.setFillParent(true);
        score.setVisible(true);
        score.setDebug(true);
        parent = p;
        score_label = new Label("Score : "+formatedScore, parent.skin,"black");
        score.add(score_label).pad(10,10,20,10);
        stage.addActor(score);
        stage.addActor(pausemenu);

        Table lives = new Table();
        lives.bottom().right();
        lives.setFillParent(true);
        lives.setDebug(true);
        lives_label = new Label("Lives : "+life, parent.skin,"black");
        lives.add(lives_label).pad(10,10,10,10);
        stage.addActor(lives);

    }
    public void update(){
        formatedScore = Integer.toString(Score);
        score_label.setText("Score : "+formatedScore);
        lives_label.setText("Lives : "+life);
    }

    public void resize(int w, int h){
        viewport.update(w,h,true);
    }
}

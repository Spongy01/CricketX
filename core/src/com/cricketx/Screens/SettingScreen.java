package com.cricketx.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.cricketx.CricketX;

public class SettingScreen implements Screen {
    private CricketX parent;
    Stage stage;
    private Label titleLabel;
    private Label volumeMusicLabel;
    private Label volumeSoundLabel;
    private Label musicOnOffLabel;
    private Label soundOnOffLabel;
    public static boolean fromPause = false;
    public  static boolean fromMenu = false;
    public SettingScreen(CricketX cx){
        parent = cx;
        stage = new Stage(new ScreenViewport());
    }

    @Override
    public void show() {
        stage.clear();
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        Skin skin = parent.loader.manager.get(parent.loader.skin);
        table.setDebug(true);
        table.setFillParent(true);
        stage.addActor(table);

        final Slider volumeMusicSlider = new Slider(0f,1f,0.1f,false,skin);
        volumeMusicSlider.setValue(parent.setting.musicVol);
        volumeMusicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                parent.setting.musicVol = (volumeMusicSlider.getValue());
                return false;
            }
        });
        final Slider volumeSoundSlider = new Slider(0f,1f,0.1f,false,skin);
        volumeSoundSlider.setValue(parent.setting.soundVol);
        volumeSoundSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                parent.setting.soundVol = (volumeSoundSlider.getValue());
                return false;
            }
        });

        final CheckBox musicCheckbox = new CheckBox(null,skin);
        musicCheckbox.setChecked(parent.setting.isMusic);
        musicCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                parent.setting.isMusic = (musicCheckbox.isChecked());
                return false;
            }
        });

        final CheckBox soundCheckbox = new CheckBox(null,skin);
        soundCheckbox.setChecked(parent.setting.isSound);
        soundCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                parent.setting.isSound = (soundCheckbox.isChecked());
                return false;
            }
        });

        final TextButton backButton = new TextButton("Back",skin,"small");
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(fromMenu) {
                    parent.changeScreen(CricketX.MENU);
                }
                if(fromPause){
                    parent.changeScreen(CricketX.PAUSE);
                }
            }
        });
        titleLabel = new Label( "Preferences", skin );
        volumeMusicLabel = new Label("Music Volume", skin );
        volumeSoundLabel = new Label( "Sound Volume", skin );
        musicOnOffLabel = new Label( "Music", skin );
        soundOnOffLabel = new Label( "Sound", skin );

        table.add(titleLabel).colspan(2);
        table.row().pad(10,0,0,10);
        table.add(musicOnOffLabel).left();
        table.add(musicCheckbox);
        table.row().pad(10,0,0,10);;
        table.add(volumeMusicLabel).left();
        table.add(volumeMusicSlider);
        table.row().pad(10,0,0,10);;
        table.add(soundOnOffLabel).left();
        table.add(soundCheckbox);
        table.row().pad(10,0,0,10);;
        table.add(volumeSoundLabel).left();
        table.add(volumeSoundSlider);

        table.row().pad(10,0,0,10);;
        table.add(backButton).colspan(2);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f,0f,0f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(delta,1/30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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

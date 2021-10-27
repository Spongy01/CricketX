package com.cricketx.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.cricketx.CricketX;

public class LoadingScreen implements Screen {

    private CricketX parent;
    public LoadingScreen(CricketX cx){
        parent = cx;
        parent.loader.queueAddAtlas();
        parent.loader.queueAddSkin();
        parent.loader.manager.finishLoading();
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        parent.changeScreen(CricketX.MENU);
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

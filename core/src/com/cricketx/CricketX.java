package com.cricketx;


import com.badlogic.gdx.Game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.cricketx.FileManager.Settings;
import com.cricketx.FileManager.UserData;
import com.cricketx.Loader.AssetLoader;
import com.cricketx.Screens.*;

import java.io.IOException;


public class CricketX extends Game {

    private LoadingScreen loadingScreen;
    private MainScreen mainScreen;
    private MenuScreen menuScreen;
    private SettingScreen settingScreen;
    private ExitScreen exitScreen;
    private PauseScreen pauseScreen;
    private GameOver gameOver;
    public SpriteBatch batch;
    public AssetLoader loader = new AssetLoader();
    public Skin skin;
    public Settings setting;
    public UserData userData;
    public HUD hud;

    public final static int MENU = 0;
    public final static int SETTING = 1;
    public final static int GAME = 2;
    public final static int ENDGAME = 3;
    public final static int PAUSE = 4;
    public final static int GAMEOVER = 5;
    public String currentPlayer = "";


    public void changeScreen(int screen) {
        switch (screen) {
            case MENU: {
                if (menuScreen == null) menuScreen = new MenuScreen(this);
                this.setScreen(menuScreen);
                break;
            }
            case SETTING: {
                if (settingScreen == null) settingScreen = new SettingScreen((this));
                this.setScreen(settingScreen);
                break;
            }
            case GAME: {
                if (mainScreen == null) mainScreen = new MainScreen(this);
                this.setScreen(mainScreen);
                break;
            }
            case ENDGAME: {
                if (exitScreen == null) exitScreen = new ExitScreen(this);
                this.setScreen(mainScreen);
                break;
            }
            case PAUSE: {
                if (pauseScreen == null) pauseScreen = new PauseScreen(this);
                this.setScreen(pauseScreen);
                break;
            }
            case GAMEOVER: {
                if (gameOver == null) gameOver = new GameOver(this);
                this.setScreen(gameOver);
                break;
            }
        }
    }

    @Override
    public void create() {
        loadingScreen = new LoadingScreen(this);
        batch = new SpriteBatch();
        try {
            setting = new Settings();
        } catch (IOException e) {
            e.printStackTrace();
        }
        userData = new UserData();
        this.setScreen(loadingScreen);
    }

    public void setData() throws IOException {
        setting.setData();
        userData.setData();
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        loader.manager.dispose();
    }
}

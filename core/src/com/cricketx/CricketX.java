package com.cricketx;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Sound;
import com.cricketx.Screens.*;


public class CricketX extends Game {

	private LoadingScreen loadingScreen;
	private MainScreen mainScreen;
	private MenuScreen menuScreen;
	private SettingScreen settingScreen;
	private ExitScreen exitScreen;

	private final static int MENU=0;
	private final static int SETTING=1;
	private final static int GAME=2;
	private final static int ENDGAME=3;



	public void changeScreen(int screen){
		switch (screen){
			case MENU:{
				if(menuScreen==null) menuScreen = new MenuScreen(this);
				this.setScreen(menuScreen);
				break;
			}
			case SETTING:{
				if (settingScreen==null) settingScreen = new SettingScreen((this));
				this.setScreen(settingScreen);
				break;
			}
			case GAME:{
				if(mainScreen==null) mainScreen = new MainScreen(this);
				this.setScreen(mainScreen);
				break;
			}
			case ENDGAME:{
				if(exitScreen==null) exitScreen = new ExitScreen(this);
				this.setScreen(mainScreen);
				break;
			}
		}
	}
	
	@Override
	public void create () {

	}

	@Override
	public void render () {

	}
	
	@Override
	public void dispose () {

	}
}

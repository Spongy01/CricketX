package com.cricketx.Loader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetLoader {
    public final AssetManager manager = new AssetManager();

    public final String skin = "glassy/skin/glassy-ui.json";
    public final String atlas = "sprites.txt";
    public final String skin_c = "comic/skin/comic-ui.json";
    public final String hit_audio = "audio/hit.mp3";
    public final String crowd_audio = "audio/crowd.mp3";

    public void queueAddSkin(){
        SkinLoader.SkinParameter param = new SkinLoader.SkinParameter("glassy/skin/glassy-ui.atlas");
        manager.load(skin, Skin.class,param);
        SkinLoader.SkinParameter param2 = new SkinLoader.SkinParameter("comic/skin/comic-ui.atlas");
        manager.load(skin_c, Skin.class,param2);
    }
    public void queueAddAtlas(){
        // textureAtlas = new TextureAtlas(atlas);
        manager.load(atlas,TextureAtlas.class);
    }

    public void queueAddAudio(){
        manager.load(hit_audio, Sound.class);
        manager.load(crowd_audio, Music.class);
    }
}

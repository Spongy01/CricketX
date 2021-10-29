package com.cricketx.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cricketx.CricketX;
import com.cricketx.FileManager.UserData;
import sun.tools.jconsole.Tab;

import java.io.BufferedInputStream;
import java.io.IOException;


public class MenuScreen implements Screen {

    private CricketX parent;
    OrthographicCamera camera;
    Stage stage;
    Skin skin;
    int width = 1920/2;
    int height = 1080/2;
    boolean isCorrected = false;
    boolean userLimitExceeded = false;
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
        final SelectBox<String> box = new SelectBox<String>(skin);
        box.setItems(parent.userData.getNamesList());
        Table table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);
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


        //user
        Table usercolum = new Table();
        usercolum.top().right();
        usercolum.setFillParent(true);
        usercolum.setDebug(true);
        usercolum.add(box).pad(20,5,10,5);
        usercolum.row().pad(5);
        final TextButton adduser = new TextButton("+ User",skin,"small");
        final TextButton removeuser = new TextButton("Remove current User",skin,"small");


        usercolum.add(adduser);
        usercolum.row();
        usercolum.add(removeuser);
        stage.addActor(usercolum);

        final Dialog dialog = new Dialog("Add User",skin){
            @Override
            protected void result(Object object) {
                System.out.println("result: "+object);
            }
        };
        dialog.setSize(300,300);
        dialog.setPosition(Gdx.graphics.getWidth()/2-150,Gdx.graphics.getHeight()/2-150);
        dialog.setVisible(false);
        stage.addActor(dialog);
        adduser.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dialog.setVisible(true);
            }
        });

        final Dialog userdelete = new Dialog("User",skin){
            @Override
            protected void result(Object object) {
                super.result(object);
            }
        };
        userdelete.setSize(300,300);
        userdelete.setPosition(Gdx.graphics.getWidth()/2-150,Gdx.graphics.getHeight()/2-150);
        userdelete.setVisible(false);
        stage.addActor(userdelete);
        Table contentforuserdelete = new Table();
        Label deletetext = new Label("User Deleted : ",skin,"font","black");
        contentforuserdelete.add(deletetext);
        Table buttontable = new Table();
        TextButton okay = new TextButton("Ok",skin,"small");
        buttontable.add(okay);
        okay.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                userdelete.setVisible(false);
            }
        });

        userdelete.getContentTable().add(contentforuserdelete);
        userdelete.getButtonTable().add(buttontable);




        removeuser.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.userData.deleteUser(box.getSelected());
                userdelete.setVisible(true);
                box.setItems(parent.userData.getNamesList());
            }
        });

        Table contentTable = new Table();
        Label label = new Label("Name : ",skin,"font","black");
        contentTable.add(label).pad(5);
        final TextField entry = new TextField("",skin);
        contentTable.add(entry);


        Table dialogTable = new Table();
        TextButton cancel = new TextButton("Cancel",skin,"small");
        TextButton ok = new TextButton("Add User",skin,"small");
        dialogTable.add(ok);
        dialogTable.add(cancel);

        final Dialog cantdelete = new Dialog("User",skin){
            @Override
            protected void result(Object object) {
                super.result(object);
            }
        };

        cantdelete.setSize(300,300);
        cantdelete.setPosition(Gdx.graphics.getWidth()/2-150,Gdx.graphics.getHeight()/2-150);
        stage.addActor(cantdelete);
        Label userdeleteionerrorlabel = new Label("User limit exceeded,\ncant add anymore users",skin,"font","black");
        TextButton ok2 = new TextButton("OK",skin,"small");
        Table dialogcontent2 = new Table();
        dialogcontent2.add(userdeleteionerrorlabel);
        cantdelete.setVisible(false);
        Table buttontable2 = new Table();
        buttontable2.add(ok2);
        ok2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                cantdelete.setVisible(false);
            }
        });
        cantdelete.getButtonTable().add(buttontable2);
        cantdelete.getContentTable().add(dialogcontent2);
      //  dialog.text(label).top();

       // dialog.button(cancel);
        dialog.getContentTable().add(contentTable);
        dialog.getButtonTable().add(dialogTable);

        //dialog.button(ok);
        cancel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dialog.setVisible(false);
                entry.setText("");
            }
        });
        ok.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                dialog.setVisible(false);
                String name = entry.getText();
                System.out.println(name);
                entry.setText("");
                boolean isaddable = parent.userData.addUser(name);
                System.out.println("Can add users? :"+isaddable);
                if(!isaddable){
                    cantdelete.setVisible(true);
                    System.out.println("in Here");

                }
                box.setItems(parent.userData.getNamesList());

            }
        });
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
                parent.currentPlayer = box.getSelected();
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
        stage.dispose();
        skin.dispose();
    }
}

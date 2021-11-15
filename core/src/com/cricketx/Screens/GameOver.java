package com.cricketx.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
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
        //table.setDebug(true);
        stage.addActor(table);
        TextButton gameover = new TextButton("GameOver",skin,"unclickable");
        table.add(gameover).pad(10,10,10,10).colspan(2);
        TextButton scoreButton = new TextButton("Score: "+score,skin,"unclickable-small");
        int playerHighscore = parent.userData.getUserScore(parent.currentPlayer);
        if(score>playerHighscore){
            parent.userData.setUserScore(parent.currentPlayer,score);
        }
        TextButton highScoreButton = new TextButton("HighScore: "+parent.userData.getUserScore(parent.currentPlayer),skin,"unclickable-small");
        TextButton leaderboard = new TextButton("LeaderBoard",skin,"small");
        TextButton endgame =  new TextButton("Exit",skin,"small");
        table.row().pad(10,0,10,0);
        table.add(leaderboard).colspan(2).pad(10).fillX().uniformX();
        table.row();
        table.add(scoreButton).fillX().uniformX();

        table.add(highScoreButton).fillX().uniformX();
        table.row();
        table.add(endgame).colspan(2).pad(10);

        endgame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                MenuScreen.didGameOvercall = true;
                parent.changeScreen(CricketX.MENU);
            }
        });

        //Leaderboard Dialog
        final Dialog leaderboardDialog = new Dialog("Scores",skin){
            @Override
            protected void result(Object object) {
                super.result(object);
            }
        };
        leaderboardDialog.setVisible(false);
        leaderboardDialog.setSize(400,400);
        leaderboardDialog.setPosition(Gdx.graphics.getWidth()/2-leaderboardDialog.getWidth()/2,Gdx.graphics.getHeight()/2-leaderboardDialog.getHeight()/2);

        stage.addActor(leaderboardDialog);
        //Content Table for Leaderboard
        Table contentLeaderBoardTable = new Table();
        List<String> scoreboard = new List<>(skin);
        contentLeaderBoardTable.add(scoreboard).width(350).height(220);
        leaderboardDialog.getContentTable().add(contentLeaderBoardTable);
        //Fetch The LeaderBoard data
        String raw[] = parent.userData.getNamesList();
        String formattedList[] = new String[raw.length];
        int index = 0;
        int scoretable[] = new int[raw.length];
        for (String r :
                raw) {
            scoretable[index++] = parent.userData.getUserScore(r);
        }
        //Arranging the LeaderBoard Data
        for (int i = 0; i < raw.length; i++) {
            for (int j = 0; j < raw.length-1; j++) {
                if(scoretable[j]<scoretable[j+1]){
                    int temp = scoretable[j];
                    scoretable[j] = scoretable[j+1];
                    scoretable[j+1] = temp;
                    String t = raw[j];
                    raw[j] = raw[j+1];
                    raw[j+1] = t;
                }
            }
        }
        index =0;
        for (String s :
                raw) {
            String f = String.format("%s   :   %d",s,parent.userData.getUserScore(s));
            formattedList[index++] = f;
        }
        scoreboard.setItems(formattedList);
        scoreboard.setSelected(String.format("%s   :   %d",parent.currentPlayer,parent.userData.getUserScore(parent.currentPlayer)));
        //ScoreBoard Set

        //ButtonTable for Dialog
        TextButton okScoreButton = new TextButton("OK",skin,"small");
        Table buttonLeaderBoardTable =  new Table();
        buttonLeaderBoardTable.add(okScoreButton).pad(10);
        leaderboardDialog.getButtonTable().add(buttonLeaderBoardTable);
        okScoreButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                leaderboardDialog.setVisible(false);
            }
        });
        // Set Leaderboard Button
        leaderboard.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                leaderboardDialog.setVisible(true);
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
        stage.dispose();
        background.dispose();
    }
}

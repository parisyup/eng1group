package net.shipsandgiggles.pirate.screen.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import net.shipsandgiggles.pirate.HUDmanager;
import net.shipsandgiggles.pirate.conf.Configuration;

public class DeathScreen {

    /** adds the ui for the death screen*/
    public Stage stage;
    private Viewport viewport;
    public float score = 0;
    public float Gold = 0;

    public Label scoreLabel;
    public Label gold;
    public Label gameOver;

    public DeathScreen(SpriteBatch batch){
        /** construction and setting of the labels*/
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Table endGameTable = new Table();

        gameOver = new Label( "Game Over!", Configuration.SKIN, "title");
        scoreLabel = new Label( "Score : " + score, Configuration.SKIN, "big");
        gold = new Label( "Final Gold Count : " + Gold, Configuration.SKIN, "big");

        gameOver.setFontScale(1.5f);
        scoreLabel.setFontScale(0.75f);
        gold.setFontScale(0.75f);
        endGameTable.add(gameOver);
        endGameTable.row();
        endGameTable.add(scoreLabel);
        endGameTable.row();
        endGameTable.add(gold);

        endGameTable.setPosition(Gdx.graphics.getWidth()/2 , Gdx.graphics.getHeight()/2 + 100);

        stage.addActor(endGameTable);
    }

    public void update(HUDmanager hud, int victorykind){
        /** update the variables and check for which victory achived*/
        if(victorykind == 1){
            gameOver.setText("Pacifist Victory!");
        }
        if(victorykind == 2){
            gameOver.setText("Domination Victory!");
        }
        score = hud.score;
        Gold = hud.gold;
        scoreLabel.setText("Score: " + score);
        gold.setText("Final Gold Count: " + Gold);
    }

}

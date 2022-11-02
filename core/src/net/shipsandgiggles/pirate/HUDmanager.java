package net.shipsandgiggles.pirate;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import net.shipsandgiggles.pirate.conf.Configuration;
import net.shipsandgiggles.pirate.currency.Currency;
import net.shipsandgiggles.pirate.entity.Ship;
import net.shipsandgiggles.pirate.screen.impl.GameScreen;

import java.awt.*;

public class HUDmanager {

    /** a manager for the HUD */

    public Stage stage;
    private Viewport viewport;

    public static int score;
    public static int gold;

    public float fontScale = 1.5f;
    public float timeCounter = 0;
    public float coolDownTimerTime;

    public Texture healthBar = new Texture("models/bar.png"); /** gets helthbar textures*/

    /** setting labels and getting other textures*/
    Label scoreLabelCounter;
    Label goldLabel;
    Label scoreLabel;
    Label cooldownTimer;
    Label health;
    Label healthLabel;
    Image goldCoin = new Image(new Texture("models/gold_coin.png"));
    Image burstLogo = new Image(new Texture("models/burst_icon.png"));
    Image shootLogo = new Image(new Texture("models/attack_icon.png"));
    Image burstCooldownLogo = new Image(new Texture("models/burst_onCoolDown.png"));
    Stack cooldown = new Stack();
    Table abalities = new Table();
    Table bottomLeftTable = new Table();


    public HUDmanager(SpriteBatch batch){
        /** setting the score*/
        score = Currency.get().balance(Currency.Type.POINTS);
        gold = Currency.get().balance(Currency.Type.GOLD);

        /** setting a view port and stage for the camera to paste it on the screen and not map*/
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, batch);

        /**creation of the top left bit of the screen */
        Table topLeftTable = new Table();

        topLeftTable.setSize(200,Gdx.graphics.getHeight());
        topLeftTable.top().left();

        scoreLabelCounter = new Label(String.format("%06d", score), Configuration.SKIN, "big");
        goldLabel = new Label(String.format("%06d", gold), Configuration.SKIN, "big");
        cooldownTimer = new Label("" + coolDownTimerTime, Configuration.SKIN, "big");
        scoreLabel = new Label("Score: ", Configuration.SKIN, "big");

        /** order of adding the UI*/

        topLeftTable.add(goldCoin);
        topLeftTable.add(goldLabel);

        topLeftTable.row();
        topLeftTable.add(scoreLabel);
        topLeftTable.add(scoreLabelCounter);

        stage.addActor(topLeftTable);


        /** creation of bottom left of the screen*/
        abalities.setSize(Gdx.graphics.getWidth(),200);
        abalities.top().left();

        cooldown.add(burstLogo);


        abalities.add(shootLogo);
        abalities.add(cooldown);

        abalities.setPosition(0, -70);

        stage.addActor(abalities);

        healthLabel = new Label("Health: ", Configuration.SKIN, "big");
        health = new Label("" + Ship.health + " / " + Ship.maxHealth, Configuration.SKIN, "big");
        /** adds order */
        bottomLeftTable.add(healthLabel);
        bottomLeftTable.add(health);
        bottomLeftTable.setPosition(150, 200);
        bottomLeftTable.row();
        stage.addActor(bottomLeftTable);
    }
    /** updates all the variables on screen*/
    public void updateLabels(Batch batch){
        coolDownTimerTime = Ship.burstTimer;
        String healthText = " " + Ship.health;

        /** change colour of healthbar based on health percentage*/
        if(Ship.health > (Ship.maxHealth * 0.49)){
            batch.setColor(Color.GREEN);
            health.setText("" + healthText.substring(0,6) + " / " + Ship.maxHealth);
        }
        else if(Ship.health > (Ship.maxHealth * 0.25)){
            batch.setColor(Color.ORANGE);
            health.setText("" + healthText.substring(0,5) + " / " + Ship.maxHealth);
        }
        else{
            batch.setColor(Color.RED);
            health.setText("" + healthText.substring(0,5) + " / " + Ship.maxHealth);
        }

        /** draw health bar*/
        batch.begin();
        batch.draw(healthBar, 0,140,Gdx.graphics.getWidth()/5 * (Ship.health/Ship.maxHealth), 30);
        batch.end();

        batch.setColor(Color.WHITE);
        /** update variables and give points to player every 2 seconds*/
        timeCounter += Gdx.graphics.getDeltaTime();
        if(timeCounter >= 2){
            Currency.get().give(Currency.Type.POINTS, 1);
            timeCounter = 0;
        }
        if(coolDownTimerTime > 0){
            cooldown.removeActor(burstLogo);
            cooldown.add(burstCooldownLogo);
            String coolDownText = "" + coolDownTimerTime;
            cooldownTimer.setText("    " + coolDownText.substring(0,3));
            cooldownTimer.setFontScale(1.2f);
            cooldown.add(cooldownTimer);
        }
        else{
            cooldown.removeActor(burstCooldownLogo);
            cooldown.removeActor(cooldownTimer);
            cooldown.add(burstLogo);
        }





        gold = Currency.get().balance(Currency.Type.GOLD);
        score = Currency.get().balance(Currency.Type.POINTS);

        scoreLabelCounter.setText(String.format("%06d", score));
        goldLabel.setText(String.format("%06d", gold));
    }
}
package net.shipsandgiggles.pirate.screen.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import net.shipsandgiggles.pirate.conf.Configuration;
import net.shipsandgiggles.pirate.screen.ScreenType;
import net.shipsandgiggles.pirate.task.ChangeScreenTask;

public class InformationScreen implements Screen {

    /** the information screen before the game begins*/
    /** all using simple ui */

    private Stage stage;
    private Table table;

    private Timer.Task task;
    public final Sprite background = new Sprite(new Texture(Gdx.files.internal("models/background.PNG")));;
    private final SpriteBatch batch = new SpriteBatch();;

    @Override
    public void show() {
        this.table = new Table();

        this.table.setFillParent(true);

        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this.stage);
        this.stage.addActor(this.table);

        /** creating all the labels*/
        Label informationLabel = new Label("INFORMATION!", Configuration.SKIN, "title");
        informationLabel.setAlignment(Align.center);

        Label keysInformation = new Label("Use your arrow keys or WASD to move around.", Configuration.SKIN, "big");
        keysInformation.setAlignment(Align.center);

        Label shootingInformation = new Label("There are two methods of shooting, burst and singular.", Configuration.SKIN, "big");
        shootingInformation.setAlignment(Align.center);

        Label shootingInformationTwo = new Label("Click where you want to shoot!", Configuration.SKIN, "big");
        shootingInformationTwo.setAlignment(Align.center);

        Label burstShoot = new Label("Right-Click to burst shoot!", Configuration.SKIN, "big");
        burstShoot.setAlignment(Align.center);

        Label singularShoot = new Label("Left-Click to singular shoot!", Configuration.SKIN, "big");
        singularShoot.setAlignment(Align.center);

        Label collegeInfo1 = new Label("There are two ways to win the game!", Configuration.SKIN, "big");
        Label collegeInfo2 = new Label("taking down a college will give you 5 gold and 3 score a second and if you capture all of them you win!", Configuration.SKIN, "big");
        Label collegeInfo3 = new Label("the other way is to destroy all of them which will give you an instant 500 gold and 250 score!", Configuration.SKIN, "big");
        Label collegeInfo4 = new Label("destroying all of them will result in a victory!", Configuration.SKIN, "big");

        Label spaceToSkip = new Label("Press the space bar to skip the information!", Configuration.SKIN);
        spaceToSkip.setAlignment(Align.center);

        /**Creates a uniform X/Y table. */
        this.table.add(informationLabel);
        this.table.row();
        this.table.add(Configuration.SPACER_LABEL);
        this.table.row();
        this.table.add(keysInformation);
        this.table.row();
        this.table.add(Configuration.SPACER_LABEL);
        this.table.row();
        this.table.add(shootingInformation);
        this.table.row();
        this.table.add(shootingInformationTwo);
        this.table.row();
        this.table.add(Configuration.SPACER_LABEL);
        this.table.row();
        this.table.add(burstShoot);
        this.table.row();
        this.table.add(singularShoot);
        this.table.row();
        this.table.add(Configuration.SPACER_LABEL);
        this.table.row();
        this.table.add(Configuration.SPACER_LABEL);
        this.table.row();
        this.table.row();
        this.table.add(collegeInfo1);
        this.table.row();
        this.table.add(collegeInfo2);
        this.table.row();
        this.table.add(collegeInfo3);
        this.table.row();
        this.table.add(collegeInfo4);
        this.table.row();
        this.table.row();
        this.table.add(spaceToSkip);

        this.task = Timer.schedule(new ChangeScreenTask(ScreenType.GAME), 40);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.98f, .91f, .761f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        batch.end();

        this.stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        this.stage.draw();

        takeInput();
    }

    @Override
    public void resize(int width, int height) {
        this.stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        this.stage.getRoot().getColor().a = 0;
        this.stage.getRoot().addAction(Actions.fadeOut(1));
    }

    @Override
    public void dispose() {
        this.stage.dispose();
    }

    public void takeInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            this.task.run();
        }
    }
}
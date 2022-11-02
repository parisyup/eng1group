package net.shipsandgiggles.pirate.screen.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import net.shipsandgiggles.pirate.PirateGame;
import net.shipsandgiggles.pirate.SoundController;
import net.shipsandgiggles.pirate.conf.Configuration;
import net.shipsandgiggles.pirate.pref.GamePreferences;
import net.shipsandgiggles.pirate.screen.ScreenType;

public class PreferenceScreen implements Screen {

	/** preference screen in the main menu*/

	private Stage stage;
	private Table table;

	@Override
	public void show() {
		this.table = new Table();

		this.table.setFillParent(true);
		//this.table.setDebug(true);

		this.stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(this.stage);
		this.stage.addActor(this.table);

		GamePreferences gamePreferences = GamePreferences.get();

		Label preferencesLabel = new Label("Game Preferences", Configuration.SKIN, "big");
		preferencesLabel.setAlignment(Align.center);

		/** Music Volume Settings*/

		Slider VolumeSlider = new Slider(0f, 1f, 0.1f, false, Configuration.SKIN);
		VolumeSlider.setValue(gamePreferences.getVolumeLevel());
		VolumeSlider.addListener(event -> {
			gamePreferences.setVolumeLevel(VolumeSlider.getValue());
			return true;
		});

		Label VolumeLabel = new Label("Volume", Configuration.SKIN);

		Slider musicVolumeSlider = new Slider(0f, 1f, 0.1f, false, Configuration.SKIN);
		musicVolumeSlider.setValue(gamePreferences.getVolumeLevel());
		musicVolumeSlider.addListener(event -> {
			gamePreferences.setMusicVolumeLevel(musicVolumeSlider.getValue());
			return true;
		});

		Label musicVolumeLabel = new Label("Music Volume", Configuration.SKIN);

		/** Music Enabled Settings*/

		CheckBox musicEnabled = new CheckBox(null, Configuration.SKIN);
		musicEnabled.setChecked(gamePreferences.isMusicEnabled());
		musicEnabled.addListener(event -> {

			boolean enabled = musicEnabled.isChecked();
			gamePreferences.setMusicEnabled(enabled);
			return true;
		});

		Label musicEnabledLabel = new Label("Music Enabled", Configuration.SKIN);

		/** Volume Enabled Settings*/

		CheckBox volumeEnabled = new CheckBox(null, Configuration.SKIN);
		volumeEnabled.setChecked(gamePreferences.isVolumeEnabled());
		volumeEnabled.addListener(event -> {
			boolean enabled = volumeEnabled.isChecked();
			gamePreferences.setVolumeEnabled(enabled);
			return true;
		});

		Label volumeLabel = new Label("Volume Enabled", Configuration.SKIN);

		TextButton backButton = new TextButton("Back", Configuration.SKIN); /** the extra argument here "small" is used to set the button to the smaller version instead of the big default version*/
		 backButton.addListener(
				new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				LoadingScreen.soundController.pauseAll();
				PirateGame.get().changeScreen(ScreenType.LOADING);
			}
		});

		/** Creates a uniform X/Y table.*/
		this.table.add(preferencesLabel);
		this.table.row();
		this.table.add(Configuration.SPACER_LABEL);
		this.table.row();
		this.table.add(VolumeLabel);
		this.table.add(VolumeSlider);
		this.table.row();
		this.table.add(musicVolumeLabel);
		this.table.add(musicVolumeSlider);
		this.table.row();
		this.table.add(musicEnabledLabel);
		this.table.add(musicEnabled);
		this.table.row();
		this.table.add(volumeLabel);
		this.table.add(volumeEnabled);
		this.table.row();
		this.table.add(Configuration.SPACER_LABEL);
		this.table.row();
		this.table.add(Configuration.SPACER_LABEL);
		this.table.row();
		this.table.add(backButton);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(165f / 255f, 220f / 255f, 236f / 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		LoadingScreen.soundController.update();
		this.stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		this.stage.draw();
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

	}

	@Override
	public void dispose() {
		this.stage.dispose();
	}
}
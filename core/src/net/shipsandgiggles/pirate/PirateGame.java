package net.shipsandgiggles.pirate;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import net.shipsandgiggles.pirate.screen.ScreenType;

public class PirateGame extends Game {

	/** creation of an instance of the game*/

	private static PirateGame INSTANCE;

	public static PirateGame get() {
		return INSTANCE;
	}

	@Override
	public void create() {
		INSTANCE = this;

		this.setScreen(ScreenType.LOADING.create());
	}

	@Override
	public void dispose() {
		super.dispose();

		INSTANCE = null;
	}

	public void changeScreen(ScreenType screenType) {
		Screen screen = screenType.create();
		this.setScreen(screen);
	}
}
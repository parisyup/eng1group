package net.shipsandgiggles.pirate.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import net.shipsandgiggles.pirate.PirateGame;


public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 1080;
		config.width = 1920;
		config.foregroundFPS = 60;
		config.backgroundFPS = 60;
		config.addIcon("models/Black_Flag.png", Files.FileType.Internal);
		new LwjglApplication(new PirateGame(), config);
	}
}
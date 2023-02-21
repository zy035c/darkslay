package com.woodburn.darkslay;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.woodburn.drop_game.DropGame;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("TestGame™");
		config.setWindowedMode(1280, 720);
		config.useVsync(true);
		config.setForegroundFPS(60);
		// config.setTitle("Dark-Slay-22-10-22");
		// new Lwjgl3Application(new DropGame(), config);
		new Lwjgl3Application(new WoodBurnGame(), config);
	}
}

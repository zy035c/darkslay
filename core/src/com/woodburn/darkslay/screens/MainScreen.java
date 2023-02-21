package com.woodburn.darkslay.screens;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/******************************************************************************
 *  @author  Ziyi Chen
 *  @version  1.0.0
 *  @since 2022-11-12
 *
 *  Controller that deals with game logic of all screens. Only one screen is
 *  showing to user at a time. UI of the screen are impl in other
 *  classes in views.
 *
 ******************************************************************************/

public class MainScreen {

    public static TitleScreen titleScreen = new TitleScreen();
    public static NeowScreen neowScreen = new NeowScreen();

    public enum ScreenOption {
        Title, Neow, Battle
    }
    public static ScreenOption curScreen;

    public MainScreen() {
        curScreen = ScreenOption.Title;

    }

    public void update() {
        if (curScreen == ScreenOption.Title) {
            titleScreen.update();
        }
    }

    public void render(SpriteBatch sb) {
        if (curScreen == ScreenOption.Title) {
            titleScreen.render(sb);
        }
    }

    public void dispose() {

    }

}

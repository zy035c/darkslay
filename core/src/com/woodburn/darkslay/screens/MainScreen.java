package com.woodburn.darkslay.screens;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.woodburn.darkslay.screens.views.main_panels.PanelScreen;
import com.woodburn.darkslay.screens.views.title.TitleScreen;

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
    public static PanelScreen panelScreen = new PanelScreen();

    public enum ScreenOption {
        Title, Neow, Battle, Panel
    }

    public static ScreenOption curScreen;

    public MainScreen() {
        curScreen = ScreenOption.Title;
    }

    public void update() {

        titleScreen.update();

        switch (this.curScreen) { 

            case Panel:
            // if the current screen is a panel screen, do sth

                panelScreen.update();
                break;

        }

    }

    public void render(SpriteBatch sb) {
        titleScreen.render(sb);
        if (curScreen == ScreenOption.Panel) {
            panelScreen.render(sb);
        }
    }

    public void dispose() {

    }

}

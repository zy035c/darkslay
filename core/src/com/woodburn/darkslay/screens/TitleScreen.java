package com.woodburn.darkslay.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.woodburn.darkslay.screens.views.title.MenuButton;
import com.woodburn.darkslay.screens.views.title.TitleBackground;

import java.util.ArrayList;


/******************************************************************************
 *  @author  Ziyi Chen
 *  @version  1.0.0
 *  @since 2022-11-7
 *
 *  Controller that deals with game logic of the title screen, the screen
 *  user sees when launching the game. UI of the screen are impl in other
 *  classes in views.
 *
 ******************************************************************************/

public class TitleScreen {
    // logger

    ArrayList<MenuButton> buttons = new ArrayList<>();

    public TitleBackground bg = new TitleBackground();

    public boolean fadedOut = false;
    public boolean isFadingOut = false;


    public TitleScreen() {

        // bgm?
        int index = 0;
        this.buttons.add(new MenuButton(MenuButton.ClickResult.QUIT, index++));
        this.buttons.add(new MenuButton(MenuButton.ClickResult.SETTINGS, index++));
        this.buttons.add(new MenuButton(MenuButton.ClickResult.STAT, index++));
        this.buttons.add(new MenuButton(MenuButton.ClickResult.PLAY, index++));

    }

    public void update() {
        bg.update();
        for (MenuButton btn : buttons) btn.update();
    }

    private final Color screenColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
    public void render(SpriteBatch sb) {
        bg.render(sb);
        sb.setColor(this.screenColor);
        for (MenuButton btn : buttons) btn.render(sb);
    }

    public void hideMenuButtons() {
        for (MenuButton btn : buttons) btn.hide();
    }

    public void dispose() {

    }

    public void darken() {

    }

    public void superDarken() {

    }

}

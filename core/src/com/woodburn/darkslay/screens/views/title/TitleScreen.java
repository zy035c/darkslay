package com.woodburn.darkslay.screens.views.title;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.utils.Array;
import com.woodburn.darkslay.global_config.DisplayConfig;
import com.woodburn.darkslay.helper.ImageMaster;
import com.woodburn.darkslay.helper.MathHelper;
import com.woodburn.darkslay.screens.MainScreen;

import java.util.ArrayList;


/******************************************************************************
 *  @author  Ziyi Chen
 *  @version  1.0.0
 *  @since 2022-11-7
 *
 * For UI (view) control. Logic and calling is seperated from this class, 
 * so code in this class is focused on dealing with UI logic. MainScreen 
 * should be the controller.
 *
 ******************************************************************************/

public class TitleScreen {
    // logger

    ArrayList<TitleButton> buttons = new ArrayList<>();

    public TitleBackground bg = new TitleBackground();

    public boolean fadedOut = false;
    public boolean isFadingOut = false;


    public TitleScreen() {

        // bgm?
        int index = 0;
        this.buttons.add(new TitleButton(TitleButton.ClickResult.QUIT, index++));
        this.buttons.add(new TitleButton(TitleButton.ClickResult.SETTINGS, index++));
        this.buttons.add(new TitleButton(TitleButton.ClickResult.STAT, index++));
        this.buttons.add(new TitleButton(TitleButton.ClickResult.PLAY, index++));

    }

    public void update() {
        this.bg.update();
        for (TitleButton btn : buttons) btn.update();

        // tint the bg under different dark degree
        if (this.darken) {
            this.screenColor.a = MathHelper.popLerpSnap(this.screenColor.a, 0.8F);
        } else if (superDarken) {
            this.screenColor.a = MathHelper.popLerpSnap(this.screenColor.a, 1.0F);
        } else {
            this.screenColor.a = MathHelper.popLerpSnap(this.screenColor.a, 0.0F);
        }
    }

    /**
    For darkening everything in the screen
    */
    private final Color screenColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);

    /**
     * Darken the background of title screen (but not foreground panels)
     */
    public boolean darken;
    public boolean superDarken;

    public void render(SpriteBatch sb) {
        bg.render(sb);
        sb.setColor(this.screenColor);
        // fill the bg for darkening
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, DisplayConfig.WIDTH, DisplayConfig.HEIGHT);

        for (TitleButton btn : buttons) btn.render(sb);

    }

    public void hideMenuButtons() {
        for (TitleButton btn : buttons) btn.hide();
    }

    public void dispose() {

    }

    /**
     * Darken the background of title screen (but not foreground panels)
     */
    public void darken() {
        this.darken = true;
    }

    public void lighten() {
        this.darken = false;
    }


}

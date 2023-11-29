package com.woodburn.darkslay.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.woodburn.darkslay.global_config.DisplayConfig;
import com.woodburn.darkslay.helper.ImageMaster;
import com.woodburn.darkslay.helper.InputHelper;
import com.woodburn.darkslay.helper.MathHelper;
import com.woodburn.darkslay.screens.reused.CancelButton;
import com.woodburn.darkslay.screens.views.character_panels.CharaSelectScreen;
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
    public static CharaSelectScreen charaSelectScreen = new CharaSelectScreen();

    public static CancelButton cancelButton = new CancelButton();

    public enum ScreenOption {
        Title, Neow, Battle, Panel, CharaSelect;
    }

    public static ScreenOption curScreen;

    public MainScreen() {
        curScreen = ScreenOption.Title;
    }

    public void update() {

        if (isFadingOut) {
            InputHelper.justClickedLeft = false;
            InputHelper.justReleasedClickLeft = false;
            InputHelper.justClickedRight = false;
            InputHelper.justReleasedClickRight = false;
        }

        titleScreen.update();


        switch (curScreen) { 

            case Panel:
                // if the current screen is a panel screen, do sth
                panelScreen.update();
                break;

            case CharaSelect:
                charaSelectScreen.update();
                break;

            default:
                /* */
        }

        fadeOut();
    }

    public static boolean isFadingOut = false;
    public static boolean fadedOut = false;

    public void render(SpriteBatch sb) {

        titleScreen.render(sb);

        /* Display ver. number */

        switch (curScreen) {
            case Panel:
                panelScreen.render(sb);
                break;
            case CharaSelect:
                charaSelectScreen.render(sb);
                break;
            default:
                break;
        }

        sb.setColor(overlayColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, DisplayConfig.WIDTH, DisplayConfig.HEIGHT);
        
    }


    public void dispose() {

    }

    private static Color overlayColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);

    /**
     * Fade out the main screen, screen outside the game, and start the game scene
      */
    private void fadeOut() {
        if (isFadingOut && !fadedOut) {
            overlayColor.a += Gdx.graphics.getDeltaTime();
            if (overlayColor.a > 1.0F) {
                overlayColor.a = 1.0F;
                fadedOut = true;
                // FontHelper.ClearLeaderboardFontTextures();
            }

        } else if (overlayColor.a != 0.0F) {
            overlayColor.a -= Gdx.graphics.getDeltaTime() * 2.0F;
            if (overlayColor.a < 0.0F) {
                overlayColor.a = 0.0F;
            }
        }
    }

}

package com.woodburn.darkslay.screens.views.main_panels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.woodburn.darkslay.global_config.DisplayConfig;
import com.woodburn.darkslay.helper.InputHelper;
import com.woodburn.darkslay.localization.UIStrings;
import com.woodburn.darkslay.screens.MainScreen;
import com.woodburn.darkslay.screens.reused.CancelButton;
import com.woodburn.drop_game.MainMenuScreen;

/******************************************************************************
 *  @author  Ziyi Chen
 *  @version  1.0.1
 *  @since 2023-04-04
 *
 *  Gamemode select Screen, opens up when you click the play
 *
 ******************************************************************************/

public class PanelScreen {

    /** 
        Call when its opened.
        At last, mainScreen.screenOption will be set to panelScreen
        so update() of this class will be called instead the update()
        of another screen class
    */ 
    public void open() {
        MainScreen.curScreen = MainScreen.ScreenOption.Panel;
        initializePanels();

        String backTextString = UIStrings.getStringByPath("ui/cancel");
        MainScreen.cancelButton.show(backTextString);
        MainScreen.titleScreen.darken();
    }


    public ArrayList<PanelScreenButton> panels = new ArrayList<>();
    
    /**
     * The hashmap that stores the name of different play mode and their text.
     * Might work for different language options. 
     */
    public static Map<String, String> TEXT;

    private static final float PANEL_Y = DisplayConfig.HEIGHT / 2.0F;

    public void initializePanels() {

        TEXT = new HashMap<>();
        initText();
        
        this.panels.clear();

        /* */
        this.panels.add(
            new PanelScreenButton(
                PanelScreenButton.MainPanelResult.PLAY_NORMAL, 
                PanelScreenButton.PanelColor.GRAY, 
                DisplayConfig.WIDTH / 2.0F - (450F * DisplayConfig.scale), 
                PANEL_Y
            )
        );

        /* */
        this.panels.add(
            new PanelScreenButton(
                PanelScreenButton.MainPanelResult.PLACEHOLDER, 
                PanelScreenButton.PanelColor.RED, 
                DisplayConfig.WIDTH / 2.0F, 
                PANEL_Y
            )
        );

        /* */
        this.panels.add(
            new PanelScreenButton(
                PanelScreenButton.MainPanelResult.INFINITE, 
                PanelScreenButton.PanelColor.BLUE, 
                DisplayConfig.WIDTH / 2.0F + (450F * DisplayConfig.scale), 
                PANEL_Y
            )
        );
    }

    /**
     * Wait for update: get from localization.
     * Init the HashMap to store words for panels
     * Might be modified for future language pack
     */
    private void initText() {
        
        TEXT.put("PLAY_NORMAL", "Standard");
        TEXT.put("INFINITE", "Infinite");
        TEXT.put("PLACEHOLDER", "Drangleic...");

        TEXT.put("PLAY_NORMAL desc", "Embark on a quest to Slay the Spire.");
        TEXT.put("PLACEHOLDER desc", "Placeholder ...");
        TEXT.put("INFINITE desc", "Customize your own run with unique modifiers.");
    }

    public void update() {

        MainScreen.cancelButton.update();

        /* Cancel button handler */
        if (MainScreen.cancelButton.hb.clicked) {
            // InputHelper.pressedEscape = false;
            MainScreen.curScreen = MainScreen.ScreenOption.Title;
            MainScreen.titleScreen.lighten();
            MainScreen.cancelButton.clickedTail("Panel Screen");
        }

        /* Call update of all buttons */
        for (PanelScreenButton panel: this.panels) {
            panel.update();
        }

    }

    public void render(SpriteBatch sb) {
        for (PanelScreenButton panel: this.panels) {
            panel.render(sb);
        }

        MainScreen.cancelButton.render(sb);
    }

}

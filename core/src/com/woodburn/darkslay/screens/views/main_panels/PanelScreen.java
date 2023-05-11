package com.woodburn.darkslay.screens.views.main_panels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.woodburn.darkslay.global_config.DisplayConfig;
import com.woodburn.darkslay.helper.InputHelper;
import com.woodburn.darkslay.screens.MainScreen;
import com.woodburn.drop_game.MainMenuScreen;

/******************************************************************************
 *  @author  Ziyi Chen
 *  @version  1.0.1
 *  @since 2023-04-04
 *
 *  Screen that opens up when you click the play
 *
 ******************************************************************************/

public class PanelScreen {

    public MainCancelButton cancelButton = new MainCancelButton();

    /** 
        Call when its opened.
        At last, mainScreen.screenOption will be set to panelScreen
        so update() of this class will be called instead the update()
        of another class
    */ 
    public void open() {
        initializePanels();
        MainScreen.curScreen = MainScreen.ScreenOption.Panel;
        MainScreen.titleScreen.darken();
    }


    public ArrayList<MainPanelButton> panels = new ArrayList<>();
    
    /**
     * The hashmap that stores the name of different play mode and their text.
     * Might work for different language options. 
     */
    public static Map<String, String> TEXT;

    private static final float PANEL_Y = DisplayConfig.HEIGHT / 2.0F;

    public void initializePanels() {

        TEXT = new HashMap<>();
        
        initText();
        
        // this.panels.clear();

        this.panels.add(
            new MainPanelButton(
                MainPanelButton.MainPanelResult.PLAY_NORMAL, 
                MainPanelButton.PanelColor.BLUE, 
                DisplayConfig.WIDTH / 2.0F - (450F * DisplayConfig.scale), 
                PANEL_Y)
        );
        this.panels.add(
            new MainPanelButton(
                MainPanelButton.MainPanelResult.PLACEHOLDER, 
                MainPanelButton.PanelColor.RED, 
                DisplayConfig.WIDTH / 2.0F, 
                PANEL_Y)
        );
    }

    /**
     * Init the HashMap to store words for panels
     * Might be modified for future language pack
     */
    private void initText() {
        
        TEXT.put("PLAY_NORMAL", "PLAY");
        TEXT.put("PLACEHOLDER", "PLACEHOLDER");
    }

    public void update() {

        this.cancelButton.update();

        // if cancel button is clicked...
        if (this.cancelButton.hb.clicked) {
            // InputHelper.pressedEscape = false;
            this.cancelButton.hide();
            MainScreen.curScreen = MainScreen.ScreenOption.Title;
            MainScreen.titleScreen.lighten();
        }

        // call update of all buttons
        for (MainPanelButton panel: this.panels) {
            panel.update();
        }

        // if panel is clicked...

    }

    public void render(SpriteBatch sb) {
        for (MainPanelButton panel: this.panels) {
            panel.render(sb);
        }

        this.cancelButton.render(sb);
    }

    public void hide() {
    }

    public void refresh() {}

}

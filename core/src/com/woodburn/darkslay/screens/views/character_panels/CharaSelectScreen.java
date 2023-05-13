package com.woodburn.darkslay.screens.views.character_panels;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.woodburn.darkslay.creatures.AbstractPlayer.PlayerClass;
import com.woodburn.darkslay.global_config.DisplayConfig;
import com.woodburn.darkslay.helper.FontHelper;
import com.woodburn.darkslay.helper.MathHelper;
import com.woodburn.darkslay.screens.MainScreen;
import com.woodburn.darkslay.screens.MainScreen.ScreenOption;
import com.woodburn.darkslay.screens.views.main_panels.MainCancelButton;
import com.woodburn.darkslay.screens.views.main_panels.MainPanelButton;
import com.woodburn.drop_game.MainMenuScreen;

public class CharaSelectScreen {
    
    public ArrayList<CharaButton> options = new ArrayList<>();

    public static MainCancelButton cancelButton;
    /** ! For tentative */
    public static MainCancelButton confirmButton;

    public void open() {
        // MainScreen.ScreenOption
        initialize();


        MainScreen.curScreen = MainScreen.ScreenOption.CharaSelect; // done
    }

    private void initialize() {

        cancelButton = new MainCancelButton();

        this.options.add(new CharaButton(PlayerClass.Ironclad));
        this.options.add(new CharaButton(PlayerClass.Silent));
        this.options.add(new CharaButton(PlayerClass.Seeker));
        this.options.add(new CharaButton(PlayerClass.Dragonslayer));


        /*
         * Place buttons to correct places
         */
        int count = this.options.size();
        float offsetX = DisplayConfig.WIDTH / 2.0F - 330.0F * DisplayConfig.scale;
        for (int i = 0; i < count; ++i) {
            this.options.get(i).hb.move(
                offsetX + i * 220.0F * DisplayConfig.scale,
                190.0F * DisplayConfig.yScale
            ); 
        }

    }


    boolean anySelected = false;


    public void update() {


        this.anySelected = false;

        for (CharaButton c : this.options) {
            c.update();

            if (c.selected) {
                this.anySelected = true;
            }
        }

        /* Deal with cancel and confirm button ... */

        if (this.anySelected) {
            this.bgCharColor.a = MathHelper.fadeLerpSnap(this.bgCharColor.a, 1.0F);
            this.bg_y_offset = MathHelper.fadeLerpSnap(this.bg_y_offset, -0.0F);
        } else {
            this.bgCharColor.a = MathHelper.fadeLerpSnap(this.bgCharColor.a, 0.0F);
        }


        // MainScreen.superDarken = this.anySelected;

    }

    /**
     * Update funtion of cancel AND? confirm button
     */
    public void updatePanels() {

    }

    public void fadeOut() {

    }

    /* UI params */
    private Color bgCharColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
    private float bg_y_offset = 0.0F;

    public Texture bgCharImg;

    public void render(SpriteBatch sb) {
        
        sb.setColor(this.bgCharColor); /* Prepare color */
        if (this.bgCharImg != null) {

            /* 16 : 9 */
            sb.draw(
                this.bgCharImg, 
                DisplayConfig.WIDTH / 2.0F - 960.0F, 
                DisplayConfig.HEIGHT / 2.0F - 600.0F + this.bg_y_offset, 
                960.0F, 
                600.0F, 
                1920.0F, 
                1200.0F, 
                DisplayConfig.scale, 
                DisplayConfig.scale, 
                0.0F, 
                0, 
                0, 
                1920, 
                1200, 
                false, 
                false
            );
        } /* if ends */
        cancelButton.render(sb);
        
        for (CharaButton c : this.options) {
            c.render(sb);
        }

        // FontHelper.renderFontCenteredHeight(sb, null, null, bg_y_offset, bg_y_offset, bg_y_offset, bgCharColor); 
    }

    /**
     * If a new character is selected: move the bg to outside
     */
    public void justSelected() {
        this.bg_y_offset = 0.0F;
    }

    public void deselectOtherOptions(CharaButton selectedBtn) {
        for (CharaButton c : this.options) {
            if (c != selectedBtn) {
                c.selected = false;
            }
        }
    }
    

}

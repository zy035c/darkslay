package com.woodburn.darkslay.screens.views.character_panels;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.woodburn.darkslay.creatures.AbstractPlayer.PlayerClass;
import com.woodburn.darkslay.global_config.DisplayConfig;
import com.woodburn.darkslay.global_config.Settings;
import com.woodburn.darkslay.helper.FontHelper;
import com.woodburn.darkslay.helper.MathHelper;
import com.woodburn.darkslay.localization.UIStrings;
import com.woodburn.darkslay.screens.MainScreen;
import com.woodburn.darkslay.screens.reused.ConfirmButton;

public class CharaSelectScreen {
    
    public ArrayList<CharaButton> options = new ArrayList<>();

    public void open() {
        // MainScreen.ScreenOption
        initialize();


        MainScreen.curScreen = MainScreen.ScreenOption.CharaSelect; // done
    }

    private void initialize() {

        String backTextString = UIStrings.getStringByPath("ui/cancel");
        MainScreen.cancelButton.show(backTextString);

        /* This will overwrite old confirmButton */
        confirmButton = new ConfirmButton(); 

        this.options.clear();
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


    boolean anySelected;

    /* The button to start game !!! */
    public ConfirmButton confirmButton;
    String confirmText = UIStrings.getStringByPath("ui/confirm");

    public void update() {


        this.anySelected = false;

        for (CharaButton c : this.options) {
            c.update();

            if (c.selected) {
                this.anySelected = true;
            }
        }

        /* 
         *  Cancel Button Handler 
         */
        MainScreen.cancelButton.update();
        if (MainScreen.cancelButton.hb.clicked) {
            // MainScreen.superDarken = false; /* Not really necce */
            // InputHelper.pressedEscape = false;
            MainScreen.cancelButton.clickedTail("CharaSelectScreen");

            MainScreen.panelScreen.open();
            
            /* Reset current Screen */
            for (CharaButton b : this.options) b.selected = false;
            this.bgCharColor.a = 0.0F;
            this.anySelected = false;          
        }

        if (this.anySelected) {
            this.bgCharColor.a = MathHelper.fadeLerpSnap(this.bgCharColor.a, 1.0F);
            this.bg_y_offset = MathHelper.fadeLerpSnap(this.bg_y_offset, -0.0F);
        } else {
            this.bgCharColor.a = MathHelper.fadeLerpSnap(this.bgCharColor.a, 0.0F);
        }


        /* 
         *  Confirm Button Handler 
         */
        this.confirmButton.update();
        if (this.confirmButton.hb.clicked) {
            this.confirmButton.clickedTail("CharaSelectScreen");

            this.confirmButton.isDisabled = true; /* Cannot click many times */
             
        }


        // MainScreen.superDarken = this.anySelected;
    }

    public void fadeOut() {

    }

    /* UI params */
    private Color bgCharColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
    private float bg_y_offset = 0.0F;

    public Texture bgCharImg;

    public void render(SpriteBatch sb) {
        
        sb.setColor(this.bgCharColor); /* Prepare color */
        /* If a chara is selcted, the bg is not null, render the chara portrait. */
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
        
        MainScreen.cancelButton.render(sb);
        this.confirmButton.render(sb);
        
        for (CharaButton c : this.options) {
            c.render(sb);
        }

        /* Display msg: select your character. */
        // if (!anySelected) {
            FontHelper.renderFontCenteredHeight(
                sb,
                FontHelper.losePowerFont,
                "Recall. Your past and your name...",
                DisplayConfig.WIDTH / 2.0F,
                340.0F * DisplayConfig.yScale,
                Settings.CREAM_COLOR,
                1.2F    
            );
        // }

        sb.setColor(Color.WHITE); /* Resume color */

    }

    /**
     * If a new character is selected: move the bg to outside
     */
    public void justSelected() {
        this.bg_y_offset = 10.0F;
    }

    public void deselectOtherOptions(CharaButton selectedBtn) {
        for (CharaButton c : this.options) {
            if (c != selectedBtn) {
                c.selected = false;
            }
        }
    }
    

}

package com.woodburn.darkslay.screens.views.character_panels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.woodburn.darkslay.creatures.AbstractPlayer.PlayerClass;
import com.woodburn.darkslay.global_config.DisplayConfig;
import com.woodburn.darkslay.global_config.Settings;
import com.woodburn.darkslay.helper.FontHelper;
import com.woodburn.darkslay.helper.HitBox;
import com.woodburn.darkslay.helper.ImageMaster;
import com.woodburn.darkslay.helper.InputHelper;
import com.woodburn.darkslay.helper.MathHelper;
import com.woodburn.darkslay.screens.MainScreen;
import com.woodburn.darkslay.creatures.characters.Character;

public class CharaButton {
    
    public HitBox hb;
    private static final float HB_W = 150.0F * DisplayConfig.scale;


    Character character;

    public CharaButton(PlayerClass p) {

        this.character = Character.instantiate(p);
        this.hb = new HitBox(HB_W, HB_W);
    }

    public void update() {
        updateHitbox();

        /* Update info position. */
        if (this.selected) {
            this.infoX = MathHelper.uiLerpSnap(this.infoX, DEST_INFO_X);
        } else {
            this.infoX = MathHelper.uiLerpSnap(this.infoX, START_INFO_X);
        }
    }

    /** booleans that keep track of locking and selecting */
    public boolean locked = false, selected = false;
 
    private void updateHitbox() {
        this.hb.update();
        if (this.hb.justHovered) {
            /* play sound */
        }

        if (this.hb.hovered && this.locked) {
            /* render tip */
        }

        if (InputHelper.justClickedLeft && !this.locked && this.hb.hovered) {
            /* play lock sound */
            this.hb.clickStarted = true;
        }

        if (this.hb.clicked) {
            this.hb.clicked = false;

            if (!this.selected) {
                /* Deselect all the other option btns */
                MainScreen.charaSelectScreen.deselectOtherOptions(this);
                this.selected = true;
                MainScreen.charaSelectScreen.justSelected();

                // CharaSelectScreen.confirmButton.show();

                MainScreen.charaSelectScreen.bgCharImg = this.character.portraitImg;

                /* enable confirm button */
                MainScreen.charaSelectScreen.bgCharImg = this.character.portraitImg;
            }
        }

    }

    private Color glowColor = new Color(1.0F, 0.8F, 0.2F, 0.0F);

    public void render(SpriteBatch sb) {
        
        /* Render option button */
        if (this.selected) {
            /* A yellow glowing bg should periodically change its brightness. */
            this.glowColor.a = 0.25F +
                (MathUtils.cosDeg((float)(System.currentTimeMillis() / 4L % 360L)) + 1.25F) / 3.5F;
            sb.setColor(glowColor);
        } else {
            sb.setColor(
                new Color(0.0F, 0.0F, 0.0F, 0.5F) /* Black outline color (?) */ 
            ); 
        }

        sb.draw(
            ImageMaster.CHARA_SELECT_HIGHLIGHT_BTN,
            this.hb.cX - 110.0F,
            this.hb.cY - 110.0F,
            110.0F,
            110.0F,
            220.0F, 
            220.0F, 
            DisplayConfig.scale, 
            DisplayConfig.scale, 
            0.0F, 
            0, 
            0, 
            220, 
            220, 
            false, 
            false
        );

        if (this.selected || this.hb.hovered) {
            sb.setColor(Color.WHITE);
        } else {
            sb.setColor(Color.LIGHT_GRAY);
        }

        sb.draw(
            this.character.selectBtnImg, 
            this.hb.cX - 110.0F, 
            this.hb.cY - 110.0F, 
            110.0F, 
            110.0F, 
            220.0F, 
            220.0F, 
            DisplayConfig.scale, 
            DisplayConfig.scale, 
            0.0F, 
            0, 
            0, 
            220, 
            220, 
            false, 
            false
        );

        renderInfo(sb);

        sb.setColor(Color.WHITE); /* resume color */
        this.hb.render(sb);
    }

    private static final float DEST_INFO_X = 200.0F * DisplayConfig.scale;
    private static final float START_INFO_X = -800.0F * DisplayConfig.xScale;

    private float infoX = START_INFO_X, infoY = DisplayConfig.HEIGHT / 2.0F;
    private static final float NAME_OFFSET_Y = 200.0F * DisplayConfig.scale;

    private void renderInfo(SpriteBatch sb) {

        if (this.selected) {

            /* Render name title */
            FontHelper.renderSmartText(
                sb, 
                FontHelper.bannerNameFont, 
                this.character.nameString,
                this.infoX - 35.0F * DisplayConfig.scale, 
                this.infoY + NAME_OFFSET_Y, 
                99999.0F,
                38.0F * DisplayConfig.scale, 
                Settings.GOLD_COLOR
            );

            sb.draw(
                ImageMaster.HEALTH_ICON, 
                this.infoX - 10.0F * DisplayConfig.scale - 32.0F,
                this.infoY + 95.0F * DisplayConfig.scale - 32.0F, 
                32.0F, 
                32.0F, 
                64.0F, 
                64.0F, 
                DisplayConfig.scale,
                DisplayConfig.scale, 
                0.0F, 
                0, 
                0, 
                64, 
                64, 
                false, 
                false
            );

            FontHelper.renderSmartText(
                sb, 
                FontHelper.tipHeaderFont,
                Integer.toString(this.character.startingMaxHealth),
                this.infoX + 18.0F * DisplayConfig.scale, 
                this.infoY + 102.0F * DisplayConfig.scale, 
                10000.0F, 
                10000.0F,
                Settings.RED_TEXT_COLOR
            );

            sb.draw(
                ImageMaster.GOLD_ICON, 
                this.infoX + 190.0F * DisplayConfig.scale - 32.0F,
                this.infoY + 95.0F * DisplayConfig.scale - 32.0F, 
                32.0F, 
                32.0F, 
                64.0F, 
                64.0F, 
                DisplayConfig.scale,
                DisplayConfig.scale,
                0.0F, 
                0, 
                0, 
                64, 
                64, 
                false, 
                false
            );

            FontHelper.renderSmartText(
                sb, 
                FontHelper.tipHeaderFont, 
                Integer.toString(this.character.startingGold), 
                this.infoX + 220.0F * DisplayConfig.scale,
                this.infoY + 102.0F * DisplayConfig.scale, 
                10000.0F, 
                10000.0F, 
                Settings.GOLD_COLOR
            );

            /* Display character description. */
            FontHelper.renderSmartText(
                sb, 
                FontHelper.tipHeaderFont, 
                this.character.description,
                this.infoX - 26.0F * DisplayConfig.scale, 
                this.infoY + 40.0F * DisplayConfig.scale, 
                10000.0F,
                30.0F * DisplayConfig.scale, 
                Settings.CREAM_COLOR
            );
        } /* If ends */
    }

}

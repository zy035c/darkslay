package com.woodburn.darkslay.screens.reused;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.woodburn.darkslay.global_config.DisplayConfig;
import com.woodburn.darkslay.global_config.Settings;
import com.woodburn.darkslay.helper.FontHelper;
import com.woodburn.darkslay.helper.HitBox;
import com.woodburn.darkslay.helper.ImageMaster;
import com.woodburn.darkslay.helper.InputHelper;
import com.woodburn.darkslay.localization.UIStrings;


public class ConfirmButton {

    /**
     * Why both uiString and buttonText?
     */
    private static final String uiString = UIStrings.getStringByPath("ui/confirm");

    /*
    private static final int W = 512;
    private static final int H = 256;
    */
    
   
    /**
     * Why both uiString and buttonText?
     */
    public String buttonText = "NOT_SET";

    private static final float TEXT_OFFSET_X = 136.0F * DisplayConfig.scale;
    private static final float TEXT_OFFSET_Y = 57.0F * DisplayConfig.scale;

    private static final float HITBOX_W = 300.0F * DisplayConfig.scale;
    private static final float HITBOX_H = 100.0F * DisplayConfig.scale;

    public HitBox hb = new HitBox(0.0F, 0.0F, HITBOX_W, HITBOX_H);

    public ConfirmButton() {
        this.hb.move(
            SHOW_X + 106.0F * DisplayConfig.scale, 
            DRAW_Y + 60.0F * DisplayConfig.scale
        );
    }


    /**
     * Confirm btn might be disabled...?
     */
    public boolean isDisabled = false;

    private static final float SHOW_X = DisplayConfig.WIDTH - 256.0F * DisplayConfig.scale;
    private static final float DRAW_Y = 128.0F * DisplayConfig.scale;

    public static final float HIDE_X = SHOW_X + 400.0F * DisplayConfig.scale;
    public float current_x = HIDE_X;
    private float target_x = this.current_x;

    public boolean isHidden = true;

    public void update() {
        
        if (!this.isHidden) {
            updateGlow();
            this.hb.update();

            if (InputHelper.justClickedLeft && this.hb.hovered) {
                this.hb.clickStarted = true;
                // CardCrawlGame.sound.play("UI_CLICK_1");
            }
            if (this.hb.justHovered) {
                // CardCrawlGame.sound.play("UI_HOVER");
            }

        }

        /* Interpolation on x */
        if (this.current_x != this.target_x) {
            this.current_x = MathUtils.lerp(this.current_x, this.target_x, Gdx.graphics.getDeltaTime() * 9.0F);
            if (Math.abs(this.current_x - this.target_x) < Settings.UI_SNAP_THRESHOLD) {
                this.current_x = this.target_x;
            }
        }
    }

    /**
     * Tail of clicking on ConfirmButton, no matter what invoker.
     * Should hide btn and reset booleans. 
     * @param className
     */
    public void clickedTail(String className) {
        if (this.hb.clicked && this.current_x != HIDE_X) {
            this.hb.clicked = false;
            this.hb.clickStarted = false;
            hide();
        } else {
            System.out.println("[WARNING] Invalid call on confirmButton.clickedTail() from " + className);
        }
        
    }


    private float glowAlpha = 0.0F;
    private Color glowColor = Color.WHITE.cpy();

    private void updateGlow() {
        this.glowAlpha += Gdx.graphics.getDeltaTime() * 3.0F;
        if (this.glowAlpha < 0.0F) {
            this.glowAlpha *= -1.0F;
        }
        float tmp = MathUtils.cos(this.glowAlpha);
        if (tmp < 0.0F) {
            this.glowColor.a = -tmp / 2.0F + 0.3F;
        } else {
            this.glowColor.a = tmp / 2.0F + 0.3F;
        }
    }

    public boolean hovered() {
        return this.hb.hovered;
    }

    public void hide() {
        if (!this.isHidden) {
            this.hb.hovered = false;
            InputHelper.justClickedLeft = false;
            this.target_x = HIDE_X;
            this.isHidden = true;
        }
    }

    public void hideInstantly() {
        if (!this.isHidden) {
            this.hb.hovered = false;
            InputHelper.justClickedLeft = false;
            this.target_x = HIDE_X;
            this.current_x = this.target_x;
            this.isHidden = true;
        }
    }

    public void show(String buttonText) {
        if (this.isHidden) {
            this.glowAlpha = 0.0F;
            // this.current_x = HIDE_X;
            this.isHidden = false;
            this.buttonText = buttonText;
            this.target_x = SHOW_X;
        }
    }

    public void showInstantly(String buttonText) {
        this.current_x = SHOW_X;
        this.target_x = SHOW_X;
        this.isHidden = false;
        this.buttonText = buttonText;
        this.hb.hovered = false;
    }


    private static final Color HOVER_BLEND_COLOR = new Color(1.0F, 1.0F, 1.0F, 0.3F);

    public void render(SpriteBatch sb) {

        /* System.out.println("current_x " + current_x); */

        sb.setColor(Color.WHITE);
        renderThings(ImageMaster.CONFIRM_BUTTON_SHADOW, sb);

        sb.setColor(this.glowColor);
        renderThings(ImageMaster.CONFIRM_BUTTON_OUTLINE, sb);

        sb.setColor(Color.WHITE);
        renderThings(ImageMaster.CONFIRM_BUTTON, sb);

        if (this.hb.hovered && !this.hb.clickStarted) {
            sb.setBlendFunction(770, 1);
            sb.setColor(HOVER_BLEND_COLOR);
            /* Render button highlight when hovered */
            renderThings(ImageMaster.CONFIRM_BUTTON, sb);
            sb.setBlendFunction(770, 771);
        }

        if (this.isDisabled) {
            Color TEXT_DISABLED_COLOR = new Color(0.6F, 0.6F, 0.6F, 1.0F); 
            renderLabelText(TEXT_DISABLED_COLOR, sb);
        } else if (this.hb.clickStarted) {
            renderLabelText(Color.LIGHT_GRAY, sb);
        } else if (this.hb.hovered) {
            renderLabelText(Settings.LIGHT_YELLOW_COLOR, sb);
        } else {
            renderLabelText(Settings.LIGHT_YELLOW_COLOR, sb);
        }

        if (!this.isHidden) {
            this.hb.render(sb);
        }

        sb.setColor(Color.WHITE); /* resume color */
    }

    private void renderThings(Texture t, SpriteBatch sb) {
        sb.draw(
            t, 
            this.current_x - 256.0F, 
            DRAW_Y - 128.0F, 
            256.0F, 
            128.0F, 
            512.0F,
            256.0F, 
            DisplayConfig.scale, 
            DisplayConfig.scale, 
            0.0F, 
            0, 
            0, 
            512, 
            256, 
            false, 
            false
        );
    }


    private void renderLabelText(Color c, SpriteBatch sb) {
        FontHelper.renderFontCentered(
            sb, 
            FontHelper.buttonLabelFont, 
            this.buttonText,
            this.current_x + TEXT_OFFSET_X, 
            DRAW_Y + TEXT_OFFSET_Y, 
            c
        );
    }

}

/*
 * Location: /Users/chen/Library/Application
 * Support/Steam/steamapps/common/SlayTheSpire/SlayTheSpire.app/Contents/
 * Resources/desktop-1.0.jar!/com/megacrit/cardcrawl/ui/buttons/CancelButton.
 * class
 * Java compiler version: 8 (52.0)
 * JD-Core Version: 1.1.3
 */
package com.woodburn.darkslay.screens.reused;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.woodburn.darkslay.global_config.DisplayConfig;
import com.woodburn.darkslay.global_config.Settings;
import com.woodburn.darkslay.helper.FontHelper;
import com.woodburn.darkslay.helper.HitBox;
import com.woodburn.darkslay.helper.ImageMaster;
import com.woodburn.darkslay.helper.InputHelper;
import com.woodburn.darkslay.localization.UIStrings;
import com.woodburn.darkslay.screens.MainScreen;
import com.woodburn.darkslay.screens.MainScreen.ScreenOption;

public class CancelButton {

    private static final String uiString = UIStrings.getStringByPath("ui/cancel");

    private static final int W = 512;
    private static final int H = 256;

    private static final Color HOVER_BLEND_COLOR = new Color(1.0F, 1.0F, 1.0F, 0.4F);

    private static final float SHOW_X = 256.0F * DisplayConfig.scale;
    private static final float DRAW_Y = 128.0F * DisplayConfig.scale;

    public static final float HIDE_X = SHOW_X - 400.0F * DisplayConfig.scale;
    public float current_x = HIDE_X;
    private float target_x = this.current_x;
    public boolean isHidden = true;
    
    private float glowAlpha = 0.0F;
    private Color glowColor = Settings.GOLD_COLOR.cpy();

    public String buttonText = "NOT_SET";

    private static final float TEXT_OFFSET_X = -136.0F * DisplayConfig.scale;
    private static final float TEXT_OFFSET_Y = 57.0F * DisplayConfig.scale;

    private static final float HITBOX_W = 300.0F * DisplayConfig.scale;
    private static final float HITBOX_H = 100.0F * DisplayConfig.scale;

    public HitBox hb = new HitBox(0.0F, 0.0F, HITBOX_W, HITBOX_H);

    public CancelButton() {
        this.hb.move(SHOW_X - 106.0F * DisplayConfig.scale, DRAW_Y + 60.0F * DisplayConfig.scale);
    }

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

            if (this.hb.clicked /* || ((InputHelper.pressedEscape || CInputActionSet.cancel.isJustPressed()) */ 
                    && this.current_x != HIDE_X) {

                // AbstractDungeon.screenSwap = false;
                // InputHelper.pressedEscape = false;

                /*
                if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.MAP) {
                    CardCrawlGame.sound.play("MAP_CLOSE", 0.05F);
                }
                */

                /*
                if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID
                        && (AbstractDungeon.gridSelectScreen.forUpgrade || AbstractDungeon.gridSelectScreen.forTransform
                                || AbstractDungeon.gridSelectScreen.forPurge)) {

                    if (AbstractDungeon.gridSelectScreen.confirmScreenUp) {
                        AbstractDungeon.gridSelectScreen.cancelUpgrade();
                    } else {
                        AbstractDungeon.closeCurrentScreen();
                        if (AbstractDungeon.getCurrRoom() instanceof RestRoom) {
                            RestRoom r = (RestRoom) AbstractDungeon.getCurrRoom();
                            r.campfireUI.reopen();
                        }
                        return;
                    }
                } else {
                    if (AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() instanceof TreasureRoomBoss
                            && AbstractDungeon.screen == AbstractDungeon.CurrentScreen.BOSS_REWARD) {

                        TreasureRoomBoss r = (TreasureRoomBoss) AbstractDungeon.getCurrRoom();
                        r.chest.close();
                    }
                    AbstractDungeon.closeCurrentScreen();
                }
                */
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
     * Tail of clicking on CancelButton, no matter what invoker.
     * Should hide btn and reset booleans. 
     * @param className
     */
    public void clickedTail(String className) {
        if (this.hb.clicked && this.current_x != HIDE_X) {
            this.hb.clicked = false;
            this.hb.clickStarted = false;
            hide();
        } else {
            System.out.println("[WARNING] Invalid call on cancelButton.clickedTail() from " + className);
        }
        
    }

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
            this.current_x = HIDE_X;
            this.target_x = SHOW_X;
            this.isHidden = false;
            this.buttonText = buttonText;
        } else {
            this.current_x = HIDE_X;
            this.buttonText = buttonText;
        }
        this.hb.hovered = false;
    }

    public void showInstantly(String buttonText) {
        this.current_x = SHOW_X;
        this.target_x = SHOW_X;
        this.isHidden = false;
        this.buttonText = buttonText;
        this.hb.hovered = false;
    }

    public void render(SpriteBatch sb) {

        sb.setColor(Color.WHITE);
        renderThings(ImageMaster.CANCEL_BUTTON_SHADOW, sb);

        sb.setColor(this.glowColor);
        renderThings(ImageMaster.CANCEL_BUTTON_OUTLINE, sb);

        sb.setColor(Color.WHITE);
        renderThings(ImageMaster.CANCEL_BUTTON, sb);

        if (this.hb.hovered && !this.hb.clickStarted) {
            sb.setBlendFunction(770, 1);
            sb.setColor(HOVER_BLEND_COLOR);
            /* Render button highlight when hovered */
            renderThings(ImageMaster.CANCEL_BUTTON, sb);
            sb.setBlendFunction(770, 771);
        }

        Color tmpColor = Settings.LIGHT_YELLOW_COLOR;
        if (this.hb.clickStarted) {
            tmpColor = Color.LIGHT_GRAY;
        }
    
        FontHelper.renderFontCentered(
            sb, 
            FontHelper.buttonLabelFont, 
            this.buttonText,
            this.current_x + TEXT_OFFSET_X, 
            DRAW_Y + TEXT_OFFSET_Y, 
            tmpColor
        );

        if (!this.isHidden) {
            this.hb.render(sb);
        }

        sb.setColor(Color.WHITE); /* resume color */
    }

    private void renderThings(Texture t, SpriteBatch sb) {
        sb.draw(
            t, 
            this.current_x - 256.0F, DRAW_Y - 128.0F, 
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

}

/*
 * Location: /Users/chen/Library/Application
 * Support/Steam/steamapps/common/SlayTheSpire/SlayTheSpire.app/Contents/
 * Resources/desktop-1.0.jar!/com/megacrit/cardcrawl/ui/buttons/CancelButton.
 * class
 * Java compiler version: 8 (52.0)
 * JD-Core Version: 1.1.3
 */
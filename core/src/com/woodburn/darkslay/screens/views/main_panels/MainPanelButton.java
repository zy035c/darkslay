package com.woodburn.darkslay.screens.views.main_panels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.woodburn.darkslay.core.GameStat;
import com.woodburn.darkslay.global_config.DisplayConfig;
import com.woodburn.darkslay.global_config.Settings;
import com.woodburn.darkslay.helper.FontHelper;
import com.woodburn.darkslay.helper.HitBox;
import com.woodburn.darkslay.helper.ImageMaster;
import com.woodburn.darkslay.helper.InputHelper;
import com.woodburn.darkslay.helper.MathHelper;
import com.woodburn.darkslay.screens.MainScreen;

/******************************************************************************
 * @author Ziyi Chen
 * @version 1.0.1
 * @since 2023-04-04
 *
 *        Card-shape panels that show when PLAY is clicked, are used to select
 *        game mode. PanelScreen should arrange these card panels and move them to
 *        assigned location.
 *
 ******************************************************************************/
public class MainPanelButton {

    public HitBox hb = new HitBox(400.0F * DisplayConfig.scale, 700.0F * DisplayConfig.scale);
    public MainPanelResult result;

    private float animTime;
    private float animTimer;

    /**
     * NOT GDX.color. An enum of MainPanelButton.PanelColor. GRAY; BLUE; RED; BEIGE
     */
    public PanelColor panelColor;

    public MainPanelButton(MainPanelResult r, PanelColor color, float x, float y) {
        this.result = r;

        this.panelColor = color;
        this.hb.move(x, y);

        initialize();

        // Control the speed of the animate-in. End is 0.35F
        this.animTime = MathUtils.random(0.2F, 0.5F);
        this.animTimer = this.animTime;
    }



    public enum MainPanelResult {
        PLAY_NORMAL, PLACEHOLDER, INFINITE;
    }

    /**
     * Color of the card-shape panel
     */
    public enum PanelColor {
        RED, BLUE, BEIGE, GRAY;
    }

    private Texture panelImg = ImageMaster.PANEL_BG_BLUE;
    private String header = "error";
    private String description = "null";

    private Texture portraitImg;

    public void initialize() {

        this.panelImg = ImageMaster.PANEL_BG_BEIGE;

        // set text for different result
        switch (this.result) {

            case PLAY_NORMAL:
   
                this.header = PanelScreen.TEXT.get("PLAY_NORMAL");
                this.description = PanelScreen.TEXT.get("PLAY_NORMAL desc");
                this.portraitImg = ImageMaster.P_STANDARD;
                this.panelColor = PanelColor.BEIGE;
                // beige background by default
                break;

            case INFINITE:

                this.header = PanelScreen.TEXT.get("INFINITE");
                this.description = PanelScreen.TEXT.get("INFINITE desc");
                this.portraitImg = ImageMaster.P_LOOP;
                this.panelImg = ImageMaster.PANEL_BG_BLUE;
                break;
            
            case PLACEHOLDER:

                this.header = PanelScreen.TEXT.get("PLACEHOLDER");
                this.description = PanelScreen.TEXT.get("PLACEHOLDER desc");
                this.portraitImg = ImageMaster.P_DAILY;
                this.panelImg = ImageMaster.PANEL_BG_GRAY;
                if (!GameStat.isUnlocked(GameStat.PlayMode.PLACEHOLDER)) {
                    this.panelColor = PanelColor.GRAY; // This line will make this option become locked!
                }
                break;

            // ZIYI CHEN
            default: this.portraitImg = ImageMaster.P_LOCK;
        }
    }

    private float uiScale = 1.0F;

    public void update() {

        if (this.panelColor != PanelColor.GRAY) {
            this.hb.update();
        }

        if (this.hb.justHovered) {} // play sound

        if (this.hb.hovered) {
            this.uiScale = MathHelper.fadeLerpSnap(this.uiScale, 1.025F);
            if (InputHelper.justClickedLeft) {
                this.hb.clickStarted = true;
            }
        } else {
            this.uiScale = MathHelper.cardScaleLerpSnap(this.uiScale, 1.0F);
        }
        

        if (this.hb.clicked) {
            this.hb.clicked = false;
            // CardCrawlGame.sound.play("DECK_OPEN");
            MainScreen.panelScreen.hide();
            buttonEffect();
        }

        animatePanelIn();
    }

    private void buttonEffect() {}


    /**
     * 
     */
    private Color gColor = Settings.GOLD_COLOR.cpy();
    private Color cColor = Settings.CREAM_COLOR.cpy();
    private Color wColor = Color.WHITE.cpy();
    private Color grColor = Color.GRAY.cpy();

    private float yMod;
    private static final float START_Y = -100 * DisplayConfig.scale;

    private void animatePanelIn() {
        this.animTimer -= Gdx.graphics.getDeltaTime();
        if (this.animTimer < 0.0F) {
            this.animTimer = 0.0F;
        }

        this.yMod = Interpolation.swingIn.apply(
            0.0F,
            START_Y, 
            this.animTimer / this.animTime
        );

        this.wColor.a = 1.0F - this.animTimer / this.animTime;
        this.cColor.a = this.wColor.a;
        this.gColor.a = this.wColor.a;
        this.grColor.a = this.wColor.a;
    }


    public void render(SpriteBatch sb) {

        // init sb color
        sb.setColor(this.wColor);

        // Draw the original background panelImg
        drawPanel(sb, this.panelImg, true);

        if (this.hb.hovered) {
            sb.setColor(
                new Color(
                    1.0F, 
                    1.0F, 
                    1.0F, 
                    (this.uiScale - 1.0F) * 16.0F
                )
            );
            
            sb.setBlendFunction(770, 1);
            drawPanel(sb, ImageMaster.PANEL_BG_BLUE, true);
            sb.setBlendFunction(770, 771);
        }

        // Resume the color of the spriteBatch
        // If gray, which means it's darken and unlocked(?), set color to gray
        if (this.panelColor == PanelColor.GRAY) {
            sb.setColor(this.grColor);
        } else {
            sb.setColor(this.wColor);
        }

        // Draw the portrait
        sb.draw(
            this.portraitImg, 
            this.hb.cX - 158.5F, 
            this.hb.cY + this.yMod - 103.0F + 140.0F * DisplayConfig.scale, 
            158.5F, 
            103.0F, 
            317.0F, 
            206.0F, 
            DisplayConfig.scale, 
            DisplayConfig.scale, 
            0.0F, 
            0, 
            0, 
            317, 
            206, 
            false, 
            false
        );   

        // If gray and unlocked(?), add P_LOCK to this option panel
        if (this.panelColor == PanelColor.GRAY) {
            sb.setColor(this.wColor);
            sb.draw(
                ImageMaster.P_LOCK, 
                this.hb.cX - 158.5F, 
                this.hb.cY + this.yMod - 103.0F + 140.0F * DisplayConfig.scale, 
                158.5F, 
                103.0F, 
                317.0F, 
                206.0F, 
                DisplayConfig.scale, 
                DisplayConfig.scale, 
                0.0F, 
                0, 
                0, 
                317, 
                206, 
                false, 
                false
            );
        }

        // Draw the frame
        drawPanel(sb, ImageMaster.PANEL_FRAME, false);


        /*
         * Display texts
         */
        // Render header
        float simulatedWidth = FontHelper.getWidth(FontHelper.damageNumberFont, this.header, 0.8F);
        if (simulatedWidth > 310.0F * DisplayConfig.scale) {
            FontHelper.renderFontCenteredHeight(
                sb, 
                FontHelper.damageNumberFont, 
                this.header,
                this.hb.cX - 138.0F * DisplayConfig.scale, 
                this.hb.cY + this.yMod + 294.0F * DisplayConfig.scale,
                280.0F * DisplayConfig.scale, 
                this.gColor, 
                0.7F
            );
        } else {
            FontHelper.renderFontCenteredHeight(
                sb, 
                FontHelper.damageNumberFont, 
                this.header,
                this.hb.cX - 153.0F * DisplayConfig.scale, 
                this.hb.cY + this.yMod + 294.0F * DisplayConfig.scale,
                280.0F * DisplayConfig.scale, 
                this.gColor, 
                0.8F
            );
        }

        // Render description
        FontHelper.renderFontCenteredHeight(
            sb, 
            FontHelper.charDescFont, 
            this.description,
            this.hb.cX - 153.0F * DisplayConfig.scale, 
            this.hb.cY + this.yMod - 130.0F * DisplayConfig.scale,
            330.0F * DisplayConfig.scale, 
            this.cColor
        );

        // FontHelper.renderFontCenteredHeight(sb, FontHelper.charDescFont, this.description, 
        //         this.hb.cX - 153.0F * Settings.scale, this.hb.cY + this.yMod - 130.0F * Settings.scale, 
        //         330.0F * Settings.scale, this.cColor);
    
        this.hb.render(sb);
    }

    /**
     * Draw the texture covering the whole area of the option Panel
     * @param sb
     * @param panelTexture
     */
    private void drawPanel(SpriteBatch sb, Texture panelTexture, boolean scaled) {

        float scaledSize = this.uiScale * DisplayConfig.scale;

        sb.draw(
            panelTexture, 
            this.hb.cX - 256.0F, 
            this.hb.cY + this.yMod - 400.0F, 
            256.0F,
            400.0F, 
            512.0F, 
            800.0F, 
            scaled ? scaledSize : DisplayConfig.scale, 
            scaled ? scaledSize : DisplayConfig.scale,
            0.0F, 
            0, 
            0, 
            512, 
            800, 
            false, 
            false
        );
    }

}

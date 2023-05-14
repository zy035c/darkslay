package com.woodburn.darkslay.screens.views.title;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.woodburn.darkslay.WoodBurnGame;
import com.woodburn.darkslay.global_config.DisplayConfig;
import com.woodburn.darkslay.global_config.Settings;
import com.woodburn.darkslay.helper.FontHelper;
import com.woodburn.darkslay.helper.HitBox;
import com.woodburn.darkslay.helper.ImageMaster;
import com.woodburn.darkslay.helper.InputHelper;
import com.woodburn.darkslay.localization.UIStrings;
import com.woodburn.darkslay.screens.MainScreen;

/******************************************************************************
 *  @author  Ziyi Chen
 *  @version  1.0.1
 *  @since 2023-04-04
 *
 *  NeowScreen...
 *
 ******************************************************************************/
public class TitleButton {

    // private static final Logger logger = LogManager.getLogger(MenuButton.class.getName());

    private static final UIStrings uiStrings = UIStrings.getStr();
    private String label;
    private String[] TEXT = uiStrings.TEXT;

    private Color tint = Color.WHITE.cpy();
    private Color highlightColor = new Color(1.0F, 1.0F, 1.0F, 0);

    public static final float FONT_X = 120.0F * DisplayConfig.scale;
    public static final float START_Y = 120.0F * DisplayConfig.scale;
    public static final float SPACE_Y = 50.0F * DisplayConfig.scale;
    public static final float FONT_OFFSET_Y = 10.0F * DisplayConfig.scale;

    private int index;
    public HitBox hb;
    ClickResult result;

    private boolean confirm = false;
    private static Texture highlightImg = null;

    public enum ClickResult {
        PLAY, QUIT, SETTINGS, STAT;
    }

    private void setLabel() {
        switch (this.result) {
            case PLAY:
                this.label = TEXT[1];
                return;
            case QUIT:
                this.label = TEXT[8];
                return;
            case SETTINGS:
                this.label = TEXT[12];
                return;
            case STAT:
                this.label = TEXT[7];
                return;
        }
        this.label = "ERROR";
    }

    public TitleButton(ClickResult res, int index) {

        highlightImg = ImageMaster.loadImage("slay_spire/menu_ui/menu_option_highlight.png");
        this.result = res;

        setLabel();

        this.index = index;

        this.hb = new HitBox(
            FontHelper.getSmartWidth(FontHelper.buttonLabelFont, this.label, 9999.0F, 1.0F) + 100.0F * DisplayConfig.scale,
            SPACE_Y
        );

        this.hb.move(
            this.hb.width / 2.0F + 75.0F * DisplayConfig.scale,
            START_Y + index * SPACE_Y
        );

    }


    private float x = 0.0F;
    private float targetX = 0.0F;

    public void update() {

        if (MainScreen.titleScreen.bg.slider < 0.5F && MainScreen.curScreen == MainScreen.ScreenOption.Title) {
            this.hb.update();
        }

        this.x = uiLerpSnap(this.x, this.targetX);

        if (this.hb.justHovered && !this.hidden) {
            // play sound
        }

        // If this hitbox is hovered, change color
        if (this.hb.hovered) {

            this.highlightColor.a = 0.9F;
            this.targetX = 25.0F * DisplayConfig.scale;
            if (InputHelper.justClickedLeft) {
                //...
                this.hb.clickStarted = true;
            }
            this.tint = Color.WHITE.cpy();

        } else if (MainScreen.curScreen == MainScreen.ScreenOption.Title){
            this.highlightColor.a = fadeLerpSnap(this.highlightColor.a, 0.0F);
            this.targetX = 0.0F;
            this.hidden = false;
            this.tint.r = fadeLerpSnap(this.tint.r, 0.3F);
            this.tint.g = this.tint.r;
            this.tint.b = this.tint.r;
        }

        if (this.hb.clicked) {
            this.hb.clicked = false;
            clickEffect();
            MainScreen.titleScreen.hideMenuButtons();
        }


    }

    // the effect takes place when the play button is clicked
    private void clickEffect() {
        switch (this.result) {
            case PLAY:
                // hide();
                MainScreen.panelScreen.open();
                break;

            case QUIT:
                Gdx.app.exit();
                break;

            default:
                System.out.println("[TitleButton] unsupported options");
        }
    }

    private boolean hidden = false;
    public void hide() {
        this.hb.hovered = false;
        this.targetX = -1000.0F * DisplayConfig.scale + 30.0F * DisplayConfig.scale * this.index;
        this.hidden = true;
    }


    // MATH HELPERS
    public static float uiLerpSnap(float x, float targetX) {
        if (x != targetX) {
            x = MathUtils.lerp(x, targetX, Gdx.graphics.getDeltaTime() * 9.0F);
            if (Math.abs(x - targetX) < DisplayConfig.scale) {
                x = targetX;
            }
        }
        return x;
    }
    public static float fadeLerpSnap(float x, float targetX) {
        if (x != targetX) {
            x = MathUtils.lerp(x, targetX, Gdx.graphics.getDeltaTime() * 12.0F);
            if (Math.abs(x - targetX) < 0.01F) {
                x = targetX;
            }
        }
        return x;
    }



    public void render(SpriteBatch sb) {
        float lerper = Interpolation.circleIn.apply(MainScreen.titleScreen.bg.slider);
        float sliderX = -1000.0F * DisplayConfig.scale * lerper;
        sliderX -= this.index * 250.0F * DisplayConfig.scale * lerper;

        // System.out.println("sliderX" + sliderX);

        sb.setBlendFunction(770, 1);
        sb.setColor(highlightColor);

        sb.draw(
                highlightImg,
                this.x + FONT_X + sliderX - 179.0F + 120.0F * DisplayConfig.scale,
                this.hb.cY - 52.0F,
                179.0F,
                52.0F,
                358.0F,
                104.0F,
                DisplayConfig.scale,
                DisplayConfig.scale * 0.8F,
                0.0F,
                0,
                0,
                358,
                104,
                false,
                false
        );

        // revert to default color
        sb.setColor(Color.WHITE);

        sb.setBlendFunction(770, 771);

        FontHelper.renderSmartText(
                sb,
                FontHelper.buttonLabelFont,
                this.label,
                this.x + FONT_X + sliderX,
                this.hb.cY + FONT_OFFSET_Y,
                9999.0F,
                1.0F,
                Settings.CREAM_COLOR
        );

        // debug mode?
        // this.hb.render(sb);
    }

}

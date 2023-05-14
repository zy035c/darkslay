package com.woodburn.darkslay.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.woodburn.darkslay.global_config.DisplayConfig;
import com.woodburn.darkslay.global_config.Settings;
import com.badlogic.gdx.files.FileHandle;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FontHelper {

    public static BitmapFont panelNameFont;

    // private static final Logger logger = LogManager.getLogger(FontHelper.class.getName());

    private static FileHandle fontFileHandle = null;

    private static float fontScale = 1.0F;

    // card fonts:
    public static BitmapFont cardTitleFont;
    public static BitmapFont cardTypeFont;
    public static BitmapFont cardEnergyFont_L;
    public static BitmapFont cardDescFont_N;
    public static BitmapFont cardDescFont_L;


    public static BitmapFont energyNumFontRed;

    // battle ui fonts:
    public static BitmapFont healthInfoFont;
    public static BitmapFont blockInfoFont;
    public static BitmapFont turnNumFont;
    public static BitmapFont damageNumberFont;


    /*
     * For option panel screen descript
     */
    public static BitmapFont charDescFont;


    public static BitmapFont charTitleFont;

    // title/main menu button font
    public static BitmapFont buttonLabelFont;

    /* Add on May 13 | Used for chara descript in chara_select */
    public static BitmapFont tipHeaderFont;
    public static BitmapFont bannerNameFont;
    public static BitmapFont losePowerFont;

    static FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();


    /**************************************** INITIALIZE STARTS ****************************************************/
    public static void initialize() {

        // long startTime = System.currentTimeMillis();

        HashMap<Character, Integer> paramCreator = new HashMap<>();

        switch (Settings.language) {
            case ENG:
                fontFileHandle = Gdx.files.internal("slay_spire/font/eng/Kreon-Bold.ttf");
            case ZHS:
                // TODO

        }

        float size = 30.0F;

        param.hinting = FreeTypeFontGenerator.Hinting.Slight;


        /* Title screen button */
        param.gamma = 1.2F;
        param.incremental = true;
        param.spaceX = (int)(-2.5F * DisplayConfig.scale);
        param.shadowOffsetX = (int)(3.0F * DisplayConfig.scale);
        param.shadowOffsetY = (int)(3.0F * DisplayConfig.scale);
        param.borderWidth = 4.0F * DisplayConfig.scale;
        param.borderColor = Settings.QUARTER_TRANSPARENT_BLACK_COLOR; //
        param.shadowColor = Settings.QUARTER_TRANSPARENT_BLACK_COLOR; //

        buttonLabelFont = prepFont(34.0F, true);


        /*
         * For damageNumberFont. 
         * Also used in Screen option Panels.
         */
        param.borderWidth = 3.0F * DisplayConfig.scale;
        param.borderColor = Color.DARK_GRAY;
        damageNumberFont = prepFont(48.0F, true);
        damageNumberFont.getData().setLineHeight((damageNumberFont.getData()).lineHeight * 0.85F); // don't understand

        /*
         * For charDescFont
         * Used in Screen option Panels.
         */
        // data.xChars = new chars[] {'动'}  What's happening here in oringinal code?!
        param.hinting = FreeTypeFontGenerator.Hinting.Slight;
        param.spaceX = 0;
        param.kerning = true;

        paramCreator.clear();

        param.borderWidth = 0.0F;
        param.gamma = 0.9F;
        param.borderGamma = 0.9F;
        param.shadowColor = Settings.QUARTER_TRANSPARENT_BLACK_COLOR;
        param.shadowOffsetX = (int)(4.0F * DisplayConfig.scale);
        charDescFont = prepFont(30.0F, false); // if isMobile, size=31.0F

        param.gamma = 1.8F;
        param.borderGamma = 1.8F;
        param.shadowOffsetX = (int) (6.0F * DisplayConfig.scale);
        charTitleFont = prepFont(44.0F, false);


        /* Add on May 13 | Used for chara descript in chara_select */
        param.shadowOffsetX = 2;
        param.shadowOffsetY = 2;
        param.shadowColor = new Color(0.0F, 0.0F, 0.0F, 0.33F);
        param.gamma = 2.0F;
        param.borderGamma = 2.0F;
        param.borderStraight = true;
        param.borderColor = Color.DARK_GRAY;
        param.borderWidth = 2.0F * DisplayConfig.scale;

        param.shadowOffsetX = 1;
        param.shadowOffsetY = 1;
        tipHeaderFont = prepFont(23.0F, false);

        /* For chara select | Add on May 13 | Used for chara descript in chara_select */
        param.gamma = 0.5F;
        param.borderGamma = 0.5F;
        param.shadowOffsetX = 0;
        param.shadowOffsetY = 0;
        param.borderWidth = 6.0F * DisplayConfig.scale;
        param.borderColor = new Color(0.0F, 0.0F, 0.0F, 0.5F);
        param.spaceX = (int) (-5.0F * DisplayConfig.scale);
        // dungeonTitleFont = prepFont(115.0F, true);
        // dungeonTitleFont.getData().setScale(1.25F);
        param.borderWidth = 4.0F * DisplayConfig.scale;
        param.borderColor = new Color(0.0F, 0.0F, 0.0F, 0.33F);
        param.spaceX = (int) (-2.0F * DisplayConfig.scale);
        bannerNameFont = prepFont(72.0F, true);
        /* Add on May 13 | Used for chara select msg "please select char" */
        param.borderWidth = 4.0F * DisplayConfig.scale;
        param.borderColor = new Color(0.3F, 0.3F, 0.3F, 1.0F);
        param.shadowColor = Settings.QUARTER_TRANSPARENT_BLACK_COLOR;
        param.shadowOffsetX = Math.round(3.0F * DisplayConfig.scale);
        param.shadowOffsetY = Math.round(3.0F * DisplayConfig.scale);
        losePowerFont = prepFont(36.0F, true);


    }
    /**************************************** INITIALIZE ENDS ****************************************************/


    /**
     * Only specify size and linear-filter. Use default font, Kreon Bold.
     * @param size
     * @param isLinearFiltering
     * @return
     */
    public static BitmapFont prepFont(float size, boolean isLinearFiltering) {

        // if no?
        FreeTypeFontGenerator g = new FreeTypeFontGenerator(fontFileHandle);

        return prepFont(g, size, isLinearFiltering);
    }

    /**
     * Prep a font, return a BitmapFont with the current state of param.
     * So must prepare param before calling this function.
     * @param g
     * @param size
     * @param isLinearFiltering
     * @return : BitmapFont
     */
    public static BitmapFont prepFont(FreeTypeFontGenerator g, float size, boolean isLinearFiltering) {
        param.size = Math.round(size * fontScale * DisplayConfig.scale);
        return g.generateFont(param);
    }

    // ...............

    /**
     * A temporary layout that is going to be changed for each single text render
     */
    public static GlyphLayout layout = new GlyphLayout();
    private static float spaceWidth;

    static float curWidth;
    public static float getSmartWidth(BitmapFont font, String msg, float lineWidth, float lineSpacing) {
        curWidth = 0.0F;

        layout.setText(font, " ");
        spaceWidth = layout.width;

        for (String word : msg.split(" ")) {
            layout.setText(font, msg);
            if (curWidth + layout.width > lineWidth) {
                curWidth = layout.width + spaceWidth;
            } else {
                curWidth += layout.width + spaceWidth;
            }
        }

        return curWidth;
    }

    /** 
     * Special signs like TAB, NL...?
     */
    public static void renderSmartText(
            SpriteBatch sb,
            BitmapFont font,
            String msg,
            float x, float y,
            float lineWidth, float lineSpacing,
            Color baseColor) {

        if (msg == null) return;

        float curHeight = 0.0F;
        curWidth = 0.0F;

        layout.setText(font, " ");
        spaceWidth = layout.width;

        for (String word : msg.split(" ")) {

            /* NL: next line. Jump over rest of the loop */
            if (word.equals("NL")) {
                curWidth = 0.0F;
                curHeight -= lineSpacing;
                continue;
            }

            /* Set color with weird markup ?! */
            Color color = identifyColor(word).cpy();
            if (!color.equals(Color.WHITE)) {
                word = word.substring(2, word.length());
                color.a = baseColor.a;
                font.setColor(color);
            } else {
                font.setColor(baseColor);
            }

            layout.setText(font, word);

            /* If this line is full, change to next line. */
            if (curWidth + layout.width > lineWidth) {
                curHeight -= lineSpacing;
                font.draw((Batch) sb, word, x, y + curHeight);
                curWidth = layout.width + spaceWidth;
            } else {
                
                font.draw((Batch) sb, word, x + curWidth, y + curHeight);
                curWidth += layout.width + spaceWidth;
            }
        } /* For loop ends */

        layout.setText(font, msg); /* why? */
    }

    /**
     * Use markup like 'r' 'g' to render the following text in 
     * designated color
     * @param word
     * @return
     */
    private static Color identifyColor(String word) {
        if (word.length() > 0 && word.charAt(0) == '#') {
            switch (word.charAt(1)) {
                case 'r':
                    return Settings.RED_TEXT_COLOR;
                case 'g':
                    return Settings.GREEN_TEXT_COLOR;
                case 'b':
                    return Settings.BLUE_TEXT_COLOR;
                case 'y':
                    return Settings.GOLD_COLOR;
                case 'p':
                    return Settings.PURPLE_COLOR;
            }
            return Color.WHITE;
        }

        return Color.WHITE;
    }


    /**
     * Set the layout and return the scaled width
     * @param font
     * @param text
     * @param scale
     * @return : float
     */
    public static float getWidth(BitmapFont font, String text, float scale) {
        layout.setText(font, text);
        return layout.width * scale;
    }

    /**
     * Set the layout and return the scaled height
     * @param font
     * @param text
     * @param scale
     * @return : float 
     */
    public static float getHeight(BitmapFont font, String text, float scale) {
        layout.setText(font, text);
        return layout.height * scale;
    }

    /**
     * Render a font ... with lineWidth, color and scale
     * @param sb
     * @param font
     * @param msg
     * @param x
     * @param y
     * @param lineWidth : float
     * @param c : Color
     * @param scale : float
     */
    public static void renderFontCenteredHeight(
        SpriteBatch sb, BitmapFont font, String msg, float x, float y, float lineWidth, Color c, float scale
    ) {
        font.getData().setScale(scale); // Set scaling
        layout.setText(font, msg, c, lineWidth, 1, true);
        font.setColor(c);
        font.draw(sb, msg, x, y + layout.height / 2.0F, lineWidth, 1, true); // Center-align to y-axis
        font.getData().setScale(1.0F); // Resume scaling.
    }

    /**
     * Render fonr with color and scale
     */
    public static void renderFontCenteredHeight(
        SpriteBatch sb, BitmapFont font, String msg, float x, float y, Color c, float scale
    ) {
        font.getData().setScale(scale);
        layout.setText(font, msg);
        renderFont(sb, font, msg, x - layout.width / 2.0F, y + layout.height / 2.0F, c);
        font.getData().setScale(1.0F);
    }

    /**
     * Helper func ...
     */
    public static void renderFont(SpriteBatch sb, BitmapFont font, String msg, float x, float y, Color c) {
        font.setColor(c);
        font.draw((Batch) sb, msg, x, y);
    }

    /**
     * Render a font ... with lineWidth, color, and NO scale
     */
    public static void renderFontCenteredHeight(
        SpriteBatch sb, BitmapFont font, String msg, float x, float y, float lineWidth, Color c
    ) {
        layout.setText(font, msg, c, lineWidth, 1, true);
        font.setColor(c);
        font.draw(sb, msg, x, y + layout.height / 2.0F, lineWidth, 1, true); // Center-align to y-axis
    }


    public static void renderFontCentered(SpriteBatch sb, BitmapFont font, String msg, float x,
            float y, Color c) {
        layout.setText(font, msg);
        renderFont(sb, font, msg, x - layout.width / 2.0F, y + layout.height / 2.0F, c);
    }

}

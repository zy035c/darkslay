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

    // title/main menu button font
    public static BitmapFont buttonLabelFont;


    static FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();


    public static void initialize() {

        switch (Settings.language) {
            case ENG:
                fontFileHandle = Gdx.files.internal("slay_spire/font/eng/Kreon-Bold.ttf");
            case ZHS:
                // TODO

        }

        float size = 30.0F;


        //
        // Title screen button
        //
        param.gamma = 1.2F;
        param.incremental = true;
        param.spaceX = (int)(-2.5F * DisplayConfig.scale);
        param.shadowOffsetX = (int)(3.0F * DisplayConfig.scale);
        param.shadowOffsetY = (int)(3.0F * DisplayConfig.scale);
        param.borderWidth = 4.0F * DisplayConfig.scale;
        param.borderColor = Settings.QUARTER_TRANSPARENT_BLACK_COLOR; //
        param.shadowColor = Settings.QUARTER_TRANSPARENT_BLACK_COLOR; //

        buttonLabelFont = prepFont(34.0F, true);

        //
        //
        //

    }


    // ...............

    public static BitmapFont prepFont(float size, boolean isLinearFiltering) {

        // if no?
        FreeTypeFontGenerator g = new FreeTypeFontGenerator(fontFileHandle);

        return prepFont(g, size, isLinearFiltering);
    }

    public static BitmapFont prepFont(FreeTypeFontGenerator g, float size, boolean isLinearFiltering) {
        param.size = Math.round(size * fontScale * DisplayConfig.scale);
        return g.generateFont(param);
    }

    // ...............


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

    public static void renderSmartText(
            SpriteBatch sb,
            BitmapFont font,
            String msg,
            float x, float y,
            float lineWidth, float lineSpacing,
            Color baseColor) {

        if (msg == null) return;

        float curHeight = 0.0F;

//        font.setColor(baseColor);
//
//        for (String word : msg.split(" ")) {
//            layout.setText(font, word);
//            font.draw((Batch)sb, word, x + curWidth, y + curHeight);
//            curWidth += layout.width + spaceWidth;
//        }

        font.setColor(baseColor);
        font.draw(sb, msg, x, y);

    }
}

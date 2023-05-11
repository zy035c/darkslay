package com.woodburn.darkslay.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.woodburn.darkslay.global_config.DisplayConfig;
import com.woodburn.darkslay.global_config.Settings;
import com.woodburn.darkslay.helper.FontHelper;
import com.woodburn.darkslay.helper.ImageMaster;
import com.woodburn.darkslay.helper.InputHelper;
import com.woodburn.darkslay.screens.views.event.SpeechWord;
import com.woodburn.darkslay.screens.views.title.TitleButton;
import com.woodburn.darkslay.screens.views.title.TitleBackground;
import com.woodburn.darkslay.screens.MainScreen;

import java.util.Scanner;

/******************************************************************************
 *  @author  Ziyi Chen
 *  @version  1.0.1
 *  @since 2023-04-04
 *
 *  NeowScreen...
 *
 ******************************************************************************/

public class NeowScreen {

    private Color bgColor;
    private Color eyeColor;

    private int currentDialog = 0;
    private int clickCount = 0;

    Array<TitleButton> buttons = new Array<>();

    public TitleBackground bg = new TitleBackground();

    private boolean fadingOut;
    private float fadeOutTimer;
    private boolean textDone;
    private float wordTimer;

    private static float curLineWidth = 0.0F;
    public static int curLine = 0;
    public static Scanner s;
    private static GlyphLayout gl = new GlyphLayout();

    private static Array<SpeechWord> words = new Array<>();
    private BitmapFont font = FontHelper.panelNameFont;
    private float x = DisplayConfig.WIDTH / 2.0F, y = DisplayConfig.HEIGHT / 2.0F;


    public void open() {
        this.fadingOut = false;
        this.fadeOutTimer = 3.0f;

        playSfx();

        // s = new Scanner(charStrings.TEXT[0]);

        this.textDone = false;
        this.wordTimer = 1.0F;
        words.clear();
        curLineWidth = 0.0F;
        curLine = 0;
        this.currentDialog = 0;
        this.clickCount = 0;

        MainScreen.curScreen = MainScreen.ScreenOption.Neow;

//        this.eye1 = new NeowEye(0);
//        this.eye2 = new NeowEye(1);
//        this.eye3 = new NeowEye(2);

        this.bgColor = new Color(320149504);
        this.eyeColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);

    }

    public void update() {

        this.bgColor.a = slowColorLerpSnap(bgColor.a, 1.0F);

        this.wordTimer -= Gdx.graphics.getDeltaTime();
        if (this.clickCount > 4) {
            this.wordTimer = 0.1F;
        } else {
            this.wordTimer += 0.4F;
        }
        addWord();

        for (SpeechWord w : words) w.update();

        if (InputHelper.justClickedLeft) this.clickCount++;

        if (this.fadingOut) {
            if (this.clickCount > 4) {
                this.fadeOutTimer -= Gdx.graphics.getDeltaTime() * 3.0F;
            } else {
                this.fadeOutTimer -= Gdx.graphics.getDeltaTime();
            }
            if (this.fadeOutTimer < 0.0F) {
                this.fadeOutTimer = 0.0F;
                exit();
                return;
            }
        } else if ((InputHelper.justClickedLeft || this.clickCount > 3) && this.textDone) {
            this.currentDialog++;
            if (this.currentDialog > 2) {
                this.fadingOut = true;
                return;
            }

            playSfx();
            // s = new Scanner(charStrings.TEXT[this.currentDialog]);

            this.textDone = false;
            if (this.clickCount > 4) {
                this.wordTimer = 0.1F;
            } else {
                this.wordTimer = 0.3F;
            }
            words.clear();
            curLineWidth = 0.0F;
            curLine = 0;
        }

    }

    private void exit() {

    }

    // Math Helper
    private float slowColorLerpSnap(float startX, float targetX) {
        if (startX != targetX) {
            startX = MathUtils.lerp(startX, targetX, Gdx.graphics.getDeltaTime() * 3.0F);
            if (Math.abs(startX - targetX) < 0.01F) {
                startX = targetX;
            }
        }
        return startX;
    }

    private void addWord() {

    }

    public void playSfx() {
        // sound
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.bgColor);
        sb.draw(
                ImageMaster.WHITE_SQUARE_IMG,
                0.0F,
                0.0F,
                DisplayConfig.WIDTH,
                DisplayConfig.HEIGHT
        );

        sb.setColor(this.eyeColor);
        //

        for (SpeechWord w: words) {
            w.render(sb);
        }

        if (this.fadingOut) {

        }

    }

}

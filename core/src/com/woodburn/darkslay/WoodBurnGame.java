package com.woodburn.darkslay;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.woodburn.darkslay.creatures.AbstractPlayer;
import com.woodburn.darkslay.dungeons.AbstractDungeon;
import com.woodburn.darkslay.dungeons.TestDungeon;
import com.woodburn.darkslay.dungeons.TheSpire;
import com.woodburn.darkslay.global_config.DisplayConfig;
import com.woodburn.darkslay.helper.FontHelper;
import com.woodburn.darkslay.helper.ImageMaster;
import com.woodburn.darkslay.helper.InputHelper;
import com.woodburn.darkslay.localization.Localization;
import com.woodburn.darkslay.screens.MainScreen;
import com.woodburn.darkslay.screens.views.title.TitleScreen;
import com.woodburn.drop_game.MainMenuScreen;

public class WoodBurnGame implements ApplicationListener {


    /*
     * 
     * The regexp:  \/\*\s*\d*\s*\*\/
     * 
     */

    public static String VERSION_NUM = "[V1.0.0] (11-00-2022)";
    private OrthographicCamera camera;
    public static FitViewport viewport;

    public static PolygonSpriteBatch psb;
    private SpriteBatch sb;

    public static AbstractDungeon dungeon;

    public static String playerName;

    public static MainScreen mainScreen;

    public boolean trial = true;


    @Override
    public void create() {


        Localization.initialize();
        DisplayConfig.initialize();
        FontHelper.initialize();
        InputHelper.initialize();
        ImageMaster.initialize();

        this.camera = new OrthographicCamera(
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight()
        );
        this.sb = new SpriteBatch();


    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        this.sb.begin();
        update();
        mainScreen.render(this.sb);
        this.sb.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    public void update() {
        if (mainScreen == null) {
            mainScreen = new MainScreen();
        }

        InputHelper.updateFirst();

        /* 
         * fadedOut : boolean should be static.
         * It's not static originally, so in other
         * classes, use WoodBurnGame.mainScreen.fadedOut
         * which is too long.
         * 
         * That's why I made it static and in other
         * classes use MainScreen.fadedOut
         * 
         * If fadedOut, the game screen is going to stop
         * update and dungeon takes over.
         */
        if (!MainScreen.fadedOut) {
            mainScreen.update();
        } else {
            if (dungeon == null) {
                createDungeon("Test");
            }
            dungeon.update();
        }

        InputHelper.updateLast();
    }

    /**
     * Return a specific dungeon (local map)
     * @param key
     * @param p
     * @return
     */
    public void createDungeon(String key) {
        switch (key) {
            case "Spire":
                new TheSpire();
            case "Test":
                new TestDungeon();
            default:
        }
    }

}

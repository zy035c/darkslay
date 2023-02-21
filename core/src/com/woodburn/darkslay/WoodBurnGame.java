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
import com.woodburn.darkslay.global_config.DisplayConfig;
import com.woodburn.darkslay.helper.FontHelper;
import com.woodburn.darkslay.helper.InputHelper;
import com.woodburn.darkslay.screens.MainScreen;
import com.woodburn.darkslay.screens.TitleScreen;

public class WoodBurnGame implements ApplicationListener {

    public static String VERSION_NUM = "[V1.0.0] (11-00-2022)";
    private OrthographicCamera camera;
    public static FitViewport viewport;

    public static PolygonSpriteBatch psb;
    private SpriteBatch sb;

    public static AbstractDungeon dungeon;

    public static String playerName;

    public static MainScreen mainScreen;


    @Override
    public void create() {



        DisplayConfig.initialize();
        FontHelper.initialize();
        InputHelper.initialize();

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
        mainScreen.update();

        InputHelper.updateLast();
    }

    public AbstractDungeon getDungeon(String key, AbstractPlayer p) {
        return null;
    }

}

package com.woodburn.drop_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;
import com.woodburn.darkslay.global_config.DisplayConfig;
import com.woodburn.darkslay.helper.FontHelper;
// import sun.jvm.hotspot.utilities.BitMap;

public class MainMenuScreen implements Screen {

    final DropGame game;
    OrthographicCamera camera;

    BitmapFont font;

    public MainMenuScreen(final DropGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 400);

        DisplayConfig.initialize();
        FontHelper.initialize();
        font = FontHelper.buttonLabelFont;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.batch.begin();
        font.draw(game.batch, "Welcome to Drop!!! ", 100, 150);
        font.draw(game.batch, "Tap anywhere to begin!", 100, 100);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

}

package com.woodburn.darkslay.screens.views.title;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.woodburn.darkslay.global_config.DisplayConfig;

public class TitleCloud {

    private TextureAtlas.AtlasRegion region;
    private float x;
    private float y;

    private float vX;
    private float vY;

    public TitleCloud(TextureAtlas.AtlasRegion region, float vX, float x) {

        this.region = region;

        this.vX = vX;
        this.x = x;
    }


    public void update() {
        this.x += this.vX * Gdx.graphics.getDeltaTime();
        this.y += this.vY * Gdx.graphics.getDeltaTime();

        // if cloud moves out of screen,
        if (this.vX > 0.0F && this.x > 1920.0F * DisplayConfig.xScale) {
            this.x = -1920.0F * DisplayConfig.xScale;
            this.vX = MathUtils.random(10.0F, 50.0F) * DisplayConfig.xScale;
            this.y = DisplayConfig.HEIGHT - 1100.0F * DisplayConfig.scale + MathUtils.random(-50.0F, 50.0F) * DisplayConfig.scale;
            this.vY = MathUtils.random(-this.vX / 5.0F, this.vX / 5.0F) * DisplayConfig.scale;

        } else if (this.x < -1920.0F * DisplayConfig.xScale) {
            this.x = 1920.0F * DisplayConfig.xScale;
            this.vX = MathUtils.random(-50.0F, -10.0F) * DisplayConfig.xScale;
            this.y = DisplayConfig.HEIGHT - 1100.0F * DisplayConfig.scale + MathUtils.random(-50.0F, 50.0F) * DisplayConfig.scale;
            this.vY = MathUtils.random(-this.vX / 5.0F, this.vX / 5.0F) * DisplayConfig.scale;
        }

    }

    public void render(SpriteBatch sb, float slider) {

        sb.draw(
                region.getTexture(),
                region.offsetX * DisplayConfig.scale + x,
                region.offsetY * DisplayConfig.scale + y,
                0.0F,
                0.0F,
                region.packedWidth,
                region.packedHeight,
                DisplayConfig.scale,
                DisplayConfig.scale,
                0.0F,
                region.getRegionX(),
                region.getRegionY(),
                region.getRegionWidth(),
                region.getRegionHeight(),
                false,
                false
        );
    }


}

package com.woodburn.darkslay.screens.views.title;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.woodburn.darkslay.global_config.DisplayConfig;
import com.woodburn.darkslay.helper.InputHelper;

public class TitleBackground {

    protected static TextureAtlas atlas;
    protected final TextureAtlas.AtlasRegion mg3Bot;
    protected final TextureAtlas.AtlasRegion mg3Top;
    protected final TextureAtlas.AtlasRegion topGlow;
    protected final TextureAtlas.AtlasRegion topGlow2;
    protected final TextureAtlas.AtlasRegion botGlow;
    public final TextureAtlas.AtlasRegion sky;


    protected Array<TitleCloud> topClouds = new Array<>();
    protected Array<TitleCloud> midClouds = new Array<>();

    public TitleBackground() {
        if (atlas == null) {
            atlas = new TextureAtlas(Gdx.files.internal("slay_spire/menu_ui/title/title.atlas"));
        }

        this.sky = atlas.findRegion("jpg/sky");
        this.mg3Bot = atlas.findRegion("mg3Bot");
        this.mg3Top = atlas.findRegion("mg3Top");
        this.topGlow = atlas.findRegion("mg3TopGlow1");
        this.topGlow2 = atlas.findRegion("mg3TopGlow2");
        this.botGlow = atlas.findRegion("mg3BotGlow");

        int i;
        for (i = 1; i < 7; i++) {
            TitleCloud c = new TitleCloud(
                    atlas.findRegion("topCloud" + Integer.toString(i)),
                    MathUtils.random(10.0F, 50.0F) * DisplayConfig.scale,
                    MathUtils.random(-1920.0F, 1920.0F) * DisplayConfig.scale
            );
            this.topClouds.add(c);
        }

        for (i = 1; i < 13; i++) {
            TitleCloud c = new TitleCloud(
                    atlas.findRegion("midCloud" + Integer.toString(i)),
                    MathUtils.random(-50.0F, -10.0F) * DisplayConfig.scale,
                    MathUtils.random(-1920.0F, 1920.0F) * DisplayConfig.scale
            );
            this.midClouds.add(c);
        }

    }

    private float timer = 1.0F;
    public float slider = 1.0F;
    public boolean activated = false;

    public void slideDownInstantly() {
        this.slider = 0.0F;
        this.timer = 0.0F;
        this.activated = true;
    }

    public void update() {

        if (InputHelper.justClickedLeft && !this.activated) {
            this.activated = true;
            this.timer = 1.0F;
        }


        if (this.activated && this.timer != 0.0F) {
            this.timer -= Gdx.graphics.getDeltaTime();
            if (this.timer < 0.0F) {
                this.timer = 0.0F;
            }
            if (this.timer < 1.0F) {
                this.slider = Interpolation.pow4In.apply(0.0F, 1.0F, this.timer);
            }
        }

        for (TitleCloud c : this.topClouds) c.update();
        for (TitleCloud c : this.midClouds) c.update();


    }

    public void render(SpriteBatch sb) {

        sb.setColor(Color.WHITE); /* Clear spriteBatch color */

        renderRegion(
                sb,
                this.sky,
                0.0F,
                -100.0F * DisplayConfig.scale * this.slider
        );
        renderRegion( // draw mg3Top?
                sb,
                this.mg3Top,
                0.0F,
                MathUtils.round(-45.0F * DisplayConfig.scale * this.slider + DisplayConfig.HEIGHT - 1080.0F * DisplayConfig.scale)
        );
        renderRegion( // draw mg3Bot?
                sb,
                this.mg3Bot,
                0.0F,
                MathUtils.round(-45.0F * DisplayConfig.scale * this.slider + DisplayConfig.HEIGHT - 2219.0F * DisplayConfig.scale)
        );

        // include in two setBlendFunction and setColor: topGlow, topGlow2, botGlow
        sb.setBlendFunction(770, 1);
        sb.setColor(
                new Color(1.0F, 0.2F, 0.1F, 0.1F + (
                        MathUtils.cosDeg((float)(System.currentTimeMillis() / 16L % 360L)) + 1.25F) / 5.0F
                )
        );

        renderRegion(
                sb,
                this.topGlow,
                0.0F,
                -45.0F * DisplayConfig.scale * this.slider + DisplayConfig.HEIGHT - 1080.0F * DisplayConfig.scale
        );
        renderRegion(
                sb,
                this.topGlow2,
                0.0F,
                -45.0F * DisplayConfig.scale * this.slider + DisplayConfig.HEIGHT - 1080.0F * DisplayConfig.scale
        );
        renderRegion( // bottom glow
                sb,
                this.botGlow,
                0.0F,
                -45.0F * DisplayConfig.scale * this.slider + DisplayConfig.HEIGHT - 2220F * DisplayConfig.scale
        );

        sb.setColor(Color.WHITE);
        sb.setBlendFunction(770, 771);

        // Rendering title clouds start here


        for (TitleCloud c : this.midClouds) {
            c.render(sb, this.slider);
        }
        for (TitleCloud c : this.topClouds) {
            c.render(sb, this.slider);
        }

        // sb.setColor(new Color(1.0F, 1.0F, 1.0F, this.l));

    }

    public void renderRegion(SpriteBatch sb, TextureAtlas.AtlasRegion region, float x, float y) {

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

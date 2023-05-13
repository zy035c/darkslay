package com.woodburn.darkslay.screens.views.main_panels;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.woodburn.darkslay.global_config.DisplayConfig;
import com.woodburn.darkslay.helper.HitBox;

public class MainCancelButton {
    
    private static final float SHOW_X = 256.0F * DisplayConfig.scale;
    private static final float DRAW_Y = 128.0F * DisplayConfig.scale;
    public static final float HIDE_X = SHOW_X - 400.0F * DisplayConfig.scale;

    private String buttonText = "";
    public float cur_x = HIDE_X;
    public float target_x = this.cur_x;

    public HitBox hb = new HitBox(
        300.0F * DisplayConfig.scale, 
        100.0F * DisplayConfig.scale
    );

    public MainCancelButton() {
        this.hb.move(
            SHOW_X - 106.0F * DisplayConfig.scale,
            DRAW_Y + 60.0F * DisplayConfig.scale
        );
    }

    public void update() {

    }

    public void render(SpriteBatch sb) {

    }

	public void hide() {
	}

    public void show() {
    }

}

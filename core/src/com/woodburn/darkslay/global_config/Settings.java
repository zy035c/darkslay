package com.woodburn.darkslay.global_config;

import com.badlogic.gdx.graphics.Color;

public class Settings {



    public enum lang {
        ENG, ZHS;
    }

    public static lang language = lang.ENG;


    public static final Color CREAM_COLOR = new Color(-597249);

    public static final Color QUARTER_TRANSPARENT_BLACK_COLOR = new Color(0.0F, 0.0F, 0.0F, 0.25F);;
    public static final Color QUARTER_TRANSPARENT_WHITE_COLOR = new Color(1.0F, 1.0F, 1.0F, 0.25F);;

}

package com.woodburn.darkslay.global_config;

public class DisplayConfig {

    private static final int DEFAULT_W = 1280;
    private static final int DEFAULT_H = 720;

    public static float WIDTH;
    public static float HEIGHT;

    public static float scale;
    public static float xScale;
    public static float yScale;

    private boolean isFullscreen;
    private boolean fps_limit;

    public static void initialize() {
        WIDTH = DEFAULT_W;
        HEIGHT = DEFAULT_H;

        scale = WIDTH / 1920.0F;

        xScale = WIDTH / 1920.0F;

    }

}

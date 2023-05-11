package com.woodburn.darkslay.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.woodburn.darkslay.global_config.DisplayConfig;

/******************************************************************************
 * @author Ziyi Chen
 * @version 1.0.1
 * @since 2023-4-24
 *
 * Math helpers. Different types of interpolation functions for smooth UI
 * animation.
 *
 ******************************************************************************/
public class MathHelper {

    // MATH HELPERS
    public static float uiLerpSnap(float x, float targetX) {
        if (x != targetX) {
            x = MathUtils.lerp(x, targetX, Gdx.graphics.getDeltaTime() * 9.0F);
            if (Math.abs(x - targetX) < DisplayConfig.scale) {
                x = targetX;
            }
        }
        return x;
    }

    public static float fadeLerpSnap(float x, float targetX) {
        if (x != targetX) {
            x = MathUtils.lerp(x, targetX, Gdx.graphics.getDeltaTime() * 12.0F);
            if (Math.abs(x - targetX) < 0.01F) {
                x = targetX;
            }
        }
        return x;
    }

    public static float popLerpSnap(float startX, float targetX) {
        if (startX != targetX) {
            startX = MathUtils.lerp(startX, targetX, Gdx.graphics.getDeltaTime() * 8.0F);
            if (Math.abs(startX - targetX) < 0.003F) {
                startX = targetX;
            }
        }
        return startX;
    }

}

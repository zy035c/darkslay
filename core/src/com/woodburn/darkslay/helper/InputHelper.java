package com.woodburn.darkslay.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.woodburn.darkslay.global_config.DisplayConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InputHelper {

    // private static final Logger logger = LogManager.getLogger(InputHelper.class.getName());

    private static ScrollInputProcessor processor;

    public static void initialize() {

        processor = new ScrollInputProcessor();
        Gdx.input.setInputProcessor(processor);
        // logger.info("Setting input processor to Scroller");
        // InputActionSet.load();

    }

    private static boolean isMouseDown = false;
    private static boolean isMouseDown_R = false;
    private static boolean isPrevMouseDown = false;
    private static boolean isPrevMouseDown_R = false;

    public static boolean justClickedLeft = false;
    public static boolean justClickedRight = true;
    public static boolean justReleasedClickLeft = false;
    public static boolean justReleasedClickRight = false;

    public static int mX;
    public static int mY;

    public static void updateFirst() {

        mX = Gdx.input.getX();
        if (mX > DisplayConfig.WIDTH) {
            mX = (int) DisplayConfig.WIDTH;
        } else if (mX < 0) {
            mX = 0;
        }

        mY = (int) DisplayConfig.HEIGHT - Gdx.input.getY();
        if (mY > DisplayConfig.HEIGHT) {
            mY = (int) DisplayConfig.HEIGHT;
        } else if (mY < 0) {
            mY = 0;
        }

        isMouseDown = Gdx.input.isButtonPressed(0);
        isMouseDown_R = Gdx.input.isButtonPressed(1);

        if ((!isPrevMouseDown && isMouseDown)) {
            justClickedLeft = true;
        } else if ((isPrevMouseDown && !isMouseDown)) {
            justReleasedClickLeft = true;
        }

        if ((!isPrevMouseDown_R && isMouseDown_R)) {
            justClickedRight = true;
        } else if ((isPrevMouseDown_R && !isMouseDown_R)) {
            justReleasedClickRight = true;
        }

        // pressedEscape =

        isPrevMouseDown_R = isMouseDown_R;
        isPrevMouseDown = isMouseDown;

    }

    public static void updateLast() {
        justClickedLeft = false;
        justClickedRight = false;
        justReleasedClickLeft = false;
        justReleasedClickRight = false;

    }


}

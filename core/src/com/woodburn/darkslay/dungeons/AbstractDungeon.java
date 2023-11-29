package com.woodburn.darkslay.dungeons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.woodburn.darkslay.ui.top_panel.TopPanel;

/******************************************************************************
 * @author Ziyi Chen
 * @since Jun 1 2023
 * 
 * @doc
 * The abstract parent class of various game scene (in fact, levels, or stages,
 * chapters, whatever you like).
 * Imagine dessert, forest, cave...dungeons are different stage with its theme
 * in this game.
 * However, in current dev stage, Exordium is not only a stage, but also the 
 * controller that handles the data logic of turn-alternation.
 * 
 * For example: 
 * 1. At the end of a turn, a player discards its hand.
 * 2. At the start of a turn, a creature loses all of its block. 
 * 
 * class Dungeon calls methods of its fields to change the status of objects of
 * card, player, etc. 
 * 
 * Therefore, AbstractDungeon, as an abstract class, serves as the Subject of
 * Observer pattern. It's abstract, so AbstractDungeon.onStagePlayer can be 
 * accessed from anywhere in the program. 
 * 
 * Similarly we have creature_list, the list that hold all the creatures in
 * current game.
 ******************************************************************************/
public class AbstractDungeon {
    public static Object onStagePlayer;
    public static Object actionManager;


    public Object getCurrMapNode() {
        return null;
    }

    TopPanel topPanel;
    boolean isScreenUp;

    public void update() {
        topPanel.update();
        updateFading();

        if (isScreenUp) {

        } else {

        }



    }


    public void render(SpriteBatch sb) {
        
    }


    public void updateFading() {

    }

    private static Color fadeColor;
    private static float fadeTimer = 0.0F;
    static boolean isFadingIn = false;

    public static void fadeIn() {
        if (fadeColor.a != 1.0F) {
            /* throw exception */
        }
        isFadingIn = true;
        fadeTimer = 0.1F;
    }

    public static void fadeOut() {
        if (fadeTimer == 0.0F) {

        }
    }

    public static boolean areEnemiesBasicallyDead() {
        // TODO
        return false;
    }

    protected static void generateMap() {
        // long startTime = System.currentTimeMillis();

        // ArrayList<AbstractRoom> roomList = new ArrayList<>();
        
    }

}

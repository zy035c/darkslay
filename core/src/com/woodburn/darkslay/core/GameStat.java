package com.woodburn.darkslay.core;


/**
 * It's not put into use now.
 */
public class GameStat {
    


    public enum PlayMode {
        PLAY_NORMAL, INFINITE, PLACEHOLDER;

    }


    public static boolean isUnlocked(PlayMode m) {
        if (m == PlayMode.PLAY_NORMAL || m == PlayMode.INFINITE) {
            return true;
        } else {
            return false;
        }
    }


}

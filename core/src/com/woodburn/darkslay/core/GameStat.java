package com.woodburn.darkslay.core;

import com.woodburn.darkslay.creatures.AbstractPlayer.PlayerClass;
import com.woodburn.darkslay.creatures.characters.Character;

/**
 * Global tracker of game progress, like saveData, Playerclass unlocking
 */
public class GameStat {


    public static Character character;

    public enum PlayMode {
        PLAY_NORMAL, INFINITE, PLACEHOLDER;

    }

    /**
     * For game mode. Dead imple now. Placeholder locked.
     * @param m
     * @return
     */
    public static boolean isUnlocked(PlayMode m) {
        if (m == PlayMode.PLAY_NORMAL || m == PlayMode.INFINITE) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * For player class. Dead imple now. Seeker locked.
     */
    public static boolean isUnlocked(PlayerClass c) {
        if (c == PlayerClass.Seeker) {
            return false;
        } else {
            return true;
        }
    }

    public class PlayerInfo {
        String name;
        int UID;

        PlayMode playMode;

        PlayerClass playerClass;

        public PlayerInfo() {

        }

    }


    public static boolean inGame = false;
    /**
     * ------------
     */
    public static void gameInit() {

        if (inGame) {
            System.out.println("[WANRING] GameStat.gameInit tries to start game but the game is already started.");
            return;
        }
        inGame = true;

    }

}
